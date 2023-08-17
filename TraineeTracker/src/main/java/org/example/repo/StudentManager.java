package org.example.repo;

import org.example.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class StudentManager {
    private Connection connection;
    public StudentManager() {
        // Taking DB connector
        connection = new DBConnection().getDB().connection;
    }
    // Printing all students from database
    public void printAllStudent(){
        try{
            // Query processing
            String sellectAllQuery = "SELECT * FROM student";
            PreparedStatement preparedStatement = connection.prepareStatement(sellectAllQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Printing results
            System.out.println("    List of all sessions");
            while(resultSet.next()){
                System.out.println("        ID: " + resultSet.getInt("id"));
                System.out.println("        Name: " + resultSet.getString("name"));
                System.out.println("        Email: " + resultSet.getString("email"));
                System.out.println("        Training Type: " + resultSet.getString("trainingtype"));
            }
        }
        catch (Exception e){
            System.out.println("Failed to read students from database.");
            e.printStackTrace();
        }
    }
    public void addStudent() {
        try {
            // Taking new student input data from user
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter student name: ");
            String name = scanner.nextLine();
            System.out.print("Enter student email: ");
            String email = scanner.nextLine();
            System.out.print("Enter student training type (JavaEE/SQA/MERN): ");
            String trainingType = scanner.nextLine();

            // Making the query (I've used prepared statement for more readability and as it is better for DML)
            String insertQuery = "INSERT INTO student (name, email, trainingtype) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, trainingType);

            // If changes = 0 then no rows were affected.
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Student added successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateStudent() {
        try {
            // Input
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter student ID to update: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter updated student name: ");
            String name = scanner.nextLine();
            System.out.print("Enter updated student email: ");
            String email = scanner.nextLine();
            System.out.print("Enter updated student training type (JavaEE/SQA/MERN): ");
            String trainingType = scanner.nextLine();

            // Making query
            String updateQuery = "UPDATE student SET name = ?, email = ?, trainingtype = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, trainingType);
            preparedStatement.setInt(4, id);

            // Checking success
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Student updated successfully!");
            } else {
                System.out.println("No student found with the given ID.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readStudent() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter student ID to read: ");
            int id = scanner.nextInt();

            String selectQuery = "SELECT * FROM student WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            // Printing results
            if (resultSet.next()) {
                System.out.println("    Student Information:");
                System.out.println("        ID: " + resultSet.getInt("id"));
                System.out.println("        Name: " + resultSet.getString("name"));
                System.out.println("        Email: " + resultSet.getString("email"));
                System.out.println("        Training Type: " + resultSet.getString("trainingtype"));
            } else {
                System.out.println("No student found with the given ID.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteStudent() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter student ID to delete: ");
            int id = scanner.nextInt();
            String deleteQuery = "DELETE FROM student WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("No student found with the given ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
