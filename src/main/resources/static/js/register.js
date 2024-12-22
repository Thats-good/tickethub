document.getElementById("signupForm").addEventListener("submit", async (event) => {
    event.preventDefault(); // 폼 기본 동작 막기

    const id = document.getElementById("id").value;
    const password = document.getElementById("pwd").value;
    const email = document.getElementById("email").value;
    const username = document.getElementById("name").value;


    try {
        // RESTful API로 회원가입 요청 보내기
        const response = await fetch("/user/signup", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                id: id,
                pw: password,
                name: username,
                email: email,
                role: "NORMAL",
            }),
        });

        if (response.ok) {
            const data = await response.json();
            console.log("Signup successful:", data);


            window.location.href = "/view/login";
        } else {
            const error = await response.json();
            console.error("Signup failed:", error);
        }
    } catch (error) {
        console.error("error:", error);
    }
});
