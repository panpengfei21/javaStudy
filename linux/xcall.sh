#! /bin/bash

for i in bigData0 bigData1 bigData2
do
    echo -----------$i-------------
    ssh $i "$*"
done