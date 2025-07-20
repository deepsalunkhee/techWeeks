package com.starter;

import jakarta.servlet.*;
import jakarta.servlet.http.*;


import java.io.IOException;


public class Hello extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Add data to request scope
        request.setAttribute("message", "Hello from Servlet!");

        // Forward to JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("hello.jsp");
        dispatcher.forward(request, response);
    }
}
