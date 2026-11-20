package aff;

import jeu.Partie;
import utilitaire.Ecoute;

import javax.swing.*;
import java.awt.event.*;

public class Fenetre extends JFrame {

    private Dessin dessin;
    private Ecoute ecoute;
    private Timer timer;

    public Fenetre(Partie partie) {
        this.setTitle("Jeu de Tir");
        this.setSize(1000, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        // Dessin
        this.dessin = new Dessin(partie);
        this.add(dessin);

        // Ecoute
        this.ecoute = new Ecoute(this, partie);
        dessin.addMouseListener(ecoute);
        dessin.addMouseMotionListener(ecoute);
        dessin.addKeyListener(ecoute);
        dessin.setFocusable(true);
        dessin.requestFocus();

        this.timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                partie.tick(); // enlever 1 seconde
                dessin.repaint(); // redessiner l'écran
            }
        });
        timer.start(); // démarrer le chronomètre

        this.setVisible(true);

    }

    public Dessin getDessin() {
        return dessin;
    }

    public Ecoute getEcoute() {
        return ecoute;
    }

    public Timer getTimer() {
        return timer;
    }
}