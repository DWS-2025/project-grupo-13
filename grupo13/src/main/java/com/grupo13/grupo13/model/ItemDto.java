package com.grupo13.grupo13.model;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class ItemDto {
    private String name;
    private String description;
    private int defense;
    private int price;
    private int style;
    private int strength;
    private int intimidation;
    private String type; //armor or weapon
    private boolean bought;
    private Object item;
    private boolean isArmor;
    private boolean isWeapon;

    //constructor
    

    public ItemDto(String name, String description, int price, int strength, int intimidation, String type,
            boolean bought, Weapon weapon) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.strength = strength;
        this.intimidation = intimidation;
        this.type = type;
        this.bought = bought;
        this.item = weapon;
        this.isWeapon = true;
    }

    public ItemDto(String name, String description, int defense, int price, int style, String type, boolean bought, 
            Armor armor) {
        this.name = name;
        this.description = description;
        this.defense = defense;
        this.price = price;
        this.style = style;
        this.type = type;
        this.bought = bought;
        this.item = armor;
        this.isArmor = true;
    }

    //getters
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Object getItem() {
        return item;
    }

    public Object getbought() {
        return bought;
    }

    public String getDescription() {
        return description;
    }
    
    public int getstrength() {
        return strength;
    }

    public int getPrice() {
        return price;
    }

    public int getIntimidation() {
        return intimidation;
    }
    
    public int getDefense() {
        return defense;
    }

    public int getStyle() {
        return style;
    }

    //setters
    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setItem(Object item) {
        this.item = item;
    }   

    public void setBought(boolean bought) {
        this.bought = bought;
    } 

    public void setDescription(String description){
        this.description = description;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setStyle(int style){
      this.style = style;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setstrength(int strength) {
        this.strength = strength;
    }

    public void setIntimidation(int intimidation) {
        this.intimidation = intimidation;
    }

}
