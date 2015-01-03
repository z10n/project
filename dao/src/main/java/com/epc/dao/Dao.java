package com.epc.dao;

import com.epc.exceptions.DaoException;

import java.io.Serializable;

/**
 * generic interface for all dao classes
 */
public interface Dao<T> {

    boolean saveOrUpdate(T t) throws DaoException;

    //T get(Serializable id) throws DaoException;

    T load(Class<T> clazz, Serializable id) throws DaoException;

    boolean delete(T t) throws DaoException;
}
