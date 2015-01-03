package com.epc.filters;

import com.epc.beans.User;
import com.epc.customServices.UserService;
import com.epc.exceptions.DaoException;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filter to authentication.
 * protect admin part
 */
public class AuthFilter implements Filter {
    private UserService userService = UserService.getUserService();
    private ServletContext context;
    Logger log = Logger.getLogger(this.getClass());

    public void init(FilterConfig fConfig) throws ServletException {
        this.context = fConfig.getServletContext();

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        if (session == null) {
            res.sendRedirect("/login.jsp");
        }
        else if (session.getAttribute("user_name") == null) {
            if ( (req.getParameter("user_name")!=null)&&(req.getParameter("user_pass")!=null)) {
                String name = req.getParameter("user_name");
                String pass = req.getParameter("user_pass");

                User user = new User();
                user.setUser_name(name);
                user.setUser_pass(pass);
                boolean check = false;
                User userFromDB = null;
                try {
                    userFromDB = userService.loadUserByName(name);
                    check = user.equals(userFromDB);

                } catch (DaoException e) {
                    log.error("user loading in filter failed" + e);
                }
                if ((check) && (userFromDB.getUser_name() != null)) {

                    session.setAttribute("user_name", name);
                    session.setMaxInactiveInterval(3 * 60);
                    chain.doFilter(request, response);
                } else {
                    res.sendRedirect("/login.jsp");
                }
            }else {
                res.sendRedirect("/login.jsp");
            }
        }
        else {
            chain.doFilter(request, response);
        }
    }

    public void destroy() {
        //close any resources here
    }
}