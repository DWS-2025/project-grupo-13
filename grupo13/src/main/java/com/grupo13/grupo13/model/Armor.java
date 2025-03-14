package com.grupo13.grupo13.model;
import java.util.ArrayList;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

public class Armor{

    //the new attribute
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String picture;
    private String description;
    private int defense;
    private int style;
    private int price;
    @OneToMany (mappedBy = "armor")
    private ArrayList<Character> characters;
    @ManyToMany (mappedBy = "armor")
    private ArrayList<User> users;

    //constructor
    public Armor(String name, String picture, String description, int defense, int style, int price) {
        this.name = name;
        this.picture = picture;
        this.description = description;
        this.defense = defense;
        this.style = style;
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
    
    public int getDefense() {
        return defense;
    }

    public int getStyle() {
        return style;
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

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setStyle(int style){
      this.style = style;
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
