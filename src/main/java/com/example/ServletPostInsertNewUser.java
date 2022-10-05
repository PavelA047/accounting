package com.example;

import com.example.persist.Report;
import com.example.persist.TaskRepository;
import com.example.persist.User;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = "/insertNewUser")
public class ServletPostInsertNewUser extends HttpServlet {

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

    //обработка метода роутера insertNewUser();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user;
        BufferedReader reader = request.getReader();
        StringBuilder result = new StringBuilder();

        request.setCharacterEncoding("UTF-8");
        int intValueOfChar;
        while ((intValueOfChar = reader.read()) != -1) {
            result.append((char) intValueOfChar);
        }
        user = gson.fromJson(String.valueOf(result), User.class);
        taskRepository.insertNewUser(user.getName());
        reader.close();
    }
}
