CREATE TABLE IF NOT EXISTS pokemons (
    id TEXT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    image_url TEXT,
    primary_type VARCHAR(50) NOT NULL,
    secondary_type VARCHAR(50),
    primary_type_icon TEXT,
    secondary_type_icon TEXT,
    primary_type_name VARCHAR(50),
    secondary_type_name VARCHAR(50),
    hp INTEGER NOT NULL DEFAULT 0,
    attack INTEGER NOT NULL DEFAULT 0,
    defense INTEGER NOT NULL DEFAULT 0,
    special_attack INTEGER NOT NULL DEFAULT 0,
    special_defense INTEGER NOT NULL DEFAULT 0,
    speed INTEGER NOT NULL DEFAULT 0,
    weight DOUBLE PRECISION NOT NULL DEFAULT 0.0,
    height DOUBLE PRECISION NOT NULL DEFAULT 0.0,
    captured_at TIMESTAMP DEFAULT NOW()
    );

INSERT INTO pokemons (id, name, image_url, primary_type, secondary_type, primary_type_icon, secondary_type_icon, primary_type_name, secondary_type_name, hp, attack, defense, special_attack, special_defense, speed, weight, height)
VALUES
('001', 'Bulbasaur', 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png', 'Grass', 'Poison', 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png', 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png', 'Grass', 'Poison', 45, 49, 49, 65, 65, 45, 6.9, 0.7)