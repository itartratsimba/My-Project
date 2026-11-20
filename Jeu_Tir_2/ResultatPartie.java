package jeu;

public class ResultatPartie {
    private int id;
    private int score;
    private int nbTirs;
    private String datePartie;

    public ResultatPartie(int id, int score, int nbTirs, String datePartie) {
        this.id = id;
        this.score = score;
        this.nbTirs = nbTirs;
        this.datePartie = datePartie;
    }

    public int getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public int getNbTirs() {
        return nbTirs;
    }

    public String getDatePartie() {
        return datePartie;
    }
}