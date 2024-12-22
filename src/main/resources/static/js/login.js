document.getElementById("loginForm").addEventListener("submit", async (event) => {
    event.preventDefault();

    const username = document.getElementById("id").value;
    const password = document.getElementById("pwd").value;

    try {
        const response = await fetch("/user/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                id: username,
                pw: password,
            }),
        });

        if (response.ok) {
            const responseData = await response.json();

            if (responseData.statusCode === "200") {
                const accessToken = responseData.data.accessToken;
                const refreshToken = responseData.data.refreshToken;

                // 로컬 스토리지에 토큰 저장
                localStorage.setItem("accessToken", accessToken);
                localStorage.setItem("refreshToken", refreshToken);

                // 성공 메시지 표시
                console.log("Access token saved:", accessToken);
                console.log("Refresh token saved:", refreshToken);
            }
            alert("login success");
            window.location.href = "/view/main";
        } else {
            const error = await response.json();
            alert("login failed");
            console.error("Login failed:", error);
        }
    } catch (error) {
        console.error("error:", error);
    }
});
