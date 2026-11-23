package aff;

import jeu.Joueur;
import jeu.Partie;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Classement des joueurs, avec une barre de recherche par nom.
public class ClassementPanel extends JPanel {

    private Partie partie;
    private JTextField champRecherche;
    private JLabel labelResultat;
    private JLabel labelTemps;
    private JLabel labelActif;
    private DefaultTableModel modele;
    private JTable table;

    public ClassementPanel(Partie partie) {
        this.partie = partie;
        construire();
    }

    private void construire() {
        setPreferredSize(new Dimension(340, 640));
        setMinimumSize(new Dimension(300, 400));
        setBackground(new Color(244, 247, 244));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(210, 210, 210)));

        JPanel entete = new JPanel();
        entete.setLayout(new BoxLayout(entete, BoxLayout.Y_AXIS));
        entete.setBackground(new Color(27, 107, 68));
        entete.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));

        JLabel titre = new JLabel("Classement");
        titre.setFont(new Font("Arial", Font.BOLD, 22));
        titre.setForeground(Color.WHITE);
        titre.setAlignmentX(LEFT_ALIGNMENT);

        JLabel sousTitre = new JLabel("Une partie, " + partie.getDuree() + " secondes pour tous");
        sousTitre.setFont(new Font("Arial", Font.PLAIN, 12));
        sousTitre.setForeground(new Color(220, 240, 225));
        sousTitre.setAlignmentX(LEFT_ALIGNMENT);

        labelTemps = new JLabel(" ");
        labelTemps.setFont(new Font("Arial", Font.BOLD, 13));
        labelTemps.setForeground(Color.WHITE);
        labelTemps.setAlignmentX(LEFT_ALIGNMENT);

        labelActif = new JLabel(" ");
        labelActif.setFont(new Font("Arial", Font.PLAIN, 12));
        labelActif.setForeground(new Color(220, 240, 225));
        labelActif.setAlignmentX(LEFT_ALIGNMENT);

        entete.add(titre);
        entete.add(Box.createVerticalStrut(4));
        entete.add(sousTitre);
        entete.add(Box.createVerticalStrut(6));
        entete.add(labelTemps);
        entete.add(Box.createVerticalStrut(2));
        entete.add(labelActif);

        JPanel centre = new JPanel(new BorderLayout(0, 8));
        centre.setOpaque(false);
        centre.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel labelChamp = new JLabel("Rechercher un joueur par son nom");
        labelChamp.setFont(new Font("Arial", Font.BOLD, 12));
        labelChamp.setForeground(new Color(40, 40, 40));

        champRecherche = new JTextField();
        champRecherche.setFont(new Font("Arial", Font.PLAIN, 14));
        champRecherche.setMargin(new Insets(6, 8, 6, 8));
        champRecherche.setToolTipText("Tapez un nom, par exemple Alex ou Alice");
        labelChamp.setLabelFor(champRecherche);

        JButton effacer = new JButton("Effacer");
        effacer.setFont(new Font("Arial", Font.PLAIN, 12));
        effacer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                champRecherche.setText("");
                champRecherche.requestFocusInWindow();
            }
        });

        JPanel ligneChamp = new JPanel(new BorderLayout(6, 0));
        ligneChamp.setOpaque(false);
        ligneChamp.add(champRecherche, BorderLayout.CENTER);
        ligneChamp.add(effacer, BorderLayout.EAST);

        labelResultat = new JLabel(" ");
        labelResultat.setFont(new Font("Arial", Font.PLAIN, 12));
        labelResultat.setForeground(new Color(80, 80, 80));

        JPanel recherche = new JPanel(new BorderLayout(0, 6));
        recherche.setOpaque(false);
        recherche.add(labelChamp, BorderLayout.NORTH);
        recherche.add(ligneChamp, BorderLayout.CENTER);
        recherche.add(labelResultat, BorderLayout.SOUTH);

        String[] colonnes = {"Rang", "Joueur", "Score", "Record", "Tirs"};
        modele = new DefaultTableModel(colonnes, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(modele);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(26);
        table.setFillsViewportHeight(true);
        table.setRowSelectionAllowed(false);
        table.setFocusable(false);
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.getTableHeader().setReorderingAllowed(false);
        installerRendu();

        table.getColumnModel().getColumn(0).setPreferredWidth(42);
        table.getColumnModel().getColumn(1).setPreferredWidth(90);
        table.getColumnModel().getColumn(2).setPreferredWidth(52);
        table.getColumnModel().getColumn(3).setPreferredWidth(58);
        table.getColumnModel().getColumn(4).setPreferredWidth(42);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200, 210, 200)));

        JLabel legende = new JLabel("<html>Score = cette partie<br>Record = meilleur score<br>La ligne verte est le joueur actif</html>");
        legende.setFont(new Font("Arial", Font.PLAIN, 11));
        legende.setForeground(new Color(90, 90, 90));

        centre.add(recherche, BorderLayout.NORTH);
        centre.add(scroll, BorderLayout.CENTER);
        centre.add(legende, BorderLayout.SOUTH);

        add(entete, BorderLayout.NORTH);
        add(centre, BorderLayout.CENTER);

        champRecherche.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                rafraichir();
            }

            public void removeUpdate(DocumentEvent e) {
                rafraichir();
            }

            public void changedUpdate(DocumentEvent e) {
                rafraichir();
            }
        });
    }

    private void installerRendu() {
        DefaultTableCellRenderer rendu = new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(
                        table, value, false, false, row, column);

                String nomActif = "";
                if (partie.getJoueur() != null && partie.getJoueur().getNom() != null) {
                    nomActif = partie.getJoueur().getNom();
                }

                String nomLigne = "";
                Object valeurNom = table.getValueAt(row, 1);
                if (valeurNom != null) {
                    nomLigne = valeurNom.toString();
                }

                int rang = 0;
                Object valeurRang = table.getValueAt(row, 0);
                if (valeurRang instanceof Integer) {
                    rang = ((Integer) valeurRang).intValue();
                }

                if (nomLigne.equals(nomActif)) {
                    c.setBackground(new Color(186, 230, 186));
                    c.setFont(c.getFont().deriveFont(Font.BOLD));
                } else if (rang == 1) {
                    c.setBackground(new Color(255, 246, 214));
                    c.setFont(c.getFont().deriveFont(Font.PLAIN));
                } else if (rang == 2) {
                    c.setBackground(new Color(242, 242, 242));
                    c.setFont(c.getFont().deriveFont(Font.PLAIN));
                } else if (rang == 3) {
                    c.setBackground(new Color(255, 236, 220));
                    c.setFont(c.getFont().deriveFont(Font.PLAIN));
                } else {
                    c.setBackground(Color.WHITE);
                    c.setFont(c.getFont().deriveFont(Font.PLAIN));
                }
                c.setForeground(Color.BLACK);
                if (c instanceof JLabel) {
                    ((JLabel) c).setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 6));
                }
                return c;
            }
        };
        table.setDefaultRenderer(Object.class, rendu);
        table.setDefaultRenderer(Integer.class, rendu);
    }

    public void rafraichir() {
        if (modele == null || champRecherche == null) {
            return;
        }

        String filtre = champRecherche.getText();
        Joueur[] tous = partie.getJoueursClasses();
        modele.setRowCount(0);

        int visibles = 0;
        for (int i = 0; i < tous.length; i++) {
            if (!correspond(tous[i].getNom(), filtre)) {
                continue;
            }
            visibles++;
            modele.addRow(new Object[] {
                Integer.valueOf(i + 1),
                tous[i].getNom(),
                Integer.valueOf(tous[i].getScore()),
                Integer.valueOf(tous[i].getMeilleurScore()),
                Integer.valueOf(tous[i].getNbTirs())
            });
        }

        String texteFiltre = filtre == null ? "" : filtre.trim();
        if (texteFiltre.length() == 0) {
            labelResultat.setText(visibles + " joueur(s)");
        } else if (visibles == 0) {
            labelResultat.setText("Aucun joueur pour \"" + texteFiltre + "\"");
        } else {
            labelResultat.setText(visibles + " joueur(s) pour \"" + texteFiltre + "\"");
        }

        if (partie.isEstTerminee()) {
            String fin = "Partie terminée (" + partie.getDuree() + " s)";
            if (partie.getIdPartie() > 0) {
                fin = fin + " - n° " + partie.getIdPartie();
            }
            labelTemps.setText(fin);
        } else {
            labelTemps.setText("Temps restant : " + partie.getTempsRestant()
                    + " s / " + partie.getDuree() + " s");
        }

        if (partie.getJoueur() != null) {
            labelActif.setText("Joueur actif : " + partie.getJoueur().getNom());
        } else {
            labelActif.setText("Aucun joueur");
        }

        if (table != null) {
            table.repaint();
        }
    }

    private boolean correspond(String nom, String filtre) {
        if (filtre == null || filtre.trim().length() == 0) {
            return true;
        }
        if (nom == null) {
            return false;
        }
        return nom.toLowerCase().indexOf(filtre.trim().toLowerCase()) >= 0;
    }
}
