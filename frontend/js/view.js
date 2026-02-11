// frontend/js/view.js
document.addEventListener("DOMContentLoaded", () => {
  requireLogin();

  const btn = document.getElementById("searchBtn");
  const input = document.getElementById("searchReservationNo");
  const out = document.getElementById("resultArea");

  btn.addEventListener("click", async () => {
    const no = input.value.trim();
    if (!no) { showToast("Enter reservation number."); return; }

    try {
      const r = await apiFetch(`/reservations/${encodeURIComponent(no)}`);
      out.innerHTML = `
        <table class="table">
          <tr><th>Reservation No</th><td>${r.reservationNo}</td></tr>
          <tr><th>Guest Name</th><td>${r.guestName}</td></tr>
          <tr><th>Address</th><td>${r.address}</td></tr>
          <tr><th>Contact</th><td>${r.contact}</td></tr>
          <tr><th>Room Type</th><td>${r.roomType}</td></tr>
          <tr><th>Check-in</th><td>${r.checkIn}</td></tr>
          <tr><th>Check-out</th><td>${r.checkOut}</td></tr>
          <tr><th>Created At</th><td>${r.createdAt}</td></tr>
        </table>
      `;
    } catch (err) {
      out.innerHTML = "";
      showToast(err.message || "Reservation not found");
    }
  });
});
