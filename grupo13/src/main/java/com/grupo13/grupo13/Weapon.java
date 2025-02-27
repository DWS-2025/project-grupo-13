package com.grupo13.grupo13;

public class Weapon extends Equipment{
    
    private int intimidation;
   
   

    public Weapon(String name, int intimidation, int damage, String image, String description){
        this.name = name;
        this.intimidation = intimidation;
        this.picture = image;       
        this.attribute=damage;
     
        this.description = description;
    }

    public int getDamage() {
        return attribute;
    }

    public int getIntimidation() {
        return intimidation;
    }

    public void setIntimidation(int intimidation) {
        this.intimidation = intimidation;
    }
    
  

    

  

}
