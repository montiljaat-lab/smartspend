document.getElementById("f").addEventListener("submit", async (e)=>{
  e.preventDefault();
  const msgEl = document.getElementById("msg");
  msgEl.textContent = "Please wait...";
  const payload = {
    name: document.getElementById("name").value.trim(),
    email: document.getElementById("email").value.trim(),
    password: document.getElementById("password").value
  };
  try {
    const res = await fetch("/api/auth/register", {
      method: "POST",
      headers: {"Content-Type":"application/json"},
      body: JSON.stringify(payload)
    });
    if (!res.ok) {
      const text = await res.text().catch(()=>"Server error");
      msgEl.textContent = "Failed: " + (text || res.statusText);
      return;
    }
    const data = await res.json().catch(()=>({message:"Registered"}));
    msgEl.textContent = data.message || "Registered successfully — try login.";
  } catch (err) {
    msgEl.textContent = "Network error — check console.";
    console.error(err);
  }
});

