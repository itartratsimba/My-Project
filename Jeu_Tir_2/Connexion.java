package utilitaires;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class Connexion {

    public Connection seConnecter() throws Exception {
        String url = "jdbc:oracle:thin:@localhost:1521:EE";
        String user = "jeu_tir";
        String pwd = "huhu";

        Connection con = DriverManager.getConnection(url, user, pwd);
        System.out.println("Connected to " + user);
        return con;
    }

    public ResultSet getAllLines(String requete) throws Exception {
        Connection con = seConnecter();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(requete);
        return rs;
    }

    public void executer(String requete) {
        Connection con = null;
        Statement stmt = null;

        try {
            con = seConnecter();
            if (con != null) {
                // 1. Désactiver l'auto-commit pour gérer la transaction nous-mêmes
                con.setAutoCommit(false);

                stmt = con.createStatement();
                stmt.executeUpdate(requete);

                // 2. Valider manuellement
                con.commit();
                System.out.println("Requete executee avec succes !");
            }
        } catch (Exception e) {
            System.out.println("Erreur execution: " + e.getMessage());
            // En cas d'erreur, on annule les modifications
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (Exception ex) {
                System.out.println("Erreur rollback: " + ex.getMessage());
            }
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                System.out.println("Erreur fermeture: " + e.getMessage());
            }
        }
    }

    public void fermerConnexion(ResultSet rs) throws Exception {
        if (rs != null) {
            Statement stmt = rs.getStatement();
            Connection con = stmt.getConnection();
            rs.close();
            stmt.close();
            con.close();
        }
    }
}