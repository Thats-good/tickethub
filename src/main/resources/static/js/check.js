document.getElementById("checkForm").addEventListener("submit", async (event) => {
    event.preventDefault();
    const accessToken = localStorage.getItem("accessToken");
    if (accessToken == null) {
        alert("Please login first.");
        window.location.href = "/view/login";
        return;
    }

    const ticketToken = document.getElementById("check").value;
    const ticketId = document.getElementById("ticketId").value;
    ticketId
    try {
        const response = await fetch("/ticket/checkToken", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                ticketId: ticketId,
                token: ticketToken,
                jwtToken: accessToken,
            }),
        });

        if (response.ok) {
            const responseData = await response.json();

            if (responseData.statusCode === "200") {
                alert("token valid, enter");
            }
        } else {
            alert("token invalid or no Auth");
            const error = await response.json();
            console.error("token invalid or no Auth:", error);
        }
    } catch (error) {
        console.error("error:", error);
    }
});
