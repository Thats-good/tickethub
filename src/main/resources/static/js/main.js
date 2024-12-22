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
document.getElementById("payment-form").addEventListener("submit", (event) => {
    event.preventDefault();

    const selectedMethod = document.getElementById("payment-method").value;
    const eventName = sessionStorage.getItem("selectedEvent");
    const seats = JSON.parse(sessionStorage.getItem("selectedSeats"));

    alert(`Payment successful for ${eventName} using ${selectedMethod}. Seats: ${seats.join(", ")}`);
    sessionStorage.clear();
    window.location.reload(); // 초기화
});
