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
@Table(name = "app_user") // The name of the table was changed

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
 
    //for the DB
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

    //returns if the user has a specific weapon or armor
    public boolean hasWeapon(Weapon weapon){    
        for (Weapon equipmentWeapon : this.inventoryWeapon) {
            if (this.inventoryWeapon.contains(equipmentWeapon)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasArmor(Armor armor){    
        for (Armor equipmentArmor : this.inventoryArmor) {
            if (this.inventoryArmor.contains(equipmentArmor)) {
                return true;
            }
        }
        return false;
    }

}
