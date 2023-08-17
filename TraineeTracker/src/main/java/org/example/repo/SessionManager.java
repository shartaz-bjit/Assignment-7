package org.example.repo;

import org.example.db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class SessionManager {
    private Connection connection;
    public SessionManager() {
        // Getting connection string
        connection = new DBConnection().getDB().connection;
    }
    public void printAllSessions(){
        try{
            // Query processing
            String sellectAllQuery = "SELECT * FROM session";
            PreparedStatement preparedStatement = connection.prepareStatement(sellectAllQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Printing results
            System.out.println("    List of all sessions");
            while(resultSet.next()){
                System.out.println("        ID: " + resultSet.getInt("id"));
                System.out.println("        Title: " + resultSet.getString("title"));
                System.out.println("        Description: " + resultSet.getString("description"));
                System.out.println("        Instructor: " + resultSet.getString("instructor"));
                System.out.println("        Date: " + resultSet.getDate("date"));
                System.out.println("        Training Type: " + resultSet.getString("trainingtype"));
            }
        }
        catch (Exception e){
            System.out.println("Failed to read students from database.");
            e.printStackTrace();
        }
    }
    public void addSession() {
        try {
            // Taking user input for a session
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter session title: ");
            String title = scanner.nextLine();
            System.out.print("Enter session description: ");
            String description = scanner.nextLine();
            System.out.print("Enter session instructor: ");
            String instructor = scanner.nextLine();
            System.out.print("Enter session date (YYYY-MM-DD): ");
            String dateString = scanner.nextLine();
            java.sql.Date date = java.sql.Date.valueOf(dateString);
            System.out.print("Enter session training type (JavaEE/SQA/MERN): ");
            String trainingType = scanner.nextLine();

            // Making the query (I've used prepared statement for more readability and as it is better for DML)
            String insertQuery = "INSERT INTO session (title, description, instructor, date, trainingtype) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, instructor);
            preparedStatement.setDate(4, date);
            preparedStatement.setString(5, trainingType);

            // If changes = 0 then no rows were affected.
            int changes = preparedStatement.executeUpdate();
            if (changes > 0) {
                System.out.println("Session added successfully!");
            }
        }
        // Possible errors: SQL error, date formation error, null value error
        catch (Exception e) {
            System.out.println("Failed to inset new session");
            e.printStackTrace();
        }
    }

    public void updateSession() {
        try {
            // User input for updating session
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter session ID to update: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter updated session title: ");
            String title = scanner.nextLine();
            System.out.print("Enter updated session description: ");
            String description = scanner.nextLine();
            System.out.print("Enter updated session instructor: ");
            String instructor = scanner.nextLine();
            System.out.print("Enter updated session date (YYYY-MM-DD): ");
            String dateString = scanner.nextLine();
            java.sql.Date date = java.sql.Date.valueOf(dateString);
            System.out.print("Enter updated session training type (JavaEE/SQA/MERN): ");
            String trainingType = scanner.nextLine();

            // Query
            String updateQuery = "UPDATE session SET title = ?, description = ?, instructor = ?, date = ?, trainingtype = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, instructor);
            preparedStatement.setDate(4, date);
            preparedStatement.setString(5, trainingType);
            preparedStatement.setInt(6, id);

            // Checking success
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Session updated successfully!");
            } else {
                System.out.println("No session found with the given ID.");
            }
        } catch (Exception e) {
            System.out.println("Failed to update the session");
            e.printStackTrace();
        }
    }

    public void readSession() {
        try {
            // Taking session id from user input
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter session ID to read: ");
            int id = scanner.nextInt();
            String selectQuery = "SELECT * FROM session WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, id);

            // Querying
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println("    Session Information:");
                System.out.println("        ID: " + resultSet.getInt("id"));
                System.out.println("        Title: " + resultSet.getString("title"));
                System.out.println("        Description: " + resultSet.getString("description"));
                System.out.println("        Instructor: " + resultSet.getString("instructor"));
                System.out.println("        Date: " + resultSet.getDate("date"));
                System.out.println("        Training Type: " + resultSet.getString("trainingtype"));
            }
            else {
                System.out.println("No session found with the given ID.");
            }
        } catch (Exception e) {
            System.out.println("Failed to access database.");
            e.printStackTrace();
        }
    }

    public void deleteSession() {
        try {
            // Taking session id from user input
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter session ID to delete: ");
            int id = scanner.nextInt();

            //Querying
            String deleteQuery = "DELETE FROM session WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Session deleted successfully!");
            } else {
                System.out.println("No session found with the given ID.");
            }
        } catch (Exception e) {
            System.out.println("Failed to access database.");
            e.printStackTrace();
        }
    }
}
