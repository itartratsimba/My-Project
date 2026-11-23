package jeu;

public class Tir {
    private int x;
    private int y;
    private int pointsGagnes;
    private int tableauId;
    private boolean estCombo;

    public Tir(int x, int y, int pointsGagnes, int tableauId, boolean estCombo) {
        this.x = x;
        this.y = y;
        this.pointsGagnes = pointsGagnes;
        this.tableauId = tableauId;
        this.estCombo = estCombo;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPointsGagnes() {
        return pointsGagnes;
    }

    public int getTableauId() {
        return tableauId;
    }

    public boolean isEstCombo() {
        return estCombo;
    }

    public String toString() {
        return "Tir (" + x + ", " + y + ") | Points: " + pointsGagnes + " | Tableau: " + tableauId;
    }
}