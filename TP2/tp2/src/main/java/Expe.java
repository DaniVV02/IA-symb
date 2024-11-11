import java.io.BufferedReader;
import java.io.FileReader;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.extension.Tuples;
import org.chocosolver.solver.variables.IntVar;

public class Expe {

    private static Model lireReseau(BufferedReader in) throws Exception{
        Model model = new Model("Expe");
        int nbVariables = Integer.parseInt(in.readLine());				// le nombre de variables
        int tailleDom = Integer.parseInt(in.readLine());				// la valeur max des domaines
        IntVar []var = model.intVarArray("x",nbVariables,0,tailleDom-1);
        int nbConstraints = Integer.parseInt(in.readLine());			// le nombre de contraintes binaires
        for(int k=1;k<=nbConstraints;k++) {
            String chaine[] = in.readLine().split(";");
            IntVar portee[] = new IntVar[]{var[Integer.parseInt(chaine[0])],var[Integer.parseInt(chaine[1])]};
            int nbTuples = Integer.parseInt(in.readLine());				// le nombre de tuples
            Tuples tuples = new Tuples(new int[][]{},true);
            for(int nb=1;nb<=nbTuples;nb++) {
                chaine = in.readLine().split(";");
                int t[] = new int[]{Integer.parseInt(chaine[0]), Integer.parseInt(chaine[1])};
                tuples.add(t);
            }
            model.table(portee,tuples).post();
        }
        in.readLine();
        return model;
    }


    public static void main(String[] args) throws Exception{
        String ficName = "bench.txt";
        String ficName2 = "benchSatisf.txt";
        String ficName3 = "benchInsat.txt";

        int nbRes=3;

        BufferedReader readFile = new BufferedReader(new FileReader(ficName));
        BufferedReader readFile2 = new BufferedReader(new FileReader(ficName2));
        BufferedReader readFile3 = new BufferedReader(new FileReader(ficName3));
        int nbSol =0;
        int nbSol1 = 0;
        int nbSol2=0;
        for(int nb=1 ; nb<=nbRes; nb++) {
            Model model=lireReseau(readFile);
            Model model2=lireReseau(readFile2);
            Model model3=lireReseau(readFile3);
            if(model==null || model2==null || model3==null) {
                System.out.println("Problème de lecture de fichier !\n");
                return;
            }
            System.out.println("Bench : Réseau lu "+nb+" :\n"+model+"\n\n");
            if (model.getSolver().solve()) {
                nbSol1++;
                System.out.println("Nombre de solutions " + model.getSolver().getSolutionCount());

            }
            System.out.println("---------------------------------");
            System.out.println("Bench Satisf : Réseau lu "+nb+" :\n"+model2+"\n\n" );
            if (model2.getSolver().solve()) {
                nbSol++;
                System.out.println("Nombre de solutions : " + model2.getSolver().getSolutionCount());

            }
            System.out.println("---------------------------------");
            System.out.println("Bench Insat : Réseau lu "+nb+" :\n"+model3+"\n\n" );
            if (model3.getSolver().solve()) {
                nbSol2++;
                System.out.println("Nombre de solutions : " + model3.getSolver().getSolutionCount());

            }

        }
        System.out.println("\n Le nombre de réseaux satisfiables pour bench est : "+ nbSol1);

        System.out.println("\n Le nombre de réseaux satisfiables pour benchSatisf est : "+ nbSol);

        System.out.println("\n Le nombre de réseaux satisfiables pour benchInsat est : "+ nbSol2);
        return;
    }

}
