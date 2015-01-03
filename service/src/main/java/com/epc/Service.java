package com.epc;

import com.epc.exceptions.DaoException;

import java.io.Serializable;

/**
 *
 */
public interface Service<T> {

    boolean saveOrUpdate(T t) throws DaoException;

    T load(Class<T> clazz, Serializable id) throws DaoException;

    boolean delete(T t) throws DaoException;
}
