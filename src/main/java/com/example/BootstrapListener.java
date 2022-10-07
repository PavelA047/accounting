package com.example;

import com.example.persist.TaskRepository;
import com.example.persist.TaskRepositoryImpl;
import com.google.gson.Gson;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@WebListener
public class BootstrapListener implements ServletContextListener {
    private List<String> listOfUsers = new ArrayList<>();
    private Gson gson = new Gson();

    public static String getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        }
        return result.toString();
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        TaskRepository taskRepository = new TaskRepositoryImpl();
        sce.getServletContext().setAttribute("taskRepository", taskRepository);
//        String listOfUsersNames = null;
//        try {
//            listOfUsersNames = getHTML("http://localhost:8080/router_web_service_war/getUsers");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (listOfUsersNames == null) return;
//        List<String> list = gson.fromJson(listOfUsersNames, List.class);
//        taskRepository.insertListOfUsers(list);
    }
}
