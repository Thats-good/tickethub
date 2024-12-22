document.addEventListener("DOMContentLoaded", async () => {
    const tableBody = document.querySelector("#ticket-table tbody");

    const accessToken = localStorage.getItem("accessToken");
    if (accessToken == null) {
        alert("Please login first.");
        window.location.href = "/view/login";
        return;
    }

    try {
        // API 호출
        const response = await fetch("/ticket/checkUserTicket", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                jwtToken: accessToken,
            }),
        });

        if (!response.ok) {
            throw new Error("Failed to fetch ticket data.");
        }

        const responseData = await response.json();
        const tickets = responseData.data; // `data` 배열 접근

        // 데이터 렌더링
        tickets.forEach((ticket) => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${ticket.ticketId}</td>
                <td>${ticket.performance.name}</td>
                <td>${ticket.seatNumber}</td>
                <td>${new Date(ticket.time).toLocaleString()}</td>
                <td>
                    <button class="check-button" data-id="${ticket.ticketId}">Get Ticket</button>
                </td>
                <td class="response-cell"></td> <!-- 응답 내용을 표시할 셀 -->
            `;
            tableBody.appendChild(row);
        });

        // 버튼 클릭 이벤트 리스너 추가
        document.querySelectorAll(".check-button").forEach((button) => {
            button.addEventListener("click", async (event) => {
                const ticketId = event.target.dataset.id; // 버튼의 티켓 ID 가져오기
                const responseCell = event.target.closest("tr").querySelector(".response-cell"); // 같은 행의 응답 셀 가져오기

                try {
                    const ticketResponse = await fetch("/ticket/checkTicket", {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/json",
                        },
                        body: JSON.stringify({
                            jwtToken: accessToken,
                            ticketId: ticketId,
                        }),
                    });

                    if (!ticketResponse.ok) {
                        throw new Error("Failed to get ticket.");
                    }

                    const ticketData = await ticketResponse.json();
                    responseCell.textContent = `realTicket: ${ticketData.data}`;
                } catch (error) {
                    console.error("Error retrieving ticket:", error);
                    responseCell.textContent = "Failed to retrieve ticket.";
                }
            });
        });
    } catch (error) {
        console.error("Error fetching tickets:", error);
        tableBody.innerHTML = `<tr><td colspan="6">No tickets available</td></tr>`;
    }
});
