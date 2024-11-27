#!/bin/bash

#for tuples in {200..170..-3}
#do
 #   for i in {1..10}
  #  do
   #     ./urbcsp 30 17 190 $tuples 10 > csp_${tuples}_$i.txt
    #done
#done

for i in 200 155 110 65 20
do
  ./urbcsp 10 15 10 $i 10 > csp$i.txt
done

