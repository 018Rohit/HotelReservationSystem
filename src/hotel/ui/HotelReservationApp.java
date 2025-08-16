package hotel.ui;

import hotel.model.Room;
import hotel.model.Booking;
import hotel.service.RoomService;
import hotel.service.BookingService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class HotelReservationApp extends JFrame {
    private RoomService roomService;
    private BookingService bookingService;

    private JTextArea outputArea;

    public HotelReservationApp() {
        roomService = new RoomService();
        bookingService = new BookingService();

        setTitle("Hotel Reservation System");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Book Room", createBookPanel());
        tabs.add("Manage Bookings", createManagePanel());
        tabs.add("Add Room", createAddRoomPanel());
        tabs.add("Availability", createAvailabilityPanel());

        add(tabs);
    }

    private JPanel createBookPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextField nameField = new JTextField();
        JTextField roomNumberField = new JTextField();
        JButton bookBtn = new JButton("Book with Fake Payment");

        bookBtn.addActionListener((ActionEvent e) -> {
            try {
                int roomNo = Integer.parseInt(roomNumberField.getText());
                Room room = roomService.getRooms().stream()
                        .filter(r -> r.getRoomNumber() == roomNo && !r.isBooked())
                        .findFirst().orElse(null);
                if (room == null) {
                    JOptionPane.showMessageDialog(this, "Room not available!");
                    return;
                }
                room.setBooked(true);
                roomService.updateRooms();

                Booking booking = new Booking(roomNo, nameField.getText(), "Paid");
                bookingService.addBooking(booking);

                JOptionPane.showMessageDialog(this, "Booking Successful!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid Input!");
            }
        });

        panel.add(new JLabel("Customer Name:"), BorderLayout.NORTH);
        panel.add(nameField, BorderLayout.CENTER);
        JPanel south = new JPanel(new GridLayout(2, 1));
        south.add(new JLabel("Room Number:"));
        south.add(roomNumberField);
        panel.add(south, BorderLayout.SOUTH);
        panel.add(bookBtn, BorderLayout.EAST);

        return panel;
    }

    private JPanel createManagePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        outputArea = new JTextArea();
        JButton refreshBtn = new JButton("Show All Bookings");
        JButton cancelBtn = new JButton("Cancel Booking");

        refreshBtn.addActionListener(e -> {
            outputArea.setText("");
            for (Booking b : bookingService.getBookings()) {
                outputArea.append(b.toString() + "\n");
            }
        });

        cancelBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter Customer Name to Cancel:");
            Booking booking = bookingService.getBookings().stream()
                    .filter(b -> b.getCustomerName().equalsIgnoreCase(name))
                    .findFirst().orElse(null);

            if (booking != null) {
                bookingService.removeBooking(booking);
                roomService.getRooms().stream()
                        .filter(r -> r.getRoomNumber() == booking.getRoomNumber())
                        .forEach(r -> r.setBooked(false));
                roomService.updateRooms();
                JOptionPane.showMessageDialog(this, "Booking Cancelled!");
            } else {
                JOptionPane.showMessageDialog(this, "Booking not found!");
            }
        });

        JPanel top = new JPanel();
        top.add(refreshBtn);
        top.add(cancelBtn);

        panel.add(top, BorderLayout.NORTH);
        panel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createAddRoomPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JTextField numberField = new JTextField();
        JTextField categoryField = new JTextField();
        JButton addBtn = new JButton("Add Room");

        addBtn.addActionListener(e -> {
            try {
                int number = Integer.parseInt(numberField.getText());
                String category = categoryField.getText();
                roomService.addRoom(new Room(number, category));
                JOptionPane.showMessageDialog(this, "Room Added!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid Input!");
            }
        });

        panel.add(new JLabel("Room Number:"));
        panel.add(numberField);
        panel.add(new JLabel("Category:"));
        panel.add(categoryField);
        panel.add(addBtn);

        return panel;
    }

    private JPanel createAvailabilityPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea availabilityArea = new JTextArea();
        JButton showBtn = new JButton("Show Available Rooms");

        showBtn.addActionListener(e -> {
            availabilityArea.setText("");
            for (Room r : roomService.getRooms()) {
                if (!r.isBooked()) {
                    availabilityArea.append(r.toString() + "\n");
                }
            }
        });

        panel.add(showBtn, BorderLayout.NORTH);
        panel.add(new JScrollPane(availabilityArea), BorderLayout.CENTER);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HotelReservationApp().setVisible(true);
        });
    }
}
