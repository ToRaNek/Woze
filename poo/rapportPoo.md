```markdown
# IUT de Lille - Département informatique - N2
## Université de Lille
### Année académique 2023-2024

#### SAÉ 2.01 & 2.02
#### Comparaison d’itinéraires de transport

# Rapport - Version 1

### Membres du trinôme
- [CATRY Gaspard](https://gitlab.univ-lille.fr/gaspard.catry.etu)
- [DELANGUE Gordon](https://gitlab.univ-lille.fr/gordon.delangue.etu)
- [MILLE Gabriel](https://gitlab.univ-lille.fr/gabriel.mille.etu)

### Date de soumission
- 19/05/2024

---

## Table des Matières

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

---

## Introduction

Dans le cadre de notre projet SAÉ 2.01 et 2.02, nous avons développé "un assistant personnel de voyage" qui permet de calculer, visualiser et comparer des itinéraires pour un utilisateur donné. Ce rapport présente la version 1 de notre application, qui se concentre sur la recherche d'un itinéraire optimal entre deux villes en utilisant un seul type de ligne de transport et un critère spécifié.

---

## Cadre Applicatif

### Description du Contexte

Le projet consiste à créer un logiciel permettant de comparer des itinéraires de transport multimodal (routier, ferroviaire, aérien) entre différentes villes. Chaque itinéraire est une suite d’étapes, chaque étape étant caractérisée par un coût selon différents critères : prix, temps et émissions de gaz à effet de serre. L'objectif est de répondre aux préférences de l'utilisateur en proposant les itinéraires optimaux selon ses critères.

### Données Disponibles

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


## Modélisation et Conception

### Modélisation du Réseau de Transport

Nous avons modélisé le réseau de transport en utilisant des graphes. Chaque ville est un nœud du graphe, et chaque tronçon de transport entre deux villes est une arête. Les arêtes sont pondérées en fonction des coûts associés : prix, temps et pollution.

### Diagramme UML

Le diagramme UML ci-dessous illustre les principales classes et leurs relations dans notre application.

![Diagramme UML](.\UMLv1Poo.png)

### Algorithme de Recherche des Plus Courts Chemins

Nous avons utilisé un algorithme de plus courts chemins pour trouver les itinéraires optimaux entre deux villes selon les critères de l'utilisateur. Pour cela, nous avons utilisé la bibliothèque JGraphT et les librairies misent à notre disposition : [Librairies](https://gitlab.univ-lille.fr/sae2.01-2.02/common-tools). 

---

## Développement

### Vérification de la Validité des Données

Nous avons devolloppé une multitude de classe de teste pour tester l'integroité de notre code et les méthodes de chaque classe : tout est diponible dans le repertoire: [test](../test\Version1)



Nous avons développé une méthode pour vérifier que les données fournies sont complètes et conformes au format attendu. Cette étape est cruciale pour garantir que le réseau de transport soit correctement construit ([VérificationData](../src\Version1\VerificationData.java)).

### Filtrage des Données

L'application permet de filtrer les tronçons en fonction du moyen de transport sélectionné par l'utilisateur. Par exemple, si l'utilisateur choisit de voyager en train, seuls les tronçons de type "Train" seront considérés.
Malheureusement même si cela n'était pas demandé ou spécifié nous avons utilisé des try & catch pour éviter certaines exception pour fluidifié l'utilisation du main ([Main](../src\Version1\Main.java))

### Calcul des Itinéraires Optimaux

En utilisant l'algorithme de plus courts chemins, nous avons développé une fonctionnalité qui calcule les itinéraires optimaux en fonction du critère sélectionné par l'utilisateur (prix, temps ou pollution). Les itinéraires qui dépassent les bornes définies par l'utilisateur sont exclus.

Ne sachant pas exactement comment les fichiers devaient être organisés, certaines méthodes qui devraient peut-être se trouver dans [Plateforme](../src\Version1\Plateforme.java) se sont retrouvées dans le [Main](../src\Version1\Main.java) par souci de compréhension des consignes, comme :

```java
public static void chercherChemin();
```

qui fait bien sûr appel à une méthode de la classe Plateforme mais définit la modalité de transport et la borne à l'intérieur de celle-ci. Ce que je veux dire par là, c'est que le code pourrait être mieux organisé (il le sera dans les versions futures).
---

## Conclusion

La version 1 de notre application répond aux exigences de base du projet en permettant de calculer et de comparer des itinéraires entre deux villes en utilisant un seul type de ligne de transport. Les prochaines versions ajouteront des fonctionnalités avancées, telles que l'utilisation de multiples moyens de transport et la prise en compte de plusieurs critères simultanément. La classe [Test](../src/Version1/Test.java) permet de voir une utilisation courte du "logiciel" et la classe [Main](../src/Version1/Main.java) vous permet de l'utiliser en tant qu'utilisateur (Ce n'est pas encore au point mais c'est suffisant pour voir si cela fonctionne).

---

## Références

- Documentation JGraphT
- Cours de programmation orientée objet
- Cours de théorie des graphes
- Nos connaissances communes 
```
