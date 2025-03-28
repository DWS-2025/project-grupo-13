package com.grupo13.grupo13.model;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "app_user") // Cambié el nombre de la tabla

public class User {

    //primary key
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    //attributes
    private int money;
    private String userName;
    @OneToOne
    private Character character;
    @ManyToMany
    private List<Weapon> inventoryWeapon;
    @ManyToMany
    private List<Armor> inventoryArmor;
 
    //for the BBDD
    protected User(){}

    //constructor
    public User(int money, String userName) {
        this.money = money;
        this.userName = userName;
    }
    
    //get functions
    public Long getId() {
        return id;
    }

    public int getMoney() {
        return money;
    }

    public String getUserName() {
        return userName;
    }

    public Character getCharacter() {
        return character;
    }

    public List<Weapon> getInventoryWeapon() {
        return inventoryWeapon;
    }

    public List<Armor> getInventoryArmor() {
        return inventoryArmor;
    }

    //set functions
    public void setId(Long id) {
        this.id = id;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public void setInventoryWeapon(List<Weapon> inventoryWeapon) {
        this.inventoryWeapon = inventoryWeapon;
    }
    public void setInventoryArmor(List<Armor> inventoryArmor) {
        this.inventoryArmor = inventoryArmor;
    }



}
