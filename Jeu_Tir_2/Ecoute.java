package utilitaire;

import aff.Fenetre;
import aff.Dessin;
import jeu.Partie;
import jeu.Viseur;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Ecoute implements MouseListener, MouseMotionListener, KeyListener {

    private Fenetre fenetre;
    private Partie partie;

    public Ecoute(Fenetre fenetre, Partie partie) {
        this.fenetre = fenetre;
        this.partie = partie;
    }

// Dans utilitaire/Ecoute.java :

public void mouseClicked(MouseEvent e) {
    int mx = e.getX();
    int my = e.getY();

    // === CLIC SUR LA BARRE DU HAUT (SÉLECTION JOUEUR) ===
    if (my >= 5 && my <= 35) {
        int tabWidth = 140;
        for (int i = 0; i < partie.getNbJoueurs(); i++) {
            int tabX = 10 + i * (tabWidth + 10);
            
            // Si le clic est à l'intérieur de l'onglet i
            if (mx >= tabX && mx <= tabX + tabWidth) {
                partie.changerJoueur(i);
                fenetre.getDessin().repaint();
                return; // On arrête là, ce n'est pas un tir !
            }
        }
    }

    // === CLIC NORMAL = TIR ===
    partie.getViseur().setX(mx);
    partie.getViseur().setY(my);
    partie.tirer();
    fenetre.getDessin().repaint();
}
    public void mouseMoved(MouseEvent e) {
        partie.getViseur().setX(e.getX());
        partie.getViseur().setY(e.getY());
        fenetre.getDessin().repaint();
    }

    public void keyPressed(KeyEvent e) {
        Viseur v = partie.getViseur();

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            v.monterHaut();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            v.descendreBas();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            v.allerGauche();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            v.allerDroite();
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            partie.tirer();
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
        } else if (e.getKeyCode() == KeyEvent.VK_R) {
            // R = recommencer
            partie.recommencer();
        }
        try {
            partie.sauvegarder();
        } catch (Exception ex) {
            System.out.println("Erreur: " + ex.getMessage());
        }

        fenetre.getDessin().repaint();
    }

    


    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }
}