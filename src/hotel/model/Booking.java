package hotel.model;

import java.io.Serializable;

public class Booking implements Serializable {
    private int roomNumber;
    private String customerName;
    private String paymentStatus;

    public Booking(int roomNumber, String customerName, String paymentStatus) {
        this.roomNumber = roomNumber;
        this.customerName = customerName;
        this.paymentStatus = paymentStatus;
    }

    public int getRoomNumber() { return roomNumber; }
    public String getCustomerName() { return customerName; }
    public String getPaymentStatus() { return paymentStatus; }

    @Override
    public String toString() {
        return "Booking[Room=" + roomNumber +
                ", Name=" + customerName +
                ", Payment=" + paymentStatus + "]";
    }
}
