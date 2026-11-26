-- TP Ajax : base MySQL / MariaDB
-- Import : mysql -u root -p < database.sql   (ou via phpMyAdmin, onglet Importer)
-- Compte de test : rasoa@mail.com / 1234 (mot de passe stocké haché)

CREATE DATABASE IF NOT EXISTS tp_ajax CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
SET NAMES utf8mb4;
USE tp_ajax;

DROP TABLE IF EXISTS commentaire;
DROP TABLE IF EXISTS publication;
DROP TABLE IF EXISTS membre;

-- Table membre
CREATE TABLE membre (
    id    INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nom   VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    pwd   VARCHAR(255) NOT NULL
) ENGINE=InnoDB;

INSERT INTO membre (nom, email, pwd) VALUES
('Rasoa',  'rasoa@mail.com',  '$2y$10$7zGloHb1EbVCzHxbZVtSe.3h6JR75Ul5aU1gieejwFJk3kSUuz.0.'),
('Rakoto', 'rakoto@mail.com', '$2y$10$7zGloHb1EbVCzHxbZVtSe.3h6JR75Ul5aU1gieejwFJk3kSUuz.0.'),
('Rabe',   'rabe@mail.com',   '$2y$10$7zGloHb1EbVCzHxbZVtSe.3h6JR75Ul5aU1gieejwFJk3kSUuz.0.');
