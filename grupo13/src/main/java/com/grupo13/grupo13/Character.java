package com.grupo13.grupo13;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class Character {
    private String name;
    private String desc;
    private int strength;
    private int healthPoints;
    private boolean weaponEquiped;
    private boolean armorEquiped;
    private String imageName;

    public String getName() {
        return name;
    }
    public String getDesc() {
        return desc;
    }
    public int getStrength() {
        return strength;
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
    public void setName(String name) {
        this.name = name;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public void setStrength(int strength) {
        this.strength = strength;
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
    
    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
    

}
