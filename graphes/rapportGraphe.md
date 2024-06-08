Votre rapport doit suivre le plan donné dans ce document.

Contraintes à respecter pour le rapport

- [ ] format: Markdown qu'on peut lire sur gitlab, ou pdf, ou html
- [ ] rapport dans un répertoire `graphes` à la racine du dépôt git
- [ ] rapport prêt le 21/06/2024; aucun délai supplémentaire ne sera accordé quelle que soit la raison donnée. Concrètement, on va récupérer la dernier daté au plus tard le 21/06/2024 et on ne verra même pas de version ultérieure du rapport, si elles existent. Minuit et une minute du 22/06/2024 sera trop tard
- [ ] respecte le plan donné ci-dessous
- [ ] garder les explications *en italique* jusque la fin pour s'y référer en écrivant le rapport
- [ ] supprimer les explications *en italique* juste avant de rendre la version finale du rapport
- [ ] le rapport est un texte **rédigé** avec des phrases intelligibles (on ne se contente pas de répondre laconiquement aux questions posées)

Idéalement, le rapport est rédigé au fur et à mesure avec le calendrier donné dans le sujet:

- section Version 1 faite avant le 20/05/2024 (1pt/20 si c'est le cas) ( date modifié en amphi)
- section Version 2 faite avant le 08/06/2024 (1pt/20 si c'est le cas)

Finalement, l'utilisation d'un outils de génération de langage est autorisées, à condition de le faire intelligemment. En particulier, veillez à:
- avoir un rapport cohérent avec un style cohérent sur la totalité du document (niveau de langage, richesse du vocabulaire, termes utilisés, verbosité, ...)
- un rapport trop verbeux est fastidieux à lire. Si vous utilisez un outil pour faire du texte verbeux inutile, on utilisera un outil pour en faire un résumé et on corrigera uniquement le résumé
- les outils de génération insèrent parfois des phrases ne faisant pas partie du texte, mais qui s'adressent à l'interlocuteur (par exemple, pour vous informer que la limite de 2000 tokens est atteinte). La présence de telles phrases dans le rapport indique que vous n'avez pas relu et sera lourdement pénalisée.


**Début du rapport** Tout ce qui précède sera enlevé pour la version finale

------------------------------------------

SAE S2.02 -- Rapport pour la ressource Graphes
===

*Noms des auteurs, groupe*
Delangue 
Catry
Mille
Groupe E

Version 1 : un seul moyen de transport
---

*Cette section traite uniquement de la Version 1 du projet.*


### Présentation d'un exemple

*Présenter un exemple concret de problème (données complètes pour la plateforme avec tous les moyens de transport, préférences de l'utilisatrices qui comprennent le moyen de transport choisi, le critère d'optimisation, et nombre d'itinéraires demandés).*
*Donner la solution du problème du point de vue de l'utilisatrice, càd quels sont les itinéraires possibles, quels sont les meilleurs itinéraires et pourquoi.*
*Pour l'instant on ne parle pas de graphes; on peut éventuellement faire des schémas.*

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

*Donner le graphe modélisant l'exemple ci-dessus.*
*Donner la solution du problème (càd les meilleurs itinéraires) en tant que chemins dans le graphe.*

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

*Expliquer de manière abstraite comment, étant donné un problème de recherche d'itinéraire (plateforme avec tous types de lignes, moyen de transport choisi, critère d'optimisation, nombre d'itinéraires demandés) on peut construire un graphe permettant de résoudre le problème de recherche d'itinéraire. C'est à dire:*
- *quels sont les sommets du graphe par rapport aux données du problème*, 
- *quelles sont ses arêtes, par rapport aux données du problème*, 
- *comment sont définis les poids des arêtes*,
- *quel algorithme sur les graphes permet de résoudre le problème d'itinéraire (nom de l'algorithme, arguments).*

*Utiliser un vocabulaire précis sur les graphes.*

Pour modéliser le problème de recherche d'itinéraire dans un graphe, nous pouvons suivre les étapes suivantes :

    Sommets du Graphe : Chaque sommet du graphe représente une structure d'une ville. Dans notre exemple, nous avons trois villes : Lille, Paris et Marseille et 3 structures possible ( Gare, aéroport et Arrêt de bus).

    Arêtes du Graphe : Les arêtes du graphe représentent les connexions entre les villes/les structures, c'est-à-dire les trajets possibles entre les villes. Chaque arête est pondérée par les données du trajet, telles que le coût, les émissions de CO2 et le temps de trajet.

    Poids des Arêtes : Le poids des arêtes est déterminé par les critères d'optimisation du problème. Dans notre exemple, les poids des arêtes peuvent être le prix du billet, les émissions de CO2 et le temps de trajet.

    Algorithme de Recherche d'Itinéraire : Pour résoudre le problème de recherche d'itinéraire dans le graphe, nous pouvons utiliser l'algorithme de Dijkstra. Cet algorithme permet de trouver le chemin le plus court entre deux sommets dans un graphe pondéré. En spécifiant le sommet de départ, le sommet d'arrivée et les poids des arêtes (prix, émissions de CO2, temps), l'algorithme de Dijkstra peut nous donner le meilleur itinéraire en fonction des critères d'optimisation donnés de plus il nous est donné dans une librairie sous le nom de kpcc.

En résumé, pour résoudre un problème de recherche d'itinéraire, nous construisons un graphe où les sommets représentent les structres des villes, les arêtes représentent les trajets possibles entre les villes/structures avec des poids déterminés par les critères d'optimisation, et nous utilisons l'algorithme de Dijkstra pour trouver le meilleur itinéraire en fonction des préférences de l'utilisateur.

### Implémentation de la Version 1

*Écrire une classe de test qui reprend l'exemple, définit toutes les données de la plateforme, construit le graphe et calcule la solution.*
*Votre classe peut utiliser des assertions (test unitaire) ou bien afficher la solution.*
*Donner ici le **[Test](../../src/Version1/Test.java)** ou bien **[Main](../../src/Version1/Main.java)** si vous voulez tester vous même, **19/05/2024 4e74e89f0db2af92bc65ef7936b7e8104421efd3 ** et un **[lien vers la page de cette classe sur gitlab qui correspond au bon commit](https://gitlab.univ-lille.fr/sae2.01-2.02/2024/E3/-/commit/4e74e89f0db2af92bc65ef7936b7e8104421efd3)**.

*On insiste sur l'importance de spécifier le commit. En effet, quand vous commencerez la Version 2, le code utilisé pour le test de la Version 1 sera modifié. Il se peut que vous n'ayez pas le temps de finaliser la Version 2 et vous retrouver avec un code qui ne marche pas même pour la Version 1. C'est pourquoi il est important de rédiger le rapport au fur et à mesure et de donner ici un lien vers la version de votre code qui marche pour la Version 1 du projet.*


Version 2 : multimodalité et prise en compte des correspondances
---

*Cette section explique la solution pour la Version 2 du projet.*

### Présentation d'un exemple

*Présenter un exemple concret (plateforme, couts de correspondance, critère d'optimalité).*
*Donner la solution du problème du point de vue de l'utilisatrice (quels sont les itinéraires possibles, lesquels sont optimaux et pourquoi).*
*Il est possible d'utiliser le même exemple que pour la Version 1 ou le modifier si pertinent.*

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

*Donner le graphe modélisant l'exemple ci-dessus.*
*Donner la solution du problème (càd les meilleurs itinéraires) en tant que chemins dans le graphe.*

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

*Mêmes questions que pour la section correspondante de la Version 1, mais cette fois-ci les données d'entrée contiennent aussi des couts de correspondance.*
*Vous pouvez expliquer l'entièreté de la solution pour la Version 2, ou bien indiquer **clairement** les différences par rapport à la solution proposée pour la Version 1.*

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

*Donner ici le [**nom complet de la classe**], **la date et l'identifiant du commit à regarder** et un **lien vers la page de cette classe sur gitlab qui correspond au bon commit***.

[Main](../src/version2/main/Main.java), 08/06/2024 : adaptation au csv,  [Lien](https://gitlab.univ-lille.fr/sae2.01-2.02/2024/E3/-/commit/3541578dceef53142d47c98fabbc7d0a1351834c).

Version 3 : optimisation multi-critères
---

*Suivre le même plan que pour les deux autres sections.*
*Pour l'exemple, veillez à spécifier toutes les données des problèmes. En particulier, on ajoute ici l'expression des préférences d'optimisation de l'utilisatrice.*
*Comme précédemment, il est possible d'utiliser le même exemple et simplement l'enrichir.*

----------------------------------------------------

**Fin du rapport**

### Barème sur 30 pts

Toute question sur le barème est à adresser à iovka.boneva@univ-lille.fr


- Rapport non rendu à temps -> note 0 
- **(7, décomposé comme suit)** Divers
  - **(1,5)** Respect de la structure du rapport
  - **(1,5)** Section Version 1 rendue pour le 18/05/2024. Cette version peut contenir les parties en italique.
  - **(1,5)** Section Version 2 rendue pour le 08/06/2024. Cette version peut contenir les parties en italique.
  - **(1)** Utilisation de vocabulaire précis sur les graphes (termes vu en cours, noms des algorithmes, etc.)
  - **(1,5)** Style d'écriture fluide et compréhensible

- **(8, décomposé comme suit)** Solution pour la Version 1
  - **(2)** Exemple pertinent (illustre tous les aspects du problème) et lisible (en particulier, ni trop grand ni trop petit, bien présenté)
  - **(4)** Le modèle de l'exemple permet de trouver la solution sur l'exemple. La modélisation pour le cas général permet de résoudre le problème posé
  - **(2)** L'implémentation de l'exemple est correcte et fonctionnelle

- **(6, décomposé comme suit)** Solution pour la Version 2
  - **(1)** Exemple pertinent
  - **(4)** le modèle de l'exemple permet de trouver la solution sur l'exemple. La modélisation pour le cas général permet de résoudre le problème posé
  - **(1)** L'implémentation de l'exemple est correcte et fonctionnelle

- **(3)** Qualité de la description de la solution (concerne les sections "Modèlisation dans le cas général" pour les Versions 1 et 2):
  - La modélisation pour le cas général est décrite de manière abstraite mais précise et complète. Pour vous donner une idée, un·e étudiant·e de BUT qui a validé les ressources Graphes et Dev devrait être en mesure d'implémenter votre solution d'après la description que vous en faites, sans avoir à trop réfléchir.

- **(6)** Solution pour la Version 3: mêmes critères que pour la Version 2