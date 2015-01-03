package com.epc.customServices;

import com.epc.BaseService;
import com.epc.beans.Car;
import com.epc.beans.Category;
import com.epc.beans.Part;
import com.epc.dao.customDao.PartDao;
import com.epc.exceptions.DaoException;
import com.epc.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
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
public class PartService extends BaseService<Part> {

    public static Logger log = Logger.getLogger(PartService.class);
    private static PartService partService;
    Session session;
    Transaction transaction;
    PartDao partDao = new PartDao();
    List<Part> parts = new ArrayList<Part>();

    private PartService() {
    }

    public static PartService getPartService() {
        if (partService ==null) {
            partService = new PartService();
        }
        return partService;
    }

    public List<Part> getAllParts() throws DaoException {
        session = HibernateUtil.getSession();
        transaction = session.getTransaction();
        transaction.begin();
        try {
            parts = partDao.getAllParts();
            transaction.commit();
            log.info("get all parts transaction commit");
            return parts;
        }catch (DaoException e) {
            transaction.rollback();
            log.info("get all parts transaction rollback " + e);
            return null;
        }
    }

    public List<Part> getPartsByCategory(int category_id) throws DaoException{
        session = HibernateUtil.getSession();
        transaction = session.getTransaction();
        transaction.begin();
        try {
            parts = partDao.getPartsByCategory(category_id);
            transaction.commit();
            log.info("get parts by category transaction commit");
            return parts;
        }catch (DaoException e) {
            transaction.rollback();
            log.error("get parts by category transaction rollback " + e);
            return null;
        }
    }

    public List<Part> selectByCriteria(Criteria criteria) throws DaoException{
        session = HibernateUtil.getSession();
        List<Part> partList = null;
        transaction = session.beginTransaction();
        try {
            partList = partDao.selectByCriteria(criteria);
            transaction.commit();
            log.info("select by criteria done");
        }catch (HibernateException e) {
            transaction.rollback();
            log.error("PartService select by criteria error" + e );
            throw new DaoException(e);
        }
        return partList;
    }

    public boolean savePart(Part part) throws DaoException{
        session = HibernateUtil.getSession();
        transaction = session.beginTransaction();
        int id = part.getCategory().getCategory_id();
        Category category;
        try {
            category = CategoryService.getCategoryService().load(Category.class, id);
            part.setCategory(category);
            List<Part> partList = new ArrayList<Part>();
            partList.add(part);
            category.setParts(partList);
            partService.saveOrUpdate(part);
            transaction.commit();
            log.info("savePart transaction commit");
            partService.refresh(part);
            log.info("savePart refresh part");
            CategoryService.getCategoryService().refresh(category);
            log.info("savePart refresh category");
            return true;
        }catch (DaoException e) {
            log.error("savePart load category transaction error " + e);
            transaction.rollback();
            return false;
        }
    }

    public boolean deletePart(Part part) throws DaoException {
        session = HibernateUtil.getSession();
        transaction = session.beginTransaction();
        try {
            Part persistentPart = load(Part.class, part.getPart_id());
            Category category = persistentPart.getCategory();
            category.getParts().remove(persistentPart);
            List<Car> cars = persistentPart.getCars();
            for (Car car : cars) {
                car.getParts().remove(part);
            }
            delete(persistentPart);
            transaction.commit();
            log.info("deletePart transaction commit");
        }catch (DaoException e) {
            log.error("deletePart transaction error " + e);
            transaction.rollback();
            return false;
        }
        return true;
    }
}