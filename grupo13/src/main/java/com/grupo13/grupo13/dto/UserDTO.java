package com.grupo13.grupo13.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grupo13.grupo13.model.User;
import com.grupo13.grupo13.model.Weapon;

import java.util.List;
import java.util.stream.Collectors;

public class UserDTO {
    private long id;
    private String userName;
     @JsonBackReference
    private List<WeaponDTO> inventoryWeapon;  // unused weapon dto list

    // constructor
    public UserDTO(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();

    
    }

    // getters/setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

  
}
