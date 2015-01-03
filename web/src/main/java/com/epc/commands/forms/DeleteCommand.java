package com.epc.commands.forms;

import com.epc.beans.Category;
import com.epc.beans.Part;
import com.epc.commands.Command;
import com.epc.customServices.CategoryService;
import com.epc.customServices.PartService;
import com.epc.exceptions.DaoException;
import com.epc.exceptions.ValidateException;
import com.epc.validator.Validator;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This command save form data to DB and return user to main admin page
 */
public class DeleteCommand extends HttpServlet implements Command {
    public static Logger log = Logger.getLogger(DeleteCommand.class);

    /**
     * This command execute delete operations.
     * Objects: part, category
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        log.info("execute delete command");

        String obj = request.getParameter("obj");

        /**
         * Deleting part
         */
        if ("part".equals(obj)) {
            log.info("object=part");

            if (Validator.validateNotEmptyString(request.getParameter("id"))) {
                int id = 0;
                try {
                    id = Validator.validateInt(request.getParameter("id"));
                    Part part = new Part();
                    part.setPart_id(id);
                    PartService.getPartService().deletePart(part);
                } catch (ValidateException e) {
                    log.error("part id number format exception" + e);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/main.jsp");
                    dispatcher.forward(request, response);
                } catch (DaoException e) {
                    log.error("part load exception " + e);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/main.jsp");
                    dispatcher.forward(request, response);
                }
            }
        }

        /**
         * Deleting category
         */
        if ("category".equals(obj)) {
            log.info("object=category");
            CategoryService categoryService = CategoryService.getCategoryService();


            if (Validator.validateNotEmptyString(request.getParameter("category_edit"))) {
                int id = 0;
                try {
                    Validator.validateInt(request.getParameter("category_edit"));
                    Category category=new Category();
                    String category_id = request.getParameter("category_edit");
                    id = Integer.parseInt(category_id);
                    category.setCategory_id(id);

                    categoryService.deleteCategory(category);
                    log.info("category deleted");
                } catch (DaoException e) {
                    log.error("delete command error - cant load category " + e);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/main.jsp");
                    dispatcher.forward(request, response);
                } catch (ValidateException e) {
                    log.error("delete command error - cant validate category_id " + e);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/main.jsp");
                    dispatcher.forward(request, response);
                }
            }
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/main.jsp");
        dispatcher.forward(request, response);
    }
}

