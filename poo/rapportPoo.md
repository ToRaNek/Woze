# IUT de Lille - Département informatique - N2
## Université de Lille
### Année académique 2023-2024

#### SAÉ 2.01 & 2.02
#### Comparaison d’itinéraires de transport

# Rapport - Versions 1, 2 et 3

### Membres du trinôme
- [CATRY Gaspard](https://gitlab.univ-lille.fr/gaspard.catry.etu)
- [DELANGUE Gordon](https://gitlab.univ-lille.fr/gordon.delangue.etu)
- [MILLE Gabriel](https://gitlab.univ-lille.fr/gabriel.mille.etu)

### Dates de soumission
- version 1 : 19/05/2024
- version 2 : 08/06/2024

---

## Table des Matières

1. [Version 1](#version-1)
   1. [Introduction](#introduction)
   2. [Cadre Applicatif](#cadre-applicatif)
      1. [Description du Contexte](#description-du-contexte)
      2. [Données Disponibles](#données-disponibles)
   3. [Modélisation et Conception](#modélisation-et-conception)
      1. [Modélisation du Réseau de Transport](#modélisation-du-réseau-de-transport)
      2. [Diagramme UML](#diagramme-uml)
      3. [Algorithme de Recherche des Plus Courts Chemins](#algorithme-de-recherche-des-plus-courts-chemins)
   4. [Développement](#développement)
      1. [Vérification de la Validité des Données](#vérification-de-la-validité-des-données)
      2. [Filtrage des Données](#filtrage-des-données)
      3. [Calcul des Itinéraires Optimaux](#calcul-des-itinéraires-optimaux)
   5. [Conclusion](#conclusion)
   6. [Références](#références)
2. [Version 2](#version-2)
   1. [Introduction](#introduction-v2)
   2. [Nouveautés et Améliorations](#nouveautés-et-améliorations-v2)
      1. [Support Multimodal](#support-multimodal)
      2. [Interface Utilisateur Améliorée](#interface-utilisateur-améliorée)
      3. [Optimisations de Performance](#optimisations-de-performance)
   3. [Défis et Solutions](#défis-et-solutions-v2)
   4. [Conclusion](#conclusion-v2)
---

## Version 1

### Introduction

Dans le cadre de notre projet SAÉ 2.01 et 2.02, nous avons développé "un assistant personnel de voyage" qui permet de calculer, visualiser et comparer des itinéraires pour un utilisateur donné. Ce rapport présente la version 1 de notre application, qui se concentre sur la recherche d'un itinéraire optimal entre deux villes en utilisant un seul type de ligne de transport et un critère spécifié.

### Cadre Applicatif

#### Description du Contexte

Le projet consiste à créer un logiciel permettant de comparer des itinéraires de transport multimodal (routier, ferroviaire, aérien) entre différentes villes. Chaque itinéraire est une suite d’étapes, chaque étape étant caractérisée par un coût selon différents critères : prix, temps et émissions de gaz à effet de serre. L'objectif est de répondre aux préférences de l'utilisateur en proposant les itinéraires optimaux selon ses critères.

#### Données Disponibles

Les données sont fournies sous forme de chaînes de caractères respectant le format suivant :

```csv
villeDépart;villeArrivée;modalitéTransport;prix(€);pollution(kgCO2e);durée(minutes)
```

Par exemple :

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

### Modélisation et Conception

#### Modélisation du Réseau de Transport

Nous avons modélisé le réseau de transport en utilisant des graphes. Chaque ville est un nœud du graphe, et chaque tronçon de transport entre deux villes est une arête. Les arêtes sont pondérées en fonction des coûts associés : prix, temps et pollution.

#### Diagramme UML

Le diagramme UML ci-dessous illustre les principales classes et leurs relations dans notre application.

![Diagramme UML](.\UMLv1Poo.png)

#### Algorithme de Recherche des Plus Courts Chemins

Nous avons utilisé un algorithme de plus courts chemins pour trouver les itinéraires optimaux entre deux villes selon les critères de l'utilisateur. Pour cela, nous avons utilisé la bibliothèque JGraphT et les librairies mises à notre disposition : [Librairies](https://gitlab.univ-lille.fr/sae2.01-2.02/common-tools). 

### Développement

#### Vérification de la Validité des Données

Nous avons développé une multitude de classes de test pour vérifier l'intégrité de notre code et les méthodes de chaque classe : tout est disponible dans le répertoire : [test](../test/version1).

Nous avons développé une méthode pour vérifier que les données fournies sont complètes et conformes au format attendu. Cette étape est cruciale pour garantir que le réseau de transport soit correctement construit ([VérificationData](../src/version1/VerificationData.java)).

#### Filtrage des Données

L'application permet de filtrer les tronçons en fonction du moyen de transport sélectionné par l'utilisateur. Par exemple, si l'utilisateur choisit de voyager en train, seuls les tronçons de type "Train" seront considérés.
Malheureusement, même si cela n'était pas demandé ou spécifié, nous avons utilisé des try & catch pour éviter certaines exceptions et fluidifier l'utilisation du main ([Main](../src/version1/Main.java)).

#### Calcul des Itinéraires Optimaux

En utilisant l'algorithme de plus courts chemins, nous avons développé une fonctionnalité qui calcule les itinéraires optimaux en fonction du critère sélectionné par l'utilisateur (prix, temps ou pollution). Les itinéraires qui dépassent les bornes définies par l'utilisateur sont exclus.

Ne sachant pas exactement comment les fichiers devaient être organisés, certaines méthodes qui devraient peut-être se trouver dans [Plateforme](../src/version1/Plateforme.java) se sont retrouvées dans le [Main](../src/version1/Main.java) par souci de compréhension des consignes, comme :

```java
public static void chercherChemin();
```

qui fait bien sûr appel à une méthode de la classe Plateforme mais définit la modalité de transport et la borne à l'intérieur de celle-ci. Ce que je veux dire par là, c'est que le code pourrait être mieux organisé (il le sera dans les versions futures).

### Conclusion

La version 1 de notre application répond aux exigences de base du projet en permettant de calculer et de comparer des itinéraires entre deux villes en utilisant un seul type de ligne de transport. Les prochaines versions ajouteront des fonctionnalités avancées, telles que l'utilisation de multiples moyens de transport et la prise en compte de plusieurs critères simultanément. La classe [Test](../src/Version1/Test.java) permet de voir une utilisation courte du "logiciel" et la classe [Main](../src/Version1/Main.java) vous permet de l'utiliser en tant qu'utilisateur (ce n'est pas encore au point mais c'est suffisant pour voir si cela fonctionne).

### Références

- Documentation JGraphT
- Cours de programmation orientée objet
- Cours de théorie des graphes
- Nos connaissances communes

---

## Version 2

### Introduction

Dans cette seconde version de notre projet SAÉ 2.01 et 2.02, notre "assistant personnel de voyage" permet désormais des voyages multi-modaux, incluant des changements de moyens de transport (par exemple, train puis bus). Chaque correspondance ajoute un coût supplémentaire en termes de durée, émissions de CO2, et prix. Le critère d'optimisation n'est pas vraiment unique puisque lorsque mais le [Main](../src/version2/main/Main.java), on vous demandera de choisir une borne et un critère pour cette borne (durée, pollution ou prix) qui fera en sorte que le voyage ne dépasse pas cette borne. Nous avons adapté notre modèle pour intégrer ces coûts de correspondance et ajouté des fonctionnalités pour "éviter" les problèmes de données et filtrer l'affichage des itinéraires pour ne montrer que les points d'intérêt.

### Nouveautés et Améliorations

1. **Support Multi-modal** : Inclusion de plusieurs moyens de transport dans un même itinéraire (train, bus, avion, etc.).
2. **Coûts de Correspondance** : Intégration des coûts de correspondance (durée, émissions de CO2, et prix) pour les changements de moyens de transport.
3. **Filtrage des Itinéraires** : Affichage des itinéraires en ne montrant que les points d'intérêt (changements de transport) pour une meilleure lisibilité.
4. **Signalisation des Problèmes** : Ajout de mécanismes d'exception pour signaler les problèmes de validité des données et d'existence de chemins possibles.
5. **Optimisation selon Critères** : Maintien de l'optimisation selon un critère choisi (durée, pollution ou prix) avec respect des bornes définies par l'utilisateur.
6. **Gestion des Fichiers CSV** : Lecture des fichiers CSV dans les ressources pour les voyages et pour les informations utilisateur.
7. **Création et Modification d'Utilisateurs** : Ajout de fonctionnalités permettant de créer et modifier des utilisateurs pendant l'exécution du programme, mais aucune sauvegarde n'est effectuée pour le moment (à implémenter dans la version 3).
8. **Modifications de l'UML** : L'UML a été modifié pour les changements apportés dans la version 2. ![UMLv2](./UMLv2Poo.png)
9. **Maquette pour Interface Future** : Conception d'une maquette pour une future interface utilisateur sur [Figma](https://www.figma.com/design/Poq6nF5x8RoUqDvQOgSF86/Maquette-SAE-IHM?node-id=0-1&t=DJFxqMvTJXEmOjje-1).

### Défis et Solutions

- **Lecture des Fichiers CSV** : La mise en place de la lecture des fichiers était initialement complexe, mais une fois que nous avons trouvé la bonne approche, cela s'est déroulé sans problème.
  
- **Répartition du Travail** : Se répartir le travail n'était pas une tâche simple, alors nous avons utilisé un fichier [README](../README.md) pour lister les tâches à faire et cocher celles qui étaient terminées, ce qui a facilité l'organisation.

- **Organisation des Fichiers** : Nous avons également dû réfléchir à l'organisation logique des fichiers, en concertation, pour assurer une structure claire et cohérente.

- **Interface Graphique** : La conception de l'interface graphique était un défi, mais nous avons travaillé ensemble pour résoudre les problèmes rencontrés, bien que certains aspects restent encore compliqués à concevoir pour la suite.

- **Constructeur de Plateforme** : La mise en place du constructeur de la classe Plateforme a été complexe au départ, mais en y consacrant du temps et en ajoutant quelques méthodes annexes, nous avons pu le mettre en place avec succès.

- **Clarté des Attentes du PDF** : Le PDF contenant les instructions manquait parfois de clarté, notamment en ce qui concerne le fichier correspondance qui a été modifié / donné à la dernière minute. Malgré cela, nous avons réussi à résoudre les problèmes rapidement.

- **Utilisation de la Classe Chemin** : Nous avons rencontré des difficultés avec la classe Chemin qui ne disposait ni de constructeur ni de méthodes réelles, mais en travaillant ensemble, nous avons pu trouver des solutions efficaces.

### Conclusion

En conclusion, cette seconde version de notre projet SAÉ 2.01 et 2.02 représente une avancée  dans le développement de notre "assistant personnel de voyage". Nous avons réussi à surmonter plusieurs défis, notamment en intégrant la multi-modalité, en gérant la lecture des fichiers CSV et en améliorant l'organisation du code.

Bien que certains aspects restent encore à améliorer, comme la conception de l'interface graphique et la sauvegarde des données utilisateur, nous sommes satisfaits des progrès réalisés jusqu'à présent.

Nous sommes confiants que les versions futures apporteront encore plus de fonctionnalités et d'améliorations, notamment avec l'ajout d'une interface graphique dans la version 3.

En fin de compte, cette expérience nous a permis d'approfondir nos compétences en programmation et de mieux comprendre les défis liés au développement d'une application logicielle complexe.

