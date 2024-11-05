import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.extension.Tuples;
import org.chocosolver.solver.variables.IntVar;

public class ZebreIntension {

    public static void main(String[] args) {

        // Création du modele
        Model model = new Model("Zebre");

        // Création des variables
        IntVar blu = model.intVar("Blue", 1, 5);	// blu est une variable entière dont le nom est "Blue" est le domaine [1,5]
        IntVar gre = model.intVar("Green", 1, 5);
        IntVar ivo = model.intVar("Ivory", 1, 5);
        IntVar red = model.intVar("Red", 1, 5);
        IntVar yel = model.intVar("Yellow", 1, 5);

        IntVar eng = model.intVar("English", 1, 5);
        IntVar jap = model.intVar("Japanese", 1, 5);
        IntVar nor = model.intVar("Norwegian", 1, 5);
        IntVar spa = model.intVar("Spanish", 1, 5);
        IntVar ukr = model.intVar("Ukrainian", 1, 5);

        IntVar cof = model.intVar("Coffee", 1, 5);
        IntVar mil = model.intVar("Milk", 1, 5);
        IntVar ora = model.intVar("Orange Juice", 1, 5);
        IntVar tea = model.intVar("Tea", 1, 5);
        IntVar wat = model.intVar("Water", 1, 5);

        IntVar dog = model.intVar("Dog", 1, 5);
        IntVar fox = model.intVar("Fox", 1, 5);
        IntVar hor = model.intVar("Horse", 1, 5);
        IntVar sna = model.intVar("Snail", 1, 5);
        IntVar zeb = model.intVar("Zebra", 1, 5);

        IntVar che = model.intVar("Chesterfield", 1, 5);
        IntVar koo = model.intVar("Kool", 1, 5);
        IntVar luc = model.intVar("Lucky Strike", 1, 5);
        IntVar old = model.intVar("Old Gold", 1, 5);
        IntVar par = model.intVar("Parliament", 1, 5);


        // Contraintes de non-duplication pour chaque catégorie
        model.allDifferent(blu, gre, ivo, red, yel).post();
        model.allDifferent(eng, jap, nor, spa, ukr).post();
        model.allDifferent(cof, mil, ora, tea, wat).post();
        model.allDifferent(dog, fox, hor, sna, zeb).post();
        model.allDifferent(che, koo, luc, old, par).post();


        /************************************************************************
         *                                                                      *
         * Compléter en ajoutant les contraintes modélisant les phrases 2 à 15  *
         *
         ************************************************************************/

        // Phrases 2 à 15 modélisées en contraintes arithmétiques et logiques
        model.arithm(eng, "=", red).post();            // Phrase 2 : Anglais dans la maison rouge
        model.arithm(spa, "=", dog).post();            // Phrase 3 : Espagnol avec le chien
        model.arithm(cof, "=", gre).post();            // Phrase 4 : Café dans la maison verte
        model.arithm(ukr, "=", tea).post();            // Phrase 5 : Ukrainien boit du thé
        model.arithm(gre, "=", ivo, "+", 1).post();    // Phrase 6 : Maison verte à droite de l'ivoire
        model.arithm(old, "=", sna).post();            // Phrase 7 : Old Gold et escargots
        model.arithm(koo, "=", yel).post();            // Phrase 8 : Kools dans la maison jaune
        model.arithm(mil, "=", 3).post();              // Phrase 9 : Lait dans la maison du milieu
        model.arithm(nor, "=", 1).post();              // Phrase 10 : Norvégien dans la première maison
        model.distance(che,fox, "=", 1).post(); // Phrase 11 : Chesterfields et renard
        model.distance(koo,hor, "=", 1).post(); // Phrase 12 : Kools et cheval
        model.arithm(luc, "=", ora).post();            // Phrase 13 : Lucky Strike et jus d'orange
        model.arithm(jap, "=", par).post();            // Phrase 14 : Japonais et Parliament
        model.distance(nor,blu, "=", 1).post(); // Phrase 15 : Norvégien et maison bleue



        // Affichage du réseau de contraintes créé
        System.out.println("*** Réseau Initial ***");
        System.out.println(model);


        // Calcul de la première solution
        if(model.getSolver().solve()) {
            System.out.println("\n\n*** Première solution ***");
            System.out.println(model);
        }


/*
    	// Calcul de toutes les solutions
    	System.out.println("\n\n*** Autres solutions ***");
        while(model.getSolver().solve()) {
            System.out.println("Sol "+ model.getSolver().getSolutionCount()+"\n"+model);
	    }
*/


        // Affichage de l'ensemble des caractéristiques de résolution
        System.out.println("\n\n*** Bilan ***");
        model.getSolver().printStatistics();
    }
}
