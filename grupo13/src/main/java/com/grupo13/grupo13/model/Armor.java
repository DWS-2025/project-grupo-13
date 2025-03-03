package com.grupo13.grupo13.model;

public class Armor extends Equipment {

    private int style;

    public Armor(String name, int style, int defense, String picture, String description, int price) {
        super();
        this.setName(name);
        this.style = style;
        this.setAttribute(defense);
        this.setPicture(picture);
        this.setDescription(description);
        this.setnPrice(price);
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

}
