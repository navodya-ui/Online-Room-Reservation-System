// frontend/js/api.js
const BASE_URL = "http://localhost:8080";

function getToken() {
  return sessionStorage.getItem("token");
}

function setSession(token, username, role) {
  sessionStorage.setItem("loggedIn", "true");
  sessionStorage.setItem("token", token);
  sessionStorage.setItem("username", username);
  sessionStorage.setItem("role", role);
}

function clearSession() {
  sessionStorage.removeItem("loggedIn");
  sessionStorage.removeItem("token");
  sessionStorage.removeItem("username");
  sessionStorage.removeItem("role");
}

function requireLogin() {
  const ok = sessionStorage.getItem("loggedIn") === "true" && !!getToken();
  if (!ok) window.location.href = "login.html";
}

async function apiFetch(path, { method = "GET", body = null, auth = true } = {}) {
  const headers = { "Content-Type": "application/json" };

  if (auth) {
    const token = getToken();
    if (token) headers["X-SESSION-TOKEN"] = token;
  }

  const res = await fetch(`${BASE_URL}${path}`, {
    method,
    headers,
    body: body ? JSON.stringify(body) : null,
  });

  let data = null;
  try {
    data = await res.json();
  } catch (_) {}

  if (!res.ok) {
    const msg = data?.message || data?.error || `Request failed (${res.status})`;
    throw new Error(msg);
  }

  return data;
}

/**
 * Toast now shows INSIDE the page (under the form / card).
 * It looks for:
 * 1) #toastInline (preferred)
 * 2) #toast
 * If none found, fallback alert.
 */
function showToast(msg, type = "error") {
  const el = document.getElementById("toastInline") || document.getElementById("toast");
  if (!el) return alert(msg);

  el.className = `toast-inline ${type}`;
  el.textContent = msg;
  el.style.display = "block";

  // auto-hide
  setTimeout(() => {
    el.style.display = "none";
  }, 2800);
}
