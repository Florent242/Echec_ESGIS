package Echec_ESGIS;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Initialiser la grille et les pièces
        Grille grille = new Grille();
        grille.PlaceGrille();
        grille.AfficherGrille();

        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Jeu d'échecs en console :)");
        System.out.println("Entrez les coordonnées au format x1 y1 x2 y2 pour déplacer une pièce, ou 'exit' pour quitter.");
        System.out.println("Les Blancs commencent le bas..");


        while (true) {
            System.out.println("Entrez les coordonnées de la pièce à déplacer (x1, y1, x2, y2) :");

            if (scanner.hasNext("exit")) {
                System.out.println("Merci d'avoir joué. À bientôt !");
                break;
            }

            if (!scanner.hasNextInt()) {
                System.out.println("Entrée invalide. Veuillez entrer quatre chiffres pour les coordonnées ou 'exit' pour quitter.");
                scanner.next();
                continue;
            }

            int DeplaX = scanner.nextInt();
            int DeplaY = scanner.nextInt();
            int ArrX = scanner.nextInt();
            int ArrY = scanner.nextInt();

            if (DeplaX < 1 || DeplaX > 8 || DeplaY < 1 || DeplaY > 8 || ArrX < 1 || ArrX > 8 || ArrY < 1 || ArrY > 8) {
                System.out.println("Tu es hors de jeu");
                continue;
            }

            if (grille.deplacePiece(DeplaX, DeplaY, ArrX, ArrY)) {
                grille.AfficherGrille();
            }
        }
        
        scanner.close();
    }
}