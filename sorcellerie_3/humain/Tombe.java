package humain;
import java.awt.*;

public class Tombe extends Maison{

    private String nom;

    public Tombe(String nom, int x, int y) {
        super(x, y);
        this.nom = nom;
    }

    @Override
    public void dessiner(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(x, y, largeur, hauteur);
        g.setColor(Color.WHITE);
        g.drawString(nom, x + 10, y + hauteur / 2);
    }

    @Override
    public void travailler() {
        System.out.println("Le tombeau est pour les morts.");
    }
}