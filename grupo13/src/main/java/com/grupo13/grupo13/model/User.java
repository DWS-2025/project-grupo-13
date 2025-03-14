package com.grupo13.grupo13.model;
import java.util.ArrayList;
//test

public class User {

    //attributes
    private Long id = 0L;
    private int money;
    private String userName;
    private Character character;
    private ArrayList<Weapon> inventoryWeapon = new ArrayList<>();
    private ArrayList<Armor> inventoryArmor = new ArrayList<>();
 
    //constructor
    public User(int money, String userName) {
        this.money = money;
        this.userName = userName;
    }
    
    //get functions
    public Long getId() {
        return id;
    }

    public int getMoney() {
        return money;
    }

    public String getUserName() {
        return userName;
    }

    public Character getCharacter() {
        return character;
    }

    public ArrayList<Weapon> getInventoryWeapon() {
        return inventoryWeapon;
    }

    public ArrayList<Armor> getInventoryArmor() {
        return inventoryArmor;
    }

    //set functions
    public void setId(Long id) {
        this.id = id;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public void setInventoryWeapon(ArrayList<Weapon> inventoryWeapon) {
        this.inventoryWeapon = inventoryWeapon;
    }
    public void setInventoryArmor(ArrayList<Armor> inventoryArmor) {
        this.inventoryArmor = inventoryArmor;
    }


}
