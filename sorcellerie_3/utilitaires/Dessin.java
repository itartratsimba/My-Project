package utilitaires;

import humain.*;
import javax.swing.*;
import java.awt.*;

public class Dessin extends JPanel {
    Personne[] personnes;
    int nombre;
    int capaciteMax = 20;

    Personne selectionne = null;
    Eglise eglise;
    Doany doany;
    Tombe tombe;

    public Dessin() {
        setPreferredSize(new Dimension(700, 500));
        setBackground(Color.WHITE);
        eglise = new Eglise(50, 350);
        doany = new Doany(550, 350);


        personnes = new Personne[capaciteMax];
        nombre = 0;
    }

    public void ajouter(Personne p) {
        if (nombre < capaciteMax) {
            personnes[nombre] = p;
            nombre++;
        } else {
            System.out.println("Tableau plein !");
        }
    }

    public Personne[] getPersonnes() {
        return personnes;
    }

    public int getNombre() {
        return nombre;
    }

    public Personne getSelectionne() {
        return selectionne;
    }

    public void setSelectionne(Personne p) {
        selectionne = p;
    }

    public Eglise getEglise() {
        return eglise;
    }

    public Doany getDoany() {
        return doany;
    }

    public Personne personneAt(int x, int y) {
        for (int i = 0; i < nombre; i++) {
            Personne p = personnes[i];
            if (!p.estVivant())
                continue;
            if (x >= p.getX() && x <= p.getX() + p.getTaille()
                    && y >= p.getY() && y <= p.getY() + p.getTaille()) {
                return p;
            }
        }
        return null;
    }

    public void verifierCollisions() {
        for (int i = 0; i < nombre; i++) {
            Personne p = personnes[i];
            if (!p.estVivant())
                continue;

            if (eglise.contient(p)) {
                if (p instanceof SorciereNeg || p instanceof SorcierePos) {
                    p.perdreVie();

                } else {
                    p.gagnerVie();

                }
            }

            if (doany.contient(p)) {
                if (p instanceof SorciereNeg || p instanceof SorcierePos) {
                    p.gagnerVie();

                } else {
                    p.perdreVie();

                }
            }

            for (int j = 0; j < nombre; j++) {
                Personne autre = personnes[j];
                if (autre == p || !autre.estVivant())
                    continue;

                if (autre instanceof SorciereNeg
                        && !(p instanceof SorciereNeg)
                        && !(p instanceof Pretre)) {
                    if (((SorciereNeg) autre).estDansPerimetre(p)) {
                        p.perdreVie();
                    }
                }

                if (autre instanceof Pretre
                        && !(p instanceof SorciereNeg)
                        && !(p instanceof SorcierePos)
                        && !(p instanceof Pretre)) {
                    if (((Pretre) autre).estDansPerimetre(p)) {
                        if (p.getVies() < 3) {
                            p.gagnerVie();
                            autre.perdreVie();
                            System.out.println(autre.getNom() + " soigne " + p.getNom()
                                    + " et perd 1 vie (il lui reste " + autre.getVies() + ")");
                        }
                    }
                }

                boolean estSorcier = (p instanceof SorciereNeg || p instanceof SorcierePos);

                if (estSorcier && autre instanceof Pretre) {
                    boolean proche = false;

                    if (p instanceof SorciereNeg) {
                        proche = ((SorciereNeg) p).estDansPerimetre(autre);
                    } else {
                        int dx = (p.getX() + p.getTaille() / 2)
                                - (autre.getX() + autre.getTaille() / 2);
                        int dy = (p.getY() + p.getTaille() / 2)
                                - (autre.getY() + autre.getTaille() / 2);
                        proche = Math.sqrt(dx * dx + dy * dy) < 80;
                    }

                    if (proche) {
                        autre.perdreVie();
                        p.perdreVie();
                        System.out.println("Combat ! " + p.getNom() + " et "
                                + autre.getNom() + " se blessent mutuellement !");
                    }
                }
            }
        }

        if (selectionne != null && !selectionne.estVivant()) {
            selectionne = null;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        eglise.dessiner(g);
        doany.dessiner(g);

        for (int i = 0; i < nombre; i++) {
            personnes[i].dessiner(g);
        }

        if (selectionne != null && selectionne.estVivant()) {
            g.setColor(Color.GREEN);
            g.drawRect(selectionne.getX() - 3, selectionne.getY() - 3,
                    selectionne.getTaille() + 6, selectionne.getTaille() + 6);
            g.setColor(Color.BLACK);
            g.drawString("Sélectionné: " + selectionne.getNom(), 10, 20);
        } else {
            g.setColor(Color.BLACK);
            g.drawString("Clique sur un personnage pour le sélectionner", 10, 20);
        }
    }
}