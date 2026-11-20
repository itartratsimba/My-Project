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

        // // Fond
        // g.setColor(new Color(50, 80, 50));
        // g.fillRect(0, 0, getWidth(), getHeight());

        // Dessiner les tableaux
        Tableau[] tableaux = partie.getTableaux();
        for (int i = 0; i < partie.getNbTableaux(); i++) {
            tableaux[i].dessiner(g);
        }

        // Dessiner le viseur (croix)
        Viseur v = partie.getViseur();
        int vx = v.getX();
        int vy = v.getY();
        int t = v.getTaille();

        g.setColor(Color.RED);
        g.drawLine(vx - t, vy, vx + t, vy);
        g.drawLine(vx, vy - t, vx, vy + t);
        g.drawOval(vx - t / 2, vy - t / 2, t, t);

        // Score
        Joueur j = partie.getJoueur();
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Joueur: " + j.getNom(), 20, 320);
        g.drawString("Score: " + j.getScore(), 20, 350);
        g.drawString("Tirs: " + j.getNbTirs(), 20, 380);
        g.drawString("Meilleur: " + j.getMeilleurScore(), 20, 410);

        // Message
        // String msg = partie.getDernierMessage();
        // if (msg.length() > 0) {
        // if (msg.startsWith("Touche")) {
        // g.setColor(Color.GREEN);
        // } else {
        // g.setColor(Color.RED);
        // }
        // g.setFont(new Font("Arial", Font.BOLD, 22));
        // g.drawString(msg, 300, 350);
        // }

        // Instructions
        g.setColor(Color.LIGHT_GRAY);
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.drawString("Fleches/Souris = deplacer | Espace/Clic = tirer | S = sauvegarder", 20, 480);

        int combo = partie.getCombo();
        if (combo > 0) {
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 28));
            g.drawString("COMBO x2 !", 300, 390);

        }

        int temps = partie.getTempsRestant();
        int minutes = temps / 60;
        int secondes = temps % 60;

        String tempsTexte = "";
        
        if (secondes < 10) {
            tempsTexte = minutes + ":0" + secondes;
        } else {
            tempsTexte = minutes + ":" + secondes;
        }

        if (temps <= 10) {
            g.setColor(Color.RED); // rouge quand il reste peu de temps
        } else {
            g.setColor(Color.WHITE);
        }
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Temps: " + tempsTexte, 800, 350);

        // Si partie terminee
    // Dans Dessin.java, à la fin de paintComponent(Graphics g) :

if (partie.isEstTerminee() == true) {
    // Fond sombre semi-transparent
    g.setColor(new Color(0, 0, 0, 210));
    g.fillRect(0, 0, getWidth(), getHeight());

    // Titre de fin
    g.setColor(Color.RED);
    g.setFont(new Font("Arial", Font.BOLD, 36));
    g.drawString("PARTIE TERMINEE !", 320, 60);

    // Score actuel
    g.setColor(Color.WHITE);
    g.setFont(new Font("Arial", Font.BOLD, 22));
    g.drawString("Votre score cette partie : " + partie.getJoueur().getScore() + " pts", 320, 100);

    // En-tête Historique
    g.setColor(Color.YELLOW);
    g.setFont(new Font("Arial", Font.BOLD, 20));
    g.drawString("=== HISTORIQUE DES DERNIERES PARTIES ===", 250, 150);

    // Récupérer et afficher chaque partie enregistrée
    ResultatPartie[] historique = partie.chargerHistorique();
    
    g.setFont(new Font("Monospaced", Font.PLAIN, 16));
    g.setColor(Color.CYAN);

    if (historique.length == 0) {
        g.drawString("Aucune partie enregistree pour l'instant.", 280, 200);
    } else {
        int yPos = 200;
        for (int i = 0; i < historique.length; i++) {
            ResultatPartie r = historique[i];
            String ligne = String.format("Partie #%-3d | Score: %-4d pts | Tirs: %-3d | Date: %s",
                    r.getId(), r.getScore(), r.getNbTirs(), r.getDatePartie());
            
            g.drawString(ligne, 200, yPos);
            yPos += 30;
        }
    }

    // Instructions pour rejouer
    g.setColor(Color.GREEN);
    g.setFont(new Font("Arial", Font.BOLD, 18));
    g.drawString("Appuyez sur [R] pour rejouer une nouvelle partie", 280, 450);
}
    }
    public Partie getPartie() {
        return partie;
    }
}