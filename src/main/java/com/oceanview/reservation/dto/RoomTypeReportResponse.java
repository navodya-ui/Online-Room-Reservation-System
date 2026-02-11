package com.oceanview.reservation.dto;

public class RoomTypeReportResponse {

    private String roomType;
    private Long reservationCount;

    public RoomTypeReportResponse() {}

    public RoomTypeReportResponse(String roomType, Long reservationCount) {
        this.roomType = roomType;
        this.reservationCount = reservationCount;
    }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public Long getReservationCount() { return reservationCount; }
    public void setReservationCount(Long reservationCount) { this.reservationCount = reservationCount; }
}
