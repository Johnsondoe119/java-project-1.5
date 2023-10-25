package com.bofa.repos;

import com.bofa.entities.Clothes;
import com.bofa.exceptions.ClothesNotFoundException;
import com.bofa.utils.MockDB;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClothesRepo extends GenericRepo<Clothes> {

    @Override
    public List<Clothes> getAll() {
        return MockDB.clothesList;
    }

    @Override
    public Clothes getById(int clothesIscn) throws ClothesNotFoundException {

        Optional<Clothes> resultClothes = MockDB.clothesList.stream()
                .filter(clothes -> clothes.getIscn() == clothesIscn)
                .findFirst();

        if(resultClothes.isPresent()){
            return resultClothes.get();
        } else{
            throw new ClothesNotFoundException("Clothes was not found!");
        }
    }

    @Override
    public Clothes save(Clothes clothesToSave) {
        MockDB.clothesList.add(clothesToSave);
        return clothesToSave;
    }

    @Override
    public void update(Clothes clothesToUpdate) {
        int index = 0;
        for (Clothes clothes : MockDB.clothesList) {
            if (clothes.getIscn() == clothesToUpdate.getIscn()) {
                index = MockDB.clothesList.indexOf(clothes);
            }
        }
        MockDB.clothesList.set(index, clothesToUpdate);
    }

    @Override
    public void delete(int clothesIscn) {
        MockDB.clothesList.removeIf(clothes -> clothes.getIscn() == clothesIscn);
    }

    @Override
    public Optional<List<String>> searchByTitle(String title){
        List<String> clothes = MockDB.clothesList.stream()
                .filter(clothes -> clothes.getTitle().equals(title))
                .map(clothes -> clothes.getTitle())
                .sorted()
                .collect(Collectors.toList());

        Optional<List<String>> optionalClothes;
        if(clothes.size() > 0){
            optionalClothes = Optional.of(clothes);
        } else {
            optionalClothes = Optional.empty();
        }

        return optionalClothes;
    }
}
