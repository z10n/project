package com.epc.servlets;

import com.epc.beans.*;
import com.epc.customServices.*;
import com.epc.exceptions.DaoException;
import com.epc.exceptions.ValidateException;
import com.epc.util.HibernateUtil;
import com.epc.validator.Validator;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * web.xml: /cars.jsp
 * This class generate connection to DB,
 * create list of all cars and
 * put them into ServletContext
 * -->/jsp/cars
 */
public class Cars extends HttpServlet {

    private static Logger log = Logger.getLogger(Cars.class);

    private static final long serialVersionUID = 20L;

    /**
     * Method realize connection to DB by hibernate
     * @throws javax.servlet.ServletException
     */
    @Override
    public void init() throws ServletException {
        super.init();
    }

    /**
     * web.xml: /cars.jsp
     * This method puts list of all cars selected by criteria into request
     * -->/jsp/main.jsp
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setHeader("Cache-Control","no-cache");
        request.setCharacterEncoding("UTF-8");

        CarService carService = CarService.getCarService();
        List<Car> carList = null;

        try {
            carList = carService.getCars();

        }catch (DaoException e) {
            log.error("loading data (Main.class) failed" + e);
        }
        request.setAttribute("carList", carList);

        log.info("login-->cars.jsp");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/cars.jsp");
        dispatcher.forward(request, response);
    }

        @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
