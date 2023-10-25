package com.bofa.services;

import com.bofa.entities.Clothes;
import com.bofa.entities.Role;
import com.bofa.entities.User;
import com.bofa.exceptions.ClothesNotFoundException;
import com.bofa.exceptions.EntityNotFoundException;
import com.bofa.repos.ClothesRepo;

import java.util.List;

public class ClotheService {
    private ClothesRepo clothesRepo = new ClothesRepo();

    public List<Clothes> getAllClothes() {
        return clothesRepo.getAll();
    }

    public void markClothesUnavailable(int clothesId) throws ClothesNotFoundException, EntityNotFoundException {
        Clothes clothesToUpdate = clothesRepo.getById(clothesId);
        clothesToUpdate.setInStock(false);
        clothesRepo.update(clothesToUpdate);
    }

    public boolean checkClothesAvailability(int clothesID) throws ClothesNotFoundException, EntityNotFoundException {
        Clothes clothes = clothesRepo.getById(clothesID);
        return clothes.isInStock();
    }

    public Clothes addNewClothes(Clothes newClothes) {
        return clothesRepo.save(newClothes);
    }

    public void markClothesAvailable(int clothesId) throws ClothesNotFoundException, EntityNotFoundException {
        Clothes clothesToUpdate = clothesRepo.getById(clothesId);
        clothesToUpdate.setInStock(true);
        clothesRepo.update(clothesToUpdate);
    }

    public void addToInventory(User owner, Clothes clothing) {
        if (owner.getRole() == Role.OWNER) {
            if (!clothing.isInStock()) {
                clothing.setInStock(true);
                clothesRepo.update(clothing);
                System.out.println("Clothing added to inventory successfully.");
            } else {
                System.out.println("Clothing is already in the inventory.");
            }
        } else {
            System.out.println("Only owners can add clothing to the inventory.");
        }
    }

    public Clothes getClothesByTitle(String title) throws ClothesNotFoundException {
        List<Clothes> allClothes = getAllClothes();
        for (Clothes clothes : allClothes) {
            if (clothes.getTitle().equalsIgnoreCase(title)) {
                return clothes;
            }
        }
        throw new ClothesNotFoundException("Clothing with title '" + title + "' not found in the inventory.");
    }

    public Clothes getClothesById(int selectedClothingId) throws ClothesNotFoundException {
        for (Clothes clothes : getAllClothes()) {
            if (clothes.getIscn() == selectedClothingId) {
                return clothes;
            }
        }
        throw new ClothesNotFoundException("Clothing with ID " + selectedClothingId + " not found in the inventory.");
    }
}
