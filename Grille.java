package Echec_ESGIS;

public class Grille {
    private Pieces[][] grille = new Pieces[9][9];

    // On initialise la grille de taille 8x8 avec des cases vides
    public Grille() {
        for (int ligne = 1; ligne < 9; ligne++) {
            for (int colonn = 1; colonn < 9; colonn++) {
                grille[ligne][colonn] = null;
            }
        }
    }
    // geteeur pour acceder a une piece spé de la grille, qui va donner les coordonnees
    public Pieces getPiece(int ligne, int colonn) {
        if (ligne >= 1 && ligne <= 8 && colonn >= 1 && colonn <= 8) {
            return grille[ligne][colonn];
        }
        return null;
    }
    

    // Méthode pour afficher la grille
    public void AfficherGrille() {
        for (int ligne = 1; ligne < 9; ligne++) {
            for (int colonn = 1; colonn < 9; colonn++) {
                if (grille[ligne][colonn] == null) {
                    System.out.print("[ ]");
                } else {
                    System.out.print("[" + grille[ligne][colonn].afficher() + "]");
                }
            }
            System.out.println();
        }
    }

    // Placement des pièces sur la grille
    public void PlaceGrille() {
        grille[1][1] = new Tour("noir");
        grille[1][2] = new Cavalier("noir");
        grille[1][3] = new Fou("noir");
        grille[1][4] = new Dame("noir");
        grille[1][5] = new Roi("noir");
        grille[1][6] = new Fou("noir");
        grille[1][7] = new Cavalier("noir");
        grille[1][8] = new Tour("noir");
        // Placement des pions noirs
        for (int colonn = 1; colonn < 9; colonn++) {
            grille[2][colonn] = new Pion("noir");
        }

        // Placement des pièces blanches
        grille[8][1] = new Tour("blanc");
        grille[8][2] = new Cavalier("blanc");
        grille[8][3] = new Fou("blanc");
        grille[8][4] = new Dame("blanc");
        grille[8][5] = new Roi("blanc");
        grille[8][6] = new Fou("blanc");
        grille[8][7] = new Cavalier("blanc");
        grille[8][8] = new Tour("blanc");

        // Placement des pions blancs
        for (int colonn = 1; colonn < 9; colonn++) {
            grille[7][colonn] = new Pion("blanc");
        }
    }
    public boolean deplacePiece(int DeplaX, int DeplaY, int ArrX, int ArrY ){
        // on verifie que la condition que tant qu'il n'a pas bougé son depart est egal a son arrivee
        //Si le deplace n'est pas valide o bouge pas
            Pieces piece = grille[DeplaX][DeplaY];
    
            // Vérification de l'existence de la pièce de départ
            if (piece == null) {
                // System.out.println("Aucune pièce à cet endroit.");
                return false;
            }
    
            // Vérification de la validité du mouvement
            if (!piece.Bouge(DeplaX, DeplaY, ArrX, ArrY)) {
                System.out.println("Déplacement non valide");
                return false;
            }
    
            // Vérification de la case d'arrivée
            Pieces pieceArrivee = grille[ArrX][ArrY];
            if (pieceArrivee != null) {
                // Vérifie si la pièce cible est de la même couleur
                if (pieceArrivee.getCouleur().equals(piece.getCouleur())) {
                    System.out.println("Occupé");
                    return false;
                } else {
                    // Capture de la pièce adverse
                    System.out.println("Capturé a la position " + ArrX + "," + ArrY);
                }
            }
    
            // Déplace la pièce
            grille[ArrX][ArrY] = piece;
            grille[DeplaX][DeplaY] = null;
            return true;
    }

    //code pour le roque
    public boolean roque(int ligneRoi, boolean petitRoque) {
        int colDepartRoi = 5;
        int colArriveeRoi = petitRoque ? 7 : 3;
        int colTour = petitRoque ? 8 : 1;
        int colArriveeTour = petitRoque ? 6 : 4;

        Pieces roi = grille[ligneRoi][colDepartRoi];
        Pieces tour = grille[ligneRoi][colTour];

        // Vérification des conditions du roque
        if (roi instanceof Roi && tour instanceof Tour) {
            if (((Roi) roi).aBouge() || ((Tour) tour).aBouge()) {
                System.out.println("Impossible de roquer : le roi ou la tour ont déjà bougé.");
                return false;
            }
            if (!cheminLibre(ligneRoi, colDepartRoi, colTour) || roiEnEchec(ligneRoi, colDepartRoi)) {
                System.out.println("Impossible de roquer : le chemin n'est pas libre ou le roi est en échec.");
                return false;
            }

            // Déplacer le roi et la tour pour le roque
            grille[ligneRoi][colArriveeRoi] = roi;
            grille[ligneRoi][colArriveeTour] = tour;
            grille[ligneRoi][colDepartRoi] = null;
            grille[ligneRoi][colTour] = null;

            // Mettre à jour l'état de déplacement
            ((Roi) roi).setBouge(true);
            ((Tour) tour).setBouge(true);

            return true;
        }

        System.out.println("Impossible de roquer : pièces incorrectes.");
        return false;
    }

    private boolean cheminLibre(int ligne, int colDepart, int colArrivee) {
        int increment = (colArrivee > colDepart) ? 1 : -1;
        for (int col = colDepart + increment; col != colArrivee; col += increment) {
            if (grille[ligne][col] != null) {
                return false;
            }
        }
        return true;
    }

    private boolean roiEnEchec(int ligne, int col) {
        // c'est ici que je dois gerer la logique pour déterminer si le roi est en échec.
        // puis l'implémenter en fonction de la détection des attaques adverses.
        return false;
    }
}
