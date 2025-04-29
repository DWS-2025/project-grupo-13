package com.grupo13.grupo13.model;
import java.sql.Blob;
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
public class Armor{

    //primary key
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //attributes
    private String name;
    private String description;
    private int defense;
    private int price;
    private int style;
    private String imageName;
    
    @Lob
    @JsonIgnore
    private Blob imageFile;

    @OneToMany (mappedBy = "armor")
    private List<Character> characters;

    @ManyToMany (mappedBy = "inventoryArmor")
    private List<User> users;

    //for the DB
    protected Armor(){}

    //constructor
    public Armor(String name, String description, int defense, int price, int style) {
        this.name = name;
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

    public Blob getimageFile() {
        return imageFile;
    }

    public String getDescription() {
        return description;
    }
    
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
        Armor other = (Armor) obj;
        if (id != other.id)
            return false;
        return true;
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

    public String getImageName(){
        return imageName;
    }

    //set functions
    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPicture(Blob imageFile){
        this.imageFile = imageFile;
    }

    public void setDescription(String description){
        this.description = description;
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

    public void setImageFile(Blob imageFile) {
        this.imageFile = imageFile;
    }

    public void setImageName(String imageName){
        this.imageName = imageName;
    }

}
