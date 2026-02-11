// frontend/js/reservation.js
document.addEventListener("DOMContentLoaded", () => {
  requireLogin();

  const form = document.getElementById("reservationForm");
  const userLabel = document.getElementById("userLabel");
  if (userLabel) userLabel.textContent = `${sessionStorage.getItem("username")} (${sessionStorage.getItem("role")})`;

  form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const payload = {
      reservationNo: document.getElementById("reservationNo").value.trim(),
      guestName: document.getElementById("guestName").value.trim(),
      address: document.getElementById("address").value.trim(),
      contact: document.getElementById("contact").value.trim(),
      roomType: document.getElementById("roomType").value.trim(),
      checkIn: document.getElementById("checkIn").value,
      checkOut: document.getElementById("checkOut").value,
    };

    // simple frontend validation
    if (!payload.reservationNo || !payload.guestName || !payload.address || !payload.contact || !payload.roomType) {
      showToast("Please fill all required fields.");
      return;
    }
    if (!payload.checkIn || !payload.checkOut) {
      showToast("Please select check-in and check-out dates.");
      return;
    }
    if (payload.checkOut <= payload.checkIn) {
      showToast("Check-out must be after check-in.");
      return;
    }

    try {
      const data = await apiFetch("/reservations", { method: "POST", body: payload });
      showToast(`Reservation created: ${data.reservationNo}`, "ok");
      form.reset();
    } catch (err) {
      showToast(err.message || "Failed to create reservation");
    }
  });
});
