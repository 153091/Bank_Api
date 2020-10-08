package com.github.nikel90.bankapi.data.repository;

import java.sql.SQLException;

public interface CrudRepository<E, ID> {
    E save (E e) throws SQLException;
    E getById (ID id) throws SQLException;
    Iterable<E> getAll () throws SQLException;
    boolean removeById (ID id) throws SQLException;
    void update(E e) throws SQLException;
}
