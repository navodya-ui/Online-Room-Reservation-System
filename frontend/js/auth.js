// frontend/js/auth.js
document.addEventListener("DOMContentLoaded", () => {
  const loginForm = document.getElementById("loginForm");
  const logoutBtn = document.getElementById("logoutBtn");

  if (loginForm) {
    loginForm.addEventListener("submit", async (e) => {
      e.preventDefault();

      const username = document.getElementById("username").value.trim();
      const password = document.getElementById("password").value.trim();

      if (!username || !password) {
        showToast("Please enter username and password.");
        return;
      }

      try {
        const data = await apiFetch("/auth/login", {
          method: "POST",
          body: { username, password },
          auth: false
        });

        setSession(data.token, data.username, data.role);
        showToast("Login successful!", "ok");
        setTimeout(() => window.location.href = "reservation.html", 400);
      } catch (err) {
        showToast(err.message || "Login failed");
      }
    });
  }

  if (logoutBtn) {
    logoutBtn.addEventListener("click", async () => {
      try {
        await apiFetch("/auth/logout", { method: "POST" });
      } catch (_) {}
      clearSession();
      window.location.href = "login.html";
    });
  }
});
