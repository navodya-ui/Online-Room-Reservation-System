// frontend/js/bill.js
document.addEventListener("DOMContentLoaded", () => {
  requireLogin();

  const btn = document.getElementById("billBtn");
  const input = document.getElementById("billReservationNo");
  const out = document.getElementById("billArea");

  btn.addEventListener("click", async () => {
    const no = input.value.trim();
    if (!no) { showToast("Enter reservation number."); return; }

    try {
      const b = await apiFetch(`/billing/${encodeURIComponent(no)}`);
      out.innerHTML = `
        <div class="kpi">
          <div class="item"><b>Reservation No</b><span>${b.reservationNo}</span></div>
          <div class="item"><b>Room Type</b><span>${b.roomType}</span></div>
          <div class="item"><b>Nights</b><span>${b.nights}</span></div>
          <div class="item"><b>Rate Per Night</b><span>${b.ratePerNight}</span></div>
          <div class="item"><b>Total Amount</b><span>${b.totalAmount}</span></div>
          <div class="item"><b>Generated At</b><span>${b.generatedAt}</span></div>
        </div>
      `;
    } catch (err) {
      out.innerHTML = "";
      showToast(err.message || "Failed to generate bill");
    }
  });

  // print button
  const printBtn = document.getElementById("printBtn");
  if (printBtn) {
    printBtn.addEventListener("click", () => {
      if (!out.innerHTML.trim()) { showToast("Generate a bill first."); return; }
      window.print();
    });
  }
});
