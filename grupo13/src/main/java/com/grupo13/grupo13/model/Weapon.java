package com.grupo13.grupo13.model;

public class Weapon extends Equipment {

    //the new attribute
    private int intimidation;

    //constructor
    public Weapon(String name, int intimidation, int damage, String picture, String description, int price) {
        super(name, picture, description, damage, price);
        this.intimidation = intimidation;
    }

    //get function of the new attribute
    public int getIntimidation() {
        return intimidation;
    }

    //set function of the new attribute
    public void setIntimidation(int intimidation) {
        this.intimidation = intimidation;
    }

}
