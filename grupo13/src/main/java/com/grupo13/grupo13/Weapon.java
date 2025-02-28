package com.grupo13.grupo13;

public class Weapon extends Equipment{
    
    private int intimidation;
   
   

    public Weapon(String name, int intimidation, int damage, String image, String description, int price){
        this.setName(name);
        this.intimidation = intimidation;
        this.setPicture(image);      
        this.setAttribute(damage);
        this.setDescription(description);
        this.setnPrice(price);
    }



    public int getIntimidation() {
        return intimidation;
    }

    public void setIntimidation(int intimidation) {
        this.intimidation = intimidation;
    }
    
  

    

  

}
