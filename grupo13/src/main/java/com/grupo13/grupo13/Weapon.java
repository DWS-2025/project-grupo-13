package com.grupo13.grupo13;

public class Weapon extends Equipment{
    
    private int damage;
   
   

    public Weapon(String name, int damage, String image, String description){
       this.setName(name);
        this.damage = damage;
        this.setPicture(image);
        this.setDescription(description);
                
        
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

  

    

  

}
