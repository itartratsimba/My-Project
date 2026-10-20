package humain;

import java.awt.*;

public class SorciereNeg extends Personne {
    private String pouvoir;
    private int perimetre = 80;

    public SorciereNeg(String nom, int age, String pouvoir) {
        super(nom, age);
        this.pouvoir = pouvoir;
        this.couleur = Color.RED;
        this.x = 300;
        this.y = 200;
    }

    public int getPerimetre() {
        return perimetre;
    }

    @Override
    public void dessiner(Graphics g) {
        if (vivant) {
            g.setColor(Color.DARK_GRAY);
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
        System.out.println(nom + " utilise " + pouvoir + " (maléfique).");
    }
}