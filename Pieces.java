package Echec_ESGIS;

import java.util.List;
import java.util.ArrayList;

abstract class Pieces {
    private String couleur;

    public Pieces(String couleur) {
        this.couleur = couleur;
    }

    public String getCouleur() {
        return couleur;
    }

    public String afficher() {
        return "P";
    }
    public void setNouvellePiece(Pieces nouvellePiece) {
        this.couleur = nouvellePiece.getCouleur(); 
    }
    
    public abstract List<int[]> deplacementsPossibles(int x, int y, Grille grille);
    public abstract boolean Bouge(int DeplaX, int DeplaY, int ArrX, int ArrY, Pieces[][] echiquier);

    
}


class Roi extends Pieces {
    private boolean aBouge = false;

    public Roi(String couleur) {
        super(couleur);
    }

    @Override
    public String afficher() {
        return "R";
    }

    @Override
    public boolean Bouge(int DeplaX, int DeplaY, int ArrX, int ArrY, Pieces[][] echiquier) {
        return Math.abs(DeplaX - ArrX) <= 1 && Math.abs(DeplaY - ArrY) <= 1;
    }

    public boolean aBouge() {
        return aBouge;
    }

    public void setBouge(boolean aBouge) {
        this.aBouge = aBouge;
    }
    @Override
    public List<int[]> deplacementsPossibles(int x, int y, Grille grille) {
        List<int[]> deplacements = new ArrayList<>();
        
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
    
        for (int i = 0; i < 8; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
    
            // Vérifier les limites de l'échiquier (indices de 0 à 7)
            if (nx >= 0 && nx < 8 && ny >= 0 && ny < 8) {
                Pieces piece = grille.getPiece(nx, ny);
                if (piece == null || !piece.getCouleur().equals(this.getCouleur())) {
                    deplacements.add(new int[]{nx, ny});
                }
            }
        }
    
        return deplacements;
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

    @Override
    public boolean Bouge(int DeplaX, int DeplaY, int ArrX, int ArrY, Pieces[][] echiquier) {
        // Combiner les déplacements du Fou et de la Tour
        return new Fou(this.getCouleur()).Bouge(DeplaX, DeplaY, ArrX, ArrY, echiquier) ||
               new Tour(this.getCouleur()).Bouge(DeplaX, DeplaY, ArrX, ArrY, echiquier);
    }

    @Override
    public List<int[]> deplacementsPossibles(int x, int y, Grille grille) {
        List<int[]> deplacements = new ArrayList<>();

        // Déplacements de la tour (horizontal et vertical)
        deplacements.addAll(new Tour(this.getCouleur()).deplacementsPossibles(x, y, grille));

        // Déplacements du fou (diagonal)
        deplacements.addAll(new Fou(this.getCouleur()).deplacementsPossibles(x, y, grille));

        return deplacements;
    }
}


class Tour extends Pieces {
    private boolean aBouge = false;

    public Tour(String couleur) {
        super(couleur);
    }

    @Override
    public String afficher() {
        return "T";
    }

    @Override
    public boolean Bouge(int DeplaX, int DeplaY, int ArrX, int ArrY, Pieces[][] echiquier) {
        return DeplaX == ArrX || DeplaY == ArrY;
    }

    public boolean aBouge() {
        return aBouge;
    }

    public void setBouge(boolean aBouge) {
        this.aBouge = aBouge;
    }
    @Override
    public List<int[]> deplacementsPossibles(int x, int y, Grille grille) {
        List<int[]> deplacements = new ArrayList<>();
    
        // Haut
        for (int i = x - 1; i >= 0; i--) {
            Pieces piece = grille.getPiece(i, y);
            if (piece == null) {
                deplacements.add(new int[]{i, y});
            } else {
                if (!piece.getCouleur().equals(this.getCouleur())) {
                    deplacements.add(new int[]{i, y});
                }
                break;
            }
        }
    
        // Bas
        for (int i = x + 1; i < 8; i++) {
            Pieces piece = grille.getPiece(i, y);
            if (piece == null) {
                deplacements.add(new int[]{i, y});
            } else {
                if (!piece.getCouleur().equals(this.getCouleur())) {
                    deplacements.add(new int[]{i, y});
                }
                break;
            }
        }
    
        // Gauche
        for (int j = y - 1; j >= 0; j--) {
            Pieces piece = grille.getPiece(x, j);
            if (piece == null) {
                deplacements.add(new int[]{x, j});
            } else {
                if (!piece.getCouleur().equals(this.getCouleur())) {
                    deplacements.add(new int[]{x, j});
                }
                break;
            }
        }
    
        // Droite
        for (int j = y + 1; j < 8; j++) {
            Pieces piece = grille.getPiece(x, j);
            if (piece == null) {
                deplacements.add(new int[]{x, j});
            } else {
                if (!piece.getCouleur().equals(this.getCouleur())) {
                    deplacements.add(new int[]{x, j});
                }
                break;
            }
        }
    
        return deplacements;
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

    @Override
    public boolean Bouge(int DeplaX, int DeplaY, int ArrX, int ArrY, Pieces[][] echiquier) {
        return Math.abs(DeplaX - ArrX) == Math.abs(DeplaY - ArrY);
    }
    @Override
    public List<int[]> deplacementsPossibles(int x, int y, Grille grille) {
        List<int[]> deplacements = new ArrayList<>();
    
        // Diagonales
        for (int dx = -1; dx <= 1; dx += 2) {
            for (int dy = -1; dy <= 1; dy += 2) {
                for (int i = 1; i < 8; i++) { // Boucle de 1 à 7 pour rester dans les limites de l'échiquier
                    int nx = x + dx * i;
                    int ny = y + dy * i;
                    if (nx >= 0 && nx < 8 && ny >= 0 && ny < 8) {
                        Pieces piece = grille.getPiece(nx, ny);
                        if (piece == null) {
                            deplacements.add(new int[]{nx, ny});
                        } else if (!piece.getCouleur().equals(this.getCouleur())) {
                            deplacements.add(new int[]{nx, ny});
                            break;
                        } else {
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
        }
    
        return deplacements;
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

    @Override
    public boolean Bouge(int DeplaX, int DeplaY, int ArrX, int ArrY, Pieces[][] echiquier) {
        int dx = Math.abs(DeplaX - ArrX);
        int dy = Math.abs(DeplaY - ArrY);
        return (dx == 2 && dy == 1) || (dx == 1 && dy == 2);
    }
    @Override
    public List<int[]> deplacementsPossibles(int x, int y, Grille grille) {
        List<int[]> deplacements = new ArrayList<>();
    
        int[] dx = {-2, -1, 1, 2, 2, 1, -1, -2};
        int[] dy = {1, 2, 2, 1, -1, -2, -2, -1};
    
        for (int i = 0; i < 8; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            // Vérifier les limites de l'échiquier (index 0 à 7)
            if (nx >= 0 && nx < 8 && ny >= 0 && ny < 8) {
                Pieces piece = grille.getPiece(nx, ny);
                // Ajouter la case si elle est vide ou occupée par une pièce ennemie
                if (piece == null || !piece.getCouleur().equals(this.getCouleur())) {
                    deplacements.add(new int[]{nx, ny});
                }
            }
        }
    
        return deplacements;
    }
    
}

class Pion extends Pieces {
    private boolean aAvanceDeuxCases = false;

    public Pion(String couleur) {
        super(couleur);
    }

    @Override
    public String afficher() {
        return "p";
    }

    @Override
    public boolean Bouge(int DeplaX, int DeplaY, int ArrX, int ArrY, Pieces[][] echiquier) {
        int direction = getCouleur().equals("blanc") ? -1 : 1;

        // Mouvement de capture en diagonale
        if (ArrX == DeplaX + direction && Math.abs(ArrY - DeplaY) == 1 && echiquier[ArrX][ArrY] != null) {
            return true;
        }

        // Mouvement normal d'une case sans capture
        if (ArrX == DeplaX + direction && DeplaY == ArrY) {
            return echiquier[ArrX][ArrY] == null;
        }

        // Mouvement initial de deux cases
        if ((getCouleur().equals("blanc") && DeplaX == 6) || (getCouleur().equals("noir") && DeplaX == 1)) {
            if (ArrX == DeplaX + 2 * direction && DeplaY == ArrY) {
                aAvanceDeuxCases = true; // Marque que le pion a avancé de deux cases
                return echiquier[DeplaX + direction][DeplaY] == null && echiquier[ArrX][ArrY] == null;
            }
        }

        return false;
    }

    public boolean isAAvanceDeuxCases() {
        return aAvanceDeuxCases;
    }

    public void resetAAvanceDeuxCases() {
        this.aAvanceDeuxCases = false;
    }
    @Override
    public List<int[]> deplacementsPossibles(int x, int y, Grille grille) {
        List<int[]> deplacements = new ArrayList<>();
        int direction = (this.getCouleur().equals("blanc")) ? -1 : 1; // Les pions blancs avancent vers le haut, les noirs vers le bas
    
        if (x + direction >= 0 && x + direction < 8 && grille.getPiece(x + direction, y) == null) {
            deplacements.add(new int[]{x + direction, y});
        }
    
        if (x + direction >= 0 && x + direction < 8) {
            if (y - 1 >= 0 && grille.getPiece(x + direction, y - 1) != null && !grille.getPiece(x + direction, y - 1).getCouleur().equals(this.getCouleur())) {
                deplacements.add(new int[]{x + direction, y - 1});
            }
            if (y + 1 < 8 && grille.getPiece(x + direction, y + 1) != null && !grille.getPiece(x + direction, y + 1).getCouleur().equals(this.getCouleur())) {
                deplacements.add(new int[]{x + direction, y + 1});
            }
        }
    
        if ((this.getCouleur().equals("blanc") && x == 6) || (this.getCouleur().equals("noir") && x == 1)) {
            if (grille.getPiece(x + 2 * direction, y) == null && grille.getPiece(x + direction, y) == null) {
                deplacements.add(new int[]{x + 2 * direction, y});
            }
        }
    
        return deplacements;
    }
    
}