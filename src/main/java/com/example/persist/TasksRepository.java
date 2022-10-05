package com.example.persist;

import com.example.persist.model.Report;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TasksRepository {
    private final int defaultValue = Integer.MAX_VALUE;
    private static Connection connection;
    private static PreparedStatement preparedStatement;

    static {
        try {
            Class.forName("org.postgresql.Driver");
            //для localhost
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/habrdb", "user", "pass");
            //для облачного сервера
//            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/admin", "admin", "aston");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getListOfUserNamesWithoutTrackMoreThenThreeDays() {
        return getListByDays(3);
    }

    public List<String> getListOfUserNamesWithoutTrackMoreThenOneDays() {
        return getListByDays(1);
    }

    private List<String> getListByDays(int days) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        timestamp.setDate(timestamp.getDate() - days);
        List<String> users = new ArrayList<>();
        try {
            preparedStatement = connection
                    .prepareStatement("SELECT user_name FROM tasks WHERE time_of_track <= ?");
            preparedStatement.setTimestamp(1, timestamp);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                users.add(rs.getString(1));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    public void putTimestampUsernameTask(Timestamp timestamp, String userName, String task) {
        try {
            preparedStatement = connection
                    .prepareStatement("INSERT INTO tasks VALUES (DEFAULT, ?, ?, ?, null)");
            preparedStatement.setTimestamp(1, timestamp);
            preparedStatement.setString(2, userName);
            preparedStatement.setString(3, task);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Report> getListOfNonReportedTasks() {
        List<Report> report = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT user_name, task, time_of_track from tasks");
            while (rs.next()) {
                report.add(new Report(rs.getString(1), rs.getString(2), rs.getTimestamp(3)));
            }
            statement.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return report;
    }
}
