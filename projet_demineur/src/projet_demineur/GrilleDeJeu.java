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
    private boolean bombesPlacees = false;

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
        
    }
    
    public boolean getPresenceBombe(int i, int j) {
        // Vérifie si les indices sont valides avant d'accéder à la cellule
        if (i >= 0 && i < nbLignes && j >= 0 && j < nbColonnes) {
            return matriceCellules[i][j].getPresenceBombe();
        }
        return false; // Si les indices sont hors limites, retourne false
    }
    public boolean bombesPlacees() {
    return bombesPlacees;
}
    public boolean toutesCellulesRevelees() {
    for (int i = 0; i < nbLignes; i++) {
        for (int j = 0; j < nbColonnes; j++) {
            Cellule cellule = matriceCellules[i][j];
            // Si une cellule sans bombe n'est pas dévoilée, la victoire n'est pas atteinte
            if (!cellule.isDevoilee() && !cellule.getPresenceBombe()) {
                return false;
            }
        }
    }
    return true; // Toutes les cellules sûres ont été révélées
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
   public void placerBombesAleatoirement(int ligneInterdite, int colonneInterdite) {
    if (bombesPlacees) return; // Ne pas placer les bombes si elles sont déjà placées

    Random random = new Random();
    int bombesAjoutees = 0;

    while (bombesAjoutees < nbBombes) {
        int ligne = random.nextInt(nbLignes);
        int colonne = random.nextInt(nbColonnes);

        // Éviter la cellule cliquée et les cellules déjà occupées par des bombes
        if ((ligne == ligneInterdite && colonne == colonneInterdite) || 
            matriceCellules[ligne][colonne].getPresenceBombe()) {
            continue;
        }

        matriceCellules[ligne][colonne].setPresenceBombe(true);
        bombesAjoutees++;
    }

    calculerBombesAdjacentes();
    this.bombesPlacees = true; // Marquer les bombes comme placées
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
        int nbBombesAdjacentes = 0;

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
                        nbBombesAdjacentes++;
                    }
                }
            }
        }

        return nbBombesAdjacentes;
    }
    
    public void revelerCellule(int ligne, int colonne) {
    // Vérifie si les coordonnées sont valides
    if (ligne < 0 || ligne >= nbLignes || colonne < 0 || colonne >= nbColonnes) {
        return; // Coordonnées hors limites
    }

    Cellule cellule = matriceCellules[ligne][colonne];

    // Si la cellule est déjà dévoilée, on arrête
    if (cellule.isDevoilee()) {
        return;
    }

    // Révéler la cellule
    cellule.setDevoilee(true);

    // Si la cellule ne contient pas de bombes adjacentes, on révèle les cases adjacentes
    if (cellule.getNbBombesAdjacentes() == 0) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (!(i == 0 && j == 0)) { // Éviter de rappeler la cellule elle-même
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