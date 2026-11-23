package aff;

import jeu.Partie;
import utilitaire.Ecoute;

import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Fenetre extends JFrame {

    private Dessin dessin;
    private Ecoute ecoute;
    private Timer timer;
    private ClassementPanel classement;

    public Fenetre(Partie partie) {
        this.setTitle("Jeu de Tir - partie de " + partie.getDuree() + " s");
        this.setSize(1450, 760);
        this.setMinimumSize(new Dimension(1280, 680));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        this.dessin = new Dessin(partie);
        this.classement = new ClassementPanel(partie);

        this.add(dessin, BorderLayout.CENTER);
        this.add(classement, BorderLayout.EAST);

        this.ecoute = new Ecoute(this, partie);
        dessin.addMouseListener(ecoute);
        dessin.addMouseMotionListener(ecoute);
        dessin.addKeyListener(ecoute);
        dessin.setFocusable(true);

        // Un seul chrono pour la partie, pas un chrono par joueur
        this.timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                partie.tick();
                dessin.repaint();
                classement.rafraichir();
            }
        });
        timer.start();

        this.setVisible(true);
        dessin.requestFocusInWindow();
        classement.rafraichir();
    }

    public void rafraichirClassement() {
        if (classement != null) {
            classement.rafraichir();
        }
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
