# Charger les données depuis le fichier CSV
set datafile separator ";"

# Définir les labels pour les axes
set xlabel "Dureté (Nombre de tuples par contrainte)"
set ylabel "% de réseaux satisfaits"

# Ajouter un titre au graphique
set title "Transition de phase : % de satisfiabilité en fonction de la dureté"

# Configurer les axes pour afficher les valeurs de manière lisible
set xtics 10
set ytics 10

# Tracer la courbe
plot "resultats.csv" using 1:2 with linespoints title "% de satisfiabilité"