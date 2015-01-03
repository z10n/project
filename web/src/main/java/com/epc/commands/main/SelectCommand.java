package com.epc.commands.main;

import com.epc.commands.Command;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
public class SelectCommand extends HttpServlet implements Command {
    public static Logger log = Logger.getLogger(SelectCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        /*variable to catch next page and previous page actions from main page*/
        String new_page=null;
        /*this variable is search_page into int*/
        Integer search_page_id=1;

        if (request.getParameter("search_page")!=null) {
            String search_page = request.getParameter("search_page");

            try {
                search_page_id = Integer.parseInt(search_page);

            }catch (NumberFormatException e) {
                log.error("transform request parameter search_page to integer failed");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/main.jsp");
                dispatcher.forward(request, response);
            }

            if (request.getParameter("page") != null) {
                new_page = request.getParameter("page");

                if ("next".equals(new_page)) {
                    search_page_id = search_page_id+1;
                }

                if ("previous".equals(new_page)) {
                    search_page_id = search_page_id-1;
                }
            }
        }
        String search_page=search_page_id.toString();
        request.setAttribute("search_page", search_page);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/main.jsp");
        dispatcher.forward(request, response);
    }
}        