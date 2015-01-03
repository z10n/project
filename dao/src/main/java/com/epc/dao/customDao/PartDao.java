package com.epc.dao.customDao;

import com.epc.beans.Part;
import com.epc.dao.BaseDao;
import com.epc.exceptions.DaoException;
import com.epc.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Class for original Dao methods with Part entity
 * getAllParts()
 * getPartsByCategory()
 * getPartsByCar()
 */
public class PartDao extends BaseDao<Part> {
    List<Part> parts;
    Session session;
    private static PartDao partDao;

    private static Logger log = Logger.getLogger(PartDao.class);

    public PartDao() {
    }

    public static PartDao getPartDao() {
        if (partDao ==null) {
            partDao = new PartDao();
        }
        return partDao;
    }

    public List<Part> getAllParts() throws DaoException {
        try {
            Session session = HibernateUtil.getSession();

            String selectAllParts = "FROM com.epc.beans.Part";
            Query query = session.createQuery(selectAllParts);
            log.info("hql select all parts done");

            parts = query.list();

        }catch (HibernateException e) {
            log.error("hql select all parts error" + e);
            throw new DaoException(e);
        }
        return parts;
    }

    /**
     * one-to-many between category and parts
     * category can have no parts, part must have a category
     * @param category_id
     * @return
     */
    public List<Part> getPartsByCategory(int category_id) throws DaoException{
        try {
            Session session = HibernateUtil.getSession();

            String selectPartsByCategory = "FROM com.epc.beans.Part P WHERE P.category.category_id=:id";
            Query query = session.createQuery(selectPartsByCategory);
            query.setParameter("id", category_id);
            log.info("hql select parts by category done");

            parts = query.list();

        }catch (HibernateException e) {
            log.error("hql select parts by category failed" + e);
            throw new DaoException(e);
        }
        return parts;
    }

    /**
     * many-to-many between parts and cars
     * @param car_id
     * @return List<Part>
     */
    public List<Part> getPartsByCar(int car_id) {
        Session session = HibernateUtil.getSession();
        String selectPartsByCar = "SELECT C.parts FROM com.epc.beans.Car C WHERE C.car_id=:id";
        Query query = session.createQuery(selectPartsByCar);
        query.setParameter("id", car_id);
        List<Part> parts = query.list();
        return parts;
    }

    /**
     * this method select parts by criteria from service and web layers
     * @param criteria
     * @return
     * @throws DaoException
     */
    public List<Part> selectByCriteria(Criteria criteria) throws DaoException{
        session = HibernateUtil.getSession();
        List<Part> partList;

        try {
            partList = (List<Part>) criteria.list();
            log.info("select by criteria done");
        }catch (HibernateException e) {
            log.error("PartService select by criteria error" + e );
            throw new DaoException(e);
        }
        return partList;
    }
}
