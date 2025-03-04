package com.grupo13.grupo13.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class Character {

    //attributes 
    private long id;
    private String name;
    private String desc;
    private int strength;
    private int defense;
    private int healthPoints;
    private boolean weaponEquiped;
    private boolean armorEquiped;
    private Equipment weapon;
    private Equipment armor;
    private String imageName;
    
    //get functions
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public int getStrength() {
        return strength;
    }
    
    public int getDefense() {
        return defense;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public boolean isWeaponEquiped() {
        return weaponEquiped;
    }

    public boolean isArmorEquiped() {
        return armorEquiped;
    }
    
    public Equipment getWeapon() {
        return weapon;
    }

    public Equipment getArmor() {
        return armor;
    }
        
    public String getImageName() {
        return imageName;
    }
    
    //set function
    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }
    
    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public void setWeaponEquiped(boolean weaponEquiped) {
        this.weaponEquiped = weaponEquiped;
    }

    public void setArmorEquiped(boolean armorEquiped) {
        this.armorEquiped = armorEquiped;
    }

    public void setWeapon(Equipment weapon) {
        this.weapon = weapon;
    }

    public void setArmor(Equipment armor) {
        this.armor = armor;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

}
