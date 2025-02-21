package com.grupo13.grupo13;

public class Weapon {
    private String name;
    private int damage;
    private String image;
    private String description;

    public Weapon(String name, int damage, String image, String description){
        this.name = name;
        this.damage = damage;
        this.image = image;            
        this.description = description;
    }

    public String getName(){
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name){
        this.name = name;
    }

}
