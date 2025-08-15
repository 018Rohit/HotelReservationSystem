import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ReservationSystem rs = new ReservationSystem();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Hotel Reservation System ===");
            System.out.println("1. Show Rooms");
            System.out.println("2. Show Bookings");
            System.out.println("3. Book Room");
            System.out.println("4. Cancel Booking");
            System.out.println("5. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> rs.showRooms();
                case 2 -> rs.showBookings();
                case 3 -> {
                    System.out.print("Enter room number: ");
                    int rn = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter guest name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter booking date: ");
                    String date = sc.nextLine();
                    rs.bookRoom(rn, name, date);
                }
                case 4 -> {
                    System.out.print("Enter room number: ");
                    int rn = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter guest name: ");
                    String name = sc.nextLine();
                    rs.cancelBooking(rn, name);
                }
                case 5 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}
