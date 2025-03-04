package com.grupo13.grupo13.model;

public class Armor extends Equipment {

    //the new attribute
    private int style;

    //constructor
    public Armor(String name, int style, int defense, String picture, String description, int price) {
        super(name, picture, description, defense, price);
        this.style = style;
    }

    //get function of the new attribute
    public int getStyle() {
        return style;
    }

    //set function of the new attribute
    public void setStyle(int style) {
        this.style = style;
    }

}
