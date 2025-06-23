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
    private int style;
    private int intimidation;
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
    public Character(String description, String name, String imageName) {
        this.description =  description;
        this.name = name;
        this.imageName = imageName;
    }

    //get functions
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

    public int getStyle() {
        return style;
    }

    public int getIntimidation() {
        return intimidation;
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
    
    //set function
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

    public void setIntimidation(int intimidation) {
        this.intimidation = intimidation;
    }

    public void setStyle(int style) {
        this.style = style;
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
        Character other = (Character) obj;
        if (id != other.id)
            return false;
        return true;
    }
}
