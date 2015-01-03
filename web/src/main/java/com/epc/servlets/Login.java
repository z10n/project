package com.epc.servlets;

import com.epc.customServices.CategoryService;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * web.xml: /main.jsp
 * This class generate connection to DB,
 * create list of all categories and
 * list of all films and put them into ServletContext
 * -->/jsp/main
 */
public class Login extends HttpServlet {

    private static Logger log = Logger.getLogger(Login.class);

    private static final long serialVersionUID = 1L;
    CategoryService categoryService;


    @Override
    public void init() throws ServletException {
        super.init();
    }

    /**
     * start point to going admin part.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        response.setHeader("Cache-Control","no-cache");
        request.setCharacterEncoding("UTF-8");

        ServletContext servletContext = getServletContext();
        RequestDispatcher dispatcher = servletContext.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
        dispatcher.forward(request, response);
    }

        @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
