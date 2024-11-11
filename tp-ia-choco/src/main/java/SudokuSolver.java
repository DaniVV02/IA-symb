import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class SudokuSolver {

    public static void main(String[] args) {
        // Modèle
        Model model = new Model("Sudoku");

        // Définition des variables : une matrice 9x9 avec des valeurs de 1 à 9
        IntVar[][] grid = new IntVar[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                // création de chaque cellule
                grid[i][j] = model.intVar("cell(" + i + "," + j + ")", 1, 9);
            }
        }

        // Contraintes pour les lignes
        for (int i = 0; i < 9; i++) {
            model.allDifferent(grid[i]).post();
        }

        // Contraintes pour les colonnes
        for (int j = 0; j < 9; j++) {
            IntVar[] column = new IntVar[9];
            for (int i = 0; i < 9; i++) {
                column[i] = grid[i][j];
            }
            model.allDifferent(column).post();
        }

        // Contraintes pour les sous-grilles 3x3
        for (int blockRow = 0; blockRow < 3; blockRow++) {
            for (int blockCol = 0; blockCol < 3; blockCol++) {
                IntVar[] block = new IntVar[9];
                int idx = 0;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        block[idx++] = grid[blockRow * 3 + i][blockCol * 3 + j];
                    }
                }
                model.allDifferent(block).post();
            }
        }

        // Exemple de grille partiellement remplie
        int[][] inputGrid = {
                {5, 3, 0, 0, 7, 0, 0, 0, 0},
                {6, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 9, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 3},
                {4, 0, 0, 8, 0, 3, 0, 0, 1},
                {7, 0, 0, 0, 2, 0, 0, 0, 6},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };

        // Appliquer les valeurs initiales de la grille
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (inputGrid[i][j] != 0) {
                    model.arithm(grid[i][j], "=", inputGrid[i][j]).post();
                }
            }
        }

        /* Résolution
        if (model.getSolver().solve()) {
            // Affichage de la solution
            System.out.println("Solution trouvée :");
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    System.out.print(grid[i][j].getValue() + " ");
                }
                System.out.println();
            }
        } else {
            System.out.println("Pas de solution trouvée pour cette grille.");
        }
        */

        /* ou aussi pour toutes les solutions */

        int solutionCount = 0;
        while (model.getSolver().solve()) {
            solutionCount++;
            System.out.println("Solution " + solutionCount + " :");
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    System.out.print(grid[i][j].getValue() + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
        if (solutionCount == 0) {
            System.out.println("Pas de solution trouvée pour cette grille.");
        }


        // Statistiques
        model.getSolver().printStatistics();
    }
}
