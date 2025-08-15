import java.util.*;

public class ReservationSystem {
    private List<Room> rooms;
    private List<Booking> bookings;
    private static final String ROOMS_FILE = "rooms.txt";
    private static final String BOOKINGS_FILE = "bookings.txt";

    public ReservationSystem() {
        rooms = FileHandler.readRooms(ROOMS_FILE);
        bookings = FileHandler.readBookings(BOOKINGS_FILE);

        if (rooms.isEmpty()) {
            rooms.add(new Room(101, "Single", false));
            rooms.add(new Room(102, "Double", false));
            rooms.add(new Room(103, "Suite", false));
            FileHandler.saveRooms(ROOMS_FILE, rooms);
        }
    }

    public void bookRoom(int roomNumber, String guestName, String date) {
        for (Room r : rooms) {
            if (r.getRoomNumber() == roomNumber && !r.isBooked()) {
                r.setBooked(true);
                bookings.add(new Booking(roomNumber, guestName, date));
                FileHandler.saveRooms(ROOMS_FILE, rooms);
                FileHandler.saveBookings(BOOKINGS_FILE, bookings);
                System.out.println("Booking confirmed for " + guestName);
                return;
            }
        }
        System.out.println("Room not available!");
    }

    public void cancelBooking(int roomNumber, String guestName) {
        Booking toRemove = null;
        for (Booking b : bookings) {
            if (b.getRoomNumber() == roomNumber && b.getGuestName().equalsIgnoreCase(guestName)) {
                toRemove = b;
                break;
            }
        }
        if (toRemove != null) {
            bookings.remove(toRemove);
            for (Room r : rooms) {
                if (r.getRoomNumber() == roomNumber) {
                    r.setBooked(false);
                    break;
                }
            }
            FileHandler.saveRooms(ROOMS_FILE, rooms);
            FileHandler.saveBookings(BOOKINGS_FILE, bookings);
            System.out.println("Booking canceled.");
        } else {
            System.out.println("Booking not found.");
        }
    }

    public void showRooms() {
        for (Room r : rooms) {
            System.out.println(r);
        }
    }

    public void showBookings() {
        for (Booking b : bookings) {
            System.out.println(b);
        }
    }
}
