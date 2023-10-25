package com.bofa.services;

import com.bofa.entities.Clothes;
import com.bofa.entities.ClothesType;
import com.bofa.entities.Role;
import com.bofa.entities.User;
import com.bofa.exceptions.ClothesNotFoundException;
import com.bofa.exceptions.EntityNotFoundException;
import com.bofa.exceptions.UserAuthenticationException;
import com.bofa.repos.UserRepos;

import java.util.Scanner;

public class UserService {
    public User loginUser(String username, String password) throws UserAuthenticationException {
        User retrievedUser = null;
        try {
            retrievedUser = UserRepos.getUserByUsername(username);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
        if (retrievedUser != null) {
            if (retrievedUser.getPassword().equals(password)) {
                System.out.println("Successfully logged in");
                return retrievedUser;
            } else {
                throw new UserAuthenticationException("Username and/or password do not match");
            }
        } else {
            System.out.println("User with username " + username + " not found.");
        }
        return null;
    }

    public void addClothingToInventory(User owner, Clothes newClothes) {
        if (owner.getRole() == Role.OWNER) {
        } else {
            System.out.println("Only owners can add clothing to the inventory.");
        }
    }

    public User createUser(String username, String password, Role role) {
        if (username.length() >= 8 && password.length() >= 8) {
            User newUser = new User(username, password, role);

            return newUser;
        } else {
            System.out.println("Username/password does not meet requirements");
            return null;
        }
    }

    public User authenticateUser(String username, String password) throws UserAuthenticationException {
        try {
            User retrievedUser = UserRepos.getUserByUsername(username);

            if (retrievedUser != null) {
                if (retrievedUser.getPassword().equals(password)) {
                    System.out.println("Successfully logged in");
                    return retrievedUser;
                } else {
                    throw new UserAuthenticationException("Username and/or password do not match");
                }
            } else {
                System.out.println("User with username " + username + " not found.");
            }
        } catch (EntityNotFoundException e) {
            throw new UserAuthenticationException("An error occurred during login: " + e.getMessage());
        }

        return null;
    }

    private static void addClothingToInventory(User owner) {
        if (owner.getRole() == Role.OWNER) {
            System.out.println("Enter clothing details:");

            System.out.println("Title:");
            Scanner scanner = null;
            String title = scanner.nextLine();

            System.out.println("Color:");
            String color = scanner.nextLine();


            System.out.println("Clothes Type:");
            System.out.println("1. Shirt");
            System.out.println("2. Pants");
            int clothesTypeChoice = collectUserInput();

            ClothesType clothesType = ClothesType.SHIRTS; // Default
            if (clothesTypeChoice == 2) {
                clothesType = ClothesType.PANTS;
            }
            System.out.println("Clothing added to the inventory successfully!");
        } else {
            System.out.println("Only owners can add clothing to the inventory.");
        }
    }

    private static int collectUserInput() {
        int choice = -1;
        boolean isValidInput = false;

        while (!isValidInput) {
            try {
                System.out.print("Enter your choice: ");
                Scanner scanner = null;
                String input = scanner.nextLine();
                choice = Integer.parseInt(input);
                isValidInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        return choice;
    }


    public void addClothingToInventory(Clothes newClothes, User owner) {
        if (owner.getRole() == Role.OWNER) {
            try {
                // Check if the clothing item already exists in the inventory
                ClotheService clotheService = new ClotheService();
                Clothes existingClothes = clotheService.getClothesByTitle(newClothes.getTitle());
                if (existingClothes != null) {
                    System.out.println("This clothing item already exists in the inventory.");
                } else {
                    // The clothing item is not in the inventory, add it
                    Clothes savedClothes = clotheService.addNewClothes(newClothes);
                    System.out.println("Clothing added to the inventory successfully.");
                }
            } catch (ClothesNotFoundException e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        } else {
            System.out.println("Only owners can add clothing to the inventory.");
        }
    }


}

