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
                boutonsGrille[i][j].addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            handleClic(finalI, finalJ, true); // Clic droit
        } else if (SwingUtilities.isLeftMouseButton(e)) {
            handleClic(finalI, finalJ, false); // Clic gauche
        }
    }
});


                panelGrille.add(boutonsGrille[i][j]);
            }
        }

        add(panelGrille);
        setVisible(true);
        
    }

   private void handleClic(int ligne, int colonne, boolean clicDroit) {
    if (clicDroit) {
        // Poser ou retirer un drapeau
        partie.getGrille().poserOuRetirerDrapeau(ligne, colonne);
    } else {
        // Révéler la cellule
        if (!partie.getGrille().bombesPlacees()) {
            partie.getGrille().placerBombesAleatoirement(ligne, colonne);
        }
        partie.tourDeJeu(ligne, colonne);
    }

    // Mettre à jour l'interface après l'action
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
    GrilleDeJeu grille = partie.getGrille(); // Récupère la grille de jeu

    for (int i = 0; i < lignes; i++) {
        for (int j = 0; j < colonnes; j++) {
            JButton bouton = boutonsGrille[i][j]; // Récupère le bouton correspondant à la cellule
            Cellule cellule = grille.getMatriceCellules()[i][j]; // Récupère la cellule correspondante

            if (cellule.hasDrapeau()) { 
                // Si la cellule a un drapeau
                bouton.setText("D"); // Emoji drapeau
                bouton.setBackground(Color.YELLOW);
                bouton.setEnabled(true); // Toujours activé pour permettre de retirer le drapeau
            } else if (cellule.isDevoilee()) { 
                // Si la cellule est dévoilée
                bouton.setEnabled(false); // Désactive le bouton
                if (cellule.getPresenceBombe()) { 
                    // Si c'est une bombe
                    bouton.setText("\uD83D\uDCA3"); // Emoji bombe
                    bouton.setBackground(Color.RED);
                } else { 
                    // Si ce n'est pas une bombe
                    int bombesAdj = cellule.getNbBombesAdjacentes();
                    bouton.setText(bombesAdj > 0 ? String.valueOf(bombesAdj) : ""); // Affiche le nombre de bombes adjacentes
                    bouton.setBackground(Color.WHITE);
                }
            } else { 
                // Si la cellule n'a pas été dévoilée et n'a pas de drapeau
                bouton.setText("");
                bouton.setBackground(Color.LIGHT_GRAY);
                bouton.setEnabled(true); // Réactive le bouton si nécessaire
            }
        }
    }
}

}
