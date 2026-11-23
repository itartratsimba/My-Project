package aff;

import jeu.Partie;
import jeu.Tableau;
import jeu.Viseur;
import jeu.Joueur;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

public class Dessin extends JPanel {

    private static final int MARGE = 10;
    private static final int ESPACE = 8;
    private static final int Y_ONGLET = 6;
    private static final int H_ONGLET = 30;

    private Partie partie;

    public Dessin(Partie partie) {
        this.partie = partie;
        setBackground(Color.WHITE);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        dessinerOnglets(g);
        dessinerTableaux(g);
        dessinerViseur(g);
        dessinerHud(g);

        if (partie.isEstTerminee()) {
            dessinerFin(g);
        }
    }

    private void dessinerOnglets(Graphics g) {
        Joueur[] liste = partie.getListeJoueurs();
        int nb = partie.getNbJoueurs();
        if (liste == null || nb <= 0) {
            return;
        }

        g.setColor(new Color(248, 248, 248));
        g.fillRect(0, 0, getWidth(), Y_ONGLET + H_ONGLET + 2);

        int largeur = largeurOnglet(nb);
        g.setFont(new Font("Arial", Font.BOLD, 13));
        FontMetrics fm = g.getFontMetrics();

        for (int i = 0; i < nb; i++) {
            int x = xOnglet(i, largeur);
            if (liste[i] == partie.getJoueur()) {
                g.setColor(new Color(90, 190, 90));
            } else {
                g.setColor(new Color(220, 220, 220));
            }
            g.fillRect(x, Y_ONGLET, largeur, H_ONGLET);
            g.setColor(Color.BLACK);
            g.drawRect(x, Y_ONGLET, largeur, H_ONGLET);

            String label = liste[i].getNom() + " (" + liste[i].getScore() + ")";
            if (fm.stringWidth(label) > largeur - 10) {
                label = liste[i].getNom();
            }
            int tx = x + (largeur - fm.stringWidth(label)) / 2;
            if (tx < x + 4) {
                tx = x + 4;
            }
            g.drawString(label, tx, Y_ONGLET + 20);
        }
    }

    private int largeurOnglet(int nb) {
        if (nb <= 0) {
            return 140;
        }
        int dispo = getWidth() - 2 * MARGE - ESPACE * (nb - 1);
        if (dispo < nb) {
            dispo = nb;
        }
        int w = dispo / nb;
        if (w > 170) {
            w = 170;
        }
        if (w < 64) {
            w = 64;
        }
        return w;
    }

    private int xOnglet(int i, int largeur) {
        return MARGE + i * (largeur + ESPACE);
    }

    public int getIndexOnglet(int mx, int my) {
        int nb = partie.getNbJoueurs();
        if (nb <= 0) {
            return -1;
        }
        if (my < Y_ONGLET || my > Y_ONGLET + H_ONGLET) {
            return -1;
        }
        int largeur = largeurOnglet(nb);
        for (int i = 0; i < nb; i++) {
            int x = xOnglet(i, largeur);
            if (mx >= x && mx <= x + largeur) {
                return i;
            }
        }
        return -1;
    }

    private void dessinerTableaux(Graphics g) {
        Tableau[] tableaux = partie.getTableaux();
        if (tableaux == null) {
            return;
        }
        for (int i = 0; i < partie.getNbTableaux(); i++) {
            tableaux[i].dessiner(g);
        }
    }

    private void dessinerViseur(Graphics g) {
        Viseur v = partie.getViseur();
        if (v == null) {
            return;
        }
        int vx = v.getX();
        int vy = v.getY();
        g.setColor(Color.RED);
        g.drawLine(vx - 10, vy, vx + 10, vy);
        g.drawLine(vx, vy - 10, vx, vy + 10);
    }

    private void dessinerHud(Graphics g) {
        int h = getHeight();
        int yInfo = h - 92;
        if (yInfo < 240) {
            yInfo = 240;
        }

        Joueur j = partie.getJoueur();
        g.setFont(new Font("Arial", Font.BOLD, 16));

        if (partie.getTempsRestant() <= 5 && !partie.isEstTerminee()) {
            g.setColor(new Color(180, 30, 30));
        } else {
            g.setColor(Color.BLACK);
        }
        g.drawString("Temps de la partie : " + partie.getTempsRestant()
                + " s / " + partie.getDuree() + " s", 20, yInfo);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.drawString("Chrono commun à tous les joueurs (changer de joueur ne le relance pas)",
                20, yInfo + 22);

        if (j != null) {
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString("Joueur : " + j.getNom() + "    Score : " + j.getScore() + " pts",
                    20, yInfo + 48);
        }

        g.setFont(new Font("Arial", Font.PLAIN, 15));
        g.drawString(partie.getDernierMessage(), 20, yInfo + 70);

        int barreW = 260;
        int duree = partie.getDuree();
        int rempli = 0;
        if (duree > 0) {
            rempli = (partie.getTempsRestant() * barreW) / duree;
        }
        int barreX = getWidth() - barreW - 20;
        int barreY = yInfo - 4;
        if (barreX < 420) {
            barreX = 420;
        }
        g.setColor(new Color(230, 230, 230));
        g.fillRect(barreX, barreY, barreW, 14);
        if (partie.getTempsRestant() <= 5) {
            g.setColor(new Color(200, 60, 60));
        } else {
            g.setColor(new Color(40, 150, 80));
        }
        g.fillRect(barreX, barreY, rempli, 14);
        g.setColor(Color.BLACK);
        g.drawRect(barreX, barreY, barreW, 14);
    }

    private void dessinerFin(Graphics g) {
        int ox = 24;
        int oy = 48;
        int ow = Math.max(320, getWidth() - 48);
        int oh = Math.max(260, getHeight() - 70);

        g.setColor(new Color(245, 245, 245));
        g.fillRect(ox, oy, ow, oh);
        g.setColor(Color.BLACK);
        g.drawRect(ox, oy, ow, oh);

        g.setFont(new Font("Arial", Font.BOLD, 22));
        g.drawString("PARTIE TERMINEE", ox + 24, oy + 36);

        g.setFont(new Font("Arial", Font.PLAIN, 15));
        g.drawString("Les " + partie.getDuree()
                + " secondes étaient communes à tous les joueurs.", ox + 24, oy + 64);

        if (partie.getIdPartie() > 0) {
            g.drawString("Enregistrée dans la table partie (n° "
                    + partie.getIdPartie() + ").", ox + 24, oy + 86);
        } else {
            g.drawString("Fin de la partie commune.", ox + 24, oy + 86);
        }

        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Classement de la partie", ox + 24, oy + 120);

        Joueur[] classes = partie.getJoueursClasses();
        g.setFont(new Font("Arial", Font.PLAIN, 15));
        int y = oy + 148;
        for (int i = 0; i < classes.length; i++) {
            if (y > oy + oh - 70) {
                g.drawString("...", ox + 24, y);
                break;
            }
            Joueur j = classes[i];
            String ligne = (i + 1) + ". " + j.getNom()
                    + "  -  " + j.getScore() + " pts"
                    + "  -  " + j.getNbTirs() + " tirs"
                    + "  -  record " + j.getMeilleurScore();
            if (j == partie.getJoueur()) {
                g.setColor(new Color(20, 110, 50));
            } else {
                g.setColor(Color.BLACK);
            }
            g.drawString(ligne, ox + 24, y);
            y = y + 24;
        }

        g.setColor(new Color(20, 60, 140));
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString("Recherche par nom : panneau Classement à droite", ox + 24, oy + oh - 40);
        g.drawString("Appuyez sur R pour rejouer (" + partie.getDuree()
                + " s pour tout le monde)", ox + 24, oy + oh - 18);
    }

    public Partie getPartie() {
        return partie;
    }
}
