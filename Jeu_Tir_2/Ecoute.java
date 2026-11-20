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

    public void mouseClicked(MouseEvent e) {
        partie.getViseur().setX(e.getX());
        partie.getViseur().setY(e.getY());
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