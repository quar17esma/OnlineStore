package com.serhii.shutyi.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDAO<T> extends AutoCloseable{
    List<T> findAll();
    Optional<T> findById(int id);
    boolean update(T item);
    boolean delete(int id);
    int insert(T item);
}
