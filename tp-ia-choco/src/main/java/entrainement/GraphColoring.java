package entrainement;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class GraphColoring {

    public static void main(String[] args) {
        int numVertices = 4; // Nombre de sommets
        int numColors = 3;   // Nombre de couleurs disponibles

        // Création du modèle
        Model model = new Model("Graph Coloring");

        // Création des variables de couleurs pour chaque sommet
        IntVar[] colors = new IntVar[numVertices];
        for (int i = 0; i < numVertices; i++) {
            colors[i] = model.intVar("color_" + i, 1, numColors);
        }

        // Matrice d'adjacence du graphe (exemple avec 4 sommets et des arêtes)
        int[][] adjacencyMatrix = {
                {0, 1, 1, 0},
                {1, 0, 1, 1},
                {1, 1, 0, 1},
                {0, 1, 1, 0}
        };

        // Ajouter des contraintes de non-coloriage des voisins
        for (int i = 0; i < numVertices; i++) {
            for (int j = i + 1; j < numVertices; j++) {
                if (adjacencyMatrix[i][j] == 1) {
                    model.arithm(colors[i], "!=", colors[j]).post();
                }
            }
        }

        // Résolution
        if (model.getSolver().solve()) {
            System.out.println("Solution found:");
            for (int i = 0; i < numVertices; i++) {
                System.out.println("Vertex " + i + " is colored with color " + colors[i].getValue());
            }
        } else {
            System.out.println("No solution found.");
        }
    }
}
