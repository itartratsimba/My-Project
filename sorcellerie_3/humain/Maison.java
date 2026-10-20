package humain;

import java.awt.*;

public abstract class Maison {

    int x, y;
    Point[] extremite;
    Personne proprietairePersonne;


    int largeur = 100;
    int hauteur = 100;

    public Maison(int x, int y) {

        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLargeur() {
        return largeur;
    }

    public int getHauteur() {
        return hauteur;
    }

    public boolean contient(Personne p) {

        int cx = p.getX() + p.getTaille() / 2;
        int cy = p.getY() + p.getTaille() / 2;
        return cx >= x && cx <= x + largeur && cy >= y && cy <= y + hauteur;
    }

    public abstract void dessiner(Graphics g);

    public abstract void travailler();

}