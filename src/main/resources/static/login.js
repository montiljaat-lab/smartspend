document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("loginForm");
    const emailInput = document.getElementById("email");
    const passwordInput = document.getElementById("password");
    const msg = document.getElementById("loginMsg");

    function clearMessage() {
        msg.textContent = "";
        msg.classList.remove("error", "success");
    }

    function showMessage(text, type) {
        msg.textContent = text;
        msg.classList.remove("error", "success");
        if (type) msg.classList.add(type); // "error" / "success"
    }

    form.addEventListener("submit", async (e) => {
        e.preventDefault();
        clearMessage();

        const email = emailInput.value.trim();
        const password = passwordInput.value;

        if (!email || !password) {
            showMessage("Please enter both email and password.", "error");
            return;
        }

        try {
            // form-urlencoded body (Spring ke saath friendly)
            const body = new URLSearchParams();
            body.append("email", email);
            body.append("password", password);

            const resp = await fetch("/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: body.toString()
            });

            if (!resp.ok) {
                const txt = (await resp.text()).trim();
                if (resp.status === 401) {
                    showMessage("Invalid email or password. Please register first.", "error");
                } else {
                    showMessage(txt || "Login failed. Try again.", "error");
                }
                return;
            }

            const token = (await resp.text()).trim();
            if (!token) {
                showMessage("Login succeeded but token missing. Contact developer.", "error");
                return;
            }

            localStorage.setItem("token", token);
            localStorage.setItem("loggedInEmail", email);

            showMessage("Login successful! Redirecting…", "success");

            setTimeout(() => {
                window.location.href = "dashboard2.html";
            }, 600);

        } catch (err) {
            console.error(err);
            showMessage("Something went wrong while logging in. Check server.", "error");
        }
    });
});
