package jeu;

import utilitaires.Connexion;
import java.sql.ResultSet;

public class Partie {

    private Joueur joueur;
    private Tableau[] tableaux;
    private Viseur viseur;
    private int nbTableaux;
    private String dernierMessage;
    private int dernierTableauId;
    private int combo;
    private int duree;
    private int tempsRestant;
    private boolean estTerminee;

    public Partie(Joueur joueur, int duree) throws Exception {
        this.joueur = joueur;
        this.dernierMessage = "";
        this.viseur = new Viseur(400, 400);
        this.duree = duree;
        this.tempsRestant = duree;
        this.estTerminee = false;

        chargerTableaux();
    }

    public void tick() {
        if (estTerminee == true) {
            return;
        }
        tempsRestant = tempsRestant - 1;

        if (tempsRestant <= 0) {
            tempsRestant = 0;
            estTerminee = true;
            dernierMessage = "Terminer, R pour Recommencer";
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
        System.out.println("Tableaux charges: " + nbTableaux);
    }

    public int tirer() {

        if (estTerminee == true) {
            dernierMessage = "Partie terminee ! Appuie sur R pour recommencer";
            return 0;
        }

        int vx = viseur.getX();
        int vy = viseur.getY();
        boolean estSurPointMax = false;
        int tableauTouche = -1;
        int pointsGagnes = 0;

        for (int i = 0; i < nbTableaux; i++) {
            int p = tableaux[i].calculerPoints(vx, vy);
            if (p > 0) {
                pointsGagnes = p;
                dernierMessage = "Touche ! +" + p + " pts sur tableau #" + tableaux[i].getId();
            }
        }

        if (pointsGagnes == 0) {
            combo = 0;
            dernierTableauId = -1;
            dernierMessage = "Rate ! 0 point";
        } else if (estSurPointMax == true && tableauTouche == dernierTableauId) {
            combo++;
            pointsGagnes = pointsGagnes * 2;

        } else {
            combo = 0;
            dernierTableauId = tableauTouche;
        }

        joueur.tirer(pointsGagnes);
        return pointsGagnes;
    }

    public void recommencer() {
        tempsRestant = duree;
        estTerminee = false;
        combo = 0;
        dernierTableauId = -1;
        joueur.setScore(0);
        dernierMessage = "Nouvelle partie !";
    }

    public void sauvegarder() throws Exception {
        Connexion c = new Connexion();

        String reqHistorique = "INSERT INTO historique VALUES ("
                + "seq_historique.NEXTVAL, "
                + joueur.getId() + ", "
                + joueur.getScore() + ", "
                + joueur.getNbTirs() + ", "
                + "CURRENT_TIMESTAMP)";
        c.executer(reqHistorique);

        if (joueur.getScore() > joueur.getMeilleurScore()) {
            String reqMeilleur = "UPDATE joueur SET meilleur_score = "
                    + joueur.getScore()
                    + " WHERE id = " + joueur.getId();
            c.executer(reqMeilleur);
            joueur.setMeilleurScore(joueur.getScore());
        }

        System.out.println("Partie sauvegardee !");
    }

    public ResultatPartie[] chargerHistorique() {
        ResultatPartie[] histo = new ResultatPartie[5];
        int nb = 0;

        try {
            Connexion c = new Connexion();
            // On récupère les 5 dernières parties du joueur
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

        // On retourne un tableau ajusté au nombre réel de parties trouvées
        ResultatPartie[] resultat = new ResultatPartie[nb];
        for (int i = 0; i < nb; i++) {
            resultat[i] = histo[i];
        }
        return resultat;
    }

    public Joueur getJoueur() {
        return joueur;
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

    public int getDuree() {
        return duree;
    }

    public int getTempsRestant() {
        return tempsRestant;
    }

    public boolean isEstTerminee() {
        return estTerminee;
    }
}