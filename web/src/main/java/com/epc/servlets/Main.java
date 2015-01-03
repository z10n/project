package com.epc.servlets;

import com.epc.beans.Category;
import com.epc.beans.Part;
import com.epc.customServices.CategoryService;
import com.epc.customServices.PartService;
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
 * web.xml: /main.jsp
 * This class generate connection to DB,
 * create list of all categories and
 * list of all cars and put them into ServletContext
 * -->/jsp/main
 */
public class Main extends HttpServlet {

    private static Logger log = Logger.getLogger(Main.class);

    private static final long serialVersionUID = 20L;
    public static int maxPages=3;

    /**
     * Method realize connection to DB by hibernate
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        super.init();
    }

    /**
     * web.xml: /main.jsp
     * This method puts list of all categories
     * and list of pages selected by criteria into request
     * -->/jsp/main.jsp
     * @param request
     * @param response
     * @throws ServletException
     * @throws java.io.IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        response.setHeader("Cache-Control","no-cache");
        request.setCharacterEncoding("UTF-8");

        HibernateUtil.getInstance();
        Session session = HibernateUtil.getSession();
        PartService partService = PartService.getPartService();
        CategoryService categoryService = CategoryService.getCategoryService();
        Criteria criteria = session.createCriteria(Part.class);
        int firstResult=0;
        /*this variable used to get number of page with search results */
        String search_page="1";

        /*variable for calculating last page of select. This page will be without "next" button */
        int listSize=0;

        List<Category> categories = null;
        List<Part> partList = null;

        /*this variable is search_page in int*/
        Integer search_page_id=1;
        if (request.getAttribute("search_page")!=null) {
            try {
                search_page_id = Validator.validateInt((String) request.getAttribute("search_page"));
            } catch (ValidateException e) {
                log.error("parse search_page to integer failed (Main.class)" + e);
            }
        }

        if ( (request.getParameter("category_id")!=null)&&(!(request.getParameter("category_id").equals(""))) ) {
            String ctg = request.getParameter("category_id");
            try {
                int ctg_id = Integer.parseInt(ctg);
                criteria.add(Restrictions.eq("category.category_id", ctg_id));
                request.setAttribute("sel_ctg", ctg_id);
            }catch (NumberFormatException e) {
               log.error("parse category_id to integer failed (Main.class)" + e);
            }
        }
        log.info("after parsing main");

        firstResult=(search_page_id-1)*maxPages;
        try {
            categories = categoryService.getCategories();
            criteria.setFirstResult(firstResult);
            criteria.setMaxResults(maxPages);

            partList = partService.selectByCriteria(criteria);

        }catch (DaoException e) {
            log.error("loading data (Main.class) failed" + e);
        }

        listSize = partList.size();
        if (listSize<maxPages) {
            request.setAttribute("next_st", "return false;");
        }

        if (search_page_id==1) {
            request.setAttribute("prev_st", "return false;");

        }
        search_page=search_page_id.toString();

        request.setAttribute("allParts", partList);
        request.setAttribute("categories", categories);
        request.setAttribute("search_page", search_page);

        log.info("main-->main.jsp");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin/main.jsp");
        dispatcher.forward(request, response);
    }

        @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
