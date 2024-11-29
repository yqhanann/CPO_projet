/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projet_demineur;

/**
 *
 * @author yohan
 */
public class Cellule {
    public boolean presenceBombe;
    private boolean devoilee;
    public int nbBombesAdjacentes;
    
    public Cellule() {
        this.devoilee = false;
        this.presenceBombe = false;
        this.nbBombesAdjacentes = 0;
    }
    public boolean getPresenceBombe(){
    return presenceBombe;
    }
    // Dans la classe Cellule
    public void setPresenceBombe(boolean presenceBombe) {
    this.presenceBombe = presenceBombe;
}

    public int getNbBombesAdjacentes(){
        return nbBombesAdjacentes;
        
    }
    public void placerBombe(){
    presenceBombe = true;
    }
    public void revelerCellule(){
    devoilee = true;
    }
    public int setNbBombesAdjacentes(int nbBombesAdj){
    return nbBombesAdjacentes;
    }
    
    @Override
  public String toString() {
    String chaine_a_retourner;

    if (!devoilee) {
        // La cellule n'est pas dévoilée
        chaine_a_retourner = "?";
    } else if (presenceBombe) {
        // La cellule est dévoilée et contient une bombe
        chaine_a_retourner = "B";
    } else if (nbBombesAdjacentes > 0) {
        // La cellule est dévoilée, ne contient pas de bombe, et a des bombes adjacentes
        chaine_a_retourner = String.valueOf(nbBombesAdjacentes);
    } else {
        // La cellule est dévoilée, ne contient pas de bombe, et n'a pas de bombes adjacentes
        chaine_a_retourner = " ";
    }

    return chaine_a_retourner;}
}



