package humain;

import java.awt.*;

public class Pretre extends Personne {
    private int perimetre = 70;

    public Pretre(String nom, int age) {
        super(nom, age);
        this.couleur = Color.yellow;
        this.x = 400;
        this.y = 400;
    }

    public int getPerimetre() {
        return perimetre;
    }

    @Override
    public void dessiner(Graphics g) {
        if (vivant) {
            g.setColor(Color.GREEN);
            g.fillOval(x + taille / 2 - perimetre, y + taille / 2 - perimetre,
                    perimetre * 2, perimetre * 2);
        }
        super.dessiner(g);
    }

    public boolean estDansPerimetre(Personne p) {
        int cx1 = x + taille / 2;
        int cy1 = y + taille / 2;
        int cx2 = p.getX() + p.getTaille() / 2;
        int cy2 = p.getY() + p.getTaille() / 2;
        int dx = cx1 - cx2;
        int dy = cy1 - cy2;
        return Math.sqrt(dx * dx + dy * dy) < perimetre;
    }

    @Override
    public void travailler() {
        System.out.println(nom + " prie pour travailler.");
    }
}