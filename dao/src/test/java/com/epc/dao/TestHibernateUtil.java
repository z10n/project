package com.epc.dao;

import com.epc.util.HibernateUtil;
import junit.framework.Assert;
import org.hibernate.Session;
import org.junit.Test;

/**
 * Class for testing hibernate connection to DB
 * SessionFactory creation test
 * Session creation test
 */
public class TestHibernateUtil {

    HibernateUtil util;

    @Test
    public void test_SessionFactory_creation(){
        util = HibernateUtil.getInstance();
        Assert.assertNotNull("SessionFactory building error", util);
    }

    @Test
    public void test_Session_creation() {
        util = HibernateUtil.getInstance();
        Session session = HibernateUtil.getSession();
        Assert.assertNotNull("Session building error", session);
    }
}
