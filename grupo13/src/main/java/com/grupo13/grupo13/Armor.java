package com.grupo13.grupo13;
public class Armor extends Equipment {
    
    private int style;
    
   


    Armor(String name, int style, int defense, String picture, String description){
        this.name=name;
        this.style=style;
        this.attribute=defense;
        this.picture=picture;
        this.description=description;
    }

   

    public int getDefense() {
        return attribute;
    }

    public void setDefense(int attribute){
        this.attribute = attribute;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    
}

