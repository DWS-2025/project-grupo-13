package com.grupo13.grupo13;

public class Equipment {
    String name;
    String picture;
    String description;


    public String getName(){
        return name;
    }


    public String getPicture() {
        return picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc){
        this.description=desc;
    }

    public void setPicture(String pict){
        this.picture=pict;
    }

    public void setName(String nam){
        this.name= nam;
    }

}
