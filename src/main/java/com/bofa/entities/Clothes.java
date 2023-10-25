package com.bofa.entities;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "clothes")
public class Clothes {
    private static int clothesIdTracker = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int iscn;
    private String title;
    private String color;
    @Enumerated(value = EnumType.ORDINAL)
    private ClothesType clothesType;
    private boolean isInStock;
    private static double price;

    //@Enumerated(mappedBy = "clothes")
    //private List<User> users;
    public Clothes() {
        this.iscn = ++clothesIdTracker;
        this.isInStock = true;
    }

    public Clothes(String title, String color, ClothesType clothesType, double price) {
        this.iscn = ++clothesIdTracker;
        this.title = title;
        this.color = color;
        this.clothesType = clothesType;
        this.isInStock = true;
        this.price = price;
    }

    public static double price() {
        return price;
    }

    public double getPrice(){
        return price;
    }
    public void setPrice(double price){
        this.price = price;
    }

    public int getIscn() {
        return iscn;
    }

    public void setIscn(int iscn) {
        this.iscn = iscn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public ClothesType getClothesType() {
        return clothesType;
    }

    public void setClothesType(ClothesType clothesType) {
        this.clothesType = clothesType;
    }

    public boolean isInStock() {
        return isInStock;
    }

    public void setInStock(boolean inStock) {
        isInStock = inStock;
    }

    @Override
    public String toString() {
        return "Clothes{" +
                "iscn=" + iscn +
                ", title='" + title + '\'' +
                ", color='" + color + '\'' +
                ", clothesType=" + clothesType +
                ", isInStock=" + isInStock +
                ", price=" + price +
                '}';
    }
}
