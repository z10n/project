package com.epc.commands.main;

import com.epc.beans.Category;
import com.epc.beans.Part;
import com.epc.commands.Command;
import com.epc.customServices.CategoryService;
import com.epc.customServices.PartService;
import com.epc.exceptions.DaoException;
import com.epc.util.HibernateUtil;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * This command execute jump
 * to PartForm.jsp
 */
public class PartCommand extends HttpServlet implements Command {
    public static Logger log = Logger.getLogger(PartCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        HibernateUtil.getInstance();
        CategoryService categoryService = CategoryService.getCategoryService();
        PartService partService = PartService.getPartService();

        List<Category> categories = null;
        Part part = null;

        try {
            categories = categoryService.getCategories();
        }catch (DaoException e) {
            log.error("loading data (MainController.class) failed" + e);
        }

        request.setAttribute("categories", categories);

        if (request.getAttribute("id")!=null) {

            String id = (String) request.getAttribute("id");
            long longId = 0L;
            try {
                longId = Long.parseLong(id);

            } catch (NumberFormatException e) {
                log.error("id of part trying to load is incorrect");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/main.jsp");
                dispatcher.forward(request, response);
            }

            try {
                part = partService.load(Part.class, longId);
                request.setAttribute("selected_category", part.getCategory().getCategory_id());
                log.info("part loaded for edit");
            } catch (DaoException e) {
                log.error("part loading failed");
            }
        }
        request.setAttribute("part", part);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin/forms/partForm.jsp");
        dispatcher.forward(request, response);
    }
}        