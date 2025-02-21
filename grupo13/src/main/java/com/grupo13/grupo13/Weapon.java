package com.grupo13.grupo13;

public class Weapon {
    private int damage;
    private String image;
    private String description;

    public Weapon(int damage, String image, String description){
            this.damage = damage;
            this.image = image;
            this.description = description;
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

}
