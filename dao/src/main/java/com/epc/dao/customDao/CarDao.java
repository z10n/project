package com.epc.dao.customDao;

import com.epc.beans.Car;
import com.epc.dao.BaseDao;
import com.epc.exceptions.DaoException;
import com.epc.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;
import java.util.Set;

/**
 *
 */
public class CarDao extends BaseDao<Car> {

    private static CarDao carDao;

    public static Logger log = Logger.getLogger(CarDao.class);
    Session session;
    List<Car> cars;
    private CarDao() {
    }

    public static CarDao getCarDao() {
        if (carDao ==null) {
            carDao =new CarDao();
        }
        return carDao;
    }

    /**
     * this method returns all categories ordered by name
     * @return
     * @throws com.epc.exceptions.DaoException
     */
    public List<Car> getCars() throws DaoException {
        try {
            session = HibernateUtil.getSession();
            String selectAllCars = "FROM com.epc.beans.Car";
            Query query = session.createQuery(selectAllCars);
            cars = query.list();
            log.info("Dao select all cars done");
        }catch (HibernateException e) {
            log.error("Dao select all cars failed");
            throw new DaoException(e);
        }
        return cars;
    }
}        