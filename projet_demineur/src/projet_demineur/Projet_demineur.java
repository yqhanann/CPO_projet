/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package projet_demineur;

/**
 *
 * @author yohan
 */
public class Projet_demineur {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
            // Création d'une cellule
        Cellule cellule = new Cellule();

        // Affichage initial (non dévoilée)
        System.out.println("État initial de la cellule : " + cellule);

        // Placement d'une bombe
        cellule.placerBombe();
        System.out.println("Bombe placée : présence de bombe ? " + cellule.getPresenceBombe());

        // Dévoiler la cellule
        cellule.revelerCellule();
        System.out.println("Cellule dévoilée : " + cellule);

        // Modifier le nombre de bombes adjacentes
        cellule.setNbBombesAdjacentes(3);
        System.out.println("Nombre de bombes adjacentes défini : " + cellule.getNbBombesAdjacentes());
        System.out.println("État de la cellule : " + cellule);

        // Test avec une cellule sans bombe mais avec des bombes adjacentes
        Cellule celluleSansBombe = new Cellule();
        celluleSansBombe.setNbBombesAdjacentes(2);
        celluleSansBombe.revelerCellule();
        System.out.println("État de la cellule sans bombe : " + celluleSansBombe);

        // Test avec une cellule vide (sans bombe ni bombes adjacentes)
        Cellule celluleVide = new Cellule();
        celluleVide.revelerCellule();
        System.out.println("État de la cellule vide : " + celluleVide);
    }
}// TODO code application logic here
    
    