package com.xiaofeng.myssm.myspringmvc;

import com.xiaofeng.myssm.myspringmvc.io.BeanFactory;
import com.xiaofeng.myssm.myspringmvc.io.ClassPathXmlApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@WebServlet("*.do")
public class DispatcherServlet extends ViewBaseServlet{
    private BeanFactory beanFactory = null;
    public DispatcherServlet(){
        beanFactory = new ClassPathXmlApplicationContext();
    }
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        String servletPath = req.getServletPath().substring(1);
        int lastDotIndex = servletPath.lastIndexOf('.');
        servletPath = servletPath.substring(0, lastDotIndex);
        Object controllerObject = beanFactory.getBean(servletPath);
        String operate = req.getParameter("operate");
        if (operate == null || "".equals(operate)) {
            operate = "renderIndexPage";
        }
        try {
            Method[] methods = controllerObject.getClass().getDeclaredMethods();
            Method method = null;
            for (int i = 0; i < methods.length; i++) {
                if (operate.equals(methods[i].getName())) {
                    method = methods[i];
                    break;
                }
            }
            if (method == null) {
                new RuntimeException("operate is illegal");
            } else {
                Object[] values = getValues(method, req, resp, req.getSession());
                method.setAccessible(true);
                Object object = method.invoke(controllerObject, values);
                if (object != null) {
                    String pageStr = (String) object;
                    if (pageStr.startsWith("redirect:")) {
                        resp.sendRedirect(pageStr.substring("redirect:".length()));
                    } else {
                        super.processTemplate(pageStr, req, resp);
                    }
                }
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    private Object[] getValues(Method method, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws UnsupportedEncodingException {
        Parameter[] parameters = method.getParameters();
        Object[] values = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            String parameterName = parameters[i].getName();
            if (parameterName.equals("request")) {
                values[i] = request;
            } else if (parameterName.equals("response")) {
                values[i] = response;
            } else if (parameterName.equals("session")) {
                values[i] = session;
            } else {
                String valueStr =request.getParameter(parameters[i].getName());
                String typeName = parameters[i].getType().getTypeName();
                switch (typeName) {
                    case "java.lang.Double":
                        values[i] = Double.parseDouble(valueStr);
                        break;
                    case "java.lang.Integer":
                        values[i] = Integer.parseInt(valueStr);
                        break;
                    default:
                        values[i] = valueStr;
                }
            }
        }
        return values;
    }
}
