# Saé 2.01-2.02

## Version 1
---
### Consignes

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


### TODO

#### java
- Créations des classes ( Voyages, lieu, Platerforme,...).
- Créations des méthodes nécessaires.
-
-
-

#### Graphe
- Création d'un graphe à partir de données rentrées dans le programme java
-
-
-
-

### Autres
-
-
-
-
-
    
## Version 2
---
### Consignes

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


### TODO




⇒ commits étiquetés POO-v2 GRAPHE-v2 IHM-V1 à faire avant le 8/06

#### java 
- gas poo [~] exclure des alternatives qui excèdent les bornes définies par l’utilisateur. Par exemple, le critère le plus important est l’impact environnemental (qu’il convient alors de minimiser), à condition que la durée du voyage n’excède pas 180 minutes. Tout voyage possible dépassant cette limite doit alors être écarté.
- gas poo [x] gestion de l’existence d’un voyage possible via un mécanisme d’exception (méthode isLinked)
- gas poo [x] import des données issues de fichiers CSV 
- gab poo [x] affichage d’un voyage par ses points d’intérêt uniquement (lors des changements de modalités
de transport)
- gor poo [x] verification du fichier user ( csv à ajouter dans [verif](./src/version2/Verification.java))

### Graphe
- graphes [x] prise en compte des coûts de correspondance (Version 2)
- gas graphes [x] implémentation d’un scénario illustrant le bon fonctionnement du système

### Autres
- gor poo [x] réalisation d’un diagramme UML
- gor ihm [x] maquettage et prototype basse fidélité
- gab poo [~] PMD

## Version 3
---
### Consignes

Jusqu’à maintenant, l’utilisateur de votre application ne pouvait exprimer d’intérêt que pour un seul critère. Par exemple, il pouvait optimiser soit la durée, soit le prix mais pas les deux. Avec cette nouvelle version, l’utilisateur doit pouvoir exprimer plus finement ses préférences en spécifiant, de manière relative, l’importance des critères les uns par rapport aux autres. La manière d’exprimer cela est laissée libre : à vous de faciliter cela via une IHM ergonomique !

Il nous paraît également important d’avoir l’historique des voyages effectués par l’utilisateur, afin de pouvoir l’exploiter et lui rendre des informations sur sa manière de voyager (selon les centres d’intérêt qu’il a exprimé). Par exemple, pour un écologiste convaincu, on pourrait lui montrer l’évolution des émissions polluantes liées à ses voyages ; pour un utilisateur qui gère un budget, on montrerait l’évolution du prix
de ses voyages.


Dans cette dernière version, l’application doit, en plus des fonctionnalités fournies par les versions précédentes, être capable de :

- adapter le modèle de calcul afin de prendre en compte non pas un critère mais une combinaison des différents critères ;

- proposer une IHM ergonomique et efficace ;

- enregistrer les voyages de l’utilisateur via un mécanisme de sérialisation ;

- exploiter l’historique pour montrer l’évolution de la façon de voyager de l’utilisateur selon les différents coûts (minutes, kg CO2e et e);

### TODO
Semaines 7 (du 10/06) et 8 (du 17/06)
⇒ commits étiquetés POO-v3 GRAPHE-v3 IHM-v2, à faire avant le 21/06. . .
#### java
- gas poo [x] gestion des chemins par transport ( toutes les possibilités)
- gas poo [~] réorganisation des fichiers logiques et externalisation
- gas poo [x] gestion par triple critère (toutes les possibilités)
- gas poo [x] gestion de l’historique par sérialisation binaire

#### Graphe
- gas poo graphes [x]expression des préférences multi-critères
- gas poo graphes [x] implémentation d’un jeu de données et d’un scénario pour illustrer le bon fonctionnement de votre application

#### Interface
- gor gab ihm [x] prototype haute fidélité
- gor gab ihm [-] développement en JavaFX
- gor gab gas poo ihm [-] exploitation de l’historique pour afficher des informations pertinentes