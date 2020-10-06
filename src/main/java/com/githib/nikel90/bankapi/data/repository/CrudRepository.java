package com.githib.nikel90.bankapi.data.repository;

public interface  CrudRepository <E, ID> {
    E save (E e);
    E getById (ID id);
    Iterable<E> getAll ();
    void removeById (ID id);
}
