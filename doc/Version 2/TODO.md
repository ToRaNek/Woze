# Version 2
---
## Consignes

Dans cette seconde version, la multi-modalité devient possible. Les voyages peuvent inclure des changements de moyens de transport : l’utilisateur accepte de faire une première partie en train, puis la suite
en bus par exemple.


Chaque changement de type de transport induit un coût supplémentaire, qui devra être intégré au calcul d’itinéraire. Ce coût de correspondance dépend de la ville où a lieu le changement de moyen de transport, ainsi que des deux types de transport concernés. Il sera donné par une liste de coûts de correspondance similaires à la Table 2.


| Ville        | Correspondance | Entre      | Minutes | kg CO2e | Euro |
|--------------|----------------|------------|---------|---------|------|
| Lille        | Train          | Avion      | 130     | 0.1     | 20   |
| Lille        | Train          | Bus        | 20      | 0       | 0    |
| Lille        | Avion          | Bus        | 120     | 0.1     | 20   |
| Valenciennes | Train          | Bus        | 10      | 0       | 0    |


Table 2 – Exemple de tableau de coûts de correspondance. Par exemple, 130 est le temps en minutes nécessaire pour faire une correspondance entre le train et l’avion dans le ville de Lille.


Le critère important aux yeux de l’utilisateur reste toujours unique. Il cherchera toujours à optimiser soit la durée, soit les émissions polluantes, soit le prix, toujours dans des limites à respecter comme pour
la première version. Ainsi, vous devez adapter le graphe qui modélise le problème pour permettre la prise en compte du coût de correspondance dans le calcul des meilleurs itinéraires.


Les données vous sont fournies dans un fichier CSV suivant un format similaire au Tableau 2, dont voici la représentation en CSV pour les deux premières lignes :

```csv
Lille;Train;Avion;130;0.1;20
Lille;Train;Bus;20;0;0
```

Dans cette version, en plus des fonctionnalités fournies par les versions précédentes (qu’il est parfois nécessaire d’adapter), l’application doit être capable de :

- signaler les problèmes (de validité de données, d’existence de chemin, d’acceptabilité de propositions. . .) via un mécanisme d’exception ;


- filtrer l’affichage d’une solution pour n’afficher que les points d’intérêt. En effet, inutile de citer tous les arrêts que va faire le train si l’utilisateur reste dedans. Il conviendra de n’afficher que le départ, l’arrivée et les lieux où il y a un changement dans la modalité de transport;


## TODO

### java 
-
-
-
-
-

## Graphe
-
-
-
-
-

## Autres
-
-
-
-
-
