import java.util.Scanner;

public class Payment {
    public static boolean simulatePayment(double amount) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Payment of ₹" + amount + " required. Proceed? (yes/no): ");
        String input = sc.nextLine();
        return input.equalsIgnoreCase("yes");
    }
}
