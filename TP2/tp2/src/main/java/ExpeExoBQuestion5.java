import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.extension.Tuples;
import org.chocosolver.solver.variables.IntVar;

public class ExpeExoBQuestion5 {

    private static Model lireReseau(BufferedReader in) throws Exception {
        Model model = new Model("Expe");
        int nbVariables = Integer.parseInt(in.readLine());
        int tailleDom = Integer.parseInt(in.readLine());
        IntVar[] var = model.intVarArray("x", nbVariables, 0, tailleDom - 1);
        int nbConstraints = Integer.parseInt(in.readLine());
        // Calcul de la densité du réseau
        double densite = (double) nbConstraints / nbVariables;
        System.out.println("Densité du réseau : " + densite); // Afficher la densité du réseau
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
        String ficName = "csp_188_tuples.txt"; // Changez ce nom en fonction du fichier généré
        int nbRes = 10; // Nombre de réseaux par benchmark
        int timeOutInSeconds = 10; // Timeout de 10 secondes par réseau

        BufferedReader readFile = new BufferedReader(new FileReader(ficName));
        int satisfiedCount = 0; // Nombre de réseaux satisfaits
        int timeoutCount = 0;   // Nombre de time-outs


        int timeOutCount = 0;
        long totalTime = 0;
        long totalNodes = 0;
        int totalAttempts = 0;

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
            totalAttempts++;

            long endTime = System.nanoTime();
            long elapsedTime = endTime - startTime;
            totalTime += elapsedTime;

            totalNodes += solver.getMeasures().getNodeCount();

            if (solved) {
                satisfiedCount++;
            } else {
                timeoutCount++;
            }
        }

        double percentageSatisfied = (double) satisfiedCount / nbRes * 100;
        double percentageTimeouts = (double) timeoutCount / nbRes * 100;

        System.out.println("Pourcentage de réseaux satisfaits : " + percentageSatisfied + "%");

        double tempsMoyen = (totalAttempts - timeOutCount) > 0 ? (totalTime / (double) (totalAttempts - timeOutCount)) / 1_000_000_000 : 0;

        System.out.println("Nombre de time-outs : " + timeoutCount + " (" + "temps moyen : "+ tempsMoyen + "; " + percentageTimeouts+ "%)");


        /* Pas sûr : la dureté en fonction du pourcentage de satisfiabilité
        String durete;
        if (percentageSatisfied > 90) {
            durete = "Faible";
        } else if (percentageSatisfied >= 60) {
            durete = "Moyenne";
        } else {
            durete = "Élevée";
        }
         */


        // Sauvegarder les résultats dans un fichier CSV
        FileWriter writer = new FileWriter("resultats.csv", true);
        writer.append(ficName + ";" + percentageSatisfied + ";" + tempsMoyen + "\n");
        writer.close();
    }
}

