package main;

import jeu.Partie;
import aff.Fenetre;

public class Main {
    public static void main(String[] args) throws Exception {

        // Lancer la partie directement avec une durée de 60 secondes
        Partie partie = new Partie(15);
        Fenetre fenetre = new Fenetre(partie);

        System.out.println("Jeu de Tir lance ! Selection des joueurs directement en haut de l'ecran.");
    }
}