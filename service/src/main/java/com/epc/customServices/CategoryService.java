package com.epc.customServices;

import com.epc.BaseService;
import com.epc.beans.Category;
import com.epc.beans.Part;
import com.epc.dao.customDao.CategoryDao;
import com.epc.exceptions.DaoException;
import com.epc.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 */
public class CategoryService extends BaseService<Category> {

    private static CategoryService categoryService;
    Session session;
    Transaction transaction;
    CategoryDao categoryDao = CategoryDao.getCategoryDao();
    List<Category> categories = new ArrayList<Category>();

    private CategoryService() {
    }

    public static CategoryService getCategoryService() {
        if (categoryService==null) {
            categoryService=new CategoryService();
        }
        return categoryService;
    }

    public static Logger log = Logger.getLogger(CategoryService.class);

    public List<Category> getCategories() throws DaoException{
        HibernateUtil.getInstance();
        session = HibernateUtil.getSession();
        transaction = session.beginTransaction();

        try{
            categories = categoryDao.getCategories();
            transaction.commit();
            log.info("get all categories transaction commit");
        }catch (HibernateException e) {
            transaction.rollback();
            log.error("get all categories transaction failed");
        }
        return categories;
    }

    public void deleteCategory(Category category) throws DaoException{
        HibernateUtil.getInstance();
        session = HibernateUtil.getSession();
        transaction = session.beginTransaction();

        try {
            Category persistentCategory = categoryService.load(Category.class, category.getCategory_id());
            log.info("category loaded");
            Category categoryForPartsWithCategory = categoryService.load(Category.class, 1);
            List<Part> partList = persistentCategory.getParts();
            for (Part part : partList) {
                part.setCategory(categoryForPartsWithCategory);
            }
            partList.clear();
            categoryService.delete(persistentCategory);
            transaction.commit();
        }catch (HibernateException e) {
            log.error("delete category transaction failed");
            transaction.rollback();
            throw new DaoException(e);
        }
    }
}        