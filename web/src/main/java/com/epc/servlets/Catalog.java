package com.epc.servlets;

import com.epc.beans.*;
import com.epc.customServices.*;
import com.epc.exceptions.DaoException;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * web.xml: /catalog.jsp
 * This class generate connection to DB,
 * create list of all parts in categories and
 * put them into ServletContext
 * -->/jsp/cars
 */
public class Catalog extends HttpServlet {

    private static Logger log = Logger.getLogger(Catalog.class);

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
     * web.xml: /catalog.jsp
     * This method puts list of all parts selected by criteria into request
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

        PartService partService = PartService.getPartService();
        CategoryService categoryService = CategoryService.getCategoryService();
        List<Part> partList = null;
        List<Category> categoryList = null;
        //default category ID shown at catalog loading
        int cat_id = 1;
        try {
            categoryList = categoryService.getCategories();
            //заменить cat_id на следующий ID категории по нажатию на ссылку
            partList = partService.getPartsByCategory(cat_id);
            for(Part parts: partList) {
                log.info("part name: "+ parts.getPartDetail().getPart_name());
            }
        }catch (DaoException e) {
            log.error("loading data (Main.class) failed" + e);
        }
        request.setAttribute("parList", partList);
        request.setAttribute("categoryList", categoryList);

        log.info("cars.jsp -->catalog.jsp");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/catalog.jsp");
        dispatcher.forward(request, response);
    }

        @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
