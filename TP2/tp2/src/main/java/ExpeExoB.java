import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.extension.Tuples;
import org.chocosolver.solver.variables.IntVar;

public class ExpeExoB {

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
        String[] files = {"csp200.txt", "csp155.txt", "csp110.txt", "csp65.txt", "csp20.txt"};
        int totalNetworks;
        int satisfiableNetworks;
        int timeOutNetworks;  // Pour compter les réseaux où le Time-Out a été atteint

        // Parcourir chaque fichier de benchmark
        for (String fileName : files) {
            totalNetworks = 0;
            satisfiableNetworks = 0;
            timeOutNetworks = 0;  // Initialisation du compteur de Time-Out
            BufferedReader readFile = new BufferedReader(new FileReader(fileName));

            System.out.println("Analyse du fichier : " + fileName);

            // Lire les 10 réseaux du fichier
            for (int i = 0; i < 10; i++) {
                Model model = lireReseau(readFile);
                totalNetworks++;

                // Appliquer une limite de temps de 10 secondes
                model.getSolver().limitTime("10s");

                /* Q1 : Vérifier si le réseau a une solution
                if (model.getSolver().solve()) {
                    satisfiableNetworks++;
                }
                */

                // Vérifier si le réseau a une solution ou si le Time-Out est atteint
                if (model.getSolver().solve()) {
                    satisfiableNetworks++;
                } else if (model.getSolver().isStopCriterionMet()) {
                    // Si le Time-Out est atteint
                    timeOutNetworks++;
                    System.out.println("Time-Out atteint pour le réseau " + i + " dans le fichier " + fileName);
                } else {
                    System.out.println("Le problème n'a pas de solution.");
                }

            }

            // Calculer le pourcentage de réseaux satisfiables
            double percentage = (satisfiableNetworks / (double) totalNetworks) * 100;
            double timeOutPercentage = (timeOutNetworks / (double) totalNetworks) * 100;

            System.out.println("Pourcentage de réseaux satisfiables : " + percentage + "%\n");
            System.out.println("Pourcentage de Time-Outs : " + timeOutPercentage + "%\n");
        }

    }


}
