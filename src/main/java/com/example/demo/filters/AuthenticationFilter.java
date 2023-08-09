package com.example.demo.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    private ServletContext context;
    private List<String> allowedServlets;

    public void init(FilterConfig fConfig) throws ServletException {
        this.context = fConfig.getServletContext();
        this.context.log(">>> AuthenticationFilter initialized");

        // Initialize the list of allowed servlets
        allowedServlets = Arrays.asList(
                "/demo/saveServlet",
                "/demo/viewByIDServlet",
                "/demo/loginServlet",
                "/demo/viewServlet"
        );
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        this.context.log("Requested Resource::http://localhost:8080" + uri);

        HttpSession session = req.getSession(false);

        if (session == null && !isAllowedServlet(uri)) {
            this.context.log("<<< Unauthorized access request");
            PrintWriter out = res.getWriter();
            out.println("No access!!!");
        } else {
            chain.doFilter(request, response);
        }
    }

    private boolean isAllowedServlet(String uri) {
        return allowedServlets.stream().anyMatch(uri::endsWith);
    }

    public void destroy() {
        // Close any resources here
    }
}
