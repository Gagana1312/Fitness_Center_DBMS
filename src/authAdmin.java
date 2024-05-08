import java.sql.*;
import java.util.Scanner;
import java.util.regex.Pattern;

public class authAdmin {
    public static Connection conn = null;
    private static final Scanner scanner = new Scanner(System.in);
    private static final String EMAIL_REGEX = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b";

    private static final String PHONE_REGEX = "(?:\\+\\d{3})?\\d{3}\\D?\\d{4}";

    // Method to establish a database connection
    public static void establishConnection() {
        String dbUrl = "jdbc:mysql://localhost:3306/marino"; // Adjust the URL according to your DB setup
        String user = "root";
        String password = "Gags@1312";

        try {
            conn = DriverManager.getConnection(dbUrl, user, password);
            System.out.println("Database connection established successfully.");
        } catch (SQLException e) {
            System.out.println("Error establishing database connection: " + e.getMessage());
        }
    }

    public static void authAdmin() {
        // Ensure connection is established
        if (conn == null) {
            System.out.println("Database connection not established. Establishing now...");
            establishConnection();
        }

        System.out.println("\nAdmin Login");
        System.out.print("Email ID: ");
        String emailId = scanner.nextLine();

        // Validate the email format
        if (!Pattern.matches(EMAIL_REGEX, emailId)) {
            System.out.println("Invalid email format. Try again.");
            return;
        }

        System.out.print("Password: ");
        String password = scanner.nextLine(); // Get password input

        // SQL query to verify email and password
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM marino.admin WHERE emailid = ? AND password = ?")) {
            stmt.setString(1, emailId);
            stmt.setString(2, password);

            // Execute the query and check for results
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("\nWelcome " + emailId);
                    adminSession(); // Proceed to admin session
                } else {
                    System.out.println("Login not recognized");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error during admin login: " + e.getMessage());
        }
    }
    private static void adminSession() {
        while (true) {
            System.out.println("\nWelcome to Admin Panel");
            System.out.println("1. Register new admin");
            System.out.println("2. Delete existing admin");
            System.out.println("3. Read existing admins");
            System.out.println("4. Update Password existing admin");
            System.out.println("5. Back");

            System.out.print("Option: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    registerNewAdmin();
                    break;
                case "2":
                    deleteExistingAdmin();
                    break;
                case "3":
                    readExistingAdmins();
                    break;
                case "4":
                    updateExistingAdmin();
                    break;
                case "5":
                    return; // Exit the admin session
                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }
    }

    private static void registerNewAdmin() {
        System.out.println("\nRegister New Admin");

        System.out.print("Admin email ID: ");
        String emailId = scanner.nextLine();

        if (!Pattern.matches(EMAIL_REGEX, emailId)) {
            System.out.println("Invalid email format, try again!");
            return;
        }

        System.out.print("Admin password: ");
        String password = scanner.nextLine();


        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO admin VALUES (?,?);")) {
            stmt.setString(1, emailId);
            stmt.setString(2, password);

            stmt.executeUpdate();
            System.out.println(emailId + " has been registered as an admin.");
        } catch (SQLException e) {
            System.out.println("Error registering admin: " + e.getMessage());
        }
    }

    private static void deleteExistingAdmin() {
        System.out.println("\nDelete Existing Admin Account");

        System.out.print("Admin Email ID: ");
        String emailId = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM admin WHERE emailid= ? AND password = ?;")) {
            stmt.setString(1, emailId);
            stmt.setString(2, password);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected < 1) {
                System.out.println("Admin not found.");
            } else {
                System.out.println(emailId + " has been deleted!");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting admin: " + e.getMessage());
        }
    }

    private static void readExistingAdmins() {
        System.out.println("\nAll Existing Admin Details");

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM admin;")) {

            System.out.println(String.format("| %-20s | %-20s |", "Email ID", "Password"));
            System.out.println("--------------------------------------");

            while (rs.next()) {
                System.out.println(String.format("%-20s   %-20s",
                        rs.getString("emailid"),
                        rs.getString("password")));
            }

        } catch (SQLException e) {
            System.out.println("Error reading admin details: " + e.getMessage());
        }
    }

    private static void updateExistingAdmin() {
        System.out.println("\nUpdate Password of an Existing Admin");

        System.out.print("Admin email: ");
        String adminEmail = scanner.nextLine();
        System.out.print("Admin password: ");
        String adminPassword = scanner.nextLine();

        try (PreparedStatement stmt = conn.prepareStatement("UPDATE admin SET password = ? WHERE emailid = ?;")) {
            stmt.setString(1, adminPassword);
            stmt.setString(2, adminEmail);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected < 1) {
                System.out.println("No admin found with the provided ID.");
            } else {
                System.out.println("Updated Successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error updating admin details: " + e.getMessage());
        }
    }


}
