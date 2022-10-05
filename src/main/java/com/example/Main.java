//package com.example;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//
////
////import com.example.persist.Report;
////import com.example.persist.ReportDao;
//import com.example.persist.TaskRepository;
//import com.example.persist.TaskRepositoryImpl;
//import com.google.gson.Gson;
//
////
////import java.sql.Timestamp;
////
//public class Main {
//    public static void main(String[] args) throws Exception {
//        TaskRepository tasksRepository = new TaskRepositoryImpl();
////        List<String> list = new ArrayList<>();
////        list.add("max");
////        list.add("den");
////        list.add("pavel");
////        list.add("ivan");
////        tasksRepository.insertListOfUsers(list);
////
//////        tasksRepository.insertReport(new Report("ivan", "doSomething", Timestamp.valueOf("2022-10-05 20:49:22.000000")));
////
//////        System.out.println(tasksRepository.getListOfUserNamesWithoutTrackMoreThenThreeDays());
//////        System.out.println(tasksRepository.getListOfUserNamesWithoutTrackMoreThenOneDays());
//////
//////        System.out.println(tasksRepository.getListOfNonReportedTasks());
////
//////        tasksRepository.insertReport(new ReportDao(0, "den", "do", Timestamp.valueOf("2022-10-05 20:49:22.000000")));
//////        tasksRepository.insertReport(new ReportDao(0, "max", "do", Timestamp.valueOf("2022-10-01 20:49:22.000000")));
//////        tasksRepository.insertReport(new ReportDao(0, "pavel", "do", Timestamp.valueOf("2022-10-03 20:49:22.000000")));
//////        tasksRepository.insertReport(new ReportDao(0, "ivan", "do", Timestamp.valueOf("2022-09-05 20:49:22.000000")));
//////        tasksRepository.insertReport(new ReportDao(0, "kat", "do", Timestamp.valueOf("2022-09-05 20:49:22.000000")));
////
//
//        String html = getHTML("http://localhost:8080/getApp/getList");
//        Gson gson = new Gson();
//        List<String> list = gson.fromJson(html, List.class);
//        tasksRepository.insertListOfUsers(list);
//
//    }
//
//
//    public static String getHTML(String urlToRead) throws Exception {
//        StringBuilder result = new StringBuilder();
//        URL url = new URL(urlToRead);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("GET");
//        try (BufferedReader reader = new BufferedReader(
//                new InputStreamReader(conn.getInputStream()))) {
//            for (String line; (line = reader.readLine()) != null; ) {
//                result.append(line);
//            }
//        }
//        return result.toString();
//
//
//    }
//}
