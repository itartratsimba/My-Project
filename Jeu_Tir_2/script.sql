-- Supprimer les anciennes tables
DROP TABLE historique;
DROP TABLE cible;
DROP TABLE joueur;
DROP SEQUENCE seq_joueur;
DROP SEQUENCE seq_historique;
DROP SEQUENCE seq_cible;

-- Séquences
CREATE SEQUENCE seq_joueur START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_historique START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_tableau START WITH 1 INCREMENT BY 1;

-- Table joueur
CREATE TABLE joueur (
    id NUMBER PRIMARY KEY,
    nom VARCHAR2(50) NOT NULL,
    meilleur_score NUMBER DEFAULT 0
);

-- Table tableau (remplace cible)
CREATE TABLE tableau (
    id NUMBER PRIMARY KEY,
    x NUMBER NOT NULL,
    y NUMBER NOT NULL,
    largeur NUMBER NOT NULL,
    hauteur NUMBER NOT NULL,
    point_max NUMBER NOT NULL,
    point_max_x NUMBER NOT NULL,
    point_max_y NUMBER NOT NULL
);

-- Table historique
CREATE TABLE historique (
    id NUMBER PRIMARY KEY,
    id_joueur NUMBER NOT NULL,
    score NUMBER NOT NULL,
    nb_tirs NUMBER NOT NULL,
    date_partie TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE partie (
    id_partie NUMBER PRIMARY KEY,
    date_partie TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insérer les joueurs
INSERT INTO joueur VALUES (1, 'Joueur1', 0);
INSERT INTO joueur VALUES (2, 'Joueur2', 0);

-- Insérer les tableaux
-- Chaque tableau a son pointMax à une position DIFFÉRENTE
INSERT INTO tableau VALUES (1, 50, 50, 200, 150, 20, 180, 120);
INSERT INTO tableau VALUES (2, 300, 80, 180, 130, 15, 350, 150);
INSERT INTO tableau VALUES (3, 550, 40, 220, 160, 25, 700, 100);
INSERT INTO tableau VALUES (4, 820, 60, 150, 140, 18, 870, 160);

COMMIT;

