package main;

import utilitaires.*;
import humain.*;
import javax.swing.*;
import java.awt.*;

public class MaFenetre extends JFrame {
    public Dessin dessin;

    public MaFenetre() {
        setTitle("Jeu Sorcières");
        setSize(750, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        dessin = new Dessin();

        dessin.ajouter(new Normale("Alice", 25));
        dessin.ajouter(new Normale("Bob", 30));
        dessin.ajouter(new SorciereNeg("Morgana", 30, "invisibilité"));
        dessin.ajouter(new SorcierePos("Marie", 28, "Guérir"));
        dessin.ajouter(new Pretre("Jean", 45));

        Ecoute ecoute = new Ecoute(this, dessin);

        dessin.setFocusable(true);

        dessin.requestFocus();

        dessin.addMouseListener(ecoute);
        dessin.addKeyListener(ecoute);

        add(dessin, BorderLayout.CENTER);

        JPanel panelBoutons = new JPanel(new FlowLayout());
        JButton haut = new JButton("Haut");
        JButton bas = new JButton("Bas");
        JButton gauche = new JButton("Gauche");
        JButton droite = new JButton("Droite");

        haut.addActionListener(ecoute);
        bas.addActionListener(ecoute);
        gauche.addActionListener(ecoute);
        droite.addActionListener(ecoute);

        panelBoutons.add(gauche);
        panelBoutons.add(haut);
        panelBoutons.add(bas);
        panelBoutons.add(droite);
        add(panelBoutons, BorderLayout.SOUTH);

        setVisible(true);
    }
}