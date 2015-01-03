package com.epc.dao;

import com.epc.beans.Car;
import com.epc.beans.Category;
import com.epc.beans.Part;
import com.epc.beans.User;
import com.epc.exceptions.DaoException;
import com.epc.util.HibernateUtil;
import junit.framework.Assert;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;

/**
 * Class for testing usual hibernate methods
 * create persistence objects,
 * update, read, then delete
 * Objects: part, car, category, user
 */
public class TestBaseDao  {
    private Part part_for_CRUD_test;
    private Car car_for_CRUD_test;
    private Category category_for_CRUD_test;
    private User user_for_CRUD_test;
    HibernateUtil util;
    boolean check;

    @Before
    public void init() {
        part_for_CRUD_test = new Part();
        car_for_CRUD_test = new Car();
        category_for_CRUD_test = new Category();
        user_for_CRUD_test = new User();
    }

    @Test
    public void test_CRUD_part() throws DaoException{
        BaseDao<Part> partBaseDao = new BaseDao<Part>();
        BaseDao<Category> categoryBaseDao = new BaseDao<Category>();
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        part_for_CRUD_test = UnitsForTest.fillTestPart();
        category_for_CRUD_test = UnitsForTest.fillCategory();
        part_for_CRUD_test.setCategory(category_for_CRUD_test);
        boolean checkCategory = categoryBaseDao.saveOrUpdate(category_for_CRUD_test);
        Assert.assertTrue("saving category test failed", checkCategory);
        transaction.commit();
        category_for_CRUD_test.getParts().add(part_for_CRUD_test);
        transaction = session.beginTransaction();
        boolean check = partBaseDao.saveOrUpdate(part_for_CRUD_test);
        Assert.assertTrue("saving part test failed", check);
        transaction.commit();
    }

    @Test
    public void test_CRUD_category() throws DaoException{
        BaseDao<Category> categoryBaseDao = new BaseDao<Category>();
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        category_for_CRUD_test = UnitsForTest.fillCategory();
        category_for_CRUD_test.setCategory_name("test test test");
        transaction.commit();
        boolean checkCategory = categoryBaseDao.saveOrUpdate(category_for_CRUD_test);
        Assert.assertTrue("saving category test failed", checkCategory);
    }
}
