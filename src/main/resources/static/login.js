const API_BASE = "https://smartspend-production-d76a.up.railway.app";

document.getElementById("loginBtn").addEventListener("click", async () => {
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();
    const msg = document.getElementById("loginMessage");

    msg.textContent = "";
    
    if (!email || !password) {
        msg.textContent = "Please fill all fields.";
        msg.style.color = "red";
        return;
    }

    try {
        const res = await fetch(API_BASE + "/api/auth/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, password })
        });

        const data = await res.json();
        
        if (!res.ok) {
            msg.textContent = data.message || "Invalid credentials";
            msg.style.color = "red";
            return;
        }

        // SUCCESS
        localStorage.setItem("token", data.token);
        localStorage.setItem("userEmail", email);

        msg.textContent = "Login successful!";
        msg.style.color = "lightgreen";

        setTimeout(() => {
            window.location.href = "dashboard.html";
        }, 700);

    } catch (err) {
        msg.textContent = "Network error!";
        msg.style.color = "red";
        console.error(err);
    }
});
