package humain;

import java.awt.*;

public class Eglise extends Maison {

    public Eglise(int x, int y) {
        super(x, y);
    }

    @Override
    public void dessiner(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect(x, y, largeur, hauteur);
        g.setColor(Color.BLACK);
        g.drawString("Eglise", x + 25, y + hauteur / 2);
    }

    @Override
    public void travailler() {
        System.out.println("L'église est pour Normale et Pretre");
    }
}