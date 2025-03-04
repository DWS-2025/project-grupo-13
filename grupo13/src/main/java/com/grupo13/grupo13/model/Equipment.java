package com.grupo13.grupo13.model;

import java.util.ArrayList;

public class Equipment {
    private long id;
    private String name;
    private String picture;
    private String description;
    private int attribute;
    private int price;
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Character> characters = new ArrayList<>();

    

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


    public void setPrice(int price) {
        this.price = price;
    }


    public ArrayList<User> getUsers() {
        return users;
    }


    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(ArrayList<Character> characters) {
        this.characters = characters;
    }
}
