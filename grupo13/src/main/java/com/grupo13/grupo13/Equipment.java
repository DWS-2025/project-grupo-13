package com.grupo13.grupo13;

public class Equipment {

    long id;
    String name;
    String picture;
    String description;
    int attribute;


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


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public int getAttribute() {
        return attribute;
    }


    public void setAttribute(int attribute) {
        this.attribute = attribute;
    }

    

}
