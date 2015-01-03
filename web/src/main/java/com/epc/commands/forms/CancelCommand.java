package com.epc.commands.forms;

import com.epc.commands.Command;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This command execute return to main page
 * without changing any data.
 */
public class CancelCommand extends HttpServlet implements Command {
    public static Logger log = Logger.getLogger(CancelCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/main.jsp");
        dispatcher.forward(request, response);
    }


}        