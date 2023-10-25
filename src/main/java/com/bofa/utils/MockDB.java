package com.bofa.utils;

import com.bofa.entities.Clothes;
import com.bofa.entities.ClothesType;
import com.bofa.entities.Role;
import com.bofa.entities.User;

import java.util.ArrayList;
import java.util.List;


public class MockDB {
    //list of registered users
    //you can call this in the admin menu - this can complete one of your stories REMEMBER!!!!!!!!!!
    public static List<User> registeredUser = new ArrayList<>();
    public static List<Clothes> clothesList = new ArrayList<>(); //clothes inventory array

    //mock login data

    static {
        User user1 = new User("johnnydoe", "adminpass", Role.OWNER);
        User user2 = new User("pokemonmaster", "pikachu", Role.CUSTOMER);
        User user3 = new User("soccerplayer", "chelseafc", Role.CUSTOMER);
        User user4 = new User("lunapupzoey", "ilovemydogs", Role.CUSTOMER);

        //adding these users to our list - this is the list that tells you the current users
        registeredUser.add(user1);
        registeredUser.add(user2);
        registeredUser.add(user3);
        registeredUser.add(user4);

        //mock clothing data

        Clothes c1 = new Clothes("SZNS Hoodie", "Black", ClothesType.HOODIES, 30.99);
        Clothes c2 = new Clothes("Longsleeve", "Black", ClothesType.SHIRTS, 31.99);
        Clothes c3 = new Clothes("SZNS Jacket", "Black", ClothesType.JACKET, 39.00);
        Clothes c4 = new Clothes("SZNS Puffer", "Black", ClothesType.JACKET, 40.00);
        Clothes c5 = new Clothes("SZNS Denim Ripped Jeans", "Black", ClothesType.PANTS, 39.99);
        Clothes c6 = new Clothes("SZNS Cotton Shorts", "Black", ClothesType.SHORTS, 34.99);

        //adding the clothes to the array
        clothesList.add(c1);
        clothesList.add(c2);
        clothesList.add(c3);
        clothesList.add(c4);
        clothesList.add(c5);
        clothesList.add(c6);
    }

}
