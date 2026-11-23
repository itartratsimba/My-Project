package jeu;

public class Joueur {

    private int id;
    private String nom;
    private int score;
    private int nbTirs;
    private int meilleurScore;

    public Joueur(int id, String nom, int meilleurScore) {
        this.id = id;
        this.nom = nom;
        this.score = 0;
        this.nbTirs = 0;
        this.meilleurScore = meilleurScore;
    }

    public void tirer(int points) {
        nbTirs++;
        if (points > 0) {
            score = score + points;
        }
    }

    // Remet a zero le score de la partie en cours, pas le record
    public void reinitialiser() {
        this.score = 0;
        this.nbTirs = 0;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public int getScore() {
        return score;
    }

    public int getNbTirs() {
        return nbTirs;
    }

    public int getMeilleurScore() {
        return meilleurScore;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setMeilleurScore(int m) {
        this.meilleurScore = m;
    }

    public String toString() {
        return nom + " (Meilleur score : " + meilleurScore + " pts)";
    }
}
