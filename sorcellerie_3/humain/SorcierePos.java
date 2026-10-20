package humain;

import java.awt.*;

public class SorcierePos extends Personne {
    private String pouvoir;
    private int perimetre = 80;

    public SorcierePos(String nom, int age, String pouvoir) {
        super(nom, age);
        this.pouvoir = pouvoir;
        this.couleur = Color.MAGENTA;
        this.x = 500;
        this.y = 200;
    }

    public int getPerimetre() {
        return perimetre;
    }

    @Override
    public void dessiner(Graphics g) {
        if (vivant) {
            g.setColor(Color.PINK);
            g.fillOval(x + taille / 2 - perimetre, y + taille / 2 - perimetre,
                    perimetre * 2, perimetre * 2);
        }
        super.dessiner(g);
    }

    @Override
    public void travailler() {
        System.out.println(nom + " utilise " + pouvoir + " (bénéfique).");
    }
}