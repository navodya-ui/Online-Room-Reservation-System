package com.oceanview.reservation.dto;

import java.math.BigDecimal;

public class RevenueReportResponse {

    private String fromDate;
    private String toDate;
    private BigDecimal totalRevenue;

    public RevenueReportResponse() {}

    public RevenueReportResponse(String fromDate, String toDate, BigDecimal totalRevenue) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.totalRevenue = totalRevenue;
    }

    public String getFromDate() { return fromDate; }
    public void setFromDate(String fromDate) { this.fromDate = fromDate; }

    public String getToDate() { return toDate; }
    public void setToDate(String toDate) { this.toDate = toDate; }

    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }
}
