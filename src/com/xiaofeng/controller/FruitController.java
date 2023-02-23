package com.xiaofeng.controller;

import com.xiaofeng.pojo.Fruit;
import com.xiaofeng.service.FruitService;
import com.xiaofeng.service.impl.FruitServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class FruitController{
    private FruitService service = null;
    // add a Fruit Object to FruitList
    private String addFruit(String name, Double price, Integer count) throws IOException {
        String newName = new String(name.getBytes("iso-8859-1"), "utf-8");
        service.addFruit(new Fruit(newName, price, count));
        return "redirect:fruit.do";
    }
    // delete a Fruit Object in FruitList
    private String deleteFruit(String name) throws UnsupportedEncodingException {
        service.removeFruitByName(name);
        return "redirect:fruit.do";
    }

    private String updateFruit(String name, Double price, Integer count) throws UnsupportedEncodingException {
        String newName = new String(name.getBytes("iso-8859-1"), "utf-8");
        service.updateFruitList(newName, price, count);
        return "redirect:fruit.do";
    }
    // get FruitList in session and render index.html
    private String renderIndexPage(HttpSession session) {
        session.setAttribute("fruits", service.getFruitList());
        return "index";
    }

    // render edit.html
    private String renderEditPage(String name, HttpServletRequest request) throws UnsupportedEncodingException {
        Fruit fruit = service.getFruitByName(name);
        request.setAttribute("fruit", fruit);
        return "edit";
    }

    /**
     * render add.html
     */
    private String renderAddPage() {
        return "add";
    }

}
