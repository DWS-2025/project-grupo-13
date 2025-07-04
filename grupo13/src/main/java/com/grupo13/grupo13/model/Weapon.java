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
    //private String imageName;

    @JsonIgnore
    @Lob
	private Blob imageFile;

    @OneToMany(mappedBy = "weapon")
    private List<Character> characters;

    @ManyToMany(mappedBy = "inventoryWeapon") 
    private List<User> users;

    //for the DB
    public Weapon(){}

    //constructor
    public Weapon(String name, String description, int strength, int price, int intimidation) {
        this.name = name;
        this.description = description;
        this.strength = strength;
        this.price = price;
        this.intimidation = intimidation;
    }

    //functions
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Weapon other = (Weapon) obj;
        if (id != other.id)
            return false;
        return true;
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

    public void setImageFile(Blob imageFile) {
        this.imageFile = imageFile;
    }
}