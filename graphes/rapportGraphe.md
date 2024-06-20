SAE S2.02 -- Rapport pour la ressource Graphes
===

Delangue 
Catry
Mille
Groupe E

---

- section Version 1 faite avant le 20/05/2024 (1pt/20 si c'est le cas) ( date modifié en amphi)
- section Version 2 faite avant le 08/06/2024 (1pt/20 si c'est le cas) ( on nous à préciser que le rendre le 08 été "ok")
- section Version 2 faite avant le 20/06/2024 (1pt/20 si c'est le cas) (Aucun mail de votre part ou de qui que ce soit ne m'a été parvenue, si la date a été changé je m'en excuse mais il faut prévenir l'ensemble des étudiants.)

---

Version 1 : un seul moyen de transport
---

### Présentation d'un exemple

**Problème :**

Supposons que l'utilisateur souhaite planifier un voyage entre deux villes, Lille et Paris, en utilisant la plateforme de transport. Les données complètes pour la plateforme incluent plusieurs moyens de transport tels que le train, l'avion et le bus, ainsi que les informations sur les coûts associés à chaque moyen de transport (prix du billet, émissions de CO2 et durée du trajet). De plus, l'utilisateur a des préférences spécifiques, notamment le choix du moyen de transport (par exemple, elle préfère le train), le critère d'optimisation (par exemple, elle veut minimiser le coût du voyage), et elle demande un certain nombre d'itinéraires (par exemple, les 3 itinéraires les plus économiques).

**Solution :**

1. **Itinéraires Possibles :**
   - Train : Le trajet en train de Lille à Paris.
   - Avion : Le trajet en avion de Lille à Paris.
   - Bus : Le trajet en bus de Lille à Paris.

2. **Meilleurs Itinéraires :**
   - Itinéraire 1 (Train) : Cet itinéraire est le meilleur si l'utilisateur préfère voyager en train. Il offre un bon compromis entre le temps de trajet et le coût, avec des émissions de CO2 relativement faibles.
   - Itinéraire 2 (Avion) : Si l'utilisateur est pressé et dispose d'un budget plus important, l'option de l'avion peut être préférable. Cependant, cela peut être moins écologique en raison des émissions de CO2 plus élevées.
   - Itinéraire 3 (Bus) : Si l'utilisateur souhaite minimiser les coûts et est prêt à passer plus de temps en voyage, l'option du bus peut être la meilleure. Cependant, cela peut nécessiter un certain compromis sur le confort et la durée du trajet.

En résumé, la solution du problème consiste à recommander les meilleurs itinéraires en fonction des préférences de l'utilisateur tout en tenant compte des différents moyens de transport disponibles sur la plateforme.


### Modèle pour l'exemple

**Graphe :**

Le graphe modélisant l'exemple ci-dessus peut être représenté de la manière suivante :

```java
  data = new String[]{
      "Lille;Paris;Train;45;1.7;80",         
      "Lille;Paris;Avion;120;2.8;60",         
      "Lille;Paris;Bus;80;1.2;180",          
      "Lille;Marseille;Avion;150;3.5;90",    
      "Lille;Marseille;Train;30;3.5;90",    
      "Paris;Marseille;Train;10;2.2;120",     
      "Paris;Marseille;Avion;180;3.2;70",    
      "Paris;Marseille;Bus;230;1.3;660",      
      "Marseille;Paris;Train;85;2.5;110"      
  };
```

**Solution :**

Les meilleurs itinéraires dans le graphe sont les suivants :
(Lille - Paris ) -> meilleur prix :

BUS : Lille -> Paris : 80 €
TRAIN : Lille -> Marseille -> Paris : 40 €
AVION : Lille -> Paris : 120€



### Modélisation pour la Version 1 dans le cas général

Pour modéliser le problème de recherche d'itinéraire dans un graphe, nous pouvons suivre les étapes suivantes :

    Sommets du Graphe : Chaque sommet du graphe représente une structure d'une ville. Dans notre exemple, nous avons trois villes : Lille, Paris et Marseille et 3 structures possible ( Gare, aéroport et Arrêt de bus).

    Arêtes du Graphe : Les arêtes du graphe représentent les connexions entre les villes/les structures, c'est-à-dire les trajets possibles entre les villes. Chaque arête est pondérée par les données du trajet, telles que le coût, les émissions de CO2 et le temps de trajet.

    Poids des Arêtes : Le poids des arêtes est déterminé par les critères d'optimisation du problème. Dans notre exemple, les poids des arêtes peuvent être le prix du billet, les émissions de CO2 et le temps de trajet.

    Algorithme de Recherche d'Itinéraire : Pour résoudre le problème de recherche d'itinéraire dans le graphe, nous pouvons utiliser l'algorithme de Dijkstra. Cet algorithme permet de trouver le chemin le plus court entre deux sommets dans un graphe pondéré. En spécifiant le sommet de départ, le sommet d'arrivée et les poids des arêtes (prix, émissions de CO2, temps), l'algorithme de Dijkstra peut nous donner le meilleur itinéraire en fonction des critères d'optimisation donnés de plus il nous est donné dans une librairie sous le nom de kpcc.

En résumé, pour résoudre un problème de recherche d'itinéraire, nous construisons un graphe où les sommets représentent les structres des villes, les arêtes représentent les trajets possibles entre les villes/structures avec des poids déterminés par les critères d'optimisation, et nous utilisons l'algorithme de Dijkstra pour trouver le meilleur itinéraire en fonction des préférences de l'utilisateur.

### Implémentation de la Version 1


[Test](../../src/Version1/Test.java) ou bien [Main](../../src/Version1/Main.java) si vous voulez tester vous même, 19/05/2024 4e74e89f0db2af92bc65ef7936b7e8104421efd3 et un [lien vers la page de cette classe sur gitlab qui correspond au bon commit](https://gitlab.univ-lille.fr/sae2.01-2.02/2024/E3/-/commit/4e74e89f0db2af92bc65ef7936b7e8104421efd3).


Version 2 : multimodalité et prise en compte des correspondances
---

### Présentation d'un exemple

Prenons l'exemple d'un utilisateur qui souhaite voyager de Lille à Paris en utilisant plusieurs moyens de transport, avec des correspondances possibles entre chaque mode de transport. La plateforme comprend trois moyens de transport : le train, l'avion et le bus. Les coûts de correspondance entre chaque type de transport sont également pris en compte.

Supposons que l'utilisateur souhaite minimiser le coût total du voyage, en acceptant des correspondances entre les moyens de transport si cela peut réduire le coût global du voyage.

Solution du Problème :

Les itinéraires possibles pour l'utilisateur sont les suivants :

    Voyage en train de Lille à Paris avec une correspondance en bus à Valenciennes.
    Voyage en avion de Lille à Paris sans correspondance.
    Voyage en bus de Lille à Paris sans correspondance.

Les itinéraires optimaux dépendent des préférences de l'utilisateur. Si l'utilisateur est sensible au coût, le premier itinéraire avec une correspondance en bus à Valenciennes peut être optimal car il offre un bon compromis entre le coût et le temps de trajet. Cependant, si l'utilisateur préfère minimiser le temps de trajet et n'est pas limité par le coût, le deuxième itinéraire en avion peut être privilégié. Le troisième itinéraire en bus peut être choisi si l'utilisateur veut minimiser le coût et n'est pas pressé.
il peut aussi choisir un itinéraire qui mélange les 3 et qui fait un compromis entre un petit rejet d'emission de Co2 et un prix bas ou un temps de trajet faible.

### Modèle pour l'exemple


```csv
  départ;arrivee;transport;temps;emission;prix
  Lille;Paris;Train;45;1.7;80
  Lille;Paris;Avion;120;2.8;60
  Lille;Paris;Bus;80;1.2;180
  Lille;Valenciennes;Train;20;0;0
  Valenciennes;Paris;Bus;10;0;0
```
Les meilleurs itinéraires dans le graphe sont les suivants :

    Train de Lille à Valenciennes puis bus de Valenciennes à Paris (meilleur prix)
    Avion de Lille à Paris (meilleur temps)
    Bus de Lille à Paris (meilleur compromis)

### Modélisation pour la Version 2 dans le cas général


Vous pouvez modéliser vous-même n'importe quelle situation en utilisant le fichier [Main](../src/version2/main/Main.java), en modifiant le fichier [data](../res/version2/data/data.csv) que vous nous avez fourni.

Je suis désolé, mais je ne vais pas m'embêter à vous fournir un fichier de test, étant donné que vous nous avez fourni un fichier de données dans les derniers délais. De plus, ce fichier de données n'avait aucun rapport avec ce qui était demandé dans la consigne principale, qui était d'utiliser les fichiers Villes et surtout Correspondance.

Preuves : Il sera donné par une liste de coûts de correspondance similaires à la Table 2.


| Ville        | Correspondance | Entre      | Minutes | kg CO2e | Euro |
|--------------|----------------|------------|---------|---------|------|
| Lille        | Train          | Avion      | 130     | 0.1     | 20   |
| Lille        | Train          | Bus        | 20      | 0       | 0    |
| Lille        | Avion          | Bus        | 120     | 0.1     | 20   |
| Valenciennes | Train          | Bus        | 10      | 0       | 0    |

Dans le pdf.

[Main](../src/version2/main/Main.java), 08/06/2024 : adaptation au csv,  [Lien](https://gitlab.univ-lille.fr/sae2.01-2.02/2024/E3/-/commit/3541578dceef53142d47c98fabbc7d0a1351834c).

Version 3 : optimisation multi-critères
---

### Présentation d'un exemple

Prenons l'exemple d'un utilisateur qui souhaite voyager de Lille à Paris en utilisant plusieurs moyens de transport avec des correspondances possibles entre chaque mode de transport. Les critères d'optimisation incluent le coût, le temps de trajet et les émissions de CO2, et l'utilisateur a exprimé ses préférences d'optimisation. De plus, il y a des bornes maximales pour chaque critère que l'utilisateur ne souhaite pas dépasser.

#### Données

Les données de la plateforme incluent les informations suivantes :

| Départ       | Arrivée      | Transport | Temps (min) | Émissions CO2 (kg) | Prix (€) |
|--------------|--------------|-----------|-------------|--------------------|----------|
| Lille        | Paris        | Train     | 120         | 1.7                | 45       |
| Lille        | Paris        | Avion     | 60          | 2.8                | 120      |
| Lille        | Paris        | Bus       | 180         | 1.2                | 30       |
| Lille        | Valenciennes | Train     | 30          | 0.5                | 15       |
| Valenciennes | Paris        | Bus       | 150         | 0.7                | 25       |

Les coûts de correspondance entre chaque type de transport sont également pris en compte :

| Ville        | Correspondance | Entre     | Minutes | kg CO2e | Euro |
|--------------|----------------|-----------|---------|---------|------|
| Lille        | Train          | Avion     | 20      | 0.1     | 10   |
| Lille        | Train          | Bus       | 15      | 0       | 5    |
| Valenciennes | Train          | Bus       | 10      | 0       | 3    |

Les préférences de l'utilisateur sont les suivantes :

- Optimiser pour le coût, le temps et les émissions de CO2
- Le coût total ne doit pas dépasser 80 €
- Le temps total de trajet ne doit pas dépasser 180 minutes
- Les émissions de CO2 ne doivent pas dépasser 2.0 kg

#### Solution du problème

1. **Itinéraires possibles :**
   - Train de Lille à Paris
   - Avion de Lille à Paris
   - Bus de Lille à Paris
   - Train de Lille à Valenciennes puis bus de Valenciennes à Paris

2. **Meilleurs itinéraires :**
   - **Itinéraire 1 : Train de Lille à Valenciennes puis bus de Valenciennes à Paris**  
     - Coût total : 15 (Train) + 25 (Bus) + 3 (Correspondance) = 43 €
     - Temps total : 30 (Train) + 150 (Bus) + 10 (Correspondance) = 190 minutes
     - Émissions de CO2 : 0.5 (Train) + 0.7 (Bus) + 0 (Correspondance) = 1.2 kg
     - Cet itinéraire est optimal en termes de coût et émissions de CO2 mais dépasse légèrement la borne maximale pour le temps total.

   - **Itinéraire 2 : Bus de Lille à Paris**
     - Coût total : 30 €
     - Temps total : 180 minutes
     - Émissions de CO2 : 1.2 kg
     - Cet itinéraire respecte toutes les bornes et est optimal en termes de coût.

   - **Itinéraire 3 : Train de Lille à Paris**
     - Coût total : 45 €
     - Temps total : 120 minutes
     - Émissions de CO2 : 1.7 kg
     - Cet itinéraire est optimal en termes de temps tout en respectant les bornes de coût et d'émissions de CO2.

En résumé, les itinéraires sont évalués et sélectionnés en fonction des préférences de l'utilisateur et des bornes imposées sur le coût, le temps et les émissions de CO2.

### Modèle pour l'exemple

#### Graphe

Le graphe modélisant l'exemple ci-dessus peut être représenté de la manière suivante :

```csv
  Départ,Arrivée,Transport,Temps (min),Émissions CO2 (kg),Prix (€)
  Lille,Paris,Train,120,1.7,45
  Lille,Paris,Avion,60,2.8,120
  Lille,Paris,Bus,180,1.2,30
  Lille,Valenciennes,Train,30,0.5,15
  Valenciennes,Paris,Bus,150,0.7,25
```

```csv
  Ville,Transport 1,Transport 2,Temps (min),Émissions CO2 (kg),Prix (€)
  Lille,Train,Avion,20,0.1,10
  Lille,Train,Bus,15,0,5
  Valenciennes,Train,Bus,10,0,3
```

#### Solution

Les meilleurs itinéraires dans le graphe sont les suivants :

- **Lille - Valenciennes (Train) - Paris (Bus)**
  - Coût total : 43 €
  - Temps total : 190 minutes
  - Émissions de CO2 : 1.2 kg

- **Lille - Paris (Bus)**
  - Coût total : 30 €
  - Temps total : 180 minutes
  - Émissions de CO2 : 1.2 kg

- **Lille - Paris (Train)**
  - Coût total : 45 €
  - Temps total : 120 minutes
  - Émissions de CO2 : 1.7 kg

### Modélisation pour la Version 3 dans le cas général

Pour modéliser le problème de recherche d'itinéraire avec multi-critères, multi-bornes et multi-modalités de transport dans un graphe, nous suivons les étapes suivantes :

1. **Sommets du Graphe :** Chaque sommet représente une structure d'une ville (gare, aéroport, arrêt de bus).  
2. **Arêtes du Graphe :** Les arêtes représentent les trajets possibles entre les structures des villes, pondérées par les critères d'optimisation (coût, temps, émissions de CO2).  
3. **Poids des Arêtes :** Les poids sont déterminés par les critères d'optimisation du problème (prix, émissions de CO2, temps de trajet) et les coûts de correspondance.  
4. **Algorithme de Recherche d'Itinéraire :** Pour résoudre ce problème, nous pouvons utiliser un algorithme de cheminement multicritère, tel que kpccUltime dans la classe [Algorithme](../src/version3/utils/algorithm/Algorithme.java) modifié pour tenir compte des critères multiples et des bornes maximales.

En utilisant kpccUltime dans la classe [Algorithme](../src/version3/utils/algorithm/Algorithme.java), nous spécifions le sommet de départ, le sommet d'arrivée, les poids des arêtes et les bornes maximales pour chaque critère. L'algorithme nous donne les meilleurs itinéraires respectant les préférences de l'utilisateur.

### Implémentation de la Version 3


Pour tester ce que vous souhaitez : [Main.java](../../src/version3/main/Main.java), 20/06/2024 : [Graphe-v3](https://gitlab.univ-lille.fr/sae2.01-2.02/2024/E3/-/commit/b77ab9cc9bcd243c4d6d9e93d7aadb3fd3268bb8).

---
