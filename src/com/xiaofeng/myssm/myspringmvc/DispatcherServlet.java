package com.xiaofeng.myssm.myspringmvc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
@WebServlet("*.do")
public class DispatcherServlet extends ViewBaseServlet{
    private Map<String, Object> beanMap = new HashMap<>();

    @Override
    public void init() throws ServletException {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("applicationContext.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);
            NodeList beanNodeList = document.getElementsByTagName("bean");
            for (int i = 0; i < beanNodeList.getLength(); i++) {
                Node beanNode = beanNodeList.item(i);
                Element element = (Element) beanNode;
                String beanId = element.getAttribute("id");
                String className = element.getAttribute("class");
                Class controllerBeanClass = Class.forName(className);
                Object beanObject = controllerBeanClass.newInstance();
                Method setServletContext = controllerBeanClass.getDeclaredMethod("setServletContext", ServletContext.class);
                setServletContext.invoke(beanObject, this.getServletContext());
                beanMap.put(beanId, beanObject);
            }
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    public DispatcherServlet(){

    }
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath().substring(1);
        int lastDotIndex = servletPath.lastIndexOf('.');
        servletPath = servletPath.substring(0, lastDotIndex);
        Object controllerObject = beanMap.get(servletPath);
        String operate = req.getParameter("operate");
        if (operate == null || "".equals(operate)) {
            operate = "renderIndexPage";
        }
        try {
            Method method = controllerObject.getClass().getDeclaredMethod(operate, HttpServletRequest.class, HttpServletResponse.class);
            if (method == null) {
                new RuntimeException("operate is illegal");
            } else {
                method.setAccessible(true);
                method.invoke(controllerObject, req, resp);

            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
