package projet_demineur;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import projet_demineur.GrilleDeJeu;
import projet_demineur.Partie;
/**
 *
 * @author yohan
 */


public class DemineurGUI extends JFrame  {
    private Partie partie; // Partie actuelle
    private JButton[][] boutonsGrille; // Boutons représentant la grille
    private int lignes, colonnes, nbBombes; // Paramètres de la grille

    /**
     * Creates new form DemineurGUI
     */
    public DemineurGUI(int lignes, int colonnes, int nbBombes) {
         this.lignes = lignes;
        this.colonnes = colonnes;
        this.nbBombes = nbBombes;

        // Initialiser la partie avec les paramètres
        partie = new Partie(lignes, colonnes, nbBombes, 0); // 0 vie (Démineur classique)
        setTitle("Démineur - Niveau");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialiser l'interface graphique
        initGrille();
    }
public static void main(String[] args) {
    // Créer une instance de DemineurGUI et la rendre visible
    SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
            DemineurGUI game = new DemineurGUI(11, 11, 10); // Exemple : Niveau 1
            game.setVisible(true);  // Afficher la fenêtre de jeu
        }
    });
}

 private void initGrille() {
        // Configurer un layout de type grille
        JPanel panelGrille = new JPanel(new GridLayout(lignes, colonnes));
        boutonsGrille = new JButton[lignes][colonnes];

        // Initialiser chaque bouton
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                boutonsGrille[i][j] = new JButton();
                boutonsGrille[i][j].setFont(new Font("Arial", Font.PLAIN, 12));
                boutonsGrille[i][j].setBackground(Color.LIGHT_GRAY);

                // Ajouter un ActionListener pour gérer les clics sur les boutons
                int finalI = i;
                int finalJ = j;
                boutonsGrille[i][j].addActionListener(e -> handleClic(finalI, finalJ));

                panelGrille.add(boutonsGrille[i][j]);
            }
        }

        add(panelGrille);
        setVisible(true);
    }
private void handleClic(int ligne, int colonne) {
        // Appeler la méthode de révélation de cellule
        partie.tourDeJeu(ligne, colonne);

        // Mettre à jour l'interface après le clic
        mettreAJourGrille();

        // Vérifier si la partie est terminée
        if (partie.verifierVictoire()) {
            JOptionPane.showMessageDialog(this, "Félicitations, vous avez gagné !");
            this.dispose();
        } else if (partie.getNbVies() <= 0) {
            JOptionPane.showMessageDialog(this, "Vous avez perdu !");
            this.dispose();
        }
    }
private void mettreAJourGrille() {
        GrilleDeJeu grille = partie.getGrille(); // Accéder à la grille actuelle

        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                JButton bouton = boutonsGrille[i][j];
                if (grille.getMatriceCellules()[i][j].isDevoilee()) {
                    bouton.setEnabled(false);
                    if (grille.getPresenceBombe(i, j)) {
                        bouton.setText("💣");
                        bouton.setBackground(Color.RED);
                    } else {
                        int bombesAdj = grille.getMatriceCellules()[i][j].getNbBombesAdjacentes();
                        bouton.setText(bombesAdj > 0 ? String.valueOf(bombesAdj) : "");
                        bouton.setBackground(Color.WHITE);
                    }
                }
            }
        }
    }

}
