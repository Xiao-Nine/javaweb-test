package com.xiaofeng.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

public class TestServlet extends HttpServlet {
    public TestServlet() {
        System.out.println("TestServlet is instantiating");
    }
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("service() is called");
    }

    @Override
    public void destroy() {
        System.out.println("destroy() is called");
    }

    @Override
    public void init() throws ServletException {
        System.out.println("init() is called");
    }
}
