package com.epc.commands.forms;

import com.epc.beans.Car;
import com.epc.beans.Category;
import com.epc.beans.Part;
import com.epc.beans.PartDetail;
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
import java.util.*;
/**
 * This command saves form data to DB and returns user to main admin page
 */
public class SaveOrUpdateCommand extends HttpServlet implements Command {
    public static Logger log = Logger.getLogger(SaveOrUpdateCommand.class);

    /**
     * This command executes save operations.
     * Objects: part, category
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        log.info("execute save command");

        String obj = request.getParameter("obj");
        String action= request.getParameter("action");
        if (Validator.validateString(obj)) {

            if ("part".equals(obj)) {
                log.info("object=part");
                Category category = new Category();
                if (request.getParameter("category") != null) {
                    int ctg_id = 0;
                    try {
                        ctg_id = Validator.validateInt(request.getParameter("category"));
                    } catch (ValidateException e) {
                        log.error("category id number format exception" + e);
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/main.jsp");
                        dispatcher.forward(request, response);
                    }
                    category.setCategory_id(ctg_id);
                }

                Part part = getPart(request, response);

                part.setCategory(category);
                PartService partService = PartService.getPartService();
                try {
                    partService.savePart(part);
                } catch (DaoException e) {
                    log.error("save command error - cant save part " + e);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/main.jsp");
                    dispatcher.forward(request, response);
                }
            }

            if ("category".equals(obj)) {
                log.info("object=part");
                CategoryService categoryService = CategoryService.getCategoryService();

                String category_name = request.getParameter("category_name");
                if (Validator.validateString(category_name)) {
                    Category category=null;
                    if ("add".equals(action)) {
                        category = new Category();
                    }
                    if ( ("edit".equals(action)) ) {
                        try {
                            Validator.validateInt(request.getParameter("category_edit"));
                            String category_id = request.getParameter("category_edit");
                            int id = Integer.parseInt(category_id);
                            category = categoryService.load(Category.class, id);
                        } catch (DaoException e) {
                            log.error("save command error - cant load category " + e);
                            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/main.jsp");
                            dispatcher.forward(request, response);
                        } catch (ValidateException e) {
                            log.error("save command error - cant validate category_id " + e);
                            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/main.jsp");
                            dispatcher.forward(request, response);
                        }
                    }
                    try {
                        category.setCategory_name(category_name);
                    }catch (NullPointerException e) {
                        log.error("save command error - cant set category name to category " + e);
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/main.jsp");
                        dispatcher.forward(request, response);
                    }
                    try {
                        categoryService.saveOrUpdate(category);
                    } catch (DaoException e) {
                        log.error("save command error - cant save category " + e);
                    }
                }
            }
        }
        log.info("save command-->main");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/main.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * This method creates part and fills its parameters for
     * "add" command and loads part, fills its parameters
     * for "edit" command by form      *
     * @param request
     * @param response
     * @return Part
     * @throws ServletException
     * @throws IOException
     */
    public Part getPart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part part = null;
        if ((request.getParameter("id") != null) && !(request.getParameter("id").equals(""))) {
            long id = 0;
            try {
                id = Validator.validateLong(request.getParameter("id"));
                part = PartService.getPartService().load(Part.class, id);
            } catch (ValidateException e) {
                log.error("part id number format exception" + e);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/main.jsp");
                dispatcher.forward(request, response);
            }catch (DaoException e) {
                log.error("part load exception " + e);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/main.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            part = new Part();
            PartDetail partDetail = new PartDetail();
            Car car = new Car();
            part.setPartDetail(partDetail);
            List<Car> cars = new ArrayList<Car>();
            part.setCars(cars);
        }
        request.setAttribute("getPart", part);
        part = fillPart(request, response);
        return part;
    }

    /**
     * This method fills part with form parameters
     * @param response
     * @return filled part
     */
    public Part fillPart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part part = (Part) request.getAttribute("getPart");
        if (Validator.validateString(request.getParameter("name"))) {
            part.getPartDetail().setPart_name(request.getParameter("name"));
        }
        return part;
    }
}

