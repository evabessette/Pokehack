# POKEHACK - TP 1 - EVA - MAD

## INITIALISATION

1. Clonez le dépôt GitHub sur votre machine locale (https://github.com/evabessette/Pokehack).
2. Créez une base de données PostgreSQL nommée "PokeHack".
3. Exécutez le script `database/schema.sql` pour créer les tables nécessaires.
4. Créez un fichier `config.properties` dans `src/main/resources` avec vos informations de connexion (ex: config.properties.example).


## STRUCTURE DU PROJET
```
pokehack/
├── pom.xml
├── README.md
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
│   │       └── config.properties.example
│   │           
```

## DATABASE

```
id, nom, image_url, type_principal, type_principal_icon, type_principal_name,
type_secondaire, type_secondaire_icon, type_secondaire_name, hp, attaque,
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