package com.xiaofeng.servlets;

import com.xiaofeng.dao.FruitDao;
import com.xiaofeng.dao.Fruits;
import com.xiaofeng.myssm.myspringmvc.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/edit.do")
public class EditServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        double price = Double.parseDouble(req.getParameter("price"));
        int count = Integer.parseInt(req.getParameter("count"));
        Fruits.getInstance().updateFruitList(name, price, count);
        req.getSession().setAttribute("fruits", Fruits.getInstance().getFruitList());
        resp.sendRedirect("index");
    }
}
