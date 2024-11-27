import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.extension.Tuples;
import org.chocosolver.solver.variables.IntVar;

public class ExpeExoC {

    private static Model lireReseau(BufferedReader in) throws Exception {
        Model model = new Model("Expe");
        int nbVariables = Integer.parseInt(in.readLine());
        int tailleDom = Integer.parseInt(in.readLine());
        IntVar[] var = model.intVarArray("x", nbVariables, 0, tailleDom - 1);
        int nbConstraints = Integer.parseInt(in.readLine());
        for (int k = 1; k <= nbConstraints; k++) {
            String[] chaine = in.readLine().split(";");
            IntVar[] portee = new IntVar[]{var[Integer.parseInt(chaine[0])], var[Integer.parseInt(chaine[1])]};
            int nbTuples = Integer.parseInt(in.readLine());
            Tuples tuples = new Tuples(new int[][]{}, true);
            for (int nb = 1; nb <= nbTuples; nb++) {
                chaine = in.readLine().split(";");
                int[] t = new int[]{Integer.parseInt(chaine[0]), Integer.parseInt(chaine[1])};
                tuples.add(t);
            }
            model.table(portee, tuples).post();
        }
        in.readLine();
        return model;
    }

    public static void main(String[] args) throws Exception {
        String ficName = "csp_170_tuples.txt"; // Changez ce nom en fonction du fichier
        int nbRes = 10; // Nombre de réseaux par benchmark
        int timeOutInSeconds = 10; // Timeout de 10 secondes par réseau

        BufferedReader readFile = new BufferedReader(new FileReader(ficName));
        int satisfiedCount = 0;
        int timeoutCount = 0;
        long totalTime = 0;
        long totalNodes = 0;
        int totalAttempts = 0;
        int solvedNetworks = 0;

        for (int nb = 1; nb <= nbRes; nb++) {
            Model model = lireReseau(readFile);
            if (model == null) {
                System.out.println("Problème de lecture de fichier !");
                return;
            }

            Solver solver = model.getSolver();
            solver.limitTime("10s");

            long startTime = System.nanoTime();
            boolean solved = solver.solve();
            long endTime = System.nanoTime();

            totalAttempts++;
            long elapsedTime = endTime - startTime;

            if (solved) {
                satisfiedCount++;
                solvedNetworks++;
                totalTime += elapsedTime;
                totalNodes += solver.getMeasures().getNodeCount();
            } else {
                timeoutCount++;
            }
        }

        double percentageSatisfied = (double) satisfiedCount / nbRes * 100;
        double percentageTimeouts = (double) timeoutCount / nbRes * 100;

        // Calcul des moyennes consolidées (en éliminant les time-outs)
        double averageTime = (solvedNetworks > 0) ? (totalTime / (double) solvedNetworks) / 1_000_000_000 : 0;
        double averageNodes = (solvedNetworks > 0) ? (totalNodes / (double) solvedNetworks) : 0;

        System.out.println("Pourcentage de réseaux satisfaits : " + percentageSatisfied + "%");
        System.out.println("Nombre de time-outs : " + timeoutCount + " (" + percentageTimeouts + "%)");
        System.out.println("Temps moyen (sans time-outs) : " + averageTime + " secondes");
        System.out.println("Nombre moyen de nœuds (sans time-outs) : " + averageNodes);


        // Extraire le nombre de tuples à partir du nom du fichier
        String[] parts = ficName.split("_");
        String tuplesCount = parts[1]; // "188" est l'élément [1] dans "csp_188_tuples.txt"

        // Sauvegarder les résultats dans un fichier CSV
        FileWriter writer = new FileWriter("resultats_evaluation.csv", true);
        writer.append(tuplesCount + ";" + percentageSatisfied + ";" + percentageTimeouts + ";" + averageTime + ";" + averageNodes + "\n");
        writer.close();
    }
}
