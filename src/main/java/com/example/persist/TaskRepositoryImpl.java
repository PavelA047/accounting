package com.example.persist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskRepositoryImpl implements TaskRepository {
    private final int defaultValue = Integer.MAX_VALUE;
    private static Connection connection;
    private static PreparedStatement preparedStatement;

    static {
        try {
            Class.forName("org.postgresql.Driver");
            //для localhost
//            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/habrdb", "user", "pass");
            //для облачного сервера
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/admin", "admin", "aston");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getListOfUserNamesWithoutTrackMoreThenThreeDays() {
        return getListByDays(3);
    }

    @Override
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

    @Override
    public void insertReport(Report report) {
        try {
            preparedStatement = connection
                    .prepareStatement("INSERT INTO tasks VALUES (DEFAULT, ?, ?, ?, null)");
            preparedStatement.setTimestamp(1, report.getTimeOfTrack());
            preparedStatement.setString(2, report.getUserName());
            preparedStatement.setString(3, report.getTask());
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

    @Override
    public List<Report> getListOfNonReportedTasks() {
        List<ReportDao> reportsDao = new ArrayList<>();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        int reportId = defaultValue;
        try {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT id, user_name, task, time_of_track from tasks WHERE report_id IS NULL");
            while (rs.next()) {
                reportsDao.add(new ReportDao(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getTimestamp(4)));
            }
            rs.close();

            preparedStatement = connection.prepareStatement("INSERT INTO reports VALUES(DEFAULT, ?)");
            preparedStatement.setTimestamp(1, now);
            preparedStatement.executeUpdate();
            connection.commit();

            preparedStatement = connection
                    .prepareStatement("SELECT id FROM reports WHERE time_of_report = ?");
            preparedStatement.setTimestamp(1, now);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                reportId = rs.getInt(1);
            }
            rs.close();

            preparedStatement = connection
                    .prepareStatement("UPDATE tasks SET report_id = ? WHERE id = ?");
            for (int i = 0; i < reportsDao.size(); i++) {
                preparedStatement.setInt(1, reportId);
                preparedStatement.setInt(2, reportsDao.get(i).getId());
                preparedStatement.executeUpdate();
            }
            connection.commit();
            rs.close();
            preparedStatement.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        List<Report> reports = new ArrayList<>();
        for (ReportDao r : reportsDao) {
            reports.add(new Report(r.getUserName(), r.getTask(), r.getTimeOfTrack()));
        }
        return reports;
    }

    @Override
    public void insertListOfUsers(List<String> listOfUsers) {
        Timestamp defaultData = Timestamp.valueOf("2000-01-01 00:00:00.000000");
        String defaultTask = "No task";
        try {
            preparedStatement = connection
                    .prepareStatement("INSERT INTO tasks VALUES (DEFAULT, ?, ?, ?, null)");

            for (String userName : listOfUsers) {
                preparedStatement.setTimestamp(1, defaultData);
                preparedStatement.setString(2, userName);
                preparedStatement.setString(3, defaultTask);
                preparedStatement.executeUpdate();
            }
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
}
