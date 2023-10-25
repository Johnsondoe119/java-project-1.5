package com.bofa.repos;

import com.bofa.exceptions.ClothesNotFoundException;
import com.bofa.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public abstract class GenericRepo<T> {

    public List<T> getAll() {
        return null;
    }

    public T getById(int objectId) throws EntityNotFoundException, ClothesNotFoundException {
        return null;
    }

    public T save(T objectToSave) {
        return null;
    }

    public void update(T objectToUpdate) {
    }

    public void delete(int objectId) {
    }

    public abstract Optional<List<String>> searchByTitle(String title);
}

