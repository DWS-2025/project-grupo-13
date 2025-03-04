package com.grupo13.grupo13.model;

import java.util.ArrayList;

public class Equipment {

    //attributes
    private long id;
    private String name;
    private String picture;
    private String description;
    private int attribute;
    private int price;
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Character> characters = new ArrayList<>();

    //constructor
    public Equipment(String name, String picture, String description, int attribute, int price) {
        this.name = name;
        this.picture = picture;
        this.description = description;
        this.attribute = attribute;
        this.price = price;
    }

    //get functions
    public Long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getPicture() {
        return picture;
    }

    public String getDescription() {
        return description;
    }
    
    public int getAttribute() {
        return attribute;
    }

    public int getPrice(){
        return price;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }

    //set functions
    public void setId(long id) {
        this.id = id;
    }

    public void setName(String nam){
        this.name= nam;
    }

    public void setPicture(String pict){
        this.picture=pict;
    }

    public void setDescription(String desc){
        this.description=desc;
    }

    public void setAttribute(int attribute) {
        this.attribute = attribute;
    }

    public void setnPrice(int p){
      this.price= p;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public void setCharacters(ArrayList<Character> characters) {
        this.characters = characters;
    }
    
}
