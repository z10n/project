package com.epc.dao;

import com.epc.beans.Car;
import com.epc.beans.Category;
import com.epc.beans.Part;
import com.epc.beans.PartDetail;
import com.epc.exceptions.DaoException;

/**
 *
 */
public class UnitsForTest {

    protected static Part fillTestPart() throws DaoException{
        Part part = new Part();
        PartDetail partDetail = new PartDetail();
        partDetail.setPart_name("test_name");
        part.setPartDetail(partDetail);
        return part;
    }

    protected static Car fillTestCar() throws DaoException{
        Car car = new Car();
        car.setCar_name("test_car_name");
        return car;
    }

    protected static Category fillCategory() {
        Category category = new Category();
        category.setCategory_name("test_category_name");
        return category;
    }
}
