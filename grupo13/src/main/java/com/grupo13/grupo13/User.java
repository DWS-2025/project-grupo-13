package com.grupo13.grupo13;

import java.util.ArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class User {
    private int money;
    private String userName;
    private ArrayList<Weapon> weaponInventory;
    private ArrayList<Armor> armorInventory;    

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
    public ArrayList<Weapon> getWeaponInventory() {
        return weaponInventory;
    }
    public ArrayList<Armor> getArmorInventory() {
        return armorInventory;
    }

    

}
