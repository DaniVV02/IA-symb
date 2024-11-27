import java.io.BufferedReader;
import java.io.FileReader;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.extension.Tuples;
import org.chocosolver.solver.variables.IntVar;

public class ExpeExoA {

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

        in.readLine(); // Ligne vide après chaque réseau
        return model;
    }

    public static void main(String[] args) throws Exception {
        String[] files = {"benchSatisf.txt", "benchInsat.txt"};
        int nbRes = 3;

        for (String ficName : files) {
            BufferedReader readFile = new BufferedReader(new FileReader(ficName));
            int satisfiableCount = 0;

            System.out.println("Lecture du fichier : " + ficName);
            for (int nb = 1; nb <= nbRes; nb++) {
                Model model = lireReseau(readFile);
                if (model == null) {
                    System.out.println("Problème de lecture de fichier !");
                    return;
                }

                // Résolution
                if (model.getSolver().solve()) {
                    satisfiableCount++;
                    System.out.println("Réseau " + nb + " : Solution trouvée.");
                } else {
                    System.out.println("Réseau " + nb + " : Pas de solution.");
                }
            }

            System.out.println("Nombre de réseaux satisfaisables dans " + ficName + " : " + satisfiableCount + "/" + nbRes);
            readFile.close();
        }
    }
}
