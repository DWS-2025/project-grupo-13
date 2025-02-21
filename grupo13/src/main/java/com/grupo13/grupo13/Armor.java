package com.grupo13.grupo13;
public class Armor {
    private String name;
    private int defense;
    private String picture;
    private String description;

    Armor(String name, int defense, String picture, String description){
        this.name=name;
        this.defense=defense;
        this.picture=picture;
        this.description=description;
    }

    public String getName(){
        return name;
    }

    public int getDefense() {
        return defense;
    }

    public String getPicture() {
        return picture;
    }

    public String getDescription() {
        return description;
    }

}

