package humain;

import java.awt.*;

public class Normale extends Personne {
    public Normale(String nom, int age) {
        super(nom, age);
        this.couleur = Color.BLUE;
    }

    @Override
    public void travailler() {
        System.out.println(nom + " travaille normalement.");
    }
}