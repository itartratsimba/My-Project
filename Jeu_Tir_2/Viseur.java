package jeu;

public class Viseur {

    private int x;
    private int y;
    private int taille;
    private int pas;

    public Viseur(int x, int y) {
        this.x = x;
        this.y = y;
        this.taille = 20;
        this.pas = 10;
    }

    public void monterHaut() {
        y = y - pas;
    }

    public void descendreBas() {
        y = y + pas;
    }

    public void allerGauche() {
        x = x - pas;
    }

    public void allerDroite() {
        x = x + pas;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getTaille() {
        return taille;
    }

    public int getPas() {
        return pas;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPas(int pas) {
        this.pas = pas;
    }
}