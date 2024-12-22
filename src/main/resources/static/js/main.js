// 공연 선택
document.querySelectorAll(".select-event").forEach((button) => {
    button.addEventListener("click", (event) => {
        const card = event.target.closest(".card");
        const eventName = card.dataset.event;
        const price = card.dataset.price;

        // 선택된 이벤트 저장
        sessionStorage.setItem("selectedEvent", eventName);
        sessionStorage.setItem("eventPrice", price);

        // 좌석 선택 섹션으로 이동
        document.getElementById("events-section").style.display = "none";
        document.getElementById("seating-section").style.display = "block";

        // 좌석 배치 생성
        generateSeatGrid();
    });
});

// 좌석 선택
function generateSeatGrid() {
    const seatGrid = document.getElementById("seat-grid");
    seatGrid.innerHTML = ""; // 기존 좌석 초기화
    for (let i = 1; i <= 25; i++) {
        const seat = document.createElement("div");
        seat.className = "seat";
        seat.textContent = i;
        seat.addEventListener("click", () => {
            seat.classList.toggle("selected");
        });
        seatGrid.appendChild(seat);
    }
}

// 결제 섹션으로 이동
document.getElementById("proceed-to-payment").addEventListener("click", () => {
    const selectedSeats = document.querySelectorAll(".seat.selected");
    if (selectedSeats.length === 0) {
        alert("Please select at least one seat.");
        return;
    }

    const seats = Array.from(selectedSeats).map((seat) => seat.textContent);
    sessionStorage.setItem("selectedSeats", JSON.stringify(seats));

    document.getElementById("seating-section").style.display = "none";
    document.getElementById("payment-section").style.display = "block";
});

// 결제 방법 변경
document.getElementById("payment-method").addEventListener("change", (event) => {
    const selectedMethod = event.target.value;

    // 모든 결제 폼 숨기기
    document.querySelectorAll(".payment-method-form").forEach((form) => {
        form.style.display = "none";
    });

    // 선택된 결제 폼 보이기
    const selectedForm = document.getElementById(`${selectedMethod}-payment`);
    if (selectedForm) {
        selectedForm.style.display = "block";
    }
});

// 결제 처리
document.getElementById("payment-form").addEventListener("submit", async (event) => {
    event.preventDefault(); // 기본 폼 제출 동작 중단

    const selectedMethod = document.getElementById("payment-method").value;
    const eventName = sessionStorage.getItem("selectedEvent");
    const seats = JSON.parse(sessionStorage.getItem("selectedSeats"));
    const accessToken = localStorage.getItem('accessToken');

    if(accessToken==null){
        alert("login first")
        window.location.href = "/view/login";
    }
    // 결제 정보 가져오기
    let paymentDetails;
    if (selectedMethod === "CARD") {
        paymentDetails = {
            cardName: document.getElementById("card-name").value,
            cardNumber: document.getElementById("card-number").value,
            expiry: document.getElementById("expiry").value,
            cvv: document.getElementById("cvv").value,
        };
    } else if (selectedMethod === "KAKAO_PAY") {
        paymentDetails = {
            quickId: document.getElementById("quick-id").value,
        };
    } else if (selectedMethod === "CASH") {
        paymentDetails = {
            bankName: document.getElementById("bank-name").value,
            accountNumber: document.getElementById("account-number").value,
        };
    }

    // 서버로 보낼 데이터
    const payload = {
        performanceId: eventName,
        seatNumber: seats,
        payment: selectedMethod,
        jwtToken: accessToken,
    };

    try {
        // 서버로 데이터 전송
        const response = await fetch("/ticket/create", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(payload),
        });

        if (!response.ok) {
            throw new Error("Payment failed. Please try again.");
        }

        const result = await response.json();
        alert(`Payment successful for ${eventName} using ${selectedMethod}. Seats: ${seats.join(", ")}`);

        // 세션 초기화 및 페이지 새로고침
        sessionStorage.clear();
        window.location.reload();
    } catch (error) {
        console.error("Error submitting payment:", error);
        alert("An error occurred while processing the payment. Please try again.");
    }
});