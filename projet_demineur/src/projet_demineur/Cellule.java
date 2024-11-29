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
    private boolean presenceBombe;
    private boolean devoilee;
    private int nbBombesAdjacentes;
    
    public Cellule(boolean presenceBombe, boolean devoilee, int nbBombesAdjacentes){
    this.devoilee = devoilee;
    this.presenceBombe = presenceBombe;
    this.nbBombesAdjacentes = nbBombesAdjacentes; 
            }
    public boolean getPresenceBombe(){
    return presenceBombe;
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
    public int setNbBombesAdjacentes(){
    return nbBombesAdjacentes;
    }
    
    @Override
    public String toString() {
    String chaine_a_retourner;
    if(devoilee == true){
    chaine_a_retourner = "?";
    return chaine_a_retourner;
    }
    if(devoilee == true && presenceBombe == true){
    chaine_a_retourner = "B";
    return chaine_a_retourner;}
    if(devoilee == true && presenceBombe == false &&  )
    chaine_a_retourner = "Object : "+Nom+" "+Attaque;
    return chaine_a_retourner;
}
}

