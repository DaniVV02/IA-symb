package entrainement;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class MagicSquare {

    public static void main(String[] args) {
        int n = 3; // Dimension du carré magique (par exemple, 3x3)
        int sum = (n * (n * n + 1)) / 2; // Somme magique : (n*(n^2+1))/2

        // Création du modèle
        Model model = new Model("Magic Square");

        // Création de la grille de variables
        IntVar[][] grid = new IntVar[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = model.intVar("cell_" + i + "_" + j, 1, n * n);
            }
        }

        // Contraintes de somme pour les lignes
        for (int i = 0; i < n; i++) {
            model.sum(grid[i], "=", sum).post();
        }

        // Contraintes de somme pour les colonnes
        for (int j = 0; j < n; j++) {
            IntVar[] col = new IntVar[n];
            for (int i = 0; i < n; i++) {
                col[i] = grid[i][j];
            }
            model.sum(col, "=", sum).post();
        }

        // Contraintes de somme pour les diagonales
        IntVar[] diag1 = new IntVar[n];
        IntVar[] diag2 = new IntVar[n];
        for (int i = 0; i < n; i++) {
            diag1[i] = grid[i][i];
            diag2[i] = grid[i][n - i - 1];
        }
        model.sum(diag1, "=", sum).post();
        model.sum(diag2, "=", sum).post();

        // Contrainte d'unicité des valeurs dans la grille (toutes les cases doivent être différentes)
        IntVar[] allCells = new IntVar[n * n];
        int index = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                allCells[index++] = grid[i][j];
            }
        }
        model.allDifferent(allCells).post();

        // Résolution
        if (model.getSolver().solve()) {
            System.out.println("Solution found:");
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    System.out.print(grid[i][j].getValue() + "\t");
                }
                System.out.println();
            }
        } else {
            System.out.println("No solution found.");
        }
    }
}

