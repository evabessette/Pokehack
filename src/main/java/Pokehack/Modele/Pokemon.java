package Pokehack.Modele;

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
    public String captured_at ;

    public Pokemon() {
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
                ", captured_at='" + captured_at + '\'' +
                '}';
    }

}


//enum TypePokemad {
//    NORMAL
//    FEU
//            EAU
//    PLANTE
//            ELECTRIK
//    GLACE
//            COMBAT
//    POISON
//            SOL
//    VOL
//            PSY
//    INSECTE
//            ROCHE
//    SPECTRE
//            DRAGON
//    TENEBRES
//            ACIER
//    FEE
//    }
//
//enum Rarete {
//    COMMUN
//    RARE
//    LEGENDAIRE
//    }

//model Dresseur {
//id Int  @id @default(autoincrement())
//pseudo String @unique
//ville String?
//badge Int? @default(0)
//createdAt DateTime @default(now())
//pokemads Pokemad[]
//}

//model Pokemad {
//id Int @id @default(autoincrement())
//numeroPokedex Int @unique
//nom String
//pv Int
//taille Float
//poids Float
//typePrincipal TypePokemad
//typeSecondaire TypePokemad?
//rarete Rarete
//imageUrl String?
//captureDate DateTime @default(now())
//dresseurId Int?
//dresseur Dresseur? @relation(fields: [dresseurId], references: [id])
//        }
//
//enum Role {
//    USER
//    ADMIN
//    }
//
//model User {
//id String@id @default(uuid())
//email String @unique
//password String
//role Role @default(USER)
//createdAt DateTime @default(now())
//        }