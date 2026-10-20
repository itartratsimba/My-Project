package humain;

import java.awt.*;

public abstract class Personne {
    String nom;
    int age;
    int x, y;
    int taille = 40;
    Color couleur = Color.BLACK;
    int vies = 10;
    int viesMax = 10;
    boolean vivant = true;

    public Personne(String nom, int age) {
        this.nom = nom;
        this.age = age;
        this.x = 100;
        this.y = 100;
    }

    public String getNom() {
        return nom;
    }

    public int getAge() {
        return age;
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

    public Color getCouleur() {
        return couleur;
    }

    public int getVies() {
        return vies;
    }

    public boolean estVivant() {
        return vivant;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void perdreVie() {
        if (!vivant)
            return;
        vies--;
        if (vies <= 0) {
            vies = 0;
            vivant = false;
            System.out.println(nom + " est mort !");
        }
    }

    public void gagnerVie() {
        if (!vivant)
            return;
        if (vies < viesMax)
            vies++;
    }

    public void deplacer(int dx, int dy) {
        if (!vivant)
            return;
        this.x += dx;
        this.y += dy;
    }



    public void dessiner(Graphics g) {
        if (!vivant) {
            g.setColor(Color.GRAY);
            g.fillRect(x, y, taille, taille);
            return;
        }
        g.setColor(couleur);
        g.fillRect(x, y, taille, taille);
        g.setColor(Color.BLACK);
        g.drawString(nom, x, y - 5);
        g.setColor(Color.RED);
        g.drawString("Vies: " + vies, x, y + taille + 15);
    }

    public abstract void travailler();
}