# Version 1
---
## Consignes

Dans cette première version, vous devez calculer un itinéraire optimal entre deux villes en n’empruntant qu’un seul type de lignes. On ne prend en compte qu’une seule modalité de transport : tous les tronçons doivent être basés sur le même moyen de transport.

Par exemple, sur le réseau de l’exemple 1, si on souhaite se rendre de A à D et qu’on ne s’intéresse qu’au train comme unique mode de transport, les voyages intégrant un tronçon en avion doivent être exclus : seuls 4 voyages restent éligibles. Parmi eux, on devrait choisir :

- le voyage A-C-B-D si l’on souhaite économiser (e78)
- le voyage A-C-D (par train) si on a une forte conscience écologique (2.6kg CO2e)
- le voyage A-B-D si on est pressé (120 minutes).


L’utilisateur choisit au préalable quel est le critère le plus important pour lui. Il ne peut en sélectionner qu’un seul à la fois.
Les données vous sont fournies sous forme d’un tableau de chaînes de caractères (String[]). Ces données comporteront les informations liées à différentes modalités de transport, qu’il vous faudra filtrer le cas échéant. Les données respectent le format suivant :

```csv
villeDépart;villeArrivée;modalitéTransport;prix(e);pollution(kgCO2e);durée(min)
```

Par exemple, le tableau de chaînes représentant l’exemple 1 serait :

```java
data = new String[]{
"villeA;villeB;Train;60;1.7;80",
"villeB;villeD;Train;22;2.4;40",
"villeA;villeC;Train;42;1.4;50",
"villeB;villeC;Train;14;1.4;60",
"villeC;villeD;Avion;110;150;22",
"villeC;villeD;Train;65;1.2;90"
};
```


Dans cette première version, l’application doit être capable de :


- vérifier la validité des données fournies : elles doivent être complètes (coûts de tous les tronçons) ;

- récupérer les informations pour reconstituer le réseau ;

- filtrer les données sur un moyen de transport spécifique ;

- déterminer si, selon la modalité choisie, il existe un voyage possible entre les villes sélectionnées par l’utilisateur ;

- afficher les meilleurs voyages possibles, ordonnés selon le critère le plus important pour l’utilisateur ;

- exclure des alternatives qui excèdent les bornes définies par l’utilisateur. Par exemple, le critère le plus important est l’impact environnemental (qu’il convient alors de minimiser), à condition que la durée du voyage n’excède pas 180 minutes. Tout voyage possible dépassant cette limite doit alors
être écarté.

- La recherche des meilleurs itinéraires doit se faire par un calcul de plus courts chemins dans un graphe;


## TODO

### java
- Créations des classes ( Voyages, lieu, Platerforme,...).
- Créations des méthodes nécessaires.
-
-
-

### Graphe
- Création d'un graphe à partir de données rentrées dans le programme java
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