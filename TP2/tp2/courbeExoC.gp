set datafile separator ";"
set title "Évaluation des performances de Choco Solver"
set xlabel "Nombre de tuples par contrainte"
set ylabel "Mesure"

# Tracer le temps moyen
set y2label "Temps moyen (s)"
set ytics nomirror
set y2tics


# Définir la plage des axes pour le zoom
set xrange [180:190]
set yrange [0:*]   # Laissez le gnuplot déterminer automatiquement la plage de y
set y2range [0:*]

plot "resultats_evaluation.csv" using 1:4 with linespoints title "Temps moyen (s)", \
     "resultats_evaluation.csv" using 1:5 with linespoints title "Nombre moyen de nœuds"
