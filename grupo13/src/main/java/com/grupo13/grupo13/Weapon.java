package com.grupo13.grupo13;

public class Weapon extends Equipment{
    
    private int damage;
   
   

    public Weapon(String name, int damage, String image, String description){
        this.name = name;
        this.damage = damage;
        this.picture = image;            
        this.description = description;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

  

    

  

}
