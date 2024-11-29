/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projet_demineur;
import java.util.Random;
/**
 *
 * @author yohan
 */

public class GrilleDeJeu {
    private Cellule[][] matriceCellules;  // La grille de jeu
    private int nbLignes;                 // Nombre de lignes dans la grille
    private int nbColonnes;               // Nombre de colonnes dans la grille
    private int nbBombes;                 // Nombre total de bombes dans la grille

    /**
     * Constructeur de la classe GrilleDeJeu.
     * @param nbLignes Nombre de lignes de la grille.
     * @param nbColonnes Nombre de colonnes de la grille.
     * @param nbBombes Nombre total de bombes à placer dans la grille.
     */
    public GrilleDeJeu(int nbLignes, int nbColonnes, int nbBombes) {
        this.nbLignes = nbLignes;
        this.nbColonnes = nbColonnes;
        this.nbBombes = nbBombes;
        this.matriceCellules = new Cellule[nbLignes][nbColonnes];
        
        initialiserGrille();            // Initialise la grille avec des cellules vides
        placerBombesAleatoirement();    // Place les bombes de façon aléatoire
    }

    /**
     * Initialise la grille avec des instances de Cellule dans chaque case.
     */
    private void initialiserGrille() {
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                matriceCellules[i][j] = new Cellule();
            }
        }
    }

    /**
     * Place les bombes de manière aléatoire dans la grille.
     */
    private void placerBombesAleatoirement() {
        Random random = new Random();
        int bombesPlacees = 0;

        while (bombesPlacees < nbBombes) {
            int ligne = random.nextInt(nbLignes);     // Choix aléatoire d'une ligne
            int colonne = random.nextInt(nbColonnes); // Choix aléatoire d'une colonne

            // Vérifie si une bombe est déjà placée à cet emplacement
            if (!matriceCellules[ligne][colonne].getPresenceBombe()) {
                matriceCellules[ligne][colonne].setPresenceBombe(true);
                bombesPlacees++;
            }
        }

        calculerBombesAdjacentes();  // Met à jour le nombre de bombes adjacentes à chaque cellule
    }

    /**
     * Calcule et met à jour le nombre de bombes adjacentes pour chaque cellule.
     */
    private void calculerBombesAdjacentes() {
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                if (!matriceCellules[i][j].getPresenceBombe()) {
                    int nbBombesAdj = compterBombesAdjacentes(i, j);
                    matriceCellules[i][j].setNbBombesAdjacentes(nbBombesAdj);
                }
            }
        }
    }

    /**
     * Compte le nombre de bombes adjacentes à la cellule située à la position (ligne, colonne).
     * @param ligne La ligne de la cellule.
     * @param colonne La colonne de la cellule.
     * @return Le nombre de bombes adjacentes.
     */
    private int compterBombesAdjacentes(int ligne, int colonne) {
        int compteur = 0;

        // Vérifie les 8 voisins de la cellule
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int voisinLigne = ligne + i;
                int voisinColonne = colonne + j;

                // Vérifie que le voisin est dans les limites de la grille
                if (voisinLigne >= 0 && voisinLigne < nbLignes &&
                    voisinColonne >= 0 && voisinColonne < nbColonnes &&
                    !(i == 0 && j == 0)) {

                    // Vérifie si le voisin contient une bombe
                    if (matriceCellules[voisinLigne][voisinColonne].getPresenceBombe()) {
                        compteur++;
                    }
                }
            }
        }

        return compteur;
    }

    /**
     * Affiche la grille dans la console.
     */
    public void afficherGrille() {
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                System.out.print(matriceCellules[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Getter pour accéder à la matrice de cellules (si nécessaire)
    public Cellule[][] getMatriceCellules() {
        return matriceCellules;
    }
}