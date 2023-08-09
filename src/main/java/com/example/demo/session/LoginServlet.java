package com.example.demo.session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // створюємо HashMap для зберігання користувацьких данних
    private Map<String, String> userCredentials;

    @Override
    public void init() throws ServletException {
        super.init();
        userCredentials = new HashMap<>();
        // додаємо тестового користувача
        userCredentials.put("admin", "password");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String user = request.getParameter("user");
        String pwd = request.getParameter("pwd");

        if (userCredentials.containsKey(user) && userCredentials.get(user).equals(pwd)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(30 * 60);
            Cookie userName = new Cookie("user", user);
            userName.setMaxAge(30 * 60);
            response.addCookie(userName);
            PrintWriter out = response.getWriter();
            out.println("Welcome back to the team, " + user + "!");
        } else {
            PrintWriter out = response.getWriter();
            out.println("Either user name or password is wrong!");
        }
    }
}
