package com.epc.servlets;

import com.epc.commands.Command;
import com.epc.commands.forms.CancelCommand;
import com.epc.commands.forms.DeleteCommand;
import com.epc.commands.forms.SaveOrUpdateCommand;
import com.epc.commands.header.LogOutCommand;
import com.epc.commands.main.PartCommand;
import com.epc.commands.main.SelectCommand;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
public class MainController extends HttpServlet {
    public static Logger log = Logger.getLogger(MainController.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        request.setAttribute("action", action);

        Command command=null;

        if ("select".equals(action)) {
            command = new SelectCommand();

        } else

        if ( ("edit".equals(action))||("add".equals(action)) ) {
            String obj = request.getParameter("obj");
            request.setAttribute("obj", obj);

            String id = request.getParameter("id");
            request.setAttribute("id", id);

            if ("part".equals(obj)) {
                command = new PartCommand();
            }else

            if ("category".equals(obj)) {
                command = new SaveOrUpdateCommand();
            }
            else {
                command = new CancelCommand();
            }
        }else

        if ("delete".equals(action)) {
            command = new DeleteCommand();
        } else

        if ("logout".equals(action)) {
            command = new LogOutCommand();
        }

        else {
            command = new CancelCommand();
        }
        command.execute(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}        