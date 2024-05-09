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
                    staff.authStaff();
//                    authStaff(); // Staff authentication
                    break;
                case 3:
                    authUser user = new authUser();
                    user.authUser(); // User authentication
                    break;
                case 4:
                    authUser newUser = new authUser();
                    newUser.authNewUser();
                   // authNewUser(); // Register new user
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

//    private static void authAdmin() {
//        System.out.println("\nAdmin Login");
//
//        System.out.print("Email ID: ");
//        String emailId = scanner.nextLine();
//
//        // Validate the email format
//        if (!Pattern.matches(EMAIL_REGEX, emailId)) {
//            System.out.println("Invalid email format. Try again.");
//            return;
//        }
//
//        System.out.print("Password: ");
//        String password = scanner.nextLine(); // Get password input
//
//        // SQL query to verify email and password
//        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM marino.admin WHERE emailid = ? AND password = ?")) {
//            stmt.setString(1, emailId);
//            stmt.setString(2, password);
//
//            // Execute the query and check for results
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    System.out.println("\nWelcome " + emailId);
//                    adminSession(); // Proceed to admin session
//                } else {
//                    System.out.println("Login not recognized");
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println("Error during admin login: " + e.getMessage());
//        }
//    }


//    private static void authStaff() {
//        System.out.println("\nStaff Login");
//
//        System.out.print("Email ID: ");
//        String emailId = scanner.nextLine();
//
//        if (!Pattern.matches(EMAIL_REGEX, emailId)) {
//            System.out.println("Invalid email format, try again.");
//            return;
//        }
//
//        System.out.print("Password: ");
//        String password = scanner.nextLine();
//
//        // Authenticate against the staff table and get the staff_id
//        try (PreparedStatement stmt = conn.prepareStatement("SELECT idstaff FROM marino.staff WHERE emailid = ? AND password = ?")) {
//            stmt.setString(1, emailId);
//            stmt.setString(2, password);
//
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    int idstaff = rs.getInt("idstaff");
//                    System.out.println("Login successful, Welcome Staff!");
//                    System.out.println("ID: " + idstaff);
//                    staffSession(idstaff);
//                } else {
//                    System.out.println("Invalid credentials. Please try again.");
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println("Error during staff login: " + e.getMessage());
//        }
//    }
//
//    private static void authUser() {
//        System.out.println("\nUser Login");
//
//        System.out.print("Email ID: ");
//        String emailId = scanner.nextLine();
//
//        if (!Pattern.matches(EMAIL_REGEX, emailId)) {
//            System.out.println("Invalid email format, try again.");
//            return;
//        }
//
//        System.out.print("Password: ");
//        String password = scanner.nextLine();
//
//        // Authenticate against the user table and get the user_id
//        try (PreparedStatement stmt = conn.prepareStatement("SELECT userid FROM marino.user WHERE emailid = ? AND password = ?;")) {
//            stmt.setString(1, emailId);
//            stmt.setString(2, password);
//
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    int userId = rs.getInt("userid");
//                    System.out.println("Login successful, Welcome User!");
//                    userSession(userId); // Pass user ID to user session
//                } else {
//                    System.out.println("Invalid credentials. Please try again.");
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println("Error during user login: " + e.getMessage());
//        }
//    }
//
//    private static void authNewUser() {
//        System.out.println("\nNew User Registration");
//
//        System.out.print("User Email ID: ");
//        String emailId = scanner.nextLine();
//
//        if (!Pattern.matches(EMAIL_REGEX, emailId)) {
//            System.out.println("Invalid email format, try again.");
//            return;
//        }
//
//        System.out.print("User Password: ");
//        String password = scanner.nextLine();
//
//        System.out.print("First Name: ");
//        String firstName = scanner.nextLine();
//
//        System.out.print("Last Name: ");
//        String lastName = scanner.nextLine();
//
//        System.out.print("Age: ");
//        int age = Integer.parseInt(scanner.nextLine());
//
//        System.out.print("Phone Number: ");
//        String phoneNumber = scanner.nextLine();
//
//        // Insert the new user into the user table
//        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO marino.user (first_name, last_name, age, phone_number, emailid, password) VALUES (?, ?, ?, ?, ?, ?)")) {
//            stmt.setString(1, firstName);
//            stmt.setString(2, lastName);
//            stmt.setInt(3, age);
//            stmt.setString(4, phoneNumber);
//            stmt.setString(5, emailId);
//            stmt.setString(6, password);
//
//            stmt.executeUpdate();
//            System.out.println(firstName + " has been registered as a User.");
//        } catch (SQLException e) {
//            System.out.println("Error registering user: " + e.getMessage());
//        }
//    }
//
//    private static void userSession(int userId) {
//        while (true) {
//            System.out.println("\nWelcome to User Panel");
//            System.out.println("1. Register new Activity");
//            System.out.println("2. Delete existing activity");
//            System.out.println("3. View your locker");
//            System.out.println("5. View Bill");
//            System.out.println("6. Update User details");
//            System.out.println("7. Logout");
//
//            System.out.print("Option: ");
//            String option = scanner.nextLine();
//
//            switch (option) {
//                case "1":
//                    registerNewActivity(userId);
//                    break;
//                case "2":
//                    deleteExistingActivity(userId);
//                    break;
//                case "3":
//                    viewLocker(userId);
//                    break;
//                case "4":
//                    viewBill(userId);
//                    break;
//                case "5":
//                    updateUserDetails(userId);
//                    break;
//                case "6":
//                    return;
//                default:
//                    System.out.println("Invalid Selection!");
//                    break;
//            }
//        }
//    }
//
//    private static void registerNewActivity(int userId) {
//        System.out.println("\nRegister New Activity");
//
//        // Display all available activities
//        try (Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery("SELECT * FROM activity;")) {
//            System.out.printf("%-10s %-20s %-15s %-10s %-10s%n", "Activity ID", "Name of Activity", "Room No", "Price", "Trainer ID");
//            System.out.println("------------------------------------------------------");
//
//            while (rs.next()) {
//                System.out.printf("%-10s %-20s %-15s %-10s %-10s%n",
//                        rs.getInt("idactivity"),
//                        rs.getString("name"),
//                        rs.getString("room_no"),
//                        rs.getDouble("price"),
//                        rs.getInt("idtrainer"));
//            }
//
//            // Prompt the user to enter an activity ID
//            System.out.print("Enter Activity ID: ");
//            int activityId = Integer.parseInt(scanner.nextLine());
//
//            // Register the activity for the given user
//            try (PreparedStatement stmtReg = conn.prepareStatement("CALL user_activity(?, ?);")) {
//                stmtReg.setInt(1, activityId);
//                stmtReg.setInt(2, userId);
//                stmtReg.executeUpdate();
//                System.out.println("Activity ID " + activityId + " has been registered to you!");
//
//            } catch (SQLException e) {
//                System.out.println("Error registering activity: " + e.getMessage());
//            }
//
//        } catch (SQLException e) {
//            System.out.println("Error retrieving activities: " + e.getMessage());
//        }
//    }
//
//    private static void deleteExistingActivity(int userId) {
//        System.out.println("\nDelete Existing Activity");
//
//        try (PreparedStatement stmt = conn.prepareStatement("SELECT p.idpayment, a.price, a.idactivity, a.name " +
//                "FROM payment AS p JOIN activity AS a ON p.idactivity = a.idactivity " +
//                "JOIN user AS u ON p.userid = u.userid WHERE u.userid = ? GROUP BY p.idpayment;")) {
//            stmt.setInt(1, userId);
//            try (ResultSet rs = stmt.executeQuery()) {
//                System.out.printf("%-10s %-10s %-10s %-20s%n", "Payment ID", "Rate", "Activity ID", "Activity Name");
//                System.out.println("---------------------------------------------------");
//
//                while (rs.next()) {
//                    System.out.printf("%-10s %-10s %-10s %-20s%n",
//                            rs.getInt("idpayment"),
//                            rs.getDouble("price"),
//                            rs.getInt("idactivity"),
//                            rs.getString("name"));
//                }
//
//                System.out.print("Enter Activity ID to delete: ");
//                int activityId = Integer.parseInt(scanner.nextLine());
//
//                try (PreparedStatement stmtDel = conn.prepareStatement("CALL payment_del(?);")) {
//                    stmtDel.setInt(1, activityId);
//                    int rowsAffected = stmtDel.executeUpdate();
//
//                    if (rowsAffected < 1) {
//                        System.out.println("Activity not found.");
//                    } else {
//                        System.out.println("Activity ID " + activityId + " has been deleted!");
//                    }
//                } catch (SQLException e) {
//                    System.out.println("Error deleting activity: " + e.getMessage());
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println("Error retrieving activities: " + e.getMessage());
//        }
//    }
//
//    private static void viewLocker(int userId) {
//        System.out.println("\nView Your Locker");
//
//        // Prepare the SQL statement with the given user ID
//        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM locker WHERE userid = ?;")) {
//            stmt.setInt(1, userId);
//
//            // Execute the query and retrieve the results
//            try (ResultSet rs = stmt.executeQuery()) {
//                // Check if there are any results
//                if (!rs.isBeforeFirst()) {
//                    // No data returned by the query
//                    System.out.println("No Locker found.");
//                    return; // Exit the method early if no results
//                }
//
//                // Display the header for the locker information
//                System.out.printf("%-10s %-20s %-10s %-10s%n", "Locker ID", "Type of Locker", "Staff ID", "User ID");
//                System.out.println("----------------------------------------------------");
//
//                // Iterate through and display the locker details
//                while (rs.next()) {
//                    System.out.printf("%-10s %-20s %-10s %-10s%n",
//                            rs.getInt("idlocker"),
//                            rs.getString("type_of_locker"),
//                            rs.getInt("idstaff"),
//                            rs.getInt("userid"));
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println("Error retrieving locker information: " + e.getMessage());
//        }
//    }
//
//
//    private static void viewBill(int userId) {
//        System.out.println("\nView Bill");
//
//        // Retrieve all payment details for the given user
//        try (PreparedStatement stmt = conn.prepareStatement(
//                "SELECT p.idpayment, a.price, a.idactivity, a.name " +
//                        "FROM payment AS p JOIN activity AS a ON p.idactivity = a.idactivity " +
//                        "JOIN user AS u ON p.userid = u.userid WHERE u.userid = ? GROUP BY p.idpayment;")) {
//            stmt.setInt(1, userId);
//
//            // Execute query to get payment information
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (!rs.isBeforeFirst()) {
//                    // No payments were found for the specified user
//                    System.out.println("No Bill found.");
//                    return; // Exit method early to avoid printing headers
//                }
//
//                // Print header
//                System.out.printf("%-10s %-10s %-10s %-20s%n", "Payment ID", "Rate", "Activity ID", "Activity Name");
//                System.out.println("---------------------------------------------------");
//
//                // Iterate and print all payment details
//                while (rs.next()) {
//                    System.out.printf("%-10s %-10s %-10s %-20s%n",
//                            rs.getInt("idpayment"),
//                            rs.getDouble("price"),
//                            rs.getInt("idactivity"),
//                            rs.getString("name"));
//                }
//
//                // Retrieve the total bill amount
//                try (PreparedStatement stmtTotal = conn.prepareStatement("SELECT totalPayment(?) AS total FROM payment LIMIT 1;")) {
//                    stmtTotal.setInt(1, userId);
//
//                    try (ResultSet rsTotal = stmtTotal.executeQuery()) {
//                        if (rsTotal.next()) {
//                            // Display the total bill amount
//                            System.out.println("Total Bill Amount: " + rsTotal.getDouble("total"));
//                        }
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println("Error retrieving bill information: " + e.getMessage());
//        }
//    }
//
//
//    private static void updateUserDetails(int userId) {
//        System.out.println("\nUpdate Existing User Details");
//
//        System.out.println("User ID: " + userId);
//        System.out.print("Email ID: ");
//        String emailId = scanner.nextLine();
//
//        if (!Pattern.matches(EMAIL_REGEX, emailId)) {
//            System.out.println("Invalid email format, try again!");
//            return;
//        }
//
//        System.out.print("User First Name: ");
//        String firstName = scanner.nextLine();
//        System.out.print("User Last Name: ");
//        String lastName = scanner.nextLine();
//        System.out.print("User Age: ");
//        int age = Integer.parseInt(scanner.nextLine());
//        System.out.print("Phone Number: ");
//        String phoneNumber = scanner.nextLine();
//        System.out.print("Password: ");
//        String password = scanner.nextLine();
//
//        try (PreparedStatement stmt = conn.prepareStatement("UPDATE user SET first_name = ?, last_name = ?, age = ?, phone_number = ?, emailid = ?, password = ? WHERE userid = ?;")) {
//            stmt.setString(1, firstName);
//            stmt.setString(2, lastName);
//            stmt.setInt(3, age);
//            stmt.setString(4, phoneNumber);
//            stmt.setString(5, emailId);
//            stmt.setString(6, password);
//            stmt.setInt(7, userId);
//
//            int rowsAffected = stmt.executeUpdate();
//            if (rowsAffected < 1) {
//                System.out.println("No User found.");
//            } else {
//                System.out.println(firstName + " Updated Successfully!");
//            }
//
//        } catch (SQLException e) {
//            System.out.println("Error updating user details: " + e.getMessage());
//        }
//    }
}