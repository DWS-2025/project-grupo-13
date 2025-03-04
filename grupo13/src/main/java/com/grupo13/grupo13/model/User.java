package com.grupo13.grupo13.model;
import java.util.ArrayList;

public class User {

    //attributes
    private Long id = 0L;
    private int money;
    private String userName;
    private Character character;
    private ArrayList<Equipment> inventory = new ArrayList<>();
 
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

    public ArrayList<Equipment> getInventory() {
        return inventory;
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

    public void setInventory(ArrayList<Equipment> inventory) {
        this.inventory = inventory;
    }

}
