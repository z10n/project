package com.epc;

import com.epc.dao.BaseDao;
import com.epc.exceptions.DaoException;
import com.epc.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;

/**
 * This class realize all basic CRUD operations with Entities.
 */
public class BaseService<T> implements Service<T> {

    private static Logger log = Logger.getLogger(BaseService.class);

    private static final ThreadLocal flags = new ThreadLocal();

    private static Boolean flag;
    BaseDao<T> baseDao;
    Session session;
    Transaction transaction;

    protected BaseService() {
        HibernateUtil.getInstance();
        baseDao= new BaseDao<T>();
    }

    /**
     * ThreadLocal variable flag as a flag
     * of transaction status.
     * true = transaction is active
     * false = transaction is not active
     * @return flag
     */

    public static boolean getFlag() {
        flag = (Boolean) flags.get();
        if (flag == null) {
            flag = false;
            flags.set(flag);
        }
        return flag;
    }

    public static void setFlag(Boolean flag) {
        BaseService.flag = flag;
    }

    @Override
    public boolean saveOrUpdate(T t) throws DaoException{
        session = HibernateUtil.getSession();
        transaction = session.getTransaction();

        if (!transaction.isActive()) {
            session.getTransaction().begin();
            flag=false;
        }
        else {flag=true;}

        try {
            baseDao.saveOrUpdate(t);
            if (!flag) {
                session.getTransaction().commit();
            }
            return true;
        }
        catch (HibernateException e) {
            transaction.rollback();
            flag=false;
            log.error("error saveOrUpdate transaction " + e);
            return false;
        }
    }

    @Override
    public boolean delete(T t) throws DaoException{
        session = HibernateUtil.getSession();
        transaction = session.getTransaction();

        if (!transaction.isActive()) {
            session.getTransaction().begin();
            flag=false;
        }
        else {flag=true;}

        try {
            baseDao.delete(t);

            if (!flag) {
                session.getTransaction().commit();
            }

            return true;
        }catch (HibernateException e) {
            transaction.rollback();
            flag=false;
            log.error("error delete transaction " + e);
            return  false;
        }
    }

    public T load(Class<T> clazz, Serializable id) throws DaoException{
        session = HibernateUtil.getSession();
        transaction = session.getTransaction();
        T tClass;

        if (!transaction.isActive()) {
            session.getTransaction().begin();
            flag=false;
        }
        else {flag=true;}

        try{
            tClass = baseDao.load(clazz, id);

            if (!flag) {
                session.getTransaction().commit();
            }

        }catch (HibernateException e) {
            transaction.rollback();
            flag=false;
            log.error("error delete transaction " + e);
            throw new DaoException(e);
        }
        return tClass;
    }

    public void refresh(T t) throws DaoException{
        session = HibernateUtil.getSession();
        transaction = session.getTransaction();

        if (!transaction.isActive()) {
            session.getTransaction().begin();
            flag=false;
        }
        else {flag=true;}

        try{
            baseDao.refresh(t);

            if (!flag) {
                session.getTransaction().commit();
                log.info("refresh transaction done");
            }
        }catch (HibernateException e) {
            transaction.rollback();
            flag=false;
            log.error("error refresh transaction " + e);
            throw new DaoException(e);
        }
    }
}
