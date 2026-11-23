// package jeu;

// import java.awt.Color;
// import java.awt.Graphics;

// public class Tableau {

//     private int id;
//     private int x;
//     private int y;
//     private int largeur;
//     private int hauteur;
//     private int pointMax;
//     private int pointMaxX;
//     private int pointMaxY;

//     public Tableau(int id, int x, int y, int largeur, int hauteur,
//             int pointMax, int pointMaxX, int pointMaxY) {
//         this.id = id;
//         this.x = x;
//         this.y = y;
//         this.largeur = largeur;
//         this.hauteur = hauteur;
//         this.pointMax = pointMax;
//         this.pointMaxX = pointMaxX;
//         this.pointMaxY = pointMaxY;
//     }

//     public int calculerPoints(int vx, int vy) {
//         // Vérifier d'abord si le tir est dans le tableau
//         if (vx < x || vx > x + largeur || vy < y || vy > y + hauteur) {
//             return 0; // hors du tableau
//         }

//         // Calculer la distance entre le tir et le pointMax
//         int dx = vx - pointMaxX;
//         int dy = vy - pointMaxY;
//         double distance = Math.sqrt(dx * dx + dy * dy);

//         // Si distance >= 100 → 0 points
//         if (distance >= 100) {
//             return 0;
//         }

//         // Formule : pointMax * (1 - distance / 100)
//         double points = pointMax * (1.0 - distance / 100.0);

//         // Arrondir à l'entier
//         return (int) Math.round(points);
//     }

//     public void dessiner(Graphics g) {
//         // Dessiner le tableau (rectangle)
//         g.setColor(new Color(230, 230, 230));
//         g.fillRect(x, y, largeur, hauteur);
//         g.setColor(Color.BLACK);
//         g.drawRect(x, y, largeur, hauteur);

//         // // Dessiner les cercles de distance (100, 75, 50, 25)
//         // int[] distances = { 100, 75, 50, 25 };
//         // Color[] couleurs = {
//         //         new Color(255, 200, 200), // loin - rose clair
//         //         new Color(255, 255, 150), // moyen - jaune
//         //         new Color(150, 255, 150), // proche - vert
//         //         new Color(100, 200, 255) // très proche - bleu
//         // };

//         // for (int i = 0; i < distances.length; i++) {
//         //     int r = distances[i];
//         //     g.setColor(couleurs[i]);
//         //     g.fillOval(pointMaxX - r, pointMaxY - r, r * 2, r * 2);
//         //     g.setColor(Color.GRAY);
//         //     g.drawOval(pointMaxX - r, pointMaxY - r, r * 2, r * 2);
//         // }

//         // Dessiner le pointMax (petit point rouge au centre)
//         g.setColor(Color.RED);
//         g.fillOval(pointMaxX - 5, pointMaxY - 5, 10, 10);
//         g.setColor(Color.WHITE);
//         g.fillOval(pointMaxX - 2, pointMaxY - 2, 4, 4);

//         // Texte
//         g.setColor(Color.BLACK);
//         g.drawString("Tableau #" + id + " (max:" + pointMax + "pts)", x + 5, y + 15);
//     }

//     public int getId() {
//         return id;
//     }

//     public int getX() {
//         return x;
//     }

//     public int getY() {
//         return y;
//     }

//     public int getLargeur() {
//         return largeur;
//     }

//     public int getHauteur() {
//         return hauteur;
//     }

//     public int getPointMax() {
//         return pointMax;
//     }

//     public int getPointMaxX() {
//         return pointMaxX;
//     }

//     public int getPointMaxY() {
//         return pointMaxY;
//     }
// }

package jeu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;

public class Tableau {

    private int id;
    private int x;
    private int y;
    private int largeur;
    private int hauteur;
    private int pointMax;
    private int pointMaxX;
    private int pointMaxY;
    private double distanceMax;

    public Tableau(int id, int x, int y, int largeur, int hauteur,
            int pointMax, int pointMaxX, int pointMaxY) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.pointMax = pointMax;
        this.pointMaxX = pointMaxX;
        this.pointMaxY = pointMaxY;

        // Calculer la distance du pointMax au coin le plus éloigné
        double d1 = distance(pointMaxX, pointMaxY, x, y);
        double d2 = distance(pointMaxX, pointMaxY, x + largeur, y);
        double d3 = distance(pointMaxX, pointMaxY, x, y + hauteur);
        double d4 = distance(pointMaxX, pointMaxY, x + largeur, y + hauteur);

        this.distanceMax = d1;
        if (d2 > distanceMax) {
            distanceMax = d2;
        }
        if (d3 > distanceMax) {
            distanceMax = d3;
        }
        if (d4 > distanceMax) {
            distanceMax = d4;
        }
    }

    private double distance(int x1, int y1, int x2, int y2) {
        int dx = x1 - x2;
        int dy = y1 - y2;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public int calculerPoints(int vx, int vy) {
        // Vérifier si le tir est dans le tableau
        if (vx < x || vx > x + largeur || vy < y || vy > y + hauteur) {
            return 0;
        }

        // Calculer la distance entre le tir et le pointMax
        double dist = distance(vx, vy, pointMaxX, pointMaxY);

        // Formule : pointMax * (1 - distance / distanceMax)
        double points = pointMax * (1.0 - dist / distanceMax);

        // Si négatif → 0
        if (points < 0) {
            return 0;
        }

        return (int) Math.round(points);
    }

// Dans Tableau.java, remplace la méthode dessiner(Graphics g) par :

public void dessiner(Graphics g) {
    // 1. Rectangle du tableau (gris clair)
    g.setColor(Color.LIGHT_GRAY);
    g.fillRect(x, y, largeur, hauteur);
    g.setColor(Color.BLACK);
    g.drawRect(x, y, largeur, hauteur);

    // 2. Le point max (petit rond rouge)
    g.setColor(Color.RED);
    g.fillOval(pointMaxX - 5, pointMaxY - 5, 10, 10);

    // 3. Texte du tableau
    g.setColor(Color.BLACK);
    g.setFont(new Font("Arial", Font.PLAIN, 12));
    g.drawString("Tableau #" + id + " (" + pointMax + " pts)", x + 5, y + 15);
}

    public boolean estSurPointMax(int vx, int vy) {
        double dist = distance(vy, vy, pointMaxX, pointMaxY);
        if (dist <= 10) {
            return true;
        }
        else {
            return false;
        }
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLargeur() {
        return largeur;
    }

    public int getHauteur() {
        return hauteur;
    }

    public int getPointMax() {
        return pointMax;
    }

    public int getPointMaxX() {
        return pointMaxX;
    }

    public int getPointMaxY() {
        return pointMaxY;
    }
}