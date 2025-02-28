package com.grupo13.grupo13;

public class Equipment {
    private long id;
    private String name;
    private String picture;
    private String description;
    private int attribute;
    private int price;

    

    public String getName(){
        return name;
    }


    public String getPicture() {
        return picture;
    }

    public String getDescription() {
        return description;
    }
    public Long getId(){
        return id;
    }

    public int getPrice(){
        return price;
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


    public void setId(long id) {
        this.id = id;
    }


    public int getAttribute() {
        return attribute;
    }


    public void setAttribute(int attribute) {
        this.attribute = attribute;
    }

    public void setnPrice(int p){
      this.price= p;
    }

}
