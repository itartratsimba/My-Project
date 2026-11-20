package main;

import utilitaires.Connexion;
import jeu.Joueur;
import jeu.Partie;
import aff.Fenetre;
import java.sql.ResultSet;

public class Main {
    public static void main(String[] args) throws Exception {

        Connexion c = new Connexion();
        ResultSet rs = c.getAllLines("SELECT * FROM joueur WHERE ROWNUM = 1");

        Joueur joueur = null;
        if (rs.next()) {
            int id = rs.getInt("id");
            String nom = rs.getString("nom");
            int meilleur = rs.getInt("meilleur_score");
            joueur = new Joueur(id, nom, meilleur);
        }
        c.fermerConnexion(rs);

        // ← Vérifier que le joueur existe
        if (joueur == null) {
            System.out.println("ERREUR: aucun joueur trouve dans la BDD !");
            return;
        }
        
        Partie partie = new Partie(joueur, 15);
        Fenetre fenetre = new Fenetre(partie);

        System.out.println("Jeu de Tir lance !");
        System.out.println("Joueur: " + joueur);
        System.out.println("Jeu de Tir lance ! Duree: 15 secondes");
    }
}