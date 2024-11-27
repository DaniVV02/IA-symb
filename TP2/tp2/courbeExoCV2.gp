set datafile separator ";"
set title "Évaluation des performances de Choco Solver"
set xlabel "Nombre de tuples par contrainte"
set ylabel "Nombre moyen de nœuds"
set y2label "Temps moyen (s)"
set ytics nomirror
set y2tics

# Associer le temps moyen à l'axe y2
plot "resultats_evaluation.csv" using 1:5 with linespoints title "Nombre moyen de nœuds", \
     "resultats_evaluation.csv" using 1:4 axes x1y2 with linespoints title "Temps moyen (s)"
