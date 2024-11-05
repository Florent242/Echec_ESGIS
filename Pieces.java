package Echec_ESGIS;

abstract class Pieces {
    private String couleur;

    // Constructeur pour initialiser la couleur
    public Pieces(String couleur) {
        this.couleur = couleur;
    }

    // Getter pour obtenir la couleur
    public String getCouleur() {
        return couleur;
    }

    // Méthode pour afficher une pièce générique
    public String afficher() {
        return "P";
    }

    // on declare la methode bouge en abstract pour que chaque puisse l'implementer
    public abstract boolean Bouge(int DeplaX, int DeplaY, int ArrX, int ArrY);

}

// Création des sous-classes pour les différentes pièces du jeu
///////////////////////////////////////////////////////////////
////////                                            //////////

class Roi extends Pieces {
    private boolean aBouge = false;  // Déclaration de aBouge

    public Roi(String couleur) {
        super(couleur);
    }

    @Override
    public String afficher() {
        return "R";
    }
    public boolean Bouge(int DeplaX, int DeplaY, int ArrX, int ArrY) {
        return Math.abs(DeplaX - ArrX) <= 1 && Math.abs(DeplaY - ArrY) <= 1;
    }
    // Méthodes pour gérer l'état de mouvement du Roi ajouter 2/11/2024//
    public boolean aBouge() {
        return aBouge;
    }

    public void setBouge(boolean aBouge) {
        this.aBouge = aBouge;
    }
}

class Dame extends Pieces {
    public Dame(String couleur) {
        super(couleur);
    }

    @Override
    public String afficher() {
        return "D";
    }
    public boolean Bouge(int DeplaX, int DeplaY, int ArrX, int ArrY) {
        return (DeplaX == ArrX || DeplaY == ArrY) || 
            (Math.abs(DeplaX - ArrX) == Math.abs(DeplaY - ArrY));
    }
}

class Tour extends Pieces {
    private boolean aBouge = false;  // Déclaration de aBouge

    public Tour(String couleur) {
        super(couleur);
    }

    @Override
    public String afficher() {
        return "T";
    }

    public boolean Bouge(int DeplaX, int DeplaY, int ArrX, int ArrY){
        return DeplaX == ArrX || DeplaY == ArrY;
    }
    // Méthodes pour gérer l'état de mouvement du Roi aujouter 2/11/24//
    public boolean aBouge() {
        return aBouge;
    }
    // je recupere la methode aBouge pour la modifier 
    public void setBouge(boolean aBouge) {
        this.aBouge = aBouge;
    }
}

class Fou extends Pieces {
    public Fou(String couleur) {
        super(couleur);
    }

    @Override
    public String afficher() {
        return "F";
    }

    public boolean Bouge(int DeplaX, int DeplaY, int ArrX, int ArrY) {
        return Math.abs(DeplaX - ArrX) == Math.abs(DeplaY - ArrY);
    }
}

class Cavalier extends Pieces {
    public Cavalier(String couleur) {
        super(couleur);
    }

    @Override
    public String afficher() {
        return "C";
    }
    public boolean Bouge(int DeplaX, int DeplaY, int ArrX, int ArrY) {
        int dx = Math.abs(DeplaX - ArrX);
        int dy = Math.abs(DeplaY - ArrY);
        return (dx == 2 && dy == 1) || (dx == 1 && dy == 2);
    }
}

class Pion extends Pieces {
    public Pion(String couleur) {
        super(couleur);
    }

    @Override
    public String afficher() {
        return "p";
    }
    public boolean Bouge(int DeplaX, int DeplaY, int ArrX, int ArrY) {
        int direction = getCouleur().equals("blanc") ? -1 : 1; // Blanc avance vers le haut, Noir vers le bas
        
        // Mouvement de capture en diagonale
        if (ArrX == DeplaX + direction && Math.abs(ArrY - DeplaY) == 1 && (getCouleur().equals("blanc") || getCouleur().equals("blanc") ) ) {
            return true;
        }
        
        // Mouvement normal d'une case
        if (ArrX == DeplaX + direction && DeplaY == ArrY) {
            return true;
        }
        
        // Mouvement initial de deux cases
        if ((getCouleur().equals("blanc") && DeplaX == 7 || getCouleur().equals("noir") && DeplaX == 2) &&
            ArrX == DeplaX + 2 * direction && DeplaY == ArrY) {
            return true;
        }


        return false;
    }
}

