package com.grupo13.grupo13;

public class Equipment {
    private String name;
    private String picture;
    private String description;
    private Long id = 0L;


    public String getName(){
        return name;
    }


    public String getPicture() {
        return picture;
    }

    public String getDescription() {
        return description;
    }
    public Long getId(){
        return id;
    }

    public void setDescription(String desc){
        this.description=desc;
    }

    public void setPicture(String pict){
        this.picture=pict;
    }

    public void setName(String nam){
        this.name= nam;
    }

    public void setId(Long i){
     this.id =i;

    }

}
