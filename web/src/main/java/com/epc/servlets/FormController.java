package com.epc.servlets;

import com.epc.commands.Command;
import com.epc.commands.forms.DeleteCommand;
import com.epc.commands.forms.SaveOrUpdateCommand;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
public class FormController extends HttpServlet {
    public static Logger log = Logger.getLogger(FormController.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        request.setAttribute("action", action);

        Command command=null;

        if ( ("add".equals(action))||("edit".equals(action)) ) {
            command = new SaveOrUpdateCommand();
        }
        if ("delete".equals(action)) {
            command = new DeleteCommand();
        }
        command.execute(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}        