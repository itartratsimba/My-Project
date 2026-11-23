package aff;

import jeu.Partie;
import jeu.Tableau;
import jeu.Viseur;
import jeu.Joueur;
import jeu.ResultatPartie;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class Dessin extends JPanel {

    private Partie partie;

    public Dessin(Partie partie) {
        this.partie = partie;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 1. Fond blanc
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        // 2. Barre des Joueurs en haut
        Joueur[] liste = partie.getListeJoueurs();
        int nbJ = partie.getNbJoueurs();

        for (int i = 0; i < nbJ; i++) {
            int x = 10 + i * 150;

            // Couleur : Vert si sélectionné, Gris sinon
            if (liste[i] == partie.getJoueur()) {
                g.setColor(Color.GREEN);
            } else {
                g.setColor(Color.LIGHT_GRAY);
            }

            g.fillRect(x, 5, 140, 30);
            g.setColor(Color.BLACK);
            g.drawRect(x, 5, 140, 30);
            g.drawString(liste[i].getNom(), x + 20, 25);
        }

        // 3. Dessiner les tableaux de cible
        Tableau[] tableaux = partie.getTableaux();
        for (int i = 0; i < partie.getNbTableaux(); i++) {
            tableaux[i].dessiner(g);
        }

        // 4. Dessiner le viseur (croix rouge)
        Viseur v = partie.getViseur();
        int vx = v.getX();
        int vy = v.getY();

        g.setColor(Color.RED);
        g.drawLine(vx - 10, vy, vx + 10, vy);
        g.drawLine(vx, vy - 10, vx, vy + 10);

        // 5. Afficher les informations du jeu (en bas)
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));

        Joueur j = partie.getJoueur();
        g.drawString("Joueur: " + j.getNom(), 20, 320);
        g.drawString("Score: " + j.getScore() + " pts", 20, 350);
        g.drawString("Temps: " + partie.getTempsRestant() + " s", 20, 380);

        // Message du dernier tir
        g.drawString(partie.getDernierMessage(), 300, 350);

        // 6. Écran de FIN DE PARTIE
        if (partie.isEstTerminee() == true) {
            // Fond gris de fin
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(150, 50, 700, 400);
            g.setColor(Color.BLACK);
            g.drawRect(150, 50, 700, 400);

            g.setFont(new Font("Arial", Font.BOLD, 22));
            g.drawString("PARTIE TERMINEE !", 380, 90);
            g.drawString("Score final : " + j.getScore() + " pts", 380, 130);

            // Historique des parties
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString("--- Historique des parties ---", 350, 180);

            ResultatPartie[] histo = partie.chargerHistorique();
            int yPos = 220;
            for (int i = 0; i < histo.length; i++) {
                g.drawString("Partie #" + histo[i].getId()
                        + " | Score: " + histo[i].getScore() + " pts"
                        + " | Tirs: " + histo[i].getNbTirs()
                        + " | Date: " + histo[i].getDatePartie(), 220, yPos);
                yPos = yPos + 30;
            }

            g.setColor(Color.BLUE);
            g.drawString("Appuyez sur R pour rejouer", 360, 420);
        }
    }

    public Partie getPartie() {
        return partie;
    }
}