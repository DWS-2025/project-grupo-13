package com.grupo13.grupo13.model;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Weapon {

    //user - weapon --> N:M,   character - weapon --> 1:N (OneToMany)(cascade NO)
    //both of them are bidirectional

    //primary key
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //attributes
    private String name;
    private String description;
    private int strength;
    private int price;
    private int intimidation;
    private String imageName;

    @Lob
    @JsonIgnore
	private Blob imageFile;

    @OneToMany(mappedBy = "weapon")
    private List<Character> characters;

    @ManyToMany(mappedBy = "inventoryWeapon") 
    private List<User> users;

    //for the DB
    protected Weapon(){}

    //constructor
    public Weapon(String name, String description, int strength, int price, int intimidation) {
        this.name = name;
        this.description = description;
        this.strength = strength;
        this.price = price;
        this.intimidation = intimidation;
    }

    //getters
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public Blob getimageFile() {
        return imageFile;
    }

    public String getDescription() {
        return description;
    }
    
    public int getstrength() {
        return strength;
    }

    public int getPrice() {
        return price;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public int getIntimidation() {
        return intimidation;
    }

    public String getImageName(){
        return imageName;
    }

    //setters
    public void setId(long id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setimageFile(Blob imageFile) {
        this.imageFile = imageFile;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setstrength(int strength) {
        this.strength = strength;
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

    public void setIntimidation(int intimidation) {
        this.intimidation = intimidation;
    }

    public void setImageName(String imageName){
        this.imageName = imageName;
    }

}