package utilitaires;

import humain.*;
import main.*;
import java.awt.event.*;

public class Ecoute implements MouseListener, ActionListener, KeyListener {

    private Dessin dessin;
    private MaFenetre fenetre;
    private int pas = 20;

    public Ecoute(MaFenetre f, Dessin d) {
        this.fenetre = f;
        this.dessin = d;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand().toLowerCase();
        Personne p = dessin.getSelectionne();
        if (p == null || !p.estVivant())
            return;

         if (cmd.equals("haut")) {
            p.deplacer(0, -pas);
        } else if (cmd.equals("bas")) {
            p.deplacer(0, pas);
        } else if (cmd.equals("gauche")) {
            p.deplacer(-pas, 0);
        } else if (cmd.equals("droite")) {
            p.deplacer(pas, 0);
        }

        dessin.verifierCollisions();
        dessin.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Personne p = dessin.personneAt(e.getX(), e.getY());
        if (p != null) {
            dessin.setSelectionne(p);
            System.out.println("Sélectionné: " + p.getNom());
            dessin.repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Personne p = dessin.getSelectionne();
        if (p == null || !p.estVivant())
            return;

        int touche = e.getKeyCode();

         if (touche == KeyEvent.VK_UP) {
            p.deplacer(0, -pas);
        } else if (touche == KeyEvent.VK_DOWN) {
            p.deplacer(0, pas);
        } else if (touche == KeyEvent.VK_LEFT) {
            p.deplacer(-pas, 0);
        } else if (touche == KeyEvent.VK_RIGHT) {
            p.deplacer(pas, 0);
        } else {
            return;
        }


        dessin.verifierCollisions();
        dessin.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}