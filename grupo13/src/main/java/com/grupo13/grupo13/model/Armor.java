package com.grupo13.grupo13.model;
import java.sql.Blob;
import java.util.List;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

public class Armor{

    //the new attribute
    @Id
    @GeneratedValue
    private long id;
    private String name;
    @Lob
    private Blob image;
    private String description;
    private int defense;
    private int style;
    private int price;
    @OneToMany 
    private List<Character> characters;
    @ManyToMany (mappedBy = "armors")
    private List<User> users;

    //constructor
    public Armor(String name, Blob image, String description, int defense, int style, int price) {
        this.name = name;
        this.image = image;
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

    public Blob getimage() {
        return image;
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

    public List<User> getUsers() {
        return users;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    //set functions
    public void setId(long id) {
        this.id = id;
    }

    public void setName(String nam){
        this.name= nam;
    }

    public void setPicture(Blob image){
        this.image=image;
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

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }

}
