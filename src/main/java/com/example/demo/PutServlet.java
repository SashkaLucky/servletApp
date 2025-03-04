package com.example.demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/putServlet")
public class PutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // логіка обробки запросів
        String sid = request.getParameter("id");
        int id = Integer.parseInt(sid);

        String name = request.getParameter("name");
        String email = request.getParameter("email");

        Employee employee = new Employee(id, name, email, request.getParameter("country"));

        int status = EmployeeRepository.update(employee);

        // логування результатів
        if (status > 0) {
            System.out.println("Record updated successfully");
            response.sendRedirect("viewByIDServlet?id=" + id);
        } else {
            System.out.println("Failed to update record");
            PrintWriter out = response.getWriter();
            out.println("Sorry! unable to update record");
            out.close();
        }
    }
}