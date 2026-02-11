// frontend/js/report.js
document.addEventListener("DOMContentLoaded", () => {
  requireLogin();

  const fromEl = document.getElementById("fromDate");
  const toEl = document.getElementById("toDate");

  const revenueBtn = document.getElementById("revenueBtn");
  const countBtn = document.getElementById("countBtn");
  const roomTypeBtn = document.getElementById("roomTypeBtn");

  const reportOutput = document.getElementById("reportOutput");
  const roomTypeOutput = document.getElementById("roomTypeOutput");

  function readDates() {
    const from = fromEl.value;
    const to = toEl.value;

    if (!from || !to) throw new Error("Please select both From and To dates.");
    if (to < from) throw new Error("To date must be after From date.");
    return { from, to };
  }

  revenueBtn.addEventListener("click", async () => {
    try {
      const { from, to } = readDates();
      const data = await apiFetch(`/reports/revenue?from=${from}&to=${to}`);

      reportOutput.innerHTML = `
        <div class="kpi">
          <div class="item"><b>From</b><span>${data.fromDate}</span></div>
          <div class="item"><b>To</b><span>${data.toDate}</span></div>
          <div class="item"><b>Total Revenue</b><span>${data.totalRevenue}</span></div>
        </div>
      `;
      showToast("Revenue report generated.", "ok");
    } catch (err) {
      reportOutput.innerHTML = "";
      showToast(err.message || "Failed to generate revenue report");
    }
  });

  countBtn.addEventListener("click", async () => {
    try {
      const { from, to } = readDates();
      const count = await apiFetch(`/reports/reservation-count?from=${from}&to=${to}`);

      reportOutput.innerHTML = `
        <div class="kpi">
          <div class="item"><b>Date Range</b><span>${from} → ${to}</span></div>
          <div class="item"><b>Reservation Count</b><span>${count}</span></div>
        </div>
      `;
      showToast("Reservation count loaded.", "ok");
    } catch (err) {
      reportOutput.innerHTML = "";
      showToast(err.message || "Failed to load reservation count");
    }
  });

  roomTypeBtn.addEventListener("click", async () => {
    try {
      const rows = await apiFetch(`/reports/room-types`);

      if (!rows || rows.length === 0) {
        roomTypeOutput.innerHTML = `<p class="small">No reservations yet.</p>`;
        return;
      }

      roomTypeOutput.innerHTML = `
        <table class="table">
          <thead>
            <tr><th>Room Type</th><th>Reservation Count</th></tr>
          </thead>
          <tbody>
            ${rows.map(r => `<tr><td>${r.roomType}</td><td>${r.reservationCount}</td></tr>`).join("")}
          </tbody>
        </table>
      `;
      showToast("Room type report loaded.", "ok");
    } catch (err) {
      roomTypeOutput.innerHTML = "";
      showToast(err.message || "Failed to load room type report");
    }
  });
});
