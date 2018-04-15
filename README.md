# tkom_interpreter

Projekt realizowany w ramach przedmiotu Techniki kompilacji.

W ramach projektu zostanie zaimplementowany interpreter własnego, prostego języka.
Instrukcje zapisane w tym języku będą po znaku wczytywane z pliku wejściowego
do leksera, którego zadaniem będzie przekształcenie wejściowego ciągu znaków
w sekwencję atomów leksykalnych - tokenów. Kolejne tokeny będą przekazywane
parserowi na jego ‘prośbę’, który przeprowadzi analizę składniową grupując je
w struktury składniowe, sprawdzając przy tym czy tworzą one poprawne konstrukcje,
zgodne z gramatyką języka. Kolejnym etapem przetwarzania będzie analiza
semantyczna, zaś końcowym generacja kodu wynikowego i wykonanie instrukcji
zapisanych w pliku wejściowym.
