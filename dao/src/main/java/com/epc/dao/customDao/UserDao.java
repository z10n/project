package com.epc.dao.customDao;

import com.epc.beans.User;
import com.epc.dao.BaseDao;
import com.epc.exceptions.DaoException;
import com.epc.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 *
 */
public class UserDao extends BaseDao<User> {
    public static Logger log = Logger.getLogger(UserDao.class);
    private static UserDao userDao;
    Session session = HibernateUtil.getSession();
    private UserDao() {
    }

    public static UserDao getUserDao() {
        if (userDao==null) {
            userDao = new UserDao();
        }
        return userDao;
    }

    public User loadUserByName(String name) throws DaoException{
        User user = new User();
        try {
            Session session = HibernateUtil.getSession();

            String loadUser = "SELECT U FROM com.epc.beans.User U WHERE U.user_name=:name";
            Query query = session.createQuery(loadUser);
            query.setParameter("name", name);
            List<User> userList = query.list();

            for (User user1 : userList) {
                user = user1;
            }
        }catch (HibernateException e) {
            log.error("loading user by name failed");
            throw new DaoException(e);
        }
        return user;
    }
}        