package com.grupo13.grupo13;

import java.util.ArrayList;




public class User {

    private Long id = 0L;
    private int money;
    private String userName;
    private Character character;
    private ArrayList<Equipment> inventory = new ArrayList<>();
 
    public User(){}

    public User(int money, String userName) {
        this.money = money;
        this.userName = userName;
    }

    public int getMoney() {
        return money;
    }
    public void setMoney(int money) {
        this.money = money;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<Equipment> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Equipment> inventory) {
        this.inventory = inventory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }


    

}
