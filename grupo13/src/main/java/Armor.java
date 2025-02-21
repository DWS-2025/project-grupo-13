public class Armor {
    private static int defense;
    private String picture;
    private String description;

    Armor(int defense, String picture, String description){
        this.defense=defense;
        this.picture=picture;
        this.description=description;
    }

    public static int getDefense() {
        return defense;
    }

    public String getPicture() {
        return picture;
    }

    public String getDescription() {
        return description;
    }

}

