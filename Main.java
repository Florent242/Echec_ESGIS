package Echec_ESGIS;

// Classe principale pour tester l'affichage de chaque pièce
// public class Main {
//     public static void main(String[] args) {
//         Pieces roi = new Roi("blanc");
//         Pieces dame = new Dame("noir");
//         Pieces tour = new Tour("blanc");
//         Pieces fou = new Fou("noir");
//         Pieces cavalier = new Cavalier("blanc");
//         Pieces pion = new Pion("noir");

//         System.out.println("Roi : " + roi.afficher());
//         System.out.println("Dame : " + dame.afficher());
//         System.out.println("Tour : " + tour.afficher());
//         System.out.println("Fou : " + fou.afficher());
//         System.out.println("Cavalier : " + cavalier.afficher());
//         System.out.println("Pion : " + pion.afficher());

//         //On va voir si la grille s'affiche bien
//         Grille grille = new Grille();
//         grille.PlaceGrille();
//         grille.AfficherGrille();
//     }
// }
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Initialiser la grille et les pièces
        Grille grille = new Grille();
        grille.PlaceGrille();
        grille.AfficherGrille();
        
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("Entrez les coordonnées de la pièce à déplacer (x1, y1, x2, y2) :");
            int DeplaX = scanner.nextInt();
            int DeplaY = scanner.nextInt();
            int ArrX = scanner.nextInt();
            int ArrY = scanner.nextInt();

            if (grille.deplacePiece(DeplaX, DeplaY, ArrX, ArrY)) {
                grille.AfficherGrille(); // Affiche la grille après chaque mouvement
            } else {
                System.out.println("Mouvement invalide, essayez à nouveau.");
            }
        }
    }
}
