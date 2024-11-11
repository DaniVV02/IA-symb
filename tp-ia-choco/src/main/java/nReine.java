import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class nReine {
    public static void main(String[] args) {
        // Valeurs de n pour lesquelles trouver des solutions
        int[] valeursDeN = {1, 2, 3, 4};

        for (int n : valeursDeN) {
            System.out.println("\nSolutions pour n = " + n);

            // 1)

            // Modèle
            Model model = new Model(n + "-Reines");

            // Variables : chaque reine sur une ligne différente, avec colonne entre 1 et n
            IntVar[] reines = model.intVarArray("R", n, 1, n);

            // Contraintes
            // Contrainte de colonnes (une reine par colonne)
            model.allDifferent(reines).post();

            // Contraintes de diagonales
            for (int i = 0; i < n - 1; i++) {
                for (int j = i + 1; j < n; j++) {
                    // Différences absolues pour les diagonales
                    model.arithm(reines[i], "!=", reines[j], "+", j - i).post(); // Diagonale principale
                    model.arithm(reines[i], "!=", reines[j], "-", j - i).post(); // Diagonale secondaire
                }
            }

            // Résolution
            if (model.getSolver().solve()) {
                System.out.println("Solution trouvée :");
                do {
                    for (int i = 0; i < n; i++) {
                        System.out.print("Reine en ligne " + (i + 1) + " colonne " + reines[i].getValue() + "; ");
                    }
                    System.out.println();
                } while (model.getSolver().solve());
            } else {
                System.out.println("Pas de solution pour n = " + n);
            }

            // Statistiques de résolution
            System.out.println("Nombre de solutions pour n = " + n + ": " + model.getSolver().getSolutionCount());
            model.getSolver().printStatistics();
        }
    }
}
