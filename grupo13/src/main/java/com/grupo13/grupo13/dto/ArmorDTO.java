package com.grupo13.grupo13.dto;

import com.grupo13.grupo13.model.Weapon;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.grupo13.grupo13.model.Armor;
import com.grupo13.grupo13.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class ArmorDTO {
    private long id;
    private String name;
    private String description;
    private int defense;
    private int price;
   
    // constructor
    public ArmorDTO(Armor armor) {
        this.id = armor.getId();
        this.name = armor.getName();
        this.description = armor.getDescription();
        this.defense = armor.getDefense();
        this.price = armor.getPrice();

        
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
        return defense;
    }

    public void setStrength(int defense) {
        this.defense = defense;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

   
}

