package com.grupo13.grupo13;
public class Armor extends Equipment {
    
    private int defense;
    
   

    Armor(String name, int defense, String picture, String description){
        this.setName(name);
        this.defense=defense;
        this.setPicture(picture);
        this.setDescription(description);
        
       
    }

   

    public int getDefense() {
        return defense;
    }

    
}

