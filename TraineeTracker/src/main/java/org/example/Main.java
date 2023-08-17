package org.example;

import org.example.repo.ParticipationManager;
import org.example.repo.SessionManager;
import org.example.repo.StudentManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StudentManager studentManager = new StudentManager();
        SessionManager sessionManager = new SessionManager();
        ParticipationManager participationManager = new ParticipationManager();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Options:");
            System.out.println("1. Student Management");
            System.out.println("2. Session Management");
            System.out.println("3. Participation Management");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    studentManagementMenu(scanner, studentManager);
                    break;
                case 2:
                    sessionManagementMenu(scanner, sessionManager);
                    break;
                case 3:
                    participationManagementMenu(scanner, participationManager);
                    break;
                case 4:
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        }
    }

    private static void studentManagementMenu(Scanner scanner, StudentManager studentManager) {
        System.out.println("Student Management:");
        System.out.println("0. Student list");
        System.out.println("1. Add Student");
        System.out.println("2. Update Student");
        System.out.println("3. Read Student");
        System.out.println("4. Delete Student");
        System.out.print("Choose an option: ");
        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 0:
                studentManager.printAllStudent();
                break;
            case 1:
                studentManager.addStudent();
                break;
            case 2:
                studentManager.updateStudent();
                break;
            case 3:
                studentManager.readStudent();
                break;
            case 4:
                studentManager.deleteStudent();
                break;
            default:
                System.out.println("Invalid option. Please choose again.");
        }
    }

    private static void sessionManagementMenu(Scanner scanner, SessionManager sessionManager) {
        System.out.println("Session Management:");
        System.out.println("0. List of all sessions");
        System.out.println("1. Add Session");
        System.out.println("2. Update Session");
        System.out.println("3. Read Session");
        System.out.println("4. Delete Session");
        System.out.print("Choose an option: ");
        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 0:
                sessionManager.printAllSessions();
                break;
            case 1:
                sessionManager.addSession();
                break;
            case 2:
                sessionManager.updateSession();
                break;
            case 3:
                sessionManager.readSession();
                break;
            case 4:
                sessionManager.deleteSession();
                break;
            default:
                System.out.println("Invalid option. Please choose again.");
        }
    }

    private static void participationManagementMenu(Scanner scanner, ParticipationManager participationManager) {
        System.out.println("Participation Management:");
        System.out.println("1. Add Participation");
        System.out.println("2. Present Sessions");
        System.out.println("3. Absent Sessions");
        System.out.println("4. Attendance");
        System.out.print("Choose an option: ");
        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1:
                System.out.print("Enter student ID: ");
                int studentId = scanner.nextInt();
                System.out.print("Enter session ID: ");
                int sessionId = scanner.nextInt();
                participationManager.addParticipation(studentId, sessionId);
                break;
            case 2:
                System.out.print("Enter student ID: ");
                studentId = scanner.nextInt();
                participationManager.presentSessions(studentId);
                break;
            case 3:
                System.out.print("Enter student ID: ");
                studentId = scanner.nextInt();
                participationManager.absentSessions(studentId);
                break;
            default:
                System.out.println("Invalid option. Please choose again.");
        }
    }
}
