package utilitaire;

import aff.Fenetre;
import jeu.Partie;
import jeu.Viseur;

import java.awt.Cursor;
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

    public void mouseClicked(MouseEvent e) {
        fenetre.getDessin().requestFocusInWindow();

        int index = fenetre.getDessin().getIndexOnglet(e.getX(), e.getY());
        if (index >= 0) {
            // Changement de joueur : le chrono de la partie continue
            partie.changerJoueur(index);
            fenetre.getDessin().repaint();
            fenetre.rafraichirClassement();
            return;
        }

        partie.getViseur().setX(e.getX());
        partie.getViseur().setY(e.getY());
        partie.tirer();
        fenetre.getDessin().repaint();
        fenetre.rafraichirClassement();
    }

    public void mouseMoved(MouseEvent e) {
        int index = fenetre.getDessin().getIndexOnglet(e.getX(), e.getY());
        if (index >= 0) {
            fenetre.getDessin().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            return;
        }
        fenetre.getDessin().setCursor(Cursor.getDefaultCursor());
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
            fenetre.rafraichirClassement();
        } else if (e.getKeyCode() == KeyEvent.VK_R) {
            // R relance une partie de 15 s pour tout le monde
            partie.recommencer();
            fenetre.rafraichirClassement();
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
        fenetre.getDessin().setCursor(Cursor.getDefaultCursor());
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }
}
