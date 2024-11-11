# TP1


## Zebre 

Création d'une contrainte
-> model.table(portée, tuples)
- portée : tableau des variables de la contrainte
- une instance de Tuple (ens. de tuples) \
-> new Tuple(type des valeurs [][], vrai/faux), 
avec le premier [] qui représente chaque tuple, et le deuxième l'arité de la contrainte


En ajoutant les contraintes des phrases 2 à 15, on modélise complètement la Zebra Puzzle. Une fois les contraintes ajoutées, le solveur de Choco Solver peut trouver la solution, c’est-à-dire qui possède le zèbre et qui boit de l'eau.

La modélisation en extension consiste à énumérer explicitement les tuples (c'est-à-dire les combinaisons de valeurs) autorisés pour les variables dans une contrainte.

### Différence entre intension et extension

- En intension : Les contraintes sont définies à l'aide d'expressions logiques (model.arithm(), model.distance(), etc.). On ne donne pas explicitement tous les tuples possibles.
- En extension : Les contraintes sont définies par une liste exhaustive de tuples autorisés ou interdits. Cela convient bien lorsque le domaine des variables est petit, comme ici (1 à 5).

### Analyse de performance

- Variables : 25 variables ont été utilisées, correspondant aux différentes catégories (couleur, nationalité, boisson, animal, cigarettes).
- Contraintes : 64 contraintes ont été postées, couvrant toutes les règles du puzzle.
- Recherche complète : Une solution a été trouvée.
- Backtracks et échecs : Le solveur a dû faire 3 retours en arrière (backtracks) et a rencontré 2 échecs (fails), ce qui signifie que certaines combinaisons ont été testées puis rejetées avant d'arriver à la solution.
- Temps de construction : 0,044 secondes
- Temps de résolution : 0,018 secondes
- Nombre de nœuds explorés : 5 nœuds

Cela indique que le solveur a trouvé une solution de manière très efficace, avec peu de retours en arrière, ce qui est un bon signe pour ce type de problème de satisfaction de contraintes (CSP).


### Qui possède le zèbre et qui boit de l'eau ?

- Le Japonais possède le zèbre (Zebra = 5, Japanese = 5).
- Le Norvégien boit de l'eau (Water = 1, Norwegian = 1).



## nReines 

1. Définir les variables et domaines

Dans le problème des n-reines, nous allons utiliser une variable pour chaque ligne de l'échiquier :

- Variables : Ri représente la colonne où la reine sera placée sur la ligne i.
- Domaines : Chaque variable Ri a un domaine de [0, n-1], car l'échiquier a des colonnes allant de 0 à n-1.

En Java avec Choco Solver, on peut déclarer ces variables ainsi :

int n = 8; // Par exemple, pour un échiquier 8x8
Model model = new Model("n-Reines");
IntVar[] R = model.intVarArray("R", n, 0, n - 1);

Ici, R[i] représente la colonne de la reine sur la ligne i.
2. Contraintes pour interdire qu’elles soient sur la même ligne

En fait, il n'y a aucune contrainte supplémentaire à ajouter pour interdire qu'elles soient sur la même ligne, car chaque Ri représente déjà une ligne distincte. Par conséquent, chaque reine est déjà positionnée sur une ligne différente (ligne i).
3. Contraintes pour interdire qu’elles soient sur la même colonne

Pour que deux reines Ri et Rj ne soient pas sur la même colonne, il faut que :
R[i]≠R[j]

En Choco Solver, on utilise la contrainte allDifferent :

model.allDifferent(R).post();

4. Contraintes pour interdire qu’elles soient sur la même diagonale

Pour éviter que deux reines soient sur la même diagonale, il faut s’assurer que :

- La différence entre leurs colonnes n'est pas égale à la différence entre leurs lignes.
- La somme de leurs colonnes n'est pas égale à la somme de leurs lignes.

En d'autres termes, pour des reines Ri (ligne i) et Rj (ligne j), on doit imposer :
∣R[i]−R[j]∣≠∣i−j∣

5. Implémentation des contraintes de diagonale en Choco Solver

On peut exprimer cela en Java en utilisant les contraintes arithm :

for (int i = 0; i < n; i++) {
for (int j = i + 1; j < n; j++) {
model.arithm(R[i], "!=", R[j].add(i - j).intVar()).post();
model.arithm(R[i], "!=", R[j].sub(i - j).intVar()).post();
}
}

6. Recherche d'une solution pour différents n

Essayons de trouver une solution pour quelques valeurs de n :

Solver solver = model.getSolver();
if (solver.solve()) {
System.out.println("Solution trouvée :");
for (int i = 0; i < n; i++) {
System.out.println("Reine " + i + " est placée en colonne " + R[i].getValue());
}
} else {
System.out.println("Pas de solution trouvée.");
}

Essayer de lancer ce code pour n = 1, 2, 3, 4, 8, 12, et 16.
7. Analyse des résultats

- Pour n = 1, il y a une solution triviale (la seule reine est placée sur l'échiquier).
- Pour n = 2 et n = 3, il n'y a pas de solution, car il est impossible de placer les reines sans qu'elles se menacent.
- Pour n = 4, il y a 2 solutions.
- Pour n = 8, il y a 92 solutions.
- Pour n = 12 et n = 16, le nombre de solutions devient très élevé.

Le problème des n-reines est connu pour avoir un nombre croissant de solutions à mesure que n augmente, et il devient difficile à résoudre pour de très grandes valeurs de n.
Conclusion

Cette approche est similaire à celle utilisée pour le puzzle du zèbre :

- Vous avez défini des variables (IntVar).
- Vous avez imposé des contraintes pour satisfaire les règles du problème (colonnes différentes, diagonales différentes).
- Vous avez cherché une solution avec un solveur de CSP (Choco Solver).

Les concepts restent donc les mêmes, mais le problème est différent : ici, il s'agit d'un problème classique de positionnement, tandis que le puzzle du zèbre est un problème de logique.

## Sudoku

### Modélisation du problème du Sudoku avec Choco Solver

Le Sudoku est un puzzle où une grille de 9×99×9 doit être remplie avec des chiffres de 1 à 9 de telle sorte que :

- Chaque ligne contienne des chiffres différents.
- Chaque colonne contienne des chiffres différents.
- Chaque sous-grille 3×33×3 contienne des chiffres différents.

Nous allons créer une classe Java appelée SudokuSolver qui utilise Choco Solver pour modéliser et résoudre le problème.

Étape 1 : Définition des variables

Nous avons besoin d'une matrice 9×99×9 de variables entières, où chaque variable représente une cellule de la grille, avec un domaine de [1,9][1,9].

Étape 2 : Contraintes pour les lignes

Chaque ligne doit contenir des valeurs différentes. Nous utilisons la contrainte allDifferent pour chaque ligne.

Étape 3 : Contraintes pour les colonnes

Chaque colonne doit contenir des valeurs différentes. Nous utilisons également la contrainte allDifferent pour chaque colonne.

Étape 4 : Contraintes pour les sous-grilles 3×33×3

Pour chaque sous-grille 3×33×3, nous devons imposer la contrainte allDifferent.


### Explication du code

- Modèle : Création d'un modèle pour le Sudoku.
- Variables : Définition d'une matrice 9×99×9 de variables entières avec un domaine [1,9][1,9].
- Contraintes :
    Utilisation de allDifferent pour chaque ligne, colonne et sous-grille 3×33×3.
- Grille d'entrée : Exemple de grille partiellement remplie où 0 représente une cellule vide.
- Résolution : Utilisation de model.getSolver().solve() pour trouver une solution.
- Affichage : Affichage de la solution trouvée.

Résultats et observations

Le solveur trouvera une solution valide pour la grille d’entrée fournie, en respectant les règles du Sudoku. Vous pouvez tester avec différentes grilles d’entrée.


### GRAPH coloring

Explication du code :

Nous définissons un graphe avec 4 sommets et une matrice d'adjacence pour indiquer quelles paires de sommets sont reliées.
Les variables de couleur sont créées, où chaque sommet peut être colorié avec une couleur entre 1 et 3.
Des contraintes sont ajoutées pour interdire que des sommets adjacents aient la même couleur (basé sur la matrice d'adjacence).
Le solveur tente de trouver une solution où tous les sommets ont des couleurs différentes pour les voisins adjacents.

Le problème de coloration de graphe utilise des variables de couleur et des contraintes pour s'assurer que les sommets adjacents n'ont pas la même couleur.

### Magic square

Explication du code :

Nous définissons la taille du carré magique (n=3n=3 dans cet exemple).
Le modèle est créé avec Choco Solver et des variables entières sont utilisées pour représenter chaque case du carré magique.
Les contraintes sont ajoutées :
    Chaque ligne, colonne et diagonale doit avoir la même somme.
    Chaque nombre entre 1 et n2n2 doit être unique.
Le solveur tente de trouver une solution qui satisfait toutes les contraintes.


Le problème du carré magique utilise des contraintes de somme et d'unicité sur une grille de variables pour s'assurer que chaque ligne, colonne et diagonale ait la même somme.

### SAT

Explication du code :

Variables booléennes :
    Nous créons trois variables booléennes x1, x2, et x3à l'aide de la méthode boolVar(). Chaque variable peut prendre la valeur True ou False.
Clauses :
    Clause 1 : (x1∨x2) Nous utilisons la méthode or() pour spécifier que cette clause est vraie si x1 ou x2 est vrai.
    Clause 2 : (¬x1∨x3) Ici, nous utilisons not(x_1) pour obtenir la négation de x1 et la combinons avec x3.
    Clause 3 : (x2∨¬x3) Nous utilisons la même approche pour la négation de x3 avec x2
Résolution :
    Le solveur est utilisé pour tenter de trouver une solution qui satisfasse toutes les clauses en même temps. Si une solution est trouvée, nous affichons les valeurs de x1x1​, x2x2​, et x3x3​. Si aucune solution n'existe, nous affichons un message indiquant qu'il n'y a pas de solution.

Analyse :

Ce modèle correspond à une instance basique du problème SAT. La formule CNF donnée est simple et résolue en une recherche d'affectations des variables. Le problème SAT devient plus complexe lorsque le nombre de variables et de clauses augmente.

Ce code peut être étendu à des instances plus complexes en ajoutant plus de variables et de clauses.

Conclusion :

Le problème SAT est une problématique fondamentale en informatique théorique et en logique, utilisée dans de nombreuses applications comme la vérification de modèles, l'optimisation, et la planification. Le code ci-dessus montre comment utiliser Choco Solver pour résoudre un problème SAT en définissant des variables booléennes et des contraintes sur ces variables. Si vous souhaitez modéliser un problème SAT plus complexe, il suffit d'ajouter davantage de variables et de clauses selon la formule CNF que vous souhaitez tester.