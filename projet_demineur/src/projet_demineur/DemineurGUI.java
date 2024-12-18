package projet_demineur;

import javax.swing.*;
import java.awt.*;

public class DemineurGUI extends JFrame {
    private Partie partie; // Partie actuelle
    private JButton[][] boutonsGrille; // Boutons représentant la grille
    private int lignes, colonnes, nbBombes; // Paramètres de la grille

    public DemineurGUI(int lignes, int colonnes, int nbBombes) {
        this.lignes = lignes;
        this.colonnes = colonnes;
        this.nbBombes = nbBombes;

        // Initialiser la partie avec les paramètres
        partie = new Partie(lignes, colonnes, nbBombes, 1); // Exemple : 3 vies pour le joueur
        setTitle("Démineur - Niveau");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialiser l'interface graphique
        initGrille();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DemineurGUI game = new DemineurGUI(11, 11, 10); // Exemple : Niveau 1
            game.setVisible(true); // Afficher la fenêtre de jeu
        });
    }

    private void initGrille() {
        JPanel panelGrille = new JPanel(new GridLayout(lignes, colonnes));
        boutonsGrille = new JButton[lignes][colonnes];

        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                boutonsGrille[i][j] = new JButton();
                boutonsGrille[i][j].setFont(new Font("Arial", Font.PLAIN, 12));
                boutonsGrille[i][j].setBackground(Color.LIGHT_GRAY);

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
        // Placer les bombes si elles ne le sont pas encore (premier clic sécurisé)
        if (!partie.getGrille().bombesPlacees()) {
            partie.getGrille().placerBombesAleatoirement(ligne, colonne);
        }

        // Révéler la cellule dans la partie
        partie.tourDeJeu(ligne, colonne);

        // Mettre à jour l'interface après le clic
        mettreAJourGrille();

        // Vérifier les conditions de fin
        if (partie.getNbVies() <= 0) {
            JOptionPane.showMessageDialog(this, "Vous avez perdu !", "Fin de Partie", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        } else if (partie.verifierVictoire()) {
            JOptionPane.showMessageDialog(this, "Félicitations, vous avez gagné !", "Victoire", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        }
    }

    private void mettreAJourGrille() {
        GrilleDeJeu grille = partie.getGrille();

        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                JButton bouton = boutonsGrille[i][j];
                if (grille.getMatriceCellules()[i][j].isDevoilee()) {
                    bouton.setEnabled(false);
                    if (grille.getPresenceBombe(i, j)) {
                        bouton.setText("\uD83D\uDCA3"); // Emoji bombe
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
