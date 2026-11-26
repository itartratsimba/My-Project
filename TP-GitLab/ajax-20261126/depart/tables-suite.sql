-- Tables supplémentaires pour la suite du TP (à importer après database.sql)
-- Import : mysql -u root -p < tables-suite.sql   (ou phpMyAdmin, onglet Importer)

SET NAMES utf8mb4;
USE tp_ajax;

DROP TABLE IF EXISTS commentaire;
DROP TABLE IF EXISTS publication;

CREATE TABLE publication (
    id        INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    membre_id INT UNSIGNED NOT NULL,
    texte     TEXT NOT NULL,
    date      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (membre_id) REFERENCES membre(id)
) ENGINE=InnoDB;

CREATE TABLE commentaire (
    id             INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    publication_id INT UNSIGNED NOT NULL,
    membre_id      INT UNSIGNED NOT NULL,
    texte          TEXT NOT NULL,
    date           DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (publication_id) REFERENCES publication(id),
    FOREIGN KEY (membre_id) REFERENCES membre(id)
) ENGINE=InnoDB;

INSERT INTO publication (membre_id, texte) VALUES
(1, 'Bienvenue sur notre mini réseau social !'),
(2, 'Le TP Ajax est presque terminé.');

INSERT INTO commentaire (publication_id, membre_id, texte) VALUES
(1, 2, 'Merci Rasoa, belle initiative.'),
(1, 3, 'Très bien présenté.'),
(2, 1, 'Courage pour la suite !');
