package com.bofa.daos;

import com.bofa.entities.Clothes;

import java.util.List;

public abstract class GeneralDAO<T> {

    //READ
    T getById(int id);

    //CREATE
    T save(T objectToBeSaved);

    //UPDATE
    T update(T objectToBeUpdated);


    //DELETE
    void delete(T objectToBeDeleted);

    public abstract List<Clothes> getAll();
}
