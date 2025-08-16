package hotel.service;

import hotel.model.Booking;
import hotel.util.FileStorage;
import java.util.ArrayList;

public class BookingService {
    private final String BOOKING_FILE = "data/bookings.dat";
    private ArrayList<Booking> bookings;

    public BookingService() {
        bookings = FileStorage.loadData(BOOKING_FILE);
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
        FileStorage.saveData(BOOKING_FILE, bookings);
    }

    public void removeBooking(Booking booking) {
        bookings.remove(booking);
        FileStorage.saveData(BOOKING_FILE, bookings);
    }
}
