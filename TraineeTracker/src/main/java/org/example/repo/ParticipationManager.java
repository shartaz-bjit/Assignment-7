package org.example.repo;

import org.example.db.DBConnection;

import java.sql.*;

public class ParticipationManager {
    private Connection connection;
    public ParticipationManager() {
        connection = new DBConnection().getDB().connection;
    }

    public void addParticipation(int studentId, int sessionId) {
        try {
            String insertQuery = "INSERT INTO participation (studentid, sessionid) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, sessionId);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Participation record added successfully!");
            }
        }
        catch (SQLIntegrityConstraintViolationException nee){
            System.out.println("Course id or student id doesn't exist.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void presentSessions(int studentId) {
        try {
            String selectQuery = "SELECT session.title FROM session INNER JOIN participation ON session.id = participation.sessionid WHERE participation.studentid = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, studentId);

            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("    Sessions Participated In:");
            while (resultSet.next()) {
                System.out.println("        "+resultSet.getString("title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void absentSessions(int studentId) {
        try {
            String selectQuery = "select s.title from (\n" +
                    "    SELECT id, title\n" +
                    "    FROM session\n" +
                    "    WHERE trainingtype = (\n" +
                    "        SELECT trainingtype\n" +
                    "        FROM student\n" +
                    "        WHERE id = ?\n" +
                    ")) as s \n" +
                    "WHERE s.id not in (select sessionid from participation where studentid = ?)\n";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("Sessions Missed:");
            while (resultSet.next()) {
                System.out.println("        "+resultSet.getString("title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
