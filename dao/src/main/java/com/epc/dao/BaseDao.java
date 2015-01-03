package com.epc.dao;

import com.epc.exceptions.DaoException;
import com.epc.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

/**
 *
 */
public class BaseDao<T> implements Dao<T>  {

    private static Logger log = Logger.getLogger(BaseDao.class);
    HibernateUtil util;
//    Class clazz;
    Session session;

    public BaseDao() {
        HibernateUtil.getInstance();
        //clazz = getPersistentClass();
    }

    public boolean saveOrUpdate(T t) throws DaoException {
        log.info("saveOrUpdate:" + t);
        try{
            session = HibernateUtil.getSession();
            session.saveOrUpdate(t);
            log.info("saveOrUpdate:" + t + " done");
            return true;

        }
        catch (HibernateException e) {
            log.error("error saveOrUpdate " + t + e);
            //throw new DaoException(e);
            return false;
        }
    }

    public T load(Class<T> clazz, Serializable id) throws DaoException {
        log.info("load:" + id);
        T t = null;
        try {
            session = HibernateUtil.getSession();
            t = (T) session.load(clazz, id);
            log.info("load:" + id + " done");
        }
        catch (HibernateException e) {
            log.error("error load " + this.getPersistentClass() + " in Dao" + e);
            throw new DaoException(e);
        }

        return t;
    }

    public boolean delete(T t) throws DaoException {
        log.info("load:" + t);
        try {
            session = HibernateUtil.getSession();
            session.delete(t);
            log.info("load:" + t + " done");
            return true;
        }
        catch (HibernateException e) {
            log.error("error delete " + getPersistentClass() + e);
            return false;
        }
    }

    public void refresh(T t) throws DaoException{
        log.info("refresh:" + t);
        try {
            session = HibernateUtil.getSession();
            session.refresh(t);
            log.info("refresh:" + t + " done");
        }catch (HibernateException e) {
            log.error("error refresh:" + t + " in Dao " + e);
            throw new DaoException(e);
        }

    }

    private Class getPersistentClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }





}
