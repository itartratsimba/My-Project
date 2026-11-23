-- Supprimer les anciennes tables puis les recréer.
-- Exécuter ce fichier comme script (F5 dans SQL Developer).
-- Chaque bloc ignore l'erreur si l'objet n'existe pas encore.
-- historique est supprimé avant partie (clé étrangère).
BEGIN EXECUTE IMMEDIATE 'DROP TABLE historique CASCADE CONSTRAINTS'; EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN EXECUTE IMMEDIATE 'DROP TABLE partie CASCADE CONSTRAINTS'; EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN EXECUTE IMMEDIATE 'DROP TABLE cible CASCADE CONSTRAINTS'; EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN EXECUTE IMMEDIATE 'DROP TABLE tableau CASCADE CONSTRAINTS'; EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN EXECUTE IMMEDIATE 'DROP TABLE joueur CASCADE CONSTRAINTS'; EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN EXECUTE IMMEDIATE 'DROP SEQUENCE seq_joueur'; EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN EXECUTE IMMEDIATE 'DROP SEQUENCE seq_historique'; EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN EXECUTE IMMEDIATE 'DROP SEQUENCE seq_cible'; EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN EXECUTE IMMEDIATE 'DROP SEQUENCE seq_tableau'; EXCEPTION WHEN OTHERS THEN NULL; END;
/
BEGIN EXECUTE IMMEDIATE 'DROP SEQUENCE seq_partie'; EXCEPTION WHEN OTHERS THEN NULL; END;
/

-- Séquences
CREATE SEQUENCE seq_joueur START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_historique START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_tableau START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_partie START WITH 1 INCREMENT BY 1;

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

-- Une partie = un seul chrono (15 secondes) pour tous les joueurs
CREATE TABLE partie (
    id_partie NUMBER PRIMARY KEY,
    date_partie TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    duree NUMBER DEFAULT 15
);

-- Score de chaque joueur pour une partie
CREATE TABLE historique (
    id NUMBER PRIMARY KEY,
    id_joueur NUMBER NOT NULL,
    score NUMBER NOT NULL,
    nb_tirs NUMBER NOT NULL,
    date_partie TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_partie NUMBER,
    CONSTRAINT fk_histo_joueur FOREIGN KEY (id_joueur) REFERENCES joueur(id),
    CONSTRAINT fk_histo_partie FOREIGN KEY (id_partie) REFERENCES partie(id_partie)
);

-- Joueurs nommés (Alex, Alice, etc.)
INSERT INTO joueur VALUES (1, 'Alex', 0);
INSERT INTO joueur VALUES (2, 'Alice', 0);
INSERT INTO joueur VALUES (3, 'Sam', 0);
INSERT INTO joueur VALUES (4, 'Lina', 0);
INSERT INTO joueur VALUES (5, 'Noah', 0);
INSERT INTO joueur VALUES (6, 'Maya', 0);

-- Insérer les tableaux
-- Chaque tableau a son pointMax à une position DIFFÉRENTE
INSERT INTO tableau VALUES (1, 50, 50, 200, 150, 20, 180, 120);
INSERT INTO tableau VALUES (2, 300, 80, 180, 130, 15, 350, 150);
INSERT INTO tableau VALUES (3, 550, 40, 220, 160, 25, 700, 100);
INSERT INTO tableau VALUES (4, 820, 60, 150, 140, 18, 870, 160);

COMMIT;
