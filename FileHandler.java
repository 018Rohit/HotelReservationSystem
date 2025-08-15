import java.io.*;
import java.util.*;

public class FileHandler {

    public static List<Room> readRooms(String filename) {
        List<Room> rooms = new ArrayList<>();
        File file = new File(filename);

        if (!file.exists()) return rooms;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    int roomNumber = Integer.parseInt(parts[0]);
                    String category = parts[1];
                    boolean isBooked = Boolean.parseBoolean(parts[2]);
                    rooms.add(new Room(roomNumber, category, isBooked));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    public static List<Booking> readBookings(String filename) {
        List<Booking> bookings = new ArrayList<>();
        File file = new File(filename);

        if (!file.exists()) return bookings;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    int roomNumber = Integer.parseInt(parts[0]);
                    String guestName = parts[1];
                    String date = parts[2];
                    bookings.add(new Booking(roomNumber, guestName, date));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public static void saveRooms(String filename, List<Room> rooms) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Room r : rooms) {
                bw.write(r.getRoomNumber() + "," + r.getCategory() + "," + r.isBooked());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveBookings(String filename, List<Booking> bookings) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Booking b : bookings) {
                bw.write(b.getRoomNumber() + "," + b.getGuestName() + "," + b.getDate());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
