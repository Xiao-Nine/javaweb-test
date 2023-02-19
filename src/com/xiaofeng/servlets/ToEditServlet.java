package com.xiaofeng.servlets;

import com.xiaofeng.dao.Fruits;
import com.xiaofeng.myssm.myspringmvc.ViewBaseServlet;
import com.xiaofeng.pojo.Fruit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/toEdit")
public class ToEditServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        if (name == null || "".equals(name)) {
            resp.sendRedirect("index");
        }
        Fruits fruits = Fruits.getInstance();
        req.setAttribute("fruit", fruits.getFruitByName(name));
        super.processTemplate("edit", req, resp);
    }
}
