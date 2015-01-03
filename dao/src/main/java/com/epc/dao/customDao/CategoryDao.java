package com.epc.dao.customDao;

import com.epc.beans.Category;
import com.epc.dao.BaseDao;
import com.epc.exceptions.DaoException;
import com.epc.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.*;

/**
 *
 */
public class CategoryDao extends BaseDao<Category> {
    List<Category> categories;
    Session session;
    private static CategoryDao categoryDao;

    public static Logger log = Logger.getLogger(CategoryDao.class);

    private CategoryDao() {

    }

    public static CategoryDao getCategoryDao() {
        if (categoryDao==null) {
            categoryDao = new CategoryDao();
        }
        return categoryDao;
    }

    /**
     * this method returns all categories ordered by name
     * @return
     * @throws DaoException
     */
    public List<Category> getCategories() throws DaoException{
        try {
            session = HibernateUtil.getSession();

            String selectAllCategories = "FROM com.epc.beans.Category";
            Query query = session.createQuery(selectAllCategories);
            categories = query.list();
            log.info("Dao select all categories done");

        }catch (HibernateException e) {
            log.error("Dao select all categories failed");
            throw new DaoException(e);
        }
        return categories;
    }
}        