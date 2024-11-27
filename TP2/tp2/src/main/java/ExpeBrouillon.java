import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.extension.Tuples;
import org.chocosolver.solver.variables.IntVar;

public class ExpeBrouillon {

    private static Model lireReseau(BufferedReader in) throws Exception {
        Model model = new Model("Expe");
        int nbVariables = Integer.parseInt(in.readLine());
        int tailleDom = Integer.parseInt(in.readLine());
        IntVar[] var = model.intVarArray("x", nbVariables, 0, tailleDom - 1);
        int nbConstraints = Integer.parseInt(in.readLine());

        for (int k = 1; k <= nbConstraints; k++) {
            String[] chaine = in.readLine().split(";");
            IntVar[] portee = new IntVar[]{
                    var[Integer.parseInt(chaine[0])],
                    var[Integer.parseInt(chaine[1])]
            };
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
        String ficName = "csp200.txt"; // Exemple
        int nbRes = 10;
        BufferedReader readFile = new BufferedReader(new FileReader(ficName));
        PrintWriter writer = new PrintWriter(new FileWriter("resultats.csv"));
        writer.println("Durete,PourcentageSAT");

        int satisfiableCount;
        int timeoutCount;
        long timeLimit = 10000; // Limite de temps en millisecondes (10 secondes)

        // Boucle pour différentes duretés
        for (int durete = 200; durete >= 20; durete -= 45) {
            satisfiableCount = 0;
            timeoutCount = 0;

            // Boucle pour chaque réseau
            for (int nb = 1; nb <= nbRes; nb++) {
                Model model = lireReseau(readFile);
                Solver solver = model.getSolver();

                long startTime = System.currentTimeMillis();
                boolean foundSolution = false;

                // Contrôle explicite du temps d'exécution
                while (System.currentTimeMillis() - startTime < timeLimit) {
                    if (solver.solve()) {
                        satisfiableCount++;
                        foundSolution = true;
                        break;
                    }
                }

                if (!foundSolution) {
                    timeoutCount++;
                    System.out.println("Réseau " + nb + " : TIMEOUT");
                }
            }

            double satisfiablePercentage = (satisfiableCount / (double) nbRes) * 100;
            writer.println(durete + "," + satisfiablePercentage);
            System.out.println("Dureté : " + durete + ", % de réseaux satisfiables : " + satisfiablePercentage + "%, Time-outs : " + timeoutCount);
        }

        writer.close();
        System.out.println("Résultats écrits dans 'resultats.csv'.");
    }
}
