import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static Connection conn;
    private static final Scanner scanner = new Scanner(System.in);
    private static final String EMAIL_REGEX = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b";
    private static final String PHONE_REGEX = "(?:\\+\\d{3})?\\d{3}\\D?\\d{4}";


    public static void main(String[] args) {
        try {
            // Establish a database connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/marino", "root", "Gags@1312");

            // Start the system
            startSystem();

        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
                if (scanner != null) scanner.close();
            } catch (SQLException e) {
                System.out.println("Error closing the connection: " + e.getMessage());
            }
        }
    }

    private static void startSystem() {
        while (true) {
            System.out.println("\nWelcome to the Fitness Center Management System!");
            System.out.println("1. Login as admin");
            System.out.println("2. Login as staff");
            System.out.println("3. Login as user");
            System.out.println("4. Register as user");
            System.out.println("5. Quit");

            System.out.print("Option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume leftover newline


            switch (option) {
                case 1:
                    authAdmin admin = new authAdmin();
                    admin.authAdmin(); // Admin authentication
                    break;
                case 2:
                    authStaff staff = new authStaff();
                    staff.authStaff();// Staff authentication
                    break;
                case 3:
                    authUser user = new authUser();
                    user.authUser(); // User authentication
                    break;
                case 4:
                    authUser newUser = new authUser();
                    newUser.authNewUser();// Register new user
                    break;
                case 5:
                    System.out.println("Exiting system...");
                    return; // Exit the application
                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }
    }
}