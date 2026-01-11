CREATE DATABASE compagnie_aerienne;
\c compagnie_aerienne;

CREATE TABLE utilisateur (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    mot_de_passe TEXT NOT NULL,
    telephone VARCHAR(20),
    role VARCHAR(20) NOT NULL CHECK (role IN ('admin', 'agent', 'client')),
    actif BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table des aéroports
CREATE TABLE aeroport (
    id SERIAL PRIMARY KEY,
    code VARCHAR(3) UNIQUE NOT NULL, -- CDG, ORY, JFK
    nom VARCHAR(200) NOT NULL,
    ville VARCHAR(100) NOT NULL,
    pays VARCHAR(100) NOT NULL,
    actif BOOLEAN DEFAULT TRUE
);

-- Table des avions
CREATE TABLE avion (
    id SERIAL PRIMARY KEY,
    numero_avion VARCHAR(20) UNIQUE NOT NULL, -- A320-001
    modele VARCHAR(50) NOT NULL, -- Airbus A320, Boeing 737
    capacite INT NOT NULL,
    statut VARCHAR(20) DEFAULT 'disponible' CHECK (statut IN ('disponible', 'en_vol', 'maintenance')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table des vols
CREATE TABLE vol (
    id SERIAL PRIMARY KEY,
    numero_vol VARCHAR(10) UNIQUE NOT NULL, -- AF001
    avion_id INT NOT NULL,
    aeroport_depart_id INT NOT NULL,
    aeroport_arrivee_id INT NOT NULL,
    date_depart TIMESTAMP NOT NULL,
    date_arrivee TIMESTAMP NOT NULL,
    prix NUMERIC(10,2) NOT NULL,
    places_disponibles INT NOT NULL,
    statut VARCHAR(20) DEFAULT 'prévu' CHECK (statut IN ('prévu', 'embarquement', 'en_vol', 'atterri', 'annulé')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(avion_id) REFERENCES avion(id),
    FOREIGN KEY(aeroport_depart_id) REFERENCES aeroport(id),
    FOREIGN KEY(aeroport_arrivee_id) REFERENCES aeroport(id),
    CONSTRAINT check_dates CHECK (date_arrivee > date_depart),
    CONSTRAINT check_aeroports CHECK (aeroport_depart_id != aeroport_arrivee_id)
);

-- Table des réservations
CREATE TABLE reservation (
    id SERIAL PRIMARY KEY,
    reference VARCHAR(20) UNIQUE NOT NULL, -- RES20260109001
    utilisateur_id INT NOT NULL,
    vol_id INT NOT NULL,
    nb_passagers INT NOT NULL DEFAULT 1,
    prix_total NUMERIC(10,2) NOT NULL,
    statut VARCHAR(20) DEFAULT 'confirmée' CHECK (statut IN ('en_attente', 'confirmée', 'annulée')),
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
    numero_siege VARCHAR(10), -- 12A, 15C
    checked_in BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(reservation_id) REFERENCES reservation(id) ON DELETE CASCADE
);

-- Table des paiements
CREATE TABLE paiement (
    id SERIAL PRIMARY KEY,
    reservation_id INT NOT NULL,
    montant NUMERIC(10,2) NOT NULL,
    mode_paiement VARCHAR(30) NOT NULL CHECK (mode_paiement IN ('carte', 'paypal', 'espèces')),
    statut VARCHAR(20) DEFAULT 'réussi' CHECK (statut IN ('en_attente', 'réussi', 'échoué')),
    date_paiement TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(reservation_id) REFERENCES reservation(id),
    CONSTRAINT check_montant CHECK (montant > 0)
);

-- ============================================
-- INDEX POUR PERFORMANCE
-- ============================================

CREATE INDEX idx_vol_dates ON vol(date_depart, date_arrivee);
CREATE INDEX idx_vol_aeroport_depart ON vol(aeroport_depart_id);
CREATE INDEX idx_vol_aeroport_arrivee ON vol(aeroport_arrivee_id);
CREATE INDEX idx_reservation_utilisateur ON reservation(utilisateur_id);
CREATE INDEX idx_reservation_vol ON reservation(vol_id);
CREATE INDEX idx_passager_reservation ON passager(reservation_id);

-- ============================================
-- DONNÉES DE TEST
-- ============================================

-- Insérer des utilisateurs
INSERT INTO utilisateur (nom, prenom, email, mot_de_passe, telephone, role) VALUES
('Admin', 'System', 'admin@airline.com', '$2a$10$dummyhash', '0123456789', 'admin'),
('Dupont', 'Marie', 'marie.dupont@email.com', '$2a$10$dummyhash', '0612345678', 'client'),
('Martin', 'Pierre', 'pierre.martin@email.com', '$2a$10$dummyhash', '0698765432', 'client'),
('Agent', 'Réservation', 'agent@airline.com', '$2a$10$dummyhash', '0145678901', 'agent');

-- Insérer des aéroports
INSERT INTO aeroport (code, nom, ville, pays) VALUES
('CDG', 'Aéroport Charles de Gaulle', 'Paris', 'France'),
('ORY', 'Aéroport Orly', 'Paris', 'France'),
('LYS', 'Aéroport Lyon-Saint-Exupéry', 'Lyon', 'France'),
('MRS', 'Aéroport Marseille-Provence', 'Marseille', 'France'),
('JFK', 'John F. Kennedy International', 'New York', 'États-Unis'),
('LAX', 'Los Angeles International', 'Los Angeles', 'États-Unis'),
('LHR', 'London Heathrow', 'Londres', 'Royaume-Uni'),
('BCN', 'Aéroport de Barcelone', 'Barcelone', 'Espagne'),
('FCO', 'Aéroport Fiumicino', 'Rome', 'Italie'),
('DXB', 'Aéroport de Dubaï', 'Dubaï', 'Émirats Arabes Unis');

-- Insérer des avions
INSERT INTO avion (numero_avion, modele, capacite, statut) VALUES
('A320-001', 'Airbus A320', 180, 'disponible'),
('A320-002', 'Airbus A320', 180, 'disponible'),
('B737-001', 'Boeing 737', 189, 'disponible'),
('B737-002', 'Boeing 737', 189, 'en_vol'),
('A350-001', 'Airbus A350', 325, 'disponible'),
('B777-001', 'Boeing 777', 396, 'disponible');

-- Insérer des vols
INSERT INTO vol (numero_vol, avion_id, aeroport_depart_id, aeroport_arrivee_id, date_depart, date_arrivee, prix, places_disponibles, statut) VALUES
('AF001', 1, 1, 5, '2026-02-15 08:00:00', '2026-02-15 19:30:00', 450.00, 150, 'prévu'),
('AF002', 2, 5, 1, '2026-02-15 21:00:00', '2026-02-16 10:30:00', 480.00, 160, 'prévu'),
('AF101', 3, 1, 7, '2026-02-16 10:00:00', '2026-02-16 11:15:00', 89.00, 180, 'prévu'),
('AF102', 1, 3, 4, '2026-02-16 14:00:00', '2026-02-16 15:00:00', 65.00, 175, 'prévu'),
('AF201', 5, 1, 10, '2026-02-20 22:00:00', '2026-02-21 08:00:00', 850.00, 300, 'prévu'),
('AF301', 4, 2, 8, '2026-02-17 07:00:00', '2026-02-17 09:00:00', 120.00, 50, 'embarquement');

-- Insérer des réservations
INSERT INTO reservation (reference, utilisateur_id, vol_id, nb_passagers, prix_total, statut) VALUES
('RES20260109001', 2, 1, 2, 900.00, 'confirmée'),
('RES20260109002', 3, 3, 1, 89.00, 'confirmée'),
('RES20260109003', 2, 5, 3, 2550.00, 'confirmée');

-- Insérer des passagers
INSERT INTO passager (reservation_id, nom, prenom, date_naissance, numero_passeport, numero_siege, checked_in) VALUES
(1, 'Dupont', 'Marie', '1985-05-15', 'FR123456', '12A', TRUE),
(1, 'Dupont', 'Jean', '1983-08-20', 'FR123457', '12B', TRUE),
(2, 'Martin', 'Pierre', '1990-03-10', 'FR654321', '15C', FALSE),
(3, 'Dupont', 'Marie', '1985-05-15', 'FR123456', '8A', FALSE),
(3, 'Dupont', 'Sophie', '2010-12-01', 'FR123458', '8B', FALSE),
(3, 'Dupont', 'Lucas', '2015-06-15', 'FR123459', '8C', FALSE);

-- Insérer des paiements
INSERT INTO paiement (reservation_id, montant, mode_paiement, statut) VALUES
(1, 900.00, 'carte', 'réussi'),
(2, 89.00, 'paypal', 'réussi'),
(3, 2550.00, 'carte', 'réussi');

-- ============================================
-- VUES UTILES
-- ============================================

-- Vue des vols avec détails
CREATE VIEW v_vols_details AS
SELECT 
    v.id,
    v.numero_vol,
    v.date_depart,
    v.date_arrivee,
    ad.code AS code_depart,
    ad.ville AS ville_depart,
    ad.pays AS pays_depart,
    aa.code AS code_arrivee,
    aa.ville AS ville_arrivee,
    aa.pays AS pays_arrivee,
    a.modele AS avion_modele,
    v.prix,
    v.places_disponibles,
    v.statut
FROM vol v
JOIN aeroport ad ON v.aeroport_depart_id = ad.id
JOIN aeroport aa ON v.aeroport_arrivee_id = aa.id
JOIN avion a ON v.avion_id = a.id;

-- Vue des réservations complètes
CREATE VIEW v_reservations_completes AS
SELECT 
    r.id,
    r.reference,
    r.date_reservation,
    r.statut,
    u.nom || ' ' || u.prenom AS client,
    u.email,
    v.numero_vol,
    v.date_depart,
    ad.ville AS ville_depart,
    aa.ville AS ville_arrivee,
    r.nb_passagers,
    r.prix_total,
    p.statut AS statut_paiement
FROM reservation r
JOIN utilisateur u ON r.utilisateur_id = u.id
JOIN vol v ON r.vol_id = v.id
JOIN aeroport ad ON v.aeroport_depart_id = ad.id
JOIN aeroport aa ON v.aeroport_arrivee_id = aa.id
LEFT JOIN paiement p ON r.id = p.reservation_id;






-- 1. UTILISATEUR
INSERT INTO utilisateur (nom, prenom, email, mot_de_passe, telephone, role, actif) VALUES
('Martin', 'Sophie', 'sophie.martin@email.com', 'sophie123', '0612345678', 'CLIENT', true),
('Dubois', 'Thomas', 'thomas.dubois@email.com', 'thomas456', '0623456789', 'AGENT', true);

-- 2. AEROPORT
INSERT INTO aeroport (code, nom, ville, pays, actif) VALUES
('NCE', 'Aéroport Nice Côte d''Azur', 'Nice', 'France', true),
('MUC', 'Aéroport de Munich', 'Munich', 'Allemagne', true);

-- 3. AVION
INSERT INTO avion (numero_avion, modele, capacite, statut) VALUES
('A321-001', 'Airbus A321', 220, 'DISPONIBLE'),
('B787-001', 'Boeing 787 Dreamliner', 290, 'DISPONIBLE');

-- 4. VOL (assurez-vous que les IDs correspondent)
INSERT INTO vol (numero_vol, avion_id, aeroport_depart_id, aeroport_arrivee_id, date_depart, date_arrivee, prix, places_disponibles, statut) VALUES
('AF303', 1, 1, 2, '2026-03-10 08:00:00', '2026-03-10 10:00:00', 150.00, 210, 'PREVU'),
('AF304', 2, 2, 1, '2026-03-11 14:00:00', '2026-03-11 16:00:00', 160.00, 280, 'PREVU');

-- 5. RESERVATION
INSERT INTO reservation (reference, utilisateur_id, vol_id, nb_passagers, prix_total, statut, date_reservation) VALUES
('RES20260301001', 1, 1, 2, 300.00, 'CONFIRMEE', '2026-03-01 10:30:00'),
('RES20260301002', 2, 2, 1, 160.00, 'CONFIRMEE', '2026-03-01 11:15:00');

-- 6. PASSAGER
INSERT INTO passager (reservation_id, nom, prenom, date_naissance, numero_passeport, numero_siege, checked_in) VALUES
(1, 'Martin', 'Sophie', '1985-05-15', 'FR123456', '12A', true),
(1, 'Martin', 'Jean', '1983-08-20', 'FR123457', '12B', true);

-- 7. PAIEMENT
INSERT INTO paiement (reservation_id, montant, mode_paiement, statut, date_paiement) VALUES
(1, 300.00, 'CARTE', 'REUSSI', '2026-03-01 10:35:00'),
(2, 160.00, 'PAYPAL', 'REUSSI', '2026-03-01 11:20:00');


select 
from reservation r
join vol v on  r.vol_id = v.id
join paiement p on p.reservation_id = r.id where 


-- Recettes totales d'un vol spécifique
SELECT 
    v.numero_vol,
    v.date_depart,
    v.date_arrivee,
    ad.ville AS depart,
    aa.ville AS arrivee,
    COALESCE(SUM(r.prix_total), 0) AS recettes_totales
FROM vol v
LEFT JOIN reservation r ON v.id = r.vol_id
LEFT JOIN aeroport ad ON v.aeroport_depart_id = ad.id
LEFT JOIN aeroport aa ON v.aeroport_arrivee_id = aa.id
WHERE v.numero_vol = 'AF303'  -- Remplacez par le numéro du vol
GROUP BY v.id, ad.id, aa.id;