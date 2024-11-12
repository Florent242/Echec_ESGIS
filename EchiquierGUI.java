package Echec_ESGIS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EchiquierGUI extends JFrame {
    private JButton[][] boutons = new JButton[8][8];
    private Grille grille;
    private int[] dernierClic = null;
    private JButton boutonSelectionne = null;

    public EchiquierGUI(Grille grille) {
        this.grille = grille;
        this.setTitle("Jeu d'échecs");
        this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 8));

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                final int x = i;
                final int y = j;
                JButton bouton = new JButton();
                boutons[i][j] = bouton;
                bouton.setFont(new Font("Arial", Font.PLAIN, 40)); 

                if ((i + j) % 2 == 0) {
                    bouton.setBackground(Color.WHITE);
                } else {
                    bouton.setBackground(new Color(128, 128, 128));
                }

                bouton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        gererClic(x, y);
                    }
                });
                panel.add(bouton);
            }
        }

        this.add(panel);
        actualiserEchiquier();
    }

    public void actualiserEchiquier() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Pieces piece = grille.getPiece(i, j);
                if (piece != null) {
                    boutons[i][j].setText(getAsciiPiece(piece));
                    boutons[i][j].setForeground(piece.getCouleur().equals("blanc") ? Color.BLACK : Color.RED);
                } else {
                    boutons[i][j].setText("");
                }
                boutons[i][j].setBackground((i + j) % 2 == 0 ? Color.BLACK : new Color(128, 128, 128));
            }
        }
    }
    
    private void gererClic(int x, int y) {
        if (dernierClic == null) {
            dernierClic = new int[]{x, y};
            boutonSelectionne = boutons[x][y];
            boutonSelectionne.setBackground(Color.YELLOW);
            afficherDeplacementsPossibles(x, y);
        } else {
            if (boutonSelectionne != null) {
                boutonSelectionne.setBackground((dernierClic[0] + dernierClic[1]) % 2 == 0 ? Color.WHITE : new Color(128, 128, 128));
            }
    
            boolean deplacementValide = grille.deplacePiece(dernierClic[0], dernierClic[1], x, y);
            if (deplacementValide) {
                actualiserEchiquier();
                Pieces piece = grille.getPiece(x, y);
                if (piece instanceof Pion && (x == 0 || x == 7)) {
                    Pieces nouvellePiece = demanderPromotion(piece.getCouleur());
                    grille.getPiece(x, y).setNouvellePiece(nouvellePiece);
                    actualiserEchiquier();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Déplacement invalide");
            }
    
            dernierClic = null;
            boutonSelectionne = null;
            actualiserCouleursCases();
        }    
    }
    
    private void afficherDeplacementsPossibles(int x, int y) {
        List<int[]> deplacements = grille.getDeplacementsPossibles(x, y);
        for (int[] deplacement : deplacements) {
            int dx = deplacement[0];
            int dy = deplacement[1];
            boutons[dx][dy].setBackground(Color.GREEN);
        }
    }
    
    private void actualiserCouleursCases() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boutons[i][j].setBackground((i + j) % 2 == 0 ? Color.WHITE : new Color(128, 128, 128));
            }
        }
    }
    
    private String getAsciiPiece(Pieces piece) {
        String couleur = piece.getCouleur().toLowerCase();
        switch (piece.getClass().getSimpleName()) {
            case "Pion":
                return couleur.equals("blanc") ? "♙" : "♟";
            case "Cavalier":
                return couleur.equals("blanc") ? "♘" : "♞";
            case "Tour":
                return couleur.equals("blanc") ? "♖" : "♜";
            case "Fou":
                return couleur.equals("blanc") ? "♗" : "♝";
            case "Dame":
                return couleur.equals("blanc") ? "♕" : "♛";
            case "Roi":
                return couleur.equals("blanc") ? "♔" : "♚";
            default:
                return ""; 
        }
    }
    
    private Pieces demanderPromotion(String couleur) {
        String[] options = {"Dame", "Tour", "Fou", "Cavalier"};
        int choix = JOptionPane.showOptionDialog(this, 
                    "Choisissez la promotion :", 
                    "Promotion", 
                    JOptionPane.DEFAULT_OPTION, 
                    JOptionPane.INFORMATION_MESSAGE, 
                    null, options, options[0]);
        switch (choix) {
            case 0: return new Dame(couleur);
            case 1: return new Tour(couleur);
            case 2: return new Fou(couleur);
            case 3: return new Cavalier(couleur);
            default: return new Dame(couleur);
        }
    }
    

    public static void main(String[] args) {
        Grille grille = new Grille();
        grille.PlaceGrille();
        
        SwingUtilities.invokeLater(() -> {
            EchiquierGUI gui = new EchiquierGUI(grille);
            gui.setVisible(true);
        });
    }
}
