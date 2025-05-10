package com.grupo13.grupo13.model;
import java.sql.Blob;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "game_characters") // Evita conflicto con palabra reservada
public class Character {

    //attributes 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String description;
    private int strength;
    private int defense;
    private int healthPoints;
    private boolean weaponEquiped;
    private boolean armorEquiped;
    private String imageName;

    @JsonIgnore
    @Lob
    private Blob imageFile;

    @OneToOne(mappedBy = "character")
    private User user;

    @ManyToOne
    private Weapon weapon;
    
    @ManyToOne
    private Armor armor;

    //for the DB
    protected Character(){}

    //constructor
    public Character(String description, String name) {
        this.description =  description;
        this.name = name;
    }

    //getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getStrength() {
        return strength;
    }
    
    public int getDefense() {
        return defense;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public boolean isWeaponEquiped() {
        return weaponEquiped;
    }

    public boolean isArmorEquiped() {
        return armorEquiped;
    }
    
    public Weapon getWeapon() {
        return weapon;
    }

    public Armor getArmor() {
        return armor;
    }
        
    public Blob getImageFile() {
        return imageFile;
    }
    
    //setters
    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }
    
    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public void setWeaponEquiped(boolean weaponEquiped) {
        this.weaponEquiped = weaponEquiped;
    }

    public void setArmorEquiped(boolean armorEquiped) {
        this.armorEquiped = armorEquiped;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    public void setImageFile(Blob imageName) {
        this.imageFile = imageName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

}
