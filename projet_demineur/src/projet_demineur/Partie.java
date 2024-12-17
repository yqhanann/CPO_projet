/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projet_demineur;

import java.util.Scanner;

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
    public void initialiserPartie(int nbLignes, int nbColonnes, int nbBombes) {
    // Initialisation de la grille (sans placement des bombes)
    this.grille = new GrilleDeJeu(nbLignes, nbColonnes, nbBombes);

    // Réinitialiser les vies et l'état de la partie
    this.nbVies = 3;  // Par exemple, 3 vies pour commencer
    this.partieTerminee = false;

    System.out.println("Nouvelle partie initialisée !");
    System.out.println(grille); // Affiche la grille initiale masquée
}

    // Méthode pour effectuer un tour de jeu
   // Gérer un tour de jeu
   public void tourDeJeu(int ligne, int colonne) {
    if (partieTerminee) {
        System.out.println("La partie est déjà terminée !");
        return;
    }

    // Placer les bombes lors du premier clic
    if (!grille.bombesPlacees()) {
        grille.placerBombesAleatoirement(ligne, colonne);
    }

    // Révéler la cellule choisie
    if (grille.getPresenceBombe(ligne, colonne)) {
        nbVies--;
        System.out.println("Boom ! Vous avez déclenché une bombe.");
        if (nbVies <= 0) {
            partieTerminee = true;
            System.out.println("Game Over ! Vous avez perdu.");
            return;
        }
    } else {
        grille.revelerCellule(ligne, colonne);
        System.out.println("Cellule révélée avec succès !");
    }

    // Vérifier si le joueur a gagné
    if (grille.toutesCellulesRevelees()) {
        partieTerminee = true;
        System.out.println("Félicitations ! Vous avez gagné !");
    }
}





    // Méthode pour vérifier la victoire : si toutes les cellules sûres sont révélées
    public boolean verifierVictoire() {
        return grille.toutesCellulesRevelees();
    }

    // Démarrer une partie
    public void demarrerPartie() {
    // Scanner pour la saisie utilisateur
    Scanner scanner = new Scanner(System.in);
    boolean partieTerminee = false;

    while (!partieTerminee) {
        // Afficher les options
        System.out.println("\nOptions :");
        System.out.println("1. Révéler une cellule (ligne et colonne)");
        System.out.println("2. Quitter la partie");

        System.out.print("Votre choix : ");
        int choix = scanner.nextInt();

        if (choix == 1) {
            // Saisie des coordonnées
            System.out.print("Entrez la ligne (0-" + (grille.getNbLines() - 1) + ") : ");
            int ligne = scanner.nextInt();

            System.out.print("Entrez la colonne (0-" + (grille.getNbColonnes() - 1) + ") : ");
            int colonne = scanner.nextInt();

            // Vérification des coordonnées
            if (ligne >= 0 && ligne < grille.getNbLines() && colonne >= 0 && colonne < grille.getNbColonnes()) {
                tourDeJeu(ligne, colonne);
                
                // Vérifier la victoire
                if (grille.toutesCellulesRevelees()) {
                    System.out.println("Félicitations ! Vous avez gagné !");
                    partieTerminee = true;
                }
            } else {
                System.out.println("Coordonnées invalides. Réessayez !");
            }
        } else if (choix == 2) {
            System.out.println("Vous avez quitté la partie.");
            partieTerminee = true;
        } else {
            System.out.println("Choix invalide. Réessayez !");
        }
    }

    scanner.close();
}
public GrilleDeJeu getGrille() {
    return this.grille;
}
public int getNbVies() {
    return this.nbVies;
}
}

