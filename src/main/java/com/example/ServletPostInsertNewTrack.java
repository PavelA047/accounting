package com.example;

import com.example.persist.Report;
import com.example.persist.TaskRepository;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = "/insertNewTrack")
public class ServletPostInsertNewTrack extends HttpServlet {

    private TaskRepository taskRepository;
    private Gson gson;

    @Override
    public void init() {
        taskRepository = (TaskRepository) getServletContext().getAttribute("taskRepository");
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    }

    //обработка метода роутера insertNewTrack();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Report report;
        BufferedReader reader = request.getReader();
        StringBuilder result = new StringBuilder();

        request.setCharacterEncoding("UTF-8");
        int intValueOfChar;
        while ((intValueOfChar = reader.read()) != -1) {
            result.append((char) intValueOfChar);
        }
        report = gson.fromJson(String.valueOf(result), Report.class);
        taskRepository.insertReport(report);
        reader.close();
    }
}
