package projet_demineur;

/**
 * Classe représentant une cellule du jeu de démineur.
 */
public class Cellule {
    private boolean presenceBombe; // La cellule contient une bombe ?
    private boolean devoilee; // La cellule est-elle dévoilée ?
    private int nbBombesAdjacentes; // Nombre de bombes adjacentes à cette cellule
    private boolean aDrapeau = false;

    

    // Constructeur par défaut
    public Cellule() {
        this.devoilee = false;
        this.presenceBombe = false;
        this.nbBombesAdjacentes = 0;
    }
    public boolean hasDrapeau() {
    return aDrapeau;
}

public void setDrapeau(boolean aDrapeau) {
    this.aDrapeau = aDrapeau;
}
    // Getter pour savoir si une cellule contient une bombe
    public boolean getPresenceBombe() {
        return presenceBombe;
    }

    // Setter pour indiquer si une cellule contient une bombe
    public void setPresenceBombe(boolean presenceBombe) {
        this.presenceBombe = presenceBombe;
    }

    // Getter pour obtenir le nombre de bombes adjacentes
    public int getNbBombesAdjacentes() {
        return nbBombesAdjacentes;
    }

    // Setter pour définir le nombre de bombes adjacentes
    public void setNbBombesAdjacentes(int nbBombesAdjacentes) {
        this.nbBombesAdjacentes = nbBombesAdjacentes;
    }

    // Méthode pour placer une bombe dans la cellule
    public void placerBombe() {
        this.presenceBombe = true;
    }

    // Méthode pour révéler la cellule
    public void revelerCellule() {
        this.devoilee = true;
    }
    public void setDevoilee(boolean devoilee) {
        this.devoilee = devoilee;
    }
    // Getter pour savoir si la cellule est déjà dévoilée
    public boolean isDevoilee() {
        return devoilee;
    }

    // Affichage d'une cellule sous forme de chaîne de caractères
    @Override
    public String toString() {
        if (!devoilee) {
            return "?"; // La cellule n'est pas dévoilée
        } else if (presenceBombe) {
            return "B"; // La cellule est dévoilée et contient une bombe
        } else if (nbBombesAdjacentes > 0) {
            return String.valueOf(nbBombesAdjacentes); // La cellule est dévoilée et affiche le nombre de bombes adjacentes
        } else {
            return " "; // La cellule est vide et ne contient pas de bombes adjacentes
        }
    }
}
