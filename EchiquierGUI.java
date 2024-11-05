
package Echec_ESGIS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EchiquierGUI extends JFrame {
    private JButton[][] boutons = new JButton[8][8];
    private Grille grille;
    private int[] dernierClic = null;

        public EchiquierGUI(Grille grille) {
        this.grille = grille;
        this.setTitle("Jeu d'échecs");
        this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 8));
         panel.setBackground(new Color(200, 200, 200));   //Couleur de fond gris clair


        //Initialisation des boutons et des couleurs de l'échiquier
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                final int x = i;
                final int y = j;
                JButton bouton = new JButton();
                boutons[i][j] = bouton;
                bouton.setFont(new Font("Arial", Font.PLAIN, 20));

                   // Définir les couleurs de cases (alternance)
                if ((i + j) % 2 == 0) {
                      bouton.setBackground(Color.WHITE);   //Case claire
                } else {
                      bouton.setBackground(new Color(128, 128, 128));   //Case foncée
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




  //      Méthode pour mettre à jour l'affichage de l'échiquier
    public void actualiserEchiquier() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Pieces piece = grille.getPiece(i + 1, j + 1); //  Utilise 1 à 8 pour Grille
                if (piece != null) {
                    boutons[i][j].setText(piece.afficher());
                    boutons[i][j].setForeground(piece.getCouleur().equals("blanc") ? Color.BLACK : Color.RED);
                } else {
                    boutons[i][j].setText("");
                }
            }
        }
    }
    

//        Gère les clics pour sélectionner et déplacer les pièces
    private void gererClic(int x, int y) {
        if (dernierClic == null) { 
              dernierClic = new int[]{x + 1, y + 1}; //  Conversion pour la grille
        } else {
               // Déplacer la pièce
            if (grille.deplacePiece(dernierClic[0], dernierClic[1], x + 1, y + 1)) {
                actualiserEchiquier();
            } else {
                JOptionPane.showMessageDialog(this, "Déplacement invalide");
            }
              dernierClic = null;  // Réinitialiser après chaque déplacement
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
