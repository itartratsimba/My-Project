package jeu;

import utilitaires.Connexion;
import java.sql.ResultSet;

public class Partie {

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

    public Partie(int duree) throws Exception {
        this.dernierMessage = "";
        this.viseur = new Viseur(400, 400);
        this.dernierTableauId = -1;
        this.combo = 0;

        this.duree = duree;
        this.tempsRestant = duree;
        this.estTerminee = false;
        this.dejaSauvegardee = false;

        this.listeTirs = new Tir[200];
        this.nbTirsEffectues = 0;

        // 1. Charger tous les joueurs depuis Oracle
        chargerTousLesJoueurs();

        // 2. Charger les tableaux
        chargerTableaux();
    }

    private void chargerTousLesJoueurs() throws Exception {
        Connexion c = new Connexion();
        ResultSet rs = c.getAllLines("SELECT * FROM joueur ORDER BY id");

        listeJoueurs = new Joueur[20];
        nbJoueurs = 0;

        while (rs != null && rs.next()) {
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
    }

    // Méthode pour changer de joueur actif quand on clique sur un onglet
    public void changerJoueur(int index) {
        if (index >= 0 && index < nbJoueurs) {
            this.joueur = listeJoueurs[index];
            recommencer(); // Réinitialiser le score et le temps pour le nouveau joueur
            dernierMessage = "Joueur actif : " + joueur.getNom();
        }
    }

    private void chargerTableaux() throws Exception {
        Connexion c = new Connexion();
        ResultSet rs = c.getAllLines("SELECT * FROM tableau ORDER BY id");

        tableaux = new Tableau[20];
        nbTableaux = 0;

        while (rs.next()) {
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
            return;
        }

        tempsRestant = tempsRestant - 1;

        if (tempsRestant <= 0) {
            tempsRestant = 0;
            estTerminee = true;

            if (!dejaSauvegardee) {
                sauvegarder();
                dernierMessage = "TEMPS ECOULE ! Partie enregistree automatiquement !";
            }
        }
    }

    public int tirer() {
        if (estTerminee) {
            dernierMessage = "Partie terminee ! Appuie sur R pour recommencer";
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
        tempsRestant = duree;
        estTerminee = false;
        dejaSauvegardee = false;
        combo = 0;
        dernierTableauId = -1;
        nbTirsEffectues = 0;
        joueur.setScore(0);
    }

    public void sauvegarder() {
        if (dejaSauvegardee || joueur == null) {
            return;
        }

        Connexion c = new Connexion();

        String reqHistorique = "INSERT INTO historique (id, id_joueur, score, nb_tirs) VALUES ("
                + "(SELECT NVL(MAX(id), 0) + 1 FROM historique), "
                + joueur.getId() + ", "
                + joueur.getScore() + ", "
                + joueur.getNbTirs() + ")";
        c.executer(reqHistorique);

        if (joueur.getScore() > joueur.getMeilleurScore()) {
            String reqMeilleur = "UPDATE joueur SET meilleur_score = "
                    + joueur.getScore()
                    + " WHERE id = " + joueur.getId();
            c.executer(reqMeilleur);
            joueur.setMeilleurScore(joueur.getScore());
        }

        dejaSauvegardee = true;
        System.out.println("Partie sauvegardee pour " + joueur.getNom());
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

    public boolean isEstTerminee() {
        return estTerminee;
    }
}