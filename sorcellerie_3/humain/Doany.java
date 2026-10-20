package humain;

import java.awt.*;

public class Doany extends Maison {

    public Doany(int x, int y) {
        super(x, y);
        // largeur = 100;
    }

    @Override
    public void dessiner(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, largeur, hauteur);
        g.setColor(Color.BLACK);
        g.drawString("Doany", x + 30, y + hauteur / 2);
    }

    @Override
    public void travailler() {
        System.out.println("Doany pour Sorciere");
    }

}
