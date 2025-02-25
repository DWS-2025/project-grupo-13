package com.grupo13.grupo13;
public class Armor extends Equipment {
    
    private int defense;
    
   

    Armor(String name, int defense, String picture, String description){
        this.name=name;
        this.defense=defense;
        this.picture=picture;
        this.description=description;
    }

   

    public int getDefense() {
        return defense;
    }

    
}

