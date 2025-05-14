package com.grupo13.grupo13.model;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
    private String encodedPassword;

    @OneToOne
    private Character character;
    @ManyToMany
    private List<Weapon> inventoryWeapon;
    @ManyToMany
    private List<Armor> inventoryArmor;

    @ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles;
 
    //for the DB
    protected User(){}

    //constructor
    public User(String name, String encodedPassword, String... roles) {
		this.userName = name;
		this.encodedPassword = encodedPassword;
		this.roles = List.of(roles);
	}

    
    //get functions

    public String getEncodedPassword() {
		return encodedPassword;
	}

	public List<String> getRoles() {
		return roles;
	}

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

	public void setEncodedPassword(String encodedPassword) {
		this.encodedPassword = encodedPassword;
	}
    
    public void setRoles(List<String> roles) {
		this.roles = roles;
    }

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
