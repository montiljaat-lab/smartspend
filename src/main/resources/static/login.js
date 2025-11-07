/* login.js - robust login + token storage */
document.addEventListener("DOMContentLoaded", () => {
  const form = document.querySelector("form#loginForm") || document.querySelector("form");
  async function doLogin(e) {
    if(e) e.preventDefault();
    const emailEl = document.getElementById("email") || document.querySelector('input[type="email"]');
    const pwEl = document.getElementById("password") || document.querySelector('input[type="password"]');
    const email = emailEl?.value?.trim() || "";
    const password = pwEl?.value || "";

    if (!email || !password) {
      alert("Please enter email and password");
      return;
    }
    try {
      const res = await fetch("/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password }),
        credentials: "same-origin"
      });
      let body = null;
      try { body = await res.json(); } catch (err) { /* maybe response is plain text */ }

      if (!res.ok) {
        const msg = (body && (body.message||body.error||JSON.stringify(body))) || "Login failed";
        alert("Failed: " + msg);
        return;
      }

      // Accept either {"token":"..."} OR raw string token OR any truthy string
      let token = null;
      if (body) {
        if (typeof body === "string") token = body;
        else token = body.token || body.authToken || body.id || null;
      }

      // if no json token, maybe server returned plain text - try text
      if (!token) {
        const text = await res.text().catch(()=>null);
        if (text && text.length > 0) token = text;
      }

      if (!token) {
        alert("Login succeeded but token not found in server response. Check server response in devtools Network tab.");
        return;
      }

      try {
        localStorage.setItem("authToken", token);
      } catch (err) {
        console.warn("localStorage failed, using sessionStorage", err);
        sessionStorage.setItem("authToken", token);
      }

      // final redirect
      window.location = "/dashboard.html";
    } catch (err) {
      console.error("Login error", err);
      alert("Login error. See console for details.");
    }
  }

  if (form) {
    form.addEventListener("submit", doLogin);
  } else {
    // attach to any login button as fallback
    const btn = document.querySelector("button[type='submit'], #loginBtn");
    if (btn) btn.addEventListener("click", doLogin);
  }
});
