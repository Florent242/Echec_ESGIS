package Echec_ESGIS;

import java.util.List;
import java.util.ArrayList;


public class Grille {
    private Pieces[][] grille = new Pieces[8][8];
    private boolean tourBlanc = true;
    private int compteur50Coups = 0;

    public Grille() {
        for (int ligne = 0; ligne < 8; ligne++) {
            for (int colonn = 0; colonn < 8; colonn++) {
                grille[ligne][colonn] = null;
            }
        }
    }

    public Pieces getPiece(int ligne, int colonn) {
        if (ligne >= 0 && ligne <= 7 && colonn >= 0 && colonn <= 7) {
            return grille[ligne][colonn];
        }
        return null;
    }

    public void AfficherGrille() {
        for (int ligne = 0; ligne < 8; ligne++) {
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

    public void PlaceGrille() {
        grille[7][0] = new Tour("blanc"); 
        grille[7][1] = new Cavalier("blanc"); 
        grille[7][2] = new Fou("blanc"); 
        grille[7][3] = new Dame("blanc"); 
        grille[7][4] = new Roi("blanc"); 
        grille[7][5] = new Fou("blanc"); 
        grille[7][6] = new Cavalier("blanc"); 
        grille[7][7] = new Tour("blanc"); 
        for (int i = 0; i < 8; i++) { 
            grille[6][i] = new Pion("blanc"); 
        } 
        grille[0][0] = new Tour("noir"); 
        grille[0][1] = new Cavalier("noir"); 
        grille[0][2] = new Fou("noir"); 
        grille[0][3] = new Dame("noir"); 
        grille[0][4] = new Roi("noir"); 
        grille[0][5] = new Fou("noir"); 
        grille[0][6] = new Cavalier("noir"); 
        grille[0][7] = new Tour("noir"); 
        for (int i = 0; i < 8; i++) { 
            grille[1][i] = new Pion("noir"); 
        } 
    }

    public boolean deplacePiece(int DeplaX, int DeplaY, int ArrX, int ArrY) {
        Pieces piece = grille[DeplaX][DeplaY];

        if (piece == null) {
            System.out.println("Aucune pièce à cet endroit.");
            return false;
        }

        if ((tourBlanc && !piece.getCouleur().equals("blanc")) || (!tourBlanc && !piece.getCouleur().equals("noir"))) {
            System.out.println("Ce n'est pas le tour de cette couleur.");
            return false;
        }

        if (!piece.Bouge(DeplaX, DeplaY, ArrX, ArrY, grille)) {
            System.out.println("Déplacement non valide pour cette pièce.");
            return false;
        }

        if ((piece instanceof Tour || piece instanceof Fou || piece instanceof Dame) && !Libre(DeplaX, DeplaY, ArrX, ArrY)) {
            System.out.println("Chemin bloqué pour cette pièce.");
            return false;
        }

        Pieces pieceArrivee = grille[ArrX][ArrY];
        if (pieceArrivee != null && pieceArrivee.getCouleur().equals(piece.getCouleur())) {
            System.out.println("Case occupée par une pièce alliée.");
            return false;
        } else if (pieceArrivee != null) {
            System.out.println("Vous avez capturé une pièce ennemie.");
        }else if (grille[ArrX][ArrY] instanceof Pion) { //////////////en  verif 
            promouvoirPion(ArrX, ArrY);//en verif 
        }

        grille[ArrX][ArrY] = piece;
        grille[DeplaX][DeplaY] = null;

        tourBlanc = !tourBlanc;
        System.out.println("Tour du joueur : " + (tourBlanc ? "Blanc" : "Noir"));
        return true;
    }

    private boolean Libre(int DeplaX, int DeplaY, int ArrX, int ArrY) {
        if (DeplaX == ArrX) {
            int minY = Math.min(DeplaY, ArrY);
            int maxY = Math.max(DeplaY, ArrY);
            for (int y = minY + 1; y < maxY; y++) {
                if (grille[DeplaX][y] != null) {
                    return false;
                }
            }
        } else if (DeplaY == ArrY) {
            int minX = Math.min(DeplaX, ArrX);
            int maxX = Math.max(DeplaX, ArrX);
            for (int x = minX + 1; x < maxX; x++) {
                if (grille[x][DeplaY] != null) {
                    return false;
                }
            }
        }
        return true;
    }
    private void promouvoirPion(int ArrX, int ArrY) {
        Pieces pion = grille[ArrX][ArrY];
        if (pion instanceof Pion && (ArrX == 0 || ArrX == 7)) {
            String couleur = pion.getCouleur();
            grille[ArrX][ArrY] = new Dame(couleur); // Promotion en Dame par défaut
            System.out.println("Pion promu en Dame !");
        }
    }
    
    public boolean roiEnEchec(String couleur) {
        int[] positionRoi = trouverRoi(couleur);
        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                Pieces piece = grille[i][j];
                if (piece != null && !piece.getCouleur().equals(couleur)) {
                    if (piece.Bouge(i, j, positionRoi[0], positionRoi[1], grille)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private int[] trouverRoi(String couleur) {
        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                if (grille[i][j] instanceof Roi && grille[i][j].getCouleur().equals(couleur)) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    public boolean estEchecEtMat(String couleur) {
        if (!roiEnEchec(couleur)) return false;

        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                Pieces piece = grille[i][j];
                if (piece != null && piece.getCouleur().equals(couleur)) {
                    for (int x = 0; x <= 7; x++) {
                        for (int y = 0; y <= 7; y++) {
                            if (piece.Bouge(i, j, x, y, grille)) {
                                Pieces tmp = grille[x][y];
                                grille[x][y] = piece;
                                grille[i][j] = null;
                                if (!roiEnEchec(couleur)) {
                                    grille[i][j] = piece;
                                    grille[x][y] = tmp;
                                    return false;
                                }
                                grille[i][j] = piece;
                                grille[x][y] = tmp;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean regleDes50Coups() {
        return compteur50Coups >= 50;
    }

    public void incrementerCompteur50Coups(boolean captureOuPionAvance) {
        if (captureOuPionAvance) {
            compteur50Coups = 0;
        } else {
            compteur50Coups++;
        }
    }

    // Détection du pat (aucun coup légal possible pour le joueur au trait sans échec)
    public boolean estPat(String couleur) {
        if (roiEnEchec(couleur)) return false;
        
        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                Pieces piece = grille[i][j];
                if (piece != null && piece.getCouleur().equals(couleur)) {
                    for (int x = 0; x <= 7; x++) {
                        for (int y = 0; y <= 7; y++) {
                            if (piece.Bouge(i, j, x, y, grille)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true; // Aucun coup légal disponible, c’est un pat
    }

    public void verifierConditionsDeNul(String couleur) {
        if (estPat(couleur)) {
            System.out.println("Match nul par pat !");
        } else if (regleDes50Coups()) {
            System.out.println("Match nul par la règle des 50 coups !");
        }
    }
    ///////////////////////////////////////////////////////////////////
    public List<int[]> deplacementsPossibles(int x, int y, Grille grille) {
        List<int[]> deplacements = new ArrayList<>();
        Pieces piece = grille.getPiece(x, y);  
    
        if (piece != null) {
            if (piece instanceof Pion) {
                deplacements = deplacementsPion(x, y, grille);
            } else if (piece instanceof Tour) {
                deplacements = deplacementsTour(x, y, grille);
            } else if (piece instanceof Cavalier) {
                deplacements = deplacementsCavalier(x, y, grille);
            } else if (piece instanceof Fou) {
                deplacements = deplacementsFou(x, y, grille);
            } else if (piece instanceof Dame) {
                deplacements = deplacementsDame(x, y, grille);
            } else if (piece instanceof Roi) {
                deplacements = deplacementsRoi(x, y, grille);
            }
        }
        return deplacements;
    }
    public List<int[]> deplacementsPion(int x, int y, Grille grille) {
        List<int[]> deplacements = new ArrayList<>();
        Pieces pion = grille.getPiece(x, y);
        String couleur = pion.getCouleur();
    
        int direction = couleur.equals("blanc") ? -1 : 1;
        if (grille.getPiece(x + direction, y) == null) {
            deplacements.add(new int[]{x + direction, y});
        }
    
        if ((x == 1 && couleur.equals("blanc")) || (x == 6 && couleur.equals("noir"))) {
            if (grille.getPiece(x + direction * 2, y) == null) {
                deplacements.add(new int[]{x + direction * 2, y});
            }
        }
    
        if (y > 1 && grille.getPiece(x + direction, y - 1) != null) {
            deplacements.add(new int[]{x + direction, y - 1});
        }
        if (y < 8 && grille.getPiece(x + direction, y + 1) != null) {
            deplacements.add(new int[]{x + direction, y + 1});
        }
    
        return deplacements;
    }
    public List<int[]> deplacementsTour(int x, int y, Grille grille) {
        List<int[]> deplacements = new ArrayList<>();
    
        for (int i = x + 1; i <= 8; i++) {
            if (grille.getPiece(i, y) == null) {
                deplacements.add(new int[]{i, y});
            } else {
                if (!grille.getPiece(i, y).getCouleur().equals(grille.getPiece(x, y).getCouleur())) {
                    deplacements.add(new int[]{i, y});
                }
                break;  
            }
        }
        for (int i = x - 1; i >= 1; i--) {
            if (grille.getPiece(i, y) == null) {
                deplacements.add(new int[]{i, y});
            } else {
                if (!grille.getPiece(i, y).getCouleur().equals(grille.getPiece(x, y).getCouleur())) {
                    deplacements.add(new int[]{i, y});
                }
                break;  
            }
        }
    
        for (int i = y + 1; i <= 8; i++) {
            if (grille.getPiece(x, i) == null) {
                deplacements.add(new int[]{x, i});
            } else {
                if (!grille.getPiece(x, i).getCouleur().equals(grille.getPiece(x, y).getCouleur())) {
                    deplacements.add(new int[]{x, i});
                }
                break;  
            }
        }
        for (int i = y - 1; i >= 1; i--) {
            if (grille.getPiece(x, i) == null) {
                deplacements.add(new int[]{x, i});
            } else {
                if (!grille.getPiece(x, i).getCouleur().equals(grille.getPiece(x, y).getCouleur())) {
                    deplacements.add(new int[]{x, i});
                }
                break; 
            }
        }
    
        return deplacements;
    }
    public List<int[]> deplacementsCavalier(int x, int y, Grille grille) {
        List<int[]> deplacements = new ArrayList<>();
        int[][] directions = {
            {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
            {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };
    
        for (int[] direction : directions) {
            int newX = x + direction[0];
            int newY = y + direction[1];
            if (newX >= 1 && newX <= 8 && newY >= 1 && newY <= 8) {
                Pieces destination = grille.getPiece(newX, newY);
                if (destination == null || !destination.getCouleur().equals(grille.getPiece(x, y).getCouleur())) {
                    deplacements.add(new int[]{newX, newY});
                }
            }
        }
    
        return deplacements;
    }
    public List<int[]> deplacementsFou(int x, int y, Grille grille) {
        List<int[]> deplacements = new ArrayList<>();
    
        for (int i = 1; i <= 8; i++) {
            if (x - i >= 1 && y - i >= 1) {
                if (grille.getPiece(x - i, y - i) == null) {
                    deplacements.add(new int[]{x - i, y - i});
                } else {
                    if (!grille.getPiece(x - i, y - i).getCouleur().equals(grille.getPiece(x, y).getCouleur())) {
                        deplacements.add(new int[]{x - i, y - i});
                    }
                    break;  
                }
            }
    
        }
    
        return deplacements;
    }
    public List<int[]> deplacementsRoi(int x, int y, Grille grille) {
        List<int[]> deplacements = new ArrayList<>();
    
        
        int[] directions = {-1, 0, 1};
        for (int dx : directions) {
            for (int dy : directions) {
                if (dx != 0 || dy != 0) { 
                    int newX = x + dx;
                    int newY = y + dy;
                    if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8 && grille.getPiece(newX, newY) == null) {
                        deplacements.add(new int[]{newX, newY});
                    }
                }
            }
        }
    
        return deplacements;
    }
    public List<int[]> deplacementsDame(int x, int y, Grille grille) {
        List<int[]> deplacements = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            if (grille.getPiece(x + i, y) == null) deplacements.add(new int[]{x + i, y}); 
            if (grille.getPiece(x - i, y) == null) deplacements.add(new int[]{x - i, y}); 
            if (grille.getPiece(x, y + i) == null) deplacements.add(new int[]{x, y + i}); 
            if (grille.getPiece(x, y - i) == null) deplacements.add(new int[]{x, y - i}); 
        }
        for (int i = 1; i < 8; i++) {
            if (x + i < 8 && y + i < 8 && grille.getPiece(x + i, y + i) == null) deplacements.add(new int[]{x + i, y + i}); 
            if (x - i >= 0 && y + i < 8 && grille.getPiece(x - i, y + i) == null) deplacements.add(new int[]{x - i, y + i});
            if (x + i < 8 && y - i >= 0 && grille.getPiece(x + i, y - i) == null) deplacements.add(new int[]{x + i, y - i});
            if (x - i >= 0 && y - i >= 0 && grille.getPiece(x - i, y - i) == null) deplacements.add(new int[]{x - i, y - i});
        }
    
        return deplacements;
    }
        
                
    public List<int[]> getDeplacementsPossibles(int x, int y) {
        Pieces piece = getPiece(x, y);
        if (piece != null) {
            
            return piece.deplacementsPossibles(x, y, this);
        }
        return new ArrayList<>(); 
    }
    
    
}