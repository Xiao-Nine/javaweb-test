package com.xiaofeng.servlets;

import com.xiaofeng.dao.Fruits;
import com.xiaofeng.myssm.myspringmvc.ViewBaseServlet;
import com.xiaofeng.pojo.Fruit;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FruitController extends ViewBaseServlet {
    private ServletContext servletContext;

    public void setServletContext(ServletContext servletContext) throws ServletException {
        this.servletContext = servletContext;
        super.init(servletContext);
    }
    // add a Fruit Object to FruitList
    private void addFruit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = new String(request.getParameter("name").getBytes("iso-8859-1"), "utf-8");
        double price = Double.parseDouble(request.getParameter("price"));
        int count = Integer.parseInt(request.getParameter("count"));
        boolean flag = Fruits.getInstance().addFruit(new Fruit(name, price, count));
        renderIndexPage(request, response);
    }
    // delete a Fruit Object in FruitList
    private void deleteFruit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        Fruits.getInstance().removeFruitByName(name);
        response.sendRedirect("fruit?operate=index");
    }

    private void updateFruit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        int count = Integer.parseInt(request.getParameter("count"));
        Fruits.getInstance().updateFruitList(name, price, count);
        renderIndexPage(request, response);
    }
    // get FruitList in session and render index.html
    private void renderIndexPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.setAttribute("fruits", Fruits.getInstance().getFruitList());
        super.processTemplate("index", request, response);
    }
    // render edit.html
    private void renderEditPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        Fruit fruit = Fruits.getInstance().getFruitByName(name);
        request.setAttribute("fruit", fruit);
        super.processTemplate("edit", request, response);
    }

    /**
     * render add.html
     */
    private void renderAddPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        super.processTemplate("add", request, response);
    }

}
