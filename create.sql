-- Table des utilisateurs
CREATE TABLE utilisateur (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    mot_de_passe TEXT NOT NULL,
    telephone VARCHAR(20),
    role VARCHAR(20) NOT NULL CHECK (role IN ('ADMIN', 'AGENT', 'CLIENT')),
    actif BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table des aéroports
CREATE TABLE aeroport (
    id SERIAL PRIMARY KEY,
    code VARCHAR(3) UNIQUE NOT NULL,
    nom VARCHAR(200) NOT NULL,
    ville VARCHAR(100) NOT NULL,
    pays VARCHAR(100) NOT NULL,
    actif BOOLEAN NOT NULL DEFAULT TRUE
);

-- Table des avions
CREATE TABLE avion (
    id SERIAL PRIMARY KEY,
    numero_avion VARCHAR(20) UNIQUE NOT NULL,
    modele VARCHAR(50) NOT NULL,
    capacite INT NOT NULL,
    statut VARCHAR(20) NOT NULL DEFAULT 'DISPONIBLE' CHECK (statut IN ('DISPONIBLE', 'EN_VOL', 'MAINTENANCE')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table des vols
CREATE TABLE vol (
    id SERIAL PRIMARY KEY,
    numero_vol VARCHAR(10) UNIQUE NOT NULL,
    aeroport_depart_id INT NOT NULL,
    aeroport_arrivee_id INT NOT NULL,
    date_depart TIMESTAMP NOT NULL,
    date_arrivee TIMESTAMP NOT NULL,
    prix NUMERIC(10,2) NOT NULL,
    places_disponibles INT NOT NULL,
    statut VARCHAR(20) NOT NULL DEFAULT 'PREVU' CHECK (statut IN ('PREVU', 'EMBARQUEMENT', 'EN_VOL', 'ATTERRI', 'ANNULE')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(aeroport_depart_id) REFERENCES aeroport(id),
    FOREIGN KEY(aeroport_arrivee_id) REFERENCES aeroport(id),
    CONSTRAINT check_dates CHECK (date_arrivee > date_depart),
    CONSTRAINT check_aeroports CHECK (aeroport_depart_id != aeroport_arrivee_id)
);

-- Table de relation ManyToMany entre Vol et Avion
CREATE TABLE vol_avion (
    vol_id INT NOT NULL,
    avion_id INT NOT NULL,
    PRIMARY KEY (vol_id, avion_id),
    FOREIGN KEY(vol_id) REFERENCES vol(id) ON DELETE CASCADE,
    FOREIGN KEY(avion_id) REFERENCES avion(id) ON DELETE CASCADE
);

-- Table des réservations
CREATE TABLE reservation (
    id SERIAL PRIMARY KEY,
    reference VARCHAR(20) UNIQUE NOT NULL,
    utilisateur_id INT NOT NULL,
    vol_id INT NOT NULL,
    nb_passagers INT NOT NULL DEFAULT 1,
    prix_total NUMERIC(10,2) NOT NULL,
    statut VARCHAR(20) NOT NULL DEFAULT 'CONFIRMEE' CHECK (statut IN ('EN_ATTENTE', 'CONFIRMEE', 'ANNULEE')),
    date_reservation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(utilisateur_id) REFERENCES utilisateur(id),
    FOREIGN KEY(vol_id) REFERENCES vol(id),
    CONSTRAINT check_nb_passagers CHECK (nb_passagers > 0)
);

-- Table des passagers
CREATE TABLE passager (
    id SERIAL PRIMARY KEY,
    reservation_id INT NOT NULL,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    date_naissance DATE NOT NULL,
    numero_passeport VARCHAR(50),
    numero_siege VARCHAR(10),
    checked_in BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(reservation_id) REFERENCES reservation(id) ON DELETE CASCADE
);

-- Table des paiements
CREATE TABLE paiement (
    id SERIAL PRIMARY KEY,
    reservation_id INT NOT NULL,
    montant NUMERIC(10,2) NOT NULL,
    mode_paiement VARCHAR(30) NOT NULL CHECK (mode_paiement IN ('CARTE', 'PAYPAL', 'ESPECES')),
    statut VARCHAR(20) NOT NULL DEFAULT 'REUSSI' CHECK (statut IN ('EN_ATTENTE', 'REUSSI', 'ECHOUE')),
    date_paiement TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(reservation_id) REFERENCES reservation(id),
    CONSTRAINT check_montant CHECK (montant > 0)
);
