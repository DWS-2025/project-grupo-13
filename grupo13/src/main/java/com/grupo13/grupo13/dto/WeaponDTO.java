package com.grupo13.grupo13.dto;

import com.grupo13.grupo13.model.Weapon;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.grupo13.grupo13.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class WeaponDTO {
    private long id;
    private String name;
    private String description;
    private int strength;
    private int price;
   
    // constructor
    public WeaponDTO(Weapon weapon) {
        this.id = weapon.getId();
        this.name = weapon.getName();
        this.description = weapon.getDescription();
        this.strength = weapon.getstrength();
        this.price = weapon.getPrice();

        
    }

  
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

   
}

