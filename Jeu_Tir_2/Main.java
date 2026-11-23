package main;

import jeu.Partie;
import aff.Fenetre;

public class Main {
    public static void main(String[] args) throws Exception {

        // Une seule partie de 15 secondes pour tous les joueurs.
        // Changer de joueur ne relance pas le chrono.
        Partie partie = new Partie(15);
        Fenetre fenetre = new Fenetre(partie);

        System.out.println("Jeu de Tir lance !");
        System.out.println("Partie de 15 secondes commune a tous les joueurs.");
        System.out.println("Classement et recherche par nom : panneau de droite.");
    }
}
