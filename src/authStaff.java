import java.sql.*;
import java.util.Scanner;
import java.util.regex.Pattern;

public class authStaff {
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
    public static void authStaff() {
        // Ensure connection is established
        if (conn == null) {
            System.out.println("Database connection not established. Establishing now...");
            establishConnection();
        }
        System.out.println("\nStaff Login");

        System.out.print("Email ID: ");
        String emailId = scanner.nextLine();

        if (!Pattern.matches(EMAIL_REGEX, emailId)) {
            System.out.println("Invalid email format, try again.");
            return;
        }

        System.out.print("Password: ");
        String password = scanner.nextLine();

        // Authenticate against the staff table and get the staff_id
        try (PreparedStatement stmt = conn.prepareStatement("SELECT idstaff FROM marino.staff WHERE emailid = ? AND password = ?")) {
            stmt.setString(1, emailId);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int idstaff = rs.getInt("idstaff");
                    System.out.println("Login successful, Welcome Staff!");
                    System.out.println("ID: " + idstaff);
                    staffSession(idstaff);
                } else {
                    System.out.println("Invalid credentials. Please try again.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error during staff login: " + e.getMessage());
        }
    }



    public static void staffSession(int idstaff) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("");
            System.out.println("Welcome to Staff Panel");
            System.out.println("");
            System.out.println("1. User Panel");
            System.out.println("2. Locker Panel");
            System.out.println("3. Equipment Panel");
            System.out.println("4. Activity Panel");
            System.out.println("5. Trainer Panel");
            System.out.println("6. Staff Panel");
            System.out.println("7. Back");
            System.out.println("Please enter your choice: ");

            int userOption = scanner.nextInt();

            if (userOption == 1) {
                System.out.println("");
                System.out.println("Welcome to User Panel");
                System.out.println("");
                System.out.println("1. Register new user");
                System.out.println("2. Delete existing user");
                System.out.println("3. Read existing user");
                System.out.println("4. Update existing user");
                System.out.println("5. Back");
                System.out.println("Please enter your choice: ");

                int clientUserOption = scanner.nextInt();

                switch (clientUserOption) {
                    case 1:
                        authUser user = new authUser();
                        user.authNewUser();
                        break;
                    case 2:
                        deleteExistingUser();
                        break;
                    case 3:
                        readExistingUserDetails();
                        break;
                    case 4:
                        updateExistingUserDetails();
                        break;
                    case 5:
                        break;
                    default:
                        System.out.println("Invalid Option!");
                        break;
                }
                if (clientUserOption == 5) {
                    break;
                }

            } else if (userOption == 2) {
                while (true) {
                    System.out.println("");
                    System.out.println("Welcome to Locker Panel");
                    System.out.println("");
                    System.out.println("1. Add a new Locker");
                    System.out.println("2. Delete existing Locker");
                    System.out.println("3. Assign existing Lockers");
                    System.out.println("4. Display all Lockers");
                    System.out.println("5. Back");
                    System.out.println("Please enter your choice: ");

                    int clientUserOption = scanner.nextInt();
                    switch (clientUserOption) {
                        case 1:
                            AddLocker(idstaff);
                            break;
                        case 2:
                            DeleteLocker();
                            break;
                        case 3:
                            AssignLocker();
                            break;
                        case 4:
                            AllLockers();
                            break;
                        case 5:
                            break;
                        default:
                            System.out.println("Invalid Option!");
                            break;

                    }
                    if (clientUserOption == 5) {
                        break;
                    }
                }
            } else if (userOption == 3) {
                // Equipment Panel
                while (true) {
                    System.out.println("\nWelcome to Equipment Panel");
                    System.out.println("1. Add an equipment");
                    System.out.println("2. Delete an equipment");
                    System.out.println("3. View all equipment");
                    System.out.println("4. Assign an equipment");
                    System.out.println("5. Back");
                    System.out.println("Please enter your choice: ");
                    int eOption = scanner.nextInt();

                    switch (eOption) {
                        case 1:
                            addEquipment(idstaff);
                            break;
                        case 2:
                            deleteEquipment();
                            break;
                        case 3:
                            viewAllEquipment();
                            break;
                        case 4:
                            assignEquipment();
                            break;
                        case 5:
                            return;
                        default:
                            System.out.println("Invalid Selection!");
                            break;
                    }
                    if (eOption == 5) {
                        break;
                    }
                }
            } else if (userOption == 4) {
                // Activity Panel
                while (true) {
                    System.out.println("\nWelcome to Activity Panel");
                    System.out.println("1. Create an activity");
                    System.out.println("2. Delete an activity");
                    System.out.println("3. View all activity");
                    System.out.println("4. Update an activity");
                    System.out.println("5. Back");
                    System.out.println("Please enter your choice: ");
                    int aOption = scanner.nextInt();
                    switch (aOption) {
                        case 1:
                            createActivity();
                            break;
                        case 2:
                            deleteActivity();
                            break;
                        case 3:
                            viewAllActivities();
                            break;
                        case 4:
                            updateActivity();
                            break;
                        case 5:
                            break;
                        default:
                            System.out.println("Invalid Selection!");
                            break;
                    }
                    if (aOption == 5) {
                        break;
                    }
                }
            } else if (userOption == 5) {
                // Trainer Panel
                while (true) {
                    System.out.println("\nWelcome to Trainer Panel");
                    System.out.println("1. Create new trainer");
                    System.out.println("2. Remove trainer");
                    System.out.println("3. Assign a trainer");
                    System.out.println("4. View all trainers");
                    System.out.println("5. Back");

                    System.out.println("Please enter your choice: ");
                    String lOption = scanner.nextLine();

                    switch (lOption) {
                        case "1":
                            createTrainer();
                            break;
                        case "2":
                            removeTrainer();
                            break;
                        case "3":
                            assignTrainer();
                            break;
                        case "4":
                            viewAllTrainers();
                            break;
                        case "5":
                            return;
                        default:
                            System.out.println("Invalid Selection!");
                            break;
                    }
                    if (lOption == "5") {
                        break;
                    }
                }
            } else if (userOption == 6) {
                staffSessionChanges(idstaff);
            } else if (userOption == 7) {
                System.out.println("Main Menu");
                break;
            } else {
                System.out.println("Invalid Option!");
            }
        }
    }


    // Function to delete an existing user
    // Function to delete existing user without passing arguments
    public static void deleteExistingUser() {
        System.out.print("Enter email to delete: ");
        String email = scanner.nextLine();
        System.out.print("Enter password for deletion: ");
        String password = scanner.nextLine();

        String query = "CALL user_del(?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);
            int rowCount = statement.executeUpdate();
            if (rowCount < 1) {
                System.out.println("User not found");
            } else {
                System.out.println(email + " has been deleted!");
                printUserDetails();
            }
        } catch (SQLException e) {
            System.out.println("Error during user deletion: " + e.getMessage());
        }
    }

    // Function to read existing user details without passing arguments
    public static void readExistingUserDetails() {
        printUserDetails();
    }

    // Function to update existing user details without passing arguments
    public static void updateExistingUserDetails() {
        System.out.print("Enter user ID to update: ");
        String userId = scanner.nextLine();
        System.out.print("Enter new first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter new last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter new age: ");
        String age = scanner.nextLine();
        System.out.print("Enter new phone number: ");
        String phoneNumber = scanner.nextLine();

        String query = "UPDATE user SET first_name = ?, last_name = ?, age = ?, phone_number = ? WHERE userid = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, age);
            statement.setString(4, phoneNumber);
            statement.setString(5, userId);
            int rowCount = statement.executeUpdate();
            if (rowCount < 1) {
                System.out.println("No user found");
            } else {
                System.out.println(firstName + " updated successfully!");
                printUserDetails();
            }
        } catch (SQLException e) {
            System.out.println("Error updating user: " + e.getMessage());
        }
    }

    // Helper function to print user details in tabular format
    private static void printUserDetails() {
        String query = "SELECT * FROM user";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                System.out.println("User Details:");
                System.out.println("---------------------------------------------");
                System.out.printf("| %-10s | %-15s | %-15s | %-5s | %-15s | %-20s |%n",
                        "User ID", "First Name", "Last Name", "Age", "Phone Number", "Email ID");
                System.out.println("---------------------------------------------");
                // Print user details
                while (resultSet.next()) {
                    System.out.printf("| %-10s | %-15s | %-15s | %-5s | %-15s | %-20s |%n",
                            resultSet.getString("userid"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("age"),
                            resultSet.getString("phone_number"),
                            resultSet.getString("emailid"));
                }
                System.out.println("---------------------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving user details: " + e.getMessage());
        }
    }

    public static void AddLocker(int staffId) {
        try {
            System.out.println("Creating a new locker...");

            // Prompt the user for the type of locker
            System.out.print("Enter the type of locker: ");
            String typeOfLocker = scanner.nextLine();

            // Ask for an optional user ID, with `0` indicating no user assigned
            System.out.print("Enter the user ID to assign the locker to (or 0 for none): ");
            int userIdInput = scanner.nextInt();
            Integer userId = (userIdInput == 0) ? null : userIdInput;

            // Prepare the query to call the stored procedure
            String insertQuery = "CALL add_locker(?, ?, ?)";
            try (PreparedStatement insertStatement = conn.prepareStatement(insertQuery)) {
                insertStatement.setString(1, typeOfLocker);
                insertStatement.setInt(2, staffId);

                if (userId == null) {
                    insertStatement.setNull(3, java.sql.Types.INTEGER); // Set as NULL
                } else {
                    insertStatement.setInt(3, userId); // Provide user ID
                }

                insertStatement.executeUpdate();
                System.out.println("New locker has been created!");
            }
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            System.err.println("Error: " + e.getMessage());
            System.err.println("Please ensure that the staff ID and user ID are valid.");
        } catch (Exception e) {
            System.err.println("Unexpected error occurred: " + e.getMessage());
        }
    }

    // Assuming `scanner` is a global variable initialized earlier
    public static void DeleteLocker() {
        System.out.println("Deleting a locker...");

        try {
            // Display all lockers
            String selectQuery = "SELECT idlocker, type_of_locker FROM locker";
            PreparedStatement selectStatement = conn.prepareStatement(selectQuery);
            ResultSet result = selectStatement.executeQuery();

            System.out.println("Locker Details:");
            System.out.println("---------------------------------------------");
            System.out.printf("| %-10s | %-15s |%n", "Locker ID", "Type of Locker");
            System.out.println("---------------------------------------------");

            while (result.next()) {
                System.out.printf("| %-10s | %-15s |%n",
                        result.getString("idlocker"),
                        result.getString("type_of_locker"));
            }
            System.out.println("---------------------------------------------");

            // Prompt the user to enter the locker ID they wish to delete
            System.out.print("Enter the Locker ID to delete: ");
            int idlocker = scanner.nextInt();  // Use global `scanner` to capture user input for the locker ID

            // Prepare the deletion query
            String deleteQuery = "CALL locker_del(?)";
            try (PreparedStatement deleteStatement = conn.prepareStatement(deleteQuery)) {
                deleteStatement.setInt(1, idlocker);
                deleteStatement.executeUpdate();

                if (deleteStatement.getUpdateCount() < 1) {
                    System.out.println("Locker not found");
                } else {
                    System.out.println(idlocker + " locker has been deleted successfully");
                    System.out.println("Updated Locker");

                    // Display all lockers again after deletion
                    result = selectStatement.executeQuery();
                    System.out.println("Locker Details:");
                    System.out.println("---------------------------------------------");
                    System.out.printf("| %-10s | %-15s |%n", "Locker ID", "Type of Locker");
                    System.out.println("---------------------------------------------");

                    while (result.next()) {
                        System.out.printf("| %-10s | %-15s |%n",
                                result.getString("idlocker"),
                                result.getString("type_of_locker"));
                    }
                    System.out.println("---------------------------------------------");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Assuming the global `scanner` instance is declared and initialized earlier
    public static void AssignLocker() {
        System.out.println("Assigning a locker to a user...");

        try {
            // Viewing available lockers
            String selectQuery = "SELECT idlocker, type_of_locker, userid FROM locker WHERE userid IS NULL";
            PreparedStatement selectStatement = conn.prepareStatement(selectQuery);
            ResultSet result = selectStatement.executeQuery();

            System.out.println("Available Lockers:");
            System.out.println("---------------------------------------------");
            System.out.printf("| %-10s | %-15s |%n", "Locker ID", "Type of Locker");
            System.out.println("---------------------------------------------");

            while (result.next()) {
                System.out.printf("| %-10s | %-15s |%n",
                        result.getString("idlocker"),
                        result.getString("type_of_locker"));
            }
            System.out.println("---------------------------------------------");

            // Prompt the user for the locker ID to assign
            System.out.print("Enter the Locker ID to assign: ");
            int idlocker = scanner.nextInt(); // Use global `scanner` to capture the locker ID

            // Prompt the user for the user ID to assign to this locker
            System.out.print("Enter the User ID to assign to the locker: ");
            int userid = scanner.nextInt(); // Capture the user ID

            // Prepare the update query to assign the user ID to the locker
            String updateQuery = "UPDATE locker SET userid = ? WHERE idlocker = ?";
            PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
            updateStatement.setInt(1, userid);
            updateStatement.setInt(2, idlocker);
            updateStatement.executeUpdate();

            System.out.println("Locker " + idlocker + " has been updated successfully!");


            // Viewing all lockers after assignment
            result = selectStatement.executeQuery();
            System.out.println("Remaining Available Locker Details:");
            System.out.println("---------------------------------------------");
            System.out.printf("| %-10s | %-15s | %-8s |%n", "Locker ID", "Type of Locker", "User ID");
            System.out.println("---------------------------------------------");

            while (result.next()) {
                System.out.printf("| %-10s | %-15s | %-8s |%n",
                        result.getString("idlocker"),
                        result.getString("type_of_locker"),
                        result.getString("userid"));
            }
            System.out.println("---------------------------------------------");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void AllLockers() {
        try {
            System.out.println("Viewing all lockers...");

            // Viewing all lockers
            String selectQuery = "SELECT idlocker, type_of_locker, userid FROM locker";
            PreparedStatement selectStatement = conn.prepareStatement(selectQuery);
            ResultSet result = selectStatement.executeQuery();

            System.out.println("Locker Details:");
            System.out.println("---------------------------------------------");
            System.out.printf("| %-10s | %-15s | %-8s |%n", "Locker ID", "Type of Locker", "User ID");
            System.out.println("---------------------------------------------");

            while (result.next()) {
                System.out.printf("| %-10s | %-15s | %-8s |%n",
                        result.getString("idlocker"),
                        result.getString("type_of_locker"),
                        result.getString("userid"));
            }
            System.out.println("---------------------------------------------");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addEquipment(int idstaff) {
        System.out.println("\nAdd a New Equipment");

        // Prompt for equipment name
        System.out.print("Name of the equipment: ");
        String name = scanner.nextLine();

        // Add new equipment with `NULL` for `idactivity`
        String query = "CALL equipment_reg(?, ?, NULL)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setInt(2, idstaff);
            stmt.executeUpdate();
            System.out.println("New equipment has been added!");

        } catch (SQLException e) {
            System.out.println("Error adding equipment: " + e.getMessage());
            return; // Exit early in case of an error
        }

        // Display the updated equipment table
        System.out.println("\nEquipment Table:");
        System.out.println("---------------------------------------------");
        System.out.printf("| %-10s | %-15s | %-10s |%n", "Equipment ID", "Equipment Name", "Activity ID");
        System.out.println("---------------------------------------------");

        String equipmentQuery = "SELECT idequipment, name, idactivity FROM equipment";
        try (PreparedStatement equipmentStmt = conn.prepareStatement(equipmentQuery);
             ResultSet equipmentResultSet = equipmentStmt.executeQuery()) {

            while (equipmentResultSet.next()) {
                System.out.printf("| %-10s | %-15s | %-10s |%n",
                        equipmentResultSet.getInt("idequipment"),
                        equipmentResultSet.getString("name"),
                        equipmentResultSet.getString("idactivity")); // This will show `NULL` if not assigned
            }
            System.out.println("---------------------------------------------");

        } catch (SQLException e) {
            System.out.println("Error showing equipment table: " + e.getMessage());
        }
    }


    private static void deleteEquipment() {
        System.out.println("\nRemove an Equipment");
        viewAllEquipmentBasic();
        System.out.print("Enter the Equipment ID: ");
        int idEquipment = Integer.parseInt(scanner.nextLine());

        String query = "CALL equipment_del(?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idEquipment);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected < 1) {
                System.out.println("Equipment Not Found");
            } else {
                System.out.println(idEquipment + " equipment has been deleted successfully");
                viewAllEquipment();
                System.out.println("---------------------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting equipment: " + e.getMessage());
        }
    }

    private static void viewAllEquipment() {
        System.out.println("\nViewing All Equipment");
        String query = "SELECT idequipment, name, idactivity FROM equipment";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            System.out.printf("| %-12s | %-20s | %-10s |%n", "Equipment ID", "Name of Equipment", "Activity ID");
            System.out.println("-----------------------------------------------");
            while (rs.next()) {
                System.out.printf("| %-12s | %-20s | %-10s |%n",
                        rs.getInt("idequipment"),
                        rs.getString("name"),
                        rs.getInt("idactivity"));
            }
        } catch (SQLException e) {
            System.out.println("Error viewing equipment: " + e.getMessage());
        }
    }

    private static void viewAllEquipmentBasic() {
        System.out.println("\nViewing All Equipment");
        String query = "SELECT idequipment, name FROM equipment";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            System.out.printf("| %-12s | %-20s |%n", "Equipment ID", "Name of Equipment");
            System.out.println("----------------------------------------");
            while (rs.next()) {
                System.out.printf("| %-12s | %-20s |%n",
                        rs.getInt("idequipment"),
                        rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("Error viewing equipment: " + e.getMessage());
        }
    }

    private static void assignEquipment() {
        System.out.println("\nAssign Equipment to an Activity");

        // Display basic equipment details
        viewAllEquipmentBasic();
        viewAllActivities();

        // Prompt for the Activity ID and Equipment ID
        System.out.print("Activity ID: ");
        int idActivity = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Equipment ID: ");
        int idEquipment = Integer.parseInt(scanner.nextLine());

        // Update the equipment's activity assignment
        String query = "UPDATE equipment SET idactivity = ? WHERE idequipment = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idActivity);
            stmt.setInt(2, idEquipment);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected < 1) {
                System.out.println("No equipment found with the given ID");
            } else {
                System.out.println(idEquipment + " updated successfully!");
                viewAllEquipment(); // Display all equipment after the update
            }
        } catch (SQLException e) {
            System.out.println("Error assigning equipment: " + e.getMessage());
        }
    }

    private static void createActivity() {
        System.out.println("\nCreate an Activity");
        System.out.print("Enter the name of the activity: ");
        String name = scanner.nextLine();
        System.out.print("Enter the room number for the activity: ");
        String roomNo = scanner.nextLine();
        System.out.print("Enter activity rate: ");
        String price = scanner.nextLine();
//        displayAllTrainers();
//        System.out.print("Enter Trainer ID being assigned: ");
//        int trainer_id = scanner.nextInt();
        String query = "CALL activity_reg(?, ?, ?, null )";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, roomNo);
            stmt.setString(3, price);
            stmt.executeUpdate();
            System.out.println("New Activity has been created!");
            System.out.println("Updated all activities:");
            viewAllActivities();
        } catch (SQLException e) {
            System.out.println("Error creating activity: " + e.getMessage());
        }
    }

    private static void deleteActivity() {
        System.out.println("\nDelete an Activity");
        displayAllActivitiesForSelection();
        System.out.print("Enter the activity ID: ");
        int idActivity = Integer.parseInt(scanner.nextLine());

        String query = "CALL activity_del(?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idActivity);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected < 1) {
                System.out.println("Activity doesn't exist");
            } else {
                System.out.println("Activity has been deleted successfully!");
                System.out.println("Updated all activities:");
                viewAllActivities();
            }
        } catch (SQLException e) {
            System.out.println("Error deleting activity: " + e.getMessage());
        }
    }

    private static void viewAllActivities() {
        System.out.println("\nView all activities");
        String query = "SELECT idactivity, name, room_no, price FROM activity";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            System.out.printf("| %-10s | %-20s | %-10s | %-8s |%n", "Activity ID", "Activity Name", "Room Number", "Rate");
            System.out.println("---------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("| %-10s | %-20s | %-10s | %-8s |%n",
                        rs.getInt("idactivity"),
                        rs.getString("name"),
                        rs.getString("room_no"),
                        rs.getString("price"));
            }
            System.out.println("---------------------------------------------------------------");
        } catch (SQLException e) {
            System.out.println("Error viewing activities: " + e.getMessage());
        }
    }

    private static void activitiesTable() {
        // Define the query to select activities with a NULL trainer
        String query = "SELECT idactivity, name, room_no, price, idtrainer FROM activity WHERE idtrainer IS NULL";

        // Use a try-with-resources statement to ensure resources are closed properly
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            // Print header for the table
            System.out.printf("| %-10s | %-20s | %-10s | %-8s | %-10s |%n", "Activity ID", "Activity Name", "Room Number", "Rate", "Trainer ID");
            System.out.println("---------------------------------------------------------------");

            // Loop through the result set and print each row
            while (rs.next()) {
                System.out.printf("| %-10s | %-20s | %-10s | %-8s | %-10s |%n",
                        rs.getInt("idactivity"),
                        rs.getString("name"),
                        rs.getString("room_no"),
                        rs.getString("price"),
                        rs.getString("idtrainer")); // Use `getString` to handle NULL values gracefully
            }
            System.out.println("---------------------------------------------------------------");

        } catch (SQLException e) {
            System.out.println("Error retrieving activities: " + e.getMessage());
        }
    }


    private static void displayAllActivitiesForSelection() {
        String query = "SELECT idactivity, name, room_no, price FROM activity where idtrainer IS NULL";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            System.out.printf("| %-10s | %-20s | %-10s | %-8s |%n", "Activity ID", "Activity Name", "Room Number", "Rate");
            System.out.println("---------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("| %-10s | %-20s | %-10s | %-8s |%n",
                        rs.getInt("idactivity"),
                        rs.getString("name"),
                        rs.getString("room_no"),
                        rs.getString("price"));
            }
            System.out.println("---------------------------------------------------------------");
        } catch (SQLException e) {
            System.out.println("Error displaying activities: " + e.getMessage());
        }
    }

    private static void updateActivity() {
        System.out.println("\nUpdate Activity");
        viewAllActivities();
        System.out.print("Activity ID: ");
        int idActivity = Integer.parseInt(scanner.nextLine());
        System.out.print("Activity Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Room ID: ");
        String roomNo = scanner.nextLine();
        System.out.print("Enter the rate of the activity: ");
        String rate = scanner.nextLine();
        String query = "UPDATE activity SET name = ?, room_no = ?, price = ?, idtrainer = NULL WHERE idactivity = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, roomNo);
            stmt.setString(3, rate);
            stmt.setNull(4, java.sql.Types.INTEGER);
            stmt.setInt(5, idActivity);
            stmt.executeUpdate();
            System.out.println(name + " Updated Successfully!");
            System.out.println("\nUpdated all activities:");
            viewAllActivities();
        } catch (SQLException e) {
            System.out.println("Error updating activity: " + e.getMessage());
        }
    }

    private static void createTrainer() {
        System.out.println("\nCreate a New Trainer");
        System.out.print("Enter Trainer name: ");
        String trainerName = scanner.nextLine();
        System.out.print("Enter Trainer age: ");
        String trainerAge = scanner.nextLine();
        System.out.print("Enter Trainer phone number: ");
        String trainerPhoneNumber = scanner.nextLine();

        String query = "CALL trainer_reg(?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, trainerName);
            stmt.setString(2, trainerAge);
            stmt.setString(3, trainerPhoneNumber);
            stmt.executeUpdate();
            System.out.println("New Trainer has been created!");
        } catch (SQLException e) {
            System.out.println("Error creating trainer: " + e.getMessage());
        }
    }

    private static void removeTrainer() {
        System.out.println("\nRemove a Trainer");
        System.out.println("\nViewing all Trainers");
        viewAllTrainers();

        System.out.print("Enter the Trainer ID: ");
        int trainerId = Integer.parseInt(scanner.nextLine());

        String query = "DELETE FROM trainer WHERE idtrainer = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, trainerId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected < 1) {
                System.out.println("Trainer not found");
            } else {
                System.out.println(trainerId + " trainer has been deleted successfully");
                System.out.println("\nViewing all Trainers");
                viewAllTrainers();
            }
        } catch (SQLException e) {
            System.out.println("Error removing trainer: " + e.getMessage());
        }
    }

    private static void assignTrainer() {
        System.out.println("\nAssign Trainer to an Activity");
        System.out.println("\nAvailable Trainers:");
        viewAllTrainers();
        displayAllActivitiesForSelection();
        System.out.print("Activity ID to be updated: ");
        int activityId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Trainer ID: ");
        int trainerId = Integer.parseInt(scanner.nextLine());

        String updateQuery = "UPDATE activity SET idtrainer = ? WHERE idactivity = ?";
        try (PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            stmt.setInt(1, trainerId);
            stmt.setInt(2, activityId);
            stmt.executeUpdate();
            System.out.println("Updated Successfully!");
            System.out.println("\nViewing Updated Trainer Data");
            activitiesTable();

        } catch (SQLException e) {
            System.out.println("Error displaying activities: " + e.getMessage());
        }
    }


    private static void viewAllTrainers() {
        System.out.println("\n Trainers:");
        System.out.println("-------------------------------------------------------------");
        System.out.printf("| %-10s | %-20s | %-5s | %-15s |%n", "Trainer ID", "Name", "Age", "Phone Number");
        System.out.println("-------------------------------------------------------------");

        String query = "SELECT idtrainer, name, age, phone_number FROM trainer";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                System.out.printf("| %-10s | %-20s | %-5s | %-15s |%n",
                        rs.getInt("idtrainer"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("phone_number"));
            }
            System.out.println("-------------------------------------------------------------");

        } catch (SQLException e) {
            System.out.println("Error showing trainer table: " + e.getMessage());
        }
    }





    public static void staffSessionChanges(int staffId) {
        while (true) {
            System.out.println("\nStaff Panel");
            System.out.println("1. Register new staff");
            System.out.println("2. Delete existing staff");
            System.out.println("3. Read existing staff members");
            System.out.println("4. Update existing staff member");
            System.out.println("5. Back");

            System.out.print("Option: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    registerNewStaff(staffId);
                    break;
                case "2":
                    deleteExistingStaff(staffId);
                    break;
                case "3":
                    readExistingStaff(staffId);
                    break;
                case "4":
                    updateExistingStaff(staffId);
                    break;
                case "5":
                    return; // Exit the staff session
                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }
    }

    private static void registerNewStaff(int staffId) {
        System.out.println("\nRegister New Staff");

        System.out.print("Staff email ID: ");
        String emailId = scanner.nextLine();

        if (!Pattern.matches(EMAIL_REGEX, emailId)) {
            System.out.println("Invalid email format, try again!");
            return;
        }

        System.out.print("Staff password: ");
        String password = scanner.nextLine();
        System.out.print("Staff name: ");
        String staffName = scanner.nextLine();
        System.out.print("Staff age: ");
        int staffAge = Integer.parseInt(scanner.nextLine());
        System.out.print("Staff phone number: ");
        String phoneNumber = scanner.nextLine();

        try (PreparedStatement stmt = conn.prepareStatement("CALL staff_reg(?, ?, ?, ?, ?);")) {
            stmt.setString(1, staffName);
            stmt.setInt(2, staffAge);
            stmt.setString(3, phoneNumber);
            stmt.setString(4, emailId);
            stmt.setString(5, password); // Pass the staffId who registered

            stmt.executeUpdate();
            System.out.println(staffName + " has been registered as a staff member.");
        } catch (SQLException e) {
            System.out.println("Error registering staff member: " + e.getMessage());
        }
    }

    private static void deleteExistingStaff(int staffId) {
        // Confirm deletion with the user
        System.out.println("\nDelete Staff Member with ID: " + staffId);
        System.out.print("Are you sure you want to delete this staff member? (yes/no): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        // Proceed only if the user confirms with 'yes'
        if (confirmation.equals("yes")) {
            try (PreparedStatement stmt = conn.prepareStatement("CALL staff_del(?);")) {
                stmt.setInt(1, staffId); // Use the staffId provided as an argument

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected < 1) {
                    System.out.println("Staff member not found.");
                } else {
                    System.out.println("Staff member with ID " + staffId + " has been deleted.");
                }
            } catch (SQLException e) {
                System.out.println("Error deleting staff member: " + e.getMessage());
            }
        } else {
            System.out.println("Deletion canceled.");
        }
    }


    private static void readExistingStaff(int staffId) {
        System.out.println("\nAll Existing Staff Members (Requested by Staff ID: " + staffId + ")");

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM staff;")) {
            System.out.printf("%-10s %-20s %-10s %-15s %-20s %-20s%n",
                    "Staff ID", "Staff Name", "Age", "Phone Number", "Email ID", "Password");
            System.out.println("-------------------------------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-10s %-20s %-10s %-15s %-20s %-20s%n",
                        rs.getInt("idstaff"),
                        rs.getString("staff_name"),
                        rs.getInt("staff_age"),
                        rs.getString("phone_number"),
                        rs.getString("emailid"),
                        rs.getString("password"));
            }
        } catch (SQLException e) {
            System.out.println("Error reading staff details: " + e.getMessage());
        }
    }

    private static void updateExistingStaff(int staffId) {
        System.out.println("\nFetching details for Existing Staff ID: " + staffId);

        // Fetch the current details of the staff member
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM staff WHERE idstaff = ?;")) {
            stmt.setInt(1, staffId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Display current details to the user
                    String currentEmail = rs.getString("emailid");
                    String currentPassword = rs.getString("password");
                    String currentName = rs.getString("staff_name");
                    int currentAge = rs.getInt("staff_age");
                    String currentPhoneNumber = rs.getString("phone_number");

                    System.out.println("\nCurrent Details:");
                    System.out.println("Email ID: " + currentEmail);
                    System.out.println("Password: " + currentPassword);
                    System.out.println("Name: " + currentName);
                    System.out.println("Age: " + currentAge);
                    System.out.println("Phone Number: " + currentPhoneNumber);

                    // Confirm if the user wants to update these details
                    System.out.print("\nDo you want to update these details? (yes/no): ");
                    String confirmation = scanner.nextLine().trim().toLowerCase();
                    if (!confirmation.equals("yes")) {
                        System.out.println("Update canceled.");
                        return;
                    }

                    // Prompt for new details
                    System.out.print("New Staff email-id: ");
                    String newEmailId = scanner.nextLine();
                    System.out.print("New Staff password: ");
                    String newPassword = scanner.nextLine();
                    System.out.print("New Staff name: ");
                    String newName = scanner.nextLine();
                    System.out.print("New Staff age: ");
                    int newAge = Integer.parseInt(scanner.nextLine());
                    System.out.print("New Staff phone number: ");
                    String newPhoneNumber = scanner.nextLine();

                    // Update the record with the new details
                    try (PreparedStatement updateStmt = conn.prepareStatement("UPDATE staff SET emailid = ?, password = ?, staff_name = ?, staff_age = ?, phone_number = ? WHERE idstaff = ?;")) {
                        updateStmt.setString(1, newEmailId);
                        updateStmt.setString(2, newPassword);
                        updateStmt.setString(3, newName);
                        updateStmt.setInt(4, newAge);
                        updateStmt.setString(5, newPhoneNumber);
                        updateStmt.setInt(6, staffId);

                        int rowsAffected = updateStmt.executeUpdate();
                        if (rowsAffected < 1) {
                            System.out.println("No staff member found with the provided ID.");
                        } else {
                            System.out.println("Updated Successfully!");
                        }
                    }
                } else {
                    System.out.println("No staff member found with the provided ID.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error updating staff details: " + e.getMessage());
        }
    }
}
