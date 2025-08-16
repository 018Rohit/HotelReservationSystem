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

        setTitle("🏨 Hotel Reservation System");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 🎨 Apply modern look
        UIManager.put("TabbedPane.selected", new Color(52, 152, 219));
        UIManager.put("TabbedPane.contentAreaColor", Color.WHITE);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 16));
        tabs.add("🛎 Book Room", createBookPanel());
        tabs.add("📋 Manage Bookings", createManagePanel());
        tabs.add("➕ Add Room", createAddRoomPanel());
        tabs.add("✅ Availability", createAvailabilityPanel());

        add(tabs);
    }

    // 🔹 Modern Button Factory
    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        return btn;
    }

    private JPanel createBookPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 247, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Customer Name:");
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField nameField = new JTextField(15);

        JLabel roomLabel = new JLabel("Room Number:");
        roomLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField roomNumberField = new JTextField(15);

        JButton bookBtn = createButton("Book with Fake Payment", new Color(46, 204, 113));

        bookBtn.addActionListener((ActionEvent e) -> {
            try {
                int roomNo = Integer.parseInt(roomNumberField.getText());
                Room room = roomService.getRooms().stream()
                        .filter(r -> r.getRoomNumber() == roomNo && !r.isBooked())
                        .findFirst().orElse(null);
                if (room == null) {
                    JOptionPane.showMessageDialog(this, "❌ Room not available!");
                    return;
                }
                room.setBooked(true);
                roomService.updateRooms();

                Booking booking = new Booking(roomNo, nameField.getText(), "Paid");
                bookingService.addBooking(booking);

                JOptionPane.showMessageDialog(this, "✅ Booking Successful!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "⚠ Invalid Input!");
            }
        });

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(nameLabel, gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(roomLabel, gbc);
        gbc.gridx = 1;
        panel.add(roomNumberField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(bookBtn, gbc);

        return panel;
    }

    private JPanel createManagePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        outputArea = new JTextArea();
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        outputArea.setEditable(false);

        JButton refreshBtn = createButton("🔄 Show All Bookings", new Color(52, 152, 219));
        JButton cancelBtn = createButton("❌ Cancel Booking", new Color(231, 76, 60));

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
                JOptionPane.showMessageDialog(this, "✅ Booking Cancelled!");
            } else {
                JOptionPane.showMessageDialog(this, "❌ Booking not found!");
            }
        });

        JPanel top = new JPanel();
        top.setBackground(new Color(245, 247, 250));
        top.add(refreshBtn);
        top.add(cancelBtn);

        panel.add(top, BorderLayout.NORTH);
        panel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createAddRoomPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 247, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel numberLabel = new JLabel("Room Number:");
        numberLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField numberField = new JTextField(15);

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField categoryField = new JTextField(15);

        JButton addBtn = createButton("➕ Add Room", new Color(155, 89, 182));

        addBtn.addActionListener(e -> {
            try {
                int number = Integer.parseInt(numberField.getText());
                String category = categoryField.getText();
                roomService.addRoom(new Room(number, category));
                JOptionPane.showMessageDialog(this, "✅ Room Added!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "⚠ Invalid Input!");
            }
        });

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(numberLabel, gbc);
        gbc.gridx = 1;
        panel.add(numberField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(categoryLabel, gbc);
        gbc.gridx = 1;
        panel.add(categoryField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(addBtn, gbc);

        return panel;
    }

    private JPanel createAvailabilityPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JTextArea availabilityArea = new JTextArea();
        availabilityArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        availabilityArea.setEditable(false);

        JButton showBtn = createButton("✅ Show Available Rooms", new Color(41, 128, 185));

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
