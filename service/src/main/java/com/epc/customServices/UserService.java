package com.epc.customServices;

import com.epc.BaseService;
import com.epc.beans.User;
import com.epc.dao.customDao.UserDao;
import com.epc.exceptions.DaoException;
import com.epc.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;

/**
 *
 */
public class UserService extends BaseService<User> {
    public static Logger log = Logger.getLogger(UserService.class);
    private static UserService userService;

    private UserService() {
    }

    public static UserService getUserService() {
        if (userService==null) {
            userService=new UserService();
        }
        return userService;
    }

    public User loadUserByName(String name) throws DaoException{
        User user=null;
        Session session = HibernateUtil.getSession();
        try {
            session.getTransaction().begin();
            UserDao userDao = UserDao.getUserDao();
            user = userDao.loadUserByName(name);
            session.getTransaction().commit();
        }catch (DaoException e) {
            log.error("load user by name transaction failed" + e);
            session.getTransaction().rollback();
        }
        return user;
    }
}        