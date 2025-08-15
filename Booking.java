public class Booking {
    private int roomNumber;
    private String guestName;
    private String date;

    public Booking(int roomNumber, String guestName, String date) {
        this.roomNumber = roomNumber;
        this.guestName = guestName;
        this.date = date;
    }

    public int getRoomNumber() { return roomNumber; }
    public String getGuestName() { return guestName; }
    public String getDate() { return date; }

    @Override
    public String toString() {
        return "Room " + roomNumber + " booked by " + guestName + " on " + date;
    }
}
