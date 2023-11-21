import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class user_login {
    public static void main(String[] args) {
        String dbUrl = "jdbc:mysql://localhost:3306/user_login";
        String dbUser = "root";
        String dbPassword = "astitva";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            Scanner scanner = new Scanner(System.in);

            boolean isAuthenticated = false;

            while (!isAuthenticated) {
                System.out.println("Welcome to Pharmacy Management System");
                System.out.println("1. Sign Up (New User)");
                System.out.println("2. Log In (Existing User)");
                System.out.println("3. Exit");

                System.out.print("Please choose an option (1/2/3): ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 1) {
                    // (Sign-up)
                    System.out.print("Enter a new username: ");
                    String newUsername = scanner.nextLine();

                    System.out.print("Enter a new password: ");
                    String newPassword = scanner.nextLine();

                    // if the username already exists
                    if (!isUsernameExists(connection, newUsername)) {
                        String insertQuery = "INSERT INTO user (username, password) VALUES (?, ?)";
                        PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                        insertStatement.setString(1, newUsername);
                        insertStatement.setString(2, newPassword);
                        insertStatement.executeUpdate();

                        System.out.println("User registration successful!");
                    } else {
                        System.out.println("Username already exists. Please choose a different username.");
                    }
                } else if (choice == 2) {
                    // User Login
                    System.out.print("Enter your username: ");
                    String loginUsername = scanner.nextLine();

                    System.out.print("Enter your password: ");
                    String loginPassword = scanner.nextLine();

                    if (isLoginValid(connection, loginUsername, loginPassword)) {
                        System.out.println("Login successful! Welcome, " + loginUsername + "!");
                        isAuthenticated = true;
                    } else {
                        System.out.println("Incorrect username or password.");
                    }
                } else if (choice == 3) {
                    System.out.println("Goodbye!");
                    break;
                } else {
                    System.out.println("Invalid choice. Please choose a valid option (1/2/3).");
                }
            }

            scanner.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean isUsernameExists(Connection connection, String username) throws SQLException {
        String selectQuery = "SELECT COUNT(*) AS count FROM user WHERE username = ?";
        PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
        selectStatement.setString(1, username);
        ResultSet resultSet = selectStatement.executeQuery();

        if (resultSet.next()) {
            int count = resultSet.getInt("count");
            return count > 0;
        }
        return false;
    }

    private static boolean isLoginValid(Connection connection, String username, String password) throws SQLException {
        String selectQuery = "SELECT COUNT(*) AS count FROM user WHERE username = ? AND password = ?";
        PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
        selectStatement.setString(1, username);
        selectStatement.setString(2, password);
        ResultSet resultSet = selectStatement.executeQuery();

        if (resultSet.next()) {
            int count = resultSet.getInt("count");
            return count > 0;
        }
        return false;
    }
}

