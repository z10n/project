package com.epc.customServices;

import com.epc.BaseService;
import com.epc.beans.Car;
import com.epc.dao.customDao.CarDao;
import com.epc.exceptions.DaoException;
import com.epc.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.*;

/**
 *
 */
public class CarService extends BaseService<Car> {
    public static Logger log = Logger.getLogger(CarService.class);
    private static CarService carService;
    CarDao carDao = CarDao.getCarDao();
    List<Car> cars = new ArrayList<Car>();
    Session session;
    Transaction transaction;
    private CarService(){
    }

    public static CarService getCarService() {
        if ( carService ==null) {
            carService = new CarService();
        }
        return carService;
    }

    public List<Car> getCars() throws DaoException {
        HibernateUtil.getInstance();
        session = HibernateUtil.getSession();
        transaction = session.beginTransaction();

        try{
            cars = carDao.getCars();
            transaction.commit();
            log.info("get all categories transaction commit");
        }catch (HibernateException e) {
            transaction.rollback();
            log.error("get all categories transaction failed");
        }
        return cars;
    }
}        