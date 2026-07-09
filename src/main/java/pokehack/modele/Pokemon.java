package pokehack.modele;

public class Pokemon {
    public String id ;
    public String name;
    public String image_url ;
    public String primary_type ;
    public String secondary_type ;
    public int hp ;
    public int attack ;
    public int defense ;
    public int special_attack ;
    public int special_defense ;
    public int speed ;
    public double weight;
    public double height;
    public String captured_at ;

    public Pokemon() {
    }

    public int getTotalStats() {
        return hp + attack + defense + special_attack + special_defense + speed;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", image_url='" + image_url + '\'' +
                ", primary_type='" + primary_type + '\'' +
                ", secondary_type='" + secondary_type + '\'' +
                ", hp=" + hp +
                ", attack=" + attack +
                ", defense=" + defense +
                ", special_attack=" + special_attack +
                ", special_defense=" + special_defense +
                ", speed=" + speed +
                ", weight=" + weight +
                ", height=" + height +
                ", captured_at='" + captured_at + '\'' +
                '}';
    }

}