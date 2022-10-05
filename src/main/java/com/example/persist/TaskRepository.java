package com.example.persist;

import java.util.List;

public interface TaskRepository {
    List<String> getListOfUserNamesWithoutTrackMoreThenThreeDays();

    List<String> getListOfUserNamesWithoutTrackMoreThenOneDays();

    void insertReport(Report report);

    List<Report> getListOfNonReportedTasks();

    void insertListOfUsers(List<String> listOfUsers);
}
