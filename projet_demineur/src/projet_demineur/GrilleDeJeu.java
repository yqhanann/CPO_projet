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
    
    public boolean getPresenceBombe(int i, int j) {
        // Vérifie si les indices sont valides avant d'accéder à la cellule
        if (i >= 0 && i < nbLignes && j >= 0 && j < nbColonnes) {
            return matriceCellules[i][j].getPresenceBombe();
        }
        return false; // Si les indices sont hors limites, retourne false
    }
     
    public boolean toutesCellulesRevelees() {
        // Parcours de toutes les cellules de la grille
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                Cellule cellule = matriceCellules[i][j];

                // Si la cellule n'a pas de bombe et n'est pas dévoilée, la partie n'est pas encore gagnée
                if (!cellule.getPresenceBombe() && !cellule.isDevoilee()) {
                    return false;
                }
            }
        }
        // Si toutes les cellules sûres sont dévoilées, la partie est gagnée
        return true;
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
    public void placerBombesAleatoirement() {
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
    public void calculerBombesAdjacentes() {
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
    
    public void revelerCellule(int ligne, int colonne) {
        // Vérifie si les coordonnées sont valides
        if (ligne < 0 || ligne >= nbLignes || colonne < 0 || colonne >= nbColonnes) {
            return; // Coordonnées hors de la grille
        }

        Cellule cellule = matriceCellules[ligne][colonne];

        // Si la cellule est déjà dévoilée, on arrête
        if (cellule.isDevoilee()) {
            return;
        }

        // Révéler la cellule
        cellule.setDevoilee(true);

        // Si c'est une bombe, la partie est perdue
        if (cellule.getPresenceBombe()) {
            System.out.println("BOOM! Vous avez perdu.");
            return;
        }

        // Si la cellule ne contient pas de bombes adjacentes, on révèle les cases adjacentes
        if (cellule.getNbBombesAdjacentes() == 0) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (!(i == 0 && j == 0)) {  // Éviter de rappeler la cellule elle-même
                        revelerCellule(ligne + i, colonne + j);
                    }
                }
            }
        }
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
    
    public int getNbLines(){
        return nbLignes;
                }
    public int getNbColonnes(){
        return nbColonnes;
                }
    public int getNbBombes(){
        return nbBombes;
                }
    
    @Override
    public String toString() {
        StringBuilder grilleRepresentation = new StringBuilder();

        // Ajouter la première ligne pour les indices des colonnes
        grilleRepresentation.append("   ");
        for (int j = 0; j < nbColonnes; j++) {
            grilleRepresentation.append(j).append(" ");
        }
        grilleRepresentation.append("\n");

        // Parcours des lignes de la grille
        for (int i = 0; i < nbLignes; i++) {
            // Ajouter un indice pour la ligne
            grilleRepresentation.append(i).append("  ");
            
            // Parcours des cellules de la ligne
            for (int j = 0; j < nbColonnes; j++) {
                // Ajouter la représentation textuelle de chaque cellule
                grilleRepresentation.append(matriceCellules[i][j].toString()).append(" ");
            }
            grilleRepresentation.append("\n");
        }
        
        return grilleRepresentation.toString();
    
    
}
}