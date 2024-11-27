# TP évaluation

## Exercice A 

Déjà c'est logique qu'on trouve aucune solution pour benchInsat car le nom du fichier indique que c'est insatisfiable.

1. Analyse de benchSatisf.txt :

Paramètres :

Nombre de variables : 10 \
Taille du domaine : 15\
Nombre de contraintes : 10\
Nombre de tuples par contrainte : 80

Interprétation :

Chaque contrainte a 80 tuples, ce qui signifie que beaucoup de combinaisons de valeurs entre les variables sont permises.
En d'autres termes, les contraintes sont moins restrictives, car il y a beaucoup de tuples autorisés (80). Cela laisse plus de possibilités pour trouver une solution qui satisfait toutes les contraintes.
Le réseau de contraintes est donc plus "satisfiable" puisqu'il y a plus de flexibilité pour assigner des valeurs aux variables sans violer les contraintes.

Conclusion : Il est normal de trouver des solutions pour tous les réseaux dans ce fichier, car les contraintes sont assez lâches.

2. Analyse de benchInsat.txt :

Paramètres :

- Nombre de variables : 10
- Taille du domaine : 15
- Nombre de contraintes : 15
- Nombre de tuples par contrainte : 20

Interprétation :

Ici, chaque contrainte n'autorise que 20 tuples, ce qui signifie que peu de combinaisons de valeurs sont permises.
Avec 15 contraintes, le réseau de contraintes est plus dense (plus de contraintes que dans benchSatisf.txt), et chaque contrainte est très restrictive.
En combinant un nombre élevé de contraintes et un nombre limité de tuples permis, il devient très difficile de satisfaire toutes les contraintes simultanément, car il y a moins de possibilités de valeurs pour les variables.
Le réseau devient alors sur-contraint, ce qui explique pourquoi aucune solution n'a été trouvée pour ces réseaux.

Conclusion : Le fait qu'aucun réseau ne soit satisfiable dans ce fichier est attendu, car les contraintes sont beaucoup plus restrictives et il est difficile de trouver des assignations qui satisfont toutes les contraintes simultanément.
En résumé :

Le fichier benchSatisf.txt contient des réseaux de contraintes moins restrictifs, ce qui permet de trouver facilement des solutions.
Le fichier benchInsat.txt contient des réseaux plus contraints, rendant la recherche de solutions presque impossible.

Liens avec la théorie des CSP :

Ces résultats illustrent un concept clé dans la théorie des CSP : le seuil de satisfiabilité.

Lorsque le nombre de contraintes augmente ou que les contraintes deviennent plus strictes (moins de tuples autorisés), le problème a tendance à devenir insatisfiable.
Dans le cas contraire, si les contraintes sont lâches (plus de tuples permis), il est plus probable de trouver une solution.

Cela montre que les paramètres d'un CSP ont un impact direct sur la difficulté du problème.
avec différentes heuristiques de recherche dans Choco Solver pour voir si cela améliore les résultats pour benchInsat.txt.

## Exercice B

5. Densité du benchmark et dureté

Densité du réseau de contraintes

La densité d'un réseau de contraintes est un paramètre important qui décrit combien de contraintes sont appliquées en moyenne entre les variables. Elle peut être calculée comme suit :
Densite = Nombre de contraintes / Nombre de variables


Dans votre cas, vous avez :

Nombre de variables : 30
Nombre de contraintes : 190

La densité se calcule donc comme :
Densite =190/30=6.33


Cela signifie qu'en moyenne, chaque variable participe à environ 6.33 contraintes.
Densité de chaque niveau (par rapport aux tuples)

Les niveaux de dureté sont définis par le nombre de tuples par contrainte. La densité est liée à la manière dont les contraintes sont définies, car un nombre plus élevé de tuples par contrainte signifie que la contrainte sera plus "flexible", augmentant la probabilité de satisfaction des réseaux.

Dans votre cas, le nombre de tuples par contrainte varie de 200 à 170, ce qui influence la dureté des réseaux générés. Un nombre plus élevé de tuples par contrainte rend le problème plus satisfiable (moins dur), tandis qu'un nombre plus faible de tuples augmente la dureté du réseau.
Dureté de chaque niveau (en fonction des tuples)

La dureté d'un réseau de contraintes peut être évaluée en fonction de la probabilité qu'il ait une solution. Un réseau est plus difficile à résoudre lorsqu'il a peu de tuples par contrainte, car les contraintes sont plus strictes. Voici les niveaux de dureté définis par les différentes valeurs de tuples :

200 tuples : Réseau très satisfiable, les contraintes sont moins strictes, donc il est plus facile de trouver une solution.
170 tuples : Réseau plus insatisfiable, les contraintes sont plus strictes et il devient plus difficile de trouver une solution.

Ainsi, pour chaque fichier généré avec différentes valeurs de tuples, vous aurez un réseau dont la dureté augmente à mesure que le nombre de tuples par contrainte diminue.


### Gnuplot 

gnuplot -p plot.gnu
gnuplot -persist plot.gnuplot


### Question 6

Analyse de la courbe :

Forme de la courbe :
    La courbe commence avec un pourcentage de réseaux satisfiables très élevé (proche de 100%) pour une dureté faible (nombre élevé de tuples par contrainte, vers 200).
    Ensuite, il y a une chute rapide du pourcentage de réseaux satisfiables à mesure que la dureté augmente (nombre de tuples diminue).
    La courbe atteint presque 0% de réseaux satisfiables pour une dureté autour de 170.

Transition de phase :
    La transition de phase est bien visible sur votre courbe. Elle se situe dans la zone où la courbe décroît rapidement, probablement entre 180 et 190 tuples par contrainte.
    Cette zone correspond au point critique où le réseau passe d'un état où presque tous les réseaux sont satisfiables à un état où presque aucun réseau n'est satisfiable.
    C'est typique des problèmes de satisfaction de contraintes (CSP) : il y a une zone où un petit changement dans les paramètres (dureté) entraîne un changement drastique dans la satisfiabilité.

Interprétation :

Dureté faible (vers 200 tuples) : Les contraintes sont plus flexibles (plus de tuples autorisés), donc il est facile de trouver des solutions.
Dureté élevée (vers 170 tuples) : Les contraintes sont plus strictes (moins de tuples autorisés), rendant le problème beaucoup plus difficile, voire insoluble.
La transition de phase observée est un phénomène bien connu dans les CSP. Elle montre que pour un certain niveau de dureté, le problème passe soudainement de "facilement résoluble" à "presque impossible à résoudre".


## Exercice C

### Modifications apportées 

Le temps de calcul moyen (averageTime) et le nombre moyen de nœuds (averageNodes) sont calculés en éliminant les time-outs.
Seuls les réseaux qui ont une solution (solvedNetworks) sont pris en compte pour le calcul des moyennes.

Sauvegarde des résultats :

Les résultats sont sauvegardés dans un fichier CSV (resultats_evaluation.csv), avec les colonnes :
- Nom du fichier
- Pourcentage de réseaux satisfaits
- Pourcentage de time-outs
- Temps moyen (en secondes)
- Nombre moyen de nœuds explorés



### Analyse

- Temps moyen (s) :
    On observe une augmentation du temps moyen lorsque le nombre de tuples diminue (de 200 à 170).
    Cela est cohérent, car avec moins de tuples par contrainte, le problème devient plus difficile, nécessitant plus de temps pour trouver une solution ou prouver l'insatisfiabilité.
    L'augmentation n'est pas linéaire ; il y a un point où le temps moyen augmente de manière significative, indiquant probablement une zone de transition de phase autour de 180 à 185 tuples.

- Nombre moyen de nœuds :
    Le nombre de nœuds explorés suit une tendance similaire au temps moyen, avec une augmentation marquée lorsque le nombre de tuples diminue.
    Cela montre que le solveur explore un arbre de recherche plus grand pour les réseaux plus durs, ce qui est logique. Moins il y a de tuples, plus il y a de contraintes et donc plus le solveur doit essayer différentes assignations pour trouver une solution.

- Transition de phase :
    On observe une transition de phase claire autour de 180-185 tuples. C'est la zone où le temps de calcul et le nombre de nœuds explorés augmentent rapidement.
    Avant 185 tuples, les problèmes sont plus faciles et le solveur trouve des solutions plus rapidement avec moins de nœuds explorés.
    Après 180 tuples, les contraintes deviennent plus strictes et il est beaucoup plus difficile de trouver une solution, d'où l'augmentation rapide des mesures.

Conclusion :

La courbe est cohérente avec la théorie des CSP et le phénomène de transition de phase. Lorsque la dureté augmente (moins de tuples par contrainte), le problème passe d'un état facilement résoluble à un état difficile, voire insoluble.
Les mesures montrent bien que le solveur Choco rencontre des difficultés croissantes à mesure que le problème devient plus dur, ce qui se traduit par une augmentation du temps moyen de résolution et du nombre de nœuds explorés.


### Observations :

Transition de phase : La transition de phase est clairement visible autour de 188 à 185 tuples.
Avant 188, le solveur trouve des solutions pour presque tous les réseaux.
À partir de 185 tuples, le pourcentage de time-outs augmente fortement, et le temps moyen ainsi que le nombre moyen de nœuds explorés augmentent significativement.
Pour 182 tuples et moins, il n'y a aucune solution trouvée et 100% de time-outs, avec des temps et des nœuds moyens à 0.0 (ce qui est cohérent).

- Problème de la courbe du temps moyen :

Les valeurs du temps moyen (s) varient entre 0.007 et 1.616, tandis que le nombre moyen de nœuds atteint jusqu'à 39304. La grande différence d'échelle rend la courbe du temps moyen difficile à visualiser.
Solution : Utiliser un axe secondaire (y2) dans gnuplot

Utilisons un axe secondaire (y2) pour tracer le temps moyen séparément du nombre de nœuds :

set datafile separator ";"
set title "Évaluation des performances de Choco Solver"
set xlabel "Nombre de tuples par contrainte"
set ylabel "Nombre moyen de nœuds"
set y2label "Temps moyen (s)"
set ytics nomirror
set y2tics

- Associer le temps moyen à l'axe y2
  plot "resultats_evaluation.csv" using 1:5 with linespoints title "Nombre moyen de nœuds", \
  "resultats_evaluation.csv" using 1:4 axes x1y2 with linespoints title "Temps moyen (s)"

Explication :
- set y2label "Temps moyen (s)" : Ajoute une étiquette pour l'axe secondaire.
-  set y2tics : Active les graduations pour l'axe secondaire.
- using 1:4 axes x1y2 : Utilise l'axe secondaire (y2) pour le temps moyen.
