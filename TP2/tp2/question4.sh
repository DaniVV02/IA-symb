#!/bin/bash

# Paramètres constants
nbVariables=30
tailleDomaine=17
nbConstraints=190
nbReseaux=10

# Faire varier le nombre de tuples (de 200 à 170 par pas de 3)
for i in $(seq 200 -3 170)
do
  # Générer le fichier de benchmark pour chaque valeur de "i"
  echo "Génération du benchmark avec $i tuples par contrainte..."

  # Exécuter le générateur urbcsp pour chaque nombre de tuples et rediriger la sortie vers un fichier
  ./urbcsp $nbVariables $tailleDomaine $nbConstraints $i $nbReseaux > "csp_${i}_tuples.txt"

  # Afficher un message de progression
  echo "Fichier csp_${i}_tuples.txt généré."
done
