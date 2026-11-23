package jeu;

import utilitaires.Connexion;
import java.sql.ResultSet;

// Chrono unique pour toute la partie (pas 15 secondes par joueur).
// A la fin : une ligne dans la table PARTIE, et les scores dans HISTORIQUE.
public class Partie {

    private static final String[] NOMS_PAR_DEFAUT = {
        "Alex", "Alice", "Sam", "Lina", "Noah", "Maya"
    };

    private Joueur joueur; // Joueur actif actuel
    private Joueur[] listeJoueurs; // Liste de tous les joueurs
    private int nbJoueurs; // Nombre de joueurs dans la liste

    private Tableau[] tableaux;
    private Viseur viseur;
    private int nbTableaux;
    private String dernierMessage;

    private int dernierTableauId;
    private int combo;

    private int duree;
    private int tempsRestant;
    private boolean estTerminee;

    private Tir[] listeTirs;
    private int nbTirsEffectues;
    private boolean dejaSauvegardee;
    private int idPartie;
    private int tentativesSauvegarde;

    public Partie(int duree) throws Exception {
        this.dernierMessage = "Partie de " + duree + " s pour tous les joueurs";
        this.viseur = new Viseur(400, 400);
        this.dernierTableauId = -1;
        this.combo = 0;

        this.duree = duree;
        this.tempsRestant = duree;
        this.estTerminee = false;
        this.dejaSauvegardee = false;
        this.idPartie = 0;
        this.tentativesSauvegarde = 0;

        this.listeTirs = new Tir[200];
        this.nbTirsEffectues = 0;

        // 1. Table partie + colonne historique.id_partie si besoin
        assurerSchema();

        // 2. Noms des joueurs (Alex, Alice, ...)
        assurerJoueurs();

        // 3. Charger tous les joueurs depuis Oracle
        chargerTousLesJoueurs();

        // 4. Charger les tableaux
        chargerTableaux();

        System.out.println("Partie de " + duree + " s commune a tous les joueurs.");
    }

    private void assurerSchema() {
        Connexion c = new Connexion();

        if (!requeteRetourneUneLigne(
                "SELECT table_name FROM user_tables WHERE table_name = 'PARTIE'")) {
            c.executer("CREATE TABLE partie ("
                    + "id_partie NUMBER PRIMARY KEY, "
                    + "date_partie TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                    + "duree NUMBER DEFAULT 15)");
        } else if (!requeteRetourneUneLigne(
                "SELECT column_name FROM user_tab_columns "
                        + "WHERE table_name = 'PARTIE' AND column_name = 'DUREE'")) {
            c.executer("ALTER TABLE partie ADD (duree NUMBER DEFAULT 15)");
        }

        if (!requeteRetourneUneLigne(
                "SELECT column_name FROM user_tab_columns "
                        + "WHERE table_name = 'HISTORIQUE' AND column_name = 'ID_PARTIE'")) {
            c.executer("ALTER TABLE historique ADD (id_partie NUMBER)");
        }
    }

    private void assurerJoueurs() {
        Connexion c = new Connexion();

        // Anciens noms generiques -> vrais prenoms
        c.executer("UPDATE joueur SET nom = 'Alex' WHERE LOWER(nom) = 'joueur1'");
        c.executer("UPDATE joueur SET nom = 'Alice' WHERE LOWER(nom) = 'joueur2'");

        for (int i = 0; i < NOMS_PAR_DEFAUT.length; i++) {
            String nom = NOMS_PAR_DEFAUT[i];
            String req = "INSERT INTO joueur (id, nom, meilleur_score) "
                    + "SELECT (SELECT NVL(MAX(id), 0) + 1 FROM joueur), '" + nom + "', 0 FROM dual "
                    + "WHERE NOT EXISTS (SELECT 1 FROM joueur WHERE LOWER(nom) = '"
                    + nom.toLowerCase() + "')";
            c.executer(req);
        }
    }

    private boolean requeteRetourneUneLigne(String requete) {
        ResultSet rs = null;
        Connexion c = new Connexion();
        try {
            rs = c.getAllLines(requete);
            return rs != null && rs.next();
        } catch (Exception e) {
            System.out.println("Erreur schema: " + e.getMessage());
            return false;
        } finally {
            try {
                c.fermerConnexion(rs);
            } catch (Exception e) {
                // rien
            }
        }
    }

    private void chargerTousLesJoueurs() throws Exception {
        Connexion c = new Connexion();
        ResultSet rs = c.getAllLines("SELECT * FROM joueur ORDER BY id");

        listeJoueurs = new Joueur[50];
        nbJoueurs = 0;

        while (rs != null && rs.next() && nbJoueurs < listeJoueurs.length) {
            int id = rs.getInt("id");
            String nom = rs.getString("nom");
            int meilleur = rs.getInt("meilleur_score");

            listeJoueurs[nbJoueurs] = new Joueur(id, nom, meilleur);
            nbJoueurs++;
        }
        c.fermerConnexion(rs);

        // Sélectionner le 1er joueur par défaut
        if (nbJoueurs > 0) {
            this.joueur = listeJoueurs[0];
        }

        System.out.println(nbJoueurs + " joueur(s) charge(s) :");
        for (int i = 0; i < nbJoueurs; i++) {
            System.out.println(" - " + listeJoueurs[i].getNom());
        }
    }

    // Change le joueur actif sans relancer le chrono ni effacer les scores
    public void changerJoueur(int index) {
        if (index >= 0 && index < nbJoueurs) {
            this.joueur = listeJoueurs[index];
            this.combo = 0;
            this.dernierTableauId = -1;
            if (estTerminee) {
                dernierMessage = "Partie terminée. Joueur : " + joueur.getNom();
            } else {
                dernierMessage = joueur.getNom()
                        + " joue - le chrono continue (" + tempsRestant + " s)";
            }
        }
    }

    private void chargerTableaux() throws Exception {
        Connexion c = new Connexion();
        ResultSet rs = c.getAllLines("SELECT * FROM tableau ORDER BY id");

        tableaux = new Tableau[20];
        nbTableaux = 0;

        while (rs.next() && nbTableaux < tableaux.length) {
            int id = rs.getInt("id");
            int x = rs.getInt("x");
            int y = rs.getInt("y");
            int largeur = rs.getInt("largeur");
            int hauteur = rs.getInt("hauteur");
            int pointMax = rs.getInt("point_max");
            int pointMaxX = rs.getInt("point_max_x");
            int pointMaxY = rs.getInt("point_max_y");

            tableaux[nbTableaux] = new Tableau(id, x, y, largeur, hauteur,
                    pointMax, pointMaxX, pointMaxY);
            nbTableaux++;
        }
        c.fermerConnexion(rs);
    }

    public void tick() {
        if (estTerminee) {
            if (!dejaSauvegardee && tentativesSauvegarde < 3) {
                tentativesSauvegarde++;
                sauvegarder();
            }
            return;
        }

        tempsRestant = tempsRestant - 1;

        if (tempsRestant <= 0) {
            tempsRestant = 0;
            estTerminee = true;
            tentativesSauvegarde++;
            sauvegarder();
        }
    }

    public int tirer() {
        if (joueur == null) {
            dernierMessage = "Aucun joueur sélectionné";
            return 0;
        }

        if (estTerminee) {
            dernierMessage = "Partie terminée ! Appuie sur R pour recommencer";
            return 0;
        }

        int vx = viseur.getX();
        int vy = viseur.getY();
        int pointsGagnes = 0;
        int tableauTouche = -1;
        boolean surPointMax = false;

        for (int i = 0; i < nbTableaux; i++) {
            int p = tableaux[i].calculerPoints(vx, vy);
            if (p > 0) {
                pointsGagnes = p;
                tableauTouche = tableaux[i].getId();
                surPointMax = tableaux[i].estSurPointMax(vx, vy);
            }
        }

        boolean comboApplique = false;
        if (pointsGagnes == 0) {
            combo = 0;
            dernierTableauId = -1;
            dernierMessage = "Rate ! 0 point";
        } else if (surPointMax && tableauTouche == dernierTableauId) {
            combo++;
            pointsGagnes = pointsGagnes * 2;
            comboApplique = true;
            dernierMessage = "COMBO x2 ! +" + pointsGagnes + " pts";
        } else {
            combo = 0;
            dernierTableauId = tableauTouche;
            dernierMessage = "Touche ! +" + pointsGagnes + " pts";
        }

        Tir nouveauTir = new Tir(vx, vy, pointsGagnes, tableauTouche, comboApplique);
        if (nbTirsEffectues < listeTirs.length) {
            listeTirs[nbTirsEffectues] = nouveauTir;
            nbTirsEffectues++;
        }

        joueur.tirer(pointsGagnes);
        return pointsGagnes;
    }

    public void recommencer() {
        for (int i = 0; i < nbJoueurs; i++) {
            listeJoueurs[i].reinitialiser();
        }
        tempsRestant = duree;
        estTerminee = false;
        dejaSauvegardee = false;
        idPartie = 0;
        tentativesSauvegarde = 0;
        combo = 0;
        dernierTableauId = -1;
        nbTirsEffectues = 0;
        dernierMessage = "Nouvelle partie : " + duree + " s pour tous les joueurs";
    }

    public void sauvegarder() {
        if (dejaSauvegardee || !estTerminee || nbJoueurs == 0) {
            return;
        }

        Connexion c = new Connexion();
        int avant = lireDernierIdPartie();
        boolean aDuree = requeteRetourneUneLigne(
                "SELECT column_name FROM user_tab_columns "
                        + "WHERE table_name = 'PARTIE' AND column_name = 'DUREE'");

        if (aDuree) {
            c.executer("INSERT INTO partie (id_partie, duree) VALUES ("
                    + "(SELECT NVL(MAX(id_partie), 0) + 1 FROM partie), " + duree + ")");
        } else {
            c.executer("INSERT INTO partie (id_partie) VALUES ("
                    + "(SELECT NVL(MAX(id_partie), 0) + 1 FROM partie))");
        }

        int apres = lireDernierIdPartie();
        if (apres > avant) {
            idPartie = apres;
        } else {
            idPartie = 0;
            dernierMessage = "TEMPS ÉCOULÉ ! Impossible d'écrire dans la table partie.";
            return;
        }

        boolean avecIdPartie = requeteRetourneUneLigne(
                "SELECT column_name FROM user_tab_columns "
                        + "WHERE table_name = 'HISTORIQUE' AND column_name = 'ID_PARTIE'");

        for (int i = 0; i < nbJoueurs; i++) {
            Joueur j = listeJoueurs[i];
            String reqHistorique;
            if (avecIdPartie) {
                reqHistorique = "INSERT INTO historique (id, id_joueur, score, nb_tirs, id_partie) VALUES ("
                        + "(SELECT NVL(MAX(id), 0) + 1 FROM historique), "
                        + j.getId() + ", "
                        + j.getScore() + ", "
                        + j.getNbTirs() + ", "
                        + idPartie + ")";
            } else {
                reqHistorique = "INSERT INTO historique (id, id_joueur, score, nb_tirs) VALUES ("
                        + "(SELECT NVL(MAX(id), 0) + 1 FROM historique), "
                        + j.getId() + ", "
                        + j.getScore() + ", "
                        + j.getNbTirs() + ")";
            }
            c.executer(reqHistorique);

            if (j.getScore() > j.getMeilleurScore()) {
                String reqMeilleur = "UPDATE joueur SET meilleur_score = "
                        + j.getScore()
                        + " WHERE id = " + j.getId();
                c.executer(reqMeilleur);
                j.setMeilleurScore(j.getScore());
            }
        }

        dejaSauvegardee = true;
        dernierMessage = "TEMPS ÉCOULÉ ! Partie n° " + idPartie + " enregistrée.";
        System.out.println("Partie #" + idPartie + " sauvegardee ("
                + duree + " s, " + nbJoueurs + " joueurs).");
    }

    private int lireDernierIdPartie() {
        ResultSet rs = null;
        Connexion c = new Connexion();
        try {
            rs = c.getAllLines("SELECT NVL(MAX(id_partie), 0) AS idp FROM partie");
            if (rs != null && rs.next()) {
                return rs.getInt("idp");
            }
        } catch (Exception e) {
            System.out.println("Erreur lecture partie: " + e.getMessage());
        } finally {
            try {
                c.fermerConnexion(rs);
            } catch (Exception e) {
                // rien
            }
        }
        return 0;
    }

    public ResultatPartie[] chargerHistorique() {
        ResultatPartie[] histo = new ResultatPartie[5];
        int nb = 0;

        try {
            Connexion c = new Connexion();
            String req = "SELECT id, score, nb_tirs, TO_CHAR(date_partie, 'DD/MM HH24:MI') as dt "
                    + "FROM historique WHERE id_joueur = " + joueur.getId()
                    + " ORDER BY id DESC";

            ResultSet rs = c.getAllLines(req);
            while (rs != null && rs.next() && nb < 5) {
                int id = rs.getInt("id");
                int sc = rs.getInt("score");
                int tirs = rs.getInt("nb_tirs");
                String dt = rs.getString("dt");

                histo[nb] = new ResultatPartie(id, sc, tirs, dt);
                nb++;
            }
            c.fermerConnexion(rs);
        } catch (Exception e) {
            System.out.println("Erreur chargement historique: " + e.getMessage());
        }

        ResultatPartie[] resultat = new ResultatPartie[nb];
        for (int i = 0; i < nb; i++) {
            resultat[i] = histo[i];
        }
        return resultat;
    }

    // Copie triee : score de la partie, puis record, puis nom
    public Joueur[] getJoueursClasses() {
        Joueur[] copie = new Joueur[nbJoueurs];
        for (int i = 0; i < nbJoueurs; i++) {
            copie[i] = listeJoueurs[i];
        }

        for (int i = 0; i < copie.length - 1; i++) {
            for (int j = i + 1; j < copie.length; j++) {
                if (compareJoueurs(copie[j], copie[i]) < 0) {
                    Joueur tmp = copie[i];
                    copie[i] = copie[j];
                    copie[j] = tmp;
                }
            }
        }
        return copie;
    }

    private int compareJoueurs(Joueur a, Joueur b) {
        if (a.getScore() != b.getScore()) {
            return b.getScore() - a.getScore();
        }
        if (a.getMeilleurScore() != b.getMeilleurScore()) {
            return b.getMeilleurScore() - a.getMeilleurScore();
        }
        String na = a.getNom() == null ? "" : a.getNom();
        String nb = b.getNom() == null ? "" : b.getNom();
        return na.compareToIgnoreCase(nb);
    }

    // Getters
    public Joueur getJoueur() {
        return joueur;
    }

    public Joueur[] getListeJoueurs() {
        return listeJoueurs;
    }

    public int getNbJoueurs() {
        return nbJoueurs;
    }

    public Tableau[] getTableaux() {
        return tableaux;
    }

    public int getNbTableaux() {
        return nbTableaux;
    }

    public Viseur getViseur() {
        return viseur;
    }

    public String getDernierMessage() {
        return dernierMessage;
    }

    public int getCombo() {
        return combo;
    }

    public int getTempsRestant() {
        return tempsRestant;
    }

    public int getDuree() {
        return duree;
    }

    public int getIdPartie() {
        return idPartie;
    }

    public boolean isEstTerminee() {
        return estTerminee;
    }
}
