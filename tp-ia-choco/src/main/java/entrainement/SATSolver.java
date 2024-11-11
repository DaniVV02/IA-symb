package entrainement;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.BoolVar;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.constraints.Constraint;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.constraints.Constraint;

public class SATSolver {

    public static void main(String[] args) {
        // Création du modèle
        Model model = new Model("Boolean Satisfiability Problem");

        // Création des variables booléennes (True ou False)
        BoolVar x1 = model.boolVar("x1");
        BoolVar x2 = model.boolVar("x2");
        BoolVar x3 = model.boolVar("x3");

        // Créer une nouvelle variable pour la négation de x1
        BoolVar not_x1 = model.boolVar("not_x1");
        model.ifThen(model.arithm(x1, "=", 0), model.arithm(not_x1, "=", 1)); // Si x1 = 0, alors not_x1 = 1

        // Clause 1: (x1 OR x2)
        Constraint clause1 = model.or(x1, x2);
        model.post(clause1);

        // Clause 2: (NOT x1 OR x3) => (not_x1 OR x3)
        Constraint clause2 = model.or(not_x1, x3);
        model.post(clause2);

        // Clause 3: (x2 OR NOT x3) => (x2 OR not_x3)
        BoolVar not_x3 = model.boolVar("not_x3");
        model.ifThen(model.arithm(x3, "=", 0), model.arithm(not_x3, "=", 1)); // Si x3 = 0, alors not_x3 = 1
        Constraint clause3 = model.or(x2, not_x3);
        model.post(clause3);

        // Résolution
        if (model.getSolver().solve()) {
            System.out.println("Solution found:");
            System.out.println("x1 = " + x1.getValue());
            System.out.println("x2 = " + x2.getValue());
            System.out.println("x3 = " + x3.getValue());
        } else {
            System.out.println("No solution found.");
        }
    }
}



