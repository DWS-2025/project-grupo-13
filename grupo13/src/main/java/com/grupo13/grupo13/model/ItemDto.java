package com.grupo13.grupo13.model;

public class ItemDto {
    private String name;
    private String type; //armor or weapon
    private Object item;

    //constructor
    public ItemDto(String name, String type, Object item) {
        this.name = name;
        this.type = type;
        this.item = item;
    }

    //getters
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Object getItem() {
        return item;
    }

    //setters
    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setItem(Object item) {
        this.item = item;
    }   

}
