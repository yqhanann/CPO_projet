/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projet_demineur;

/**
 *
 * @author yohan
 */
public class Partie {
    private GrilleDeJeu grille;
    private int nbVies;
    private boolean partieTerminee;

    // Constructeur qui initialise la grille et les vies
    public Partie(int nbLignes, int nbColonnes, int nbBombes, int nbVies) {
        this.grille = new GrilleDeJeu(nbLignes, nbColonnes, nbBombes);
        this.nbVies = nbVies;
        this.partieTerminee = false;
    }

    // Méthode pour initialiser la partie : grille, bombes, vies
    public void initialiserPartie() {
        // Initialisation de la grille et placement des bombes
        grille.placerBombesAleatoirement();
        grille.calculerBombesAdjacentes();

        // Réinitialiser l'état de la grille et les vies
        this.nbVies = 3;  // Par exemple, 3 vies pour commencer
        this.partieTerminee = false;

        // Vous pouvez ajouter d'autres initialisations ici si nécessaire.
    }

    // Méthode pour effectuer un tour de jeu
    public void tourDeJeu(int ligne, int colonne) {
        // Vérifie si la partie est déjà terminée
        if (partieTerminee) {
            System.out.println("La partie est déjà terminée.");
            return;
        }

        // Révéler la cellule
        Cellule cellule = grille.getMatriceCellules()[ligne][colonne];

        // Si la cellule contient une bombe
        if (cellule.getPresenceBombe()) {
            // Le joueur perd une vie
            nbVies--;
            System.out.println("Oh non, vous avez déclenché une bombe ! Il vous reste " + nbVies + " vies.");
            
            // Vérifier si le joueur a perdu
            if (nbVies == 0) {
                System.out.println("Game Over ! Vous avez perdu !");
                partieTerminee = true;
            }
        } else {
            // Si la cellule ne contient pas de bombe, on la révèle
            cellule.revelerCellule();

            // Si la cellule a 0 bombes adjacentes, révéler les cellules adjacentes par propagation
            if (cellule.getNbBombesAdjacentes() == 0) {
                // Propagez la révélation des cellules adjacentes (récursivement ou avec une queue)
                revelerCellulesAdjacentes(ligne, colonne);
            }

            // Vérifier si la partie est gagnée après chaque tour
            if (verifierVictoire()) {
                System.out.println("Félicitations, vous avez gagné !");
                partieTerminee = true;
            }
        }
    }

    // Méthode pour révéler les cellules adjacentes (par propagation)
    private void revelerCellulesAdjacentes(int ligne, int colonne) {
        for (int i = ligne - 1; i <= ligne + 1; i++) {
            for (int j = colonne - 1; j <= colonne + 1; j++) {
                if (i >= 0 && i < grille.getNbLines() && j >= 0 && j < grille.getNbColonnes()) {
                    Cellule cellule = grille.getMatriceCellules()[i][j];
                    if (!cellule.isDevoilee()) {
                        cellule.revelerCellule();

                        // Si la cellule adjacente a 0 bombes, on propage davantage
                        if (cellule.getNbBombesAdjacentes() == 0) {
                            revelerCellulesAdjacentes(i, j);
                        }
                    }
                }
            }
        }
    }

    // Méthode pour vérifier la victoire : si toutes les cellules sûres sont révélées
    public boolean verifierVictoire() {
        return grille.toutesCellulesRevelees();
    }

    // Méthode pour démarrer la partie
    public void demarrerPartie() {
        // Initialiser la partie avant de commencer
        initialiserPartie();

        // Boucle principale du jeu
        while (!partieTerminee) {
            // Demander au joueur de choisir une cellule à révéler
            // Ici, vous pouvez adapter l'input du joueur (par exemple avec un Scanner)
            System.out.println("Entrez les coordonnées de la cellule à révéler (ligne, colonne) : ");
            // Par exemple, simuler un tour avec des coordonnées fixes (remplacer cette partie par un Scanner ou une autre méthode)
            int ligne = 2; // Remplacez par l'entrée de l'utilisateur
            int colonne = 2; // Remplacez par l'entrée de l'utilisateur

            // Effectuer le tour de jeu
            tourDeJeu(ligne, colonne);
        }
    }
}

