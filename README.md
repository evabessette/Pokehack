# POKEHACK - TP 1 - EVA - MAD

## INITIALISATION

1. Clonez le dépôt.
2. Créez une base de données PostgreSQL nommée "PokeHack".
3. Modifiez le fichier `src/main/resources/config.properties` avec vos informations de connexion.


## STRUCTURE DU PROJET
```
pokehack/
├── pom.xml
├── README.md
├── database/
│   └── schema.sql
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── ca/cmaisonneuve/pokehack/
│   │   │       ├── MainApp.java
│   │   │       ├── controller/
│   │   │       │   └── PokemonController.java
│   │   │       ├── dao/
│   │   │       │   └── PokemonDao.java
│   │   │       ├── database/
│   │   │       │   └── DatabaseManager.java
│   │   │       ├── model/
│   │   │       │   └── Pokemon.java
│   │   │       ├── service/
│   │   │       │   └── PokemonApiService.java
│   │   │       └── view/
│   │   │           └── PokemonView.java
│   │   └── resources/
│   │       ├── fonts/
│   │       ├── styles.css
│   │       ├── schema.sql
│   │       └── config.properties
│   │           
```

## DATABASE

```
id, nom, image_url, type_principal,
type_secondaire, hp, attaque,
defense, attaque_spéciale, defense_spéciale,
vitesse, poids, taille, date_capture
```

L'utilisateur doit créer une base de données PostgreSQL avec le nom "pokehack" 
et il doit ensuite adapter le fichier de connexion à la base de données en modifiant
ses informations de connexion.

## SOURCES
https://github.com/evabessette/Pokehack

https://pokedex-examen-mi-session.vercel.app/

https://pokeapi.co/api/v2/pokemon