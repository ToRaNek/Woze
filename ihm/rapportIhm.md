# Compte rendu IHM

Catry Gaspard, Delangue Gordon, Mille Gabriel

Groupe E

## Informations générales

- Lien vers le repo GitLab : https://gitlab.univ-lille.fr/sae2.01-2.02/2024/E3
- Référence du commit correspondant au rendu : IHM-v2

## Capture d'écran de l'application finale

![Page de connexion](captures/connexion.png)
![Page d'accueil](captures/accueil.png)

## Justification des choix de conception

On a essayé de faire une application simple et efficace, avec peu d'éléments afin que l'utilisateur ne soit pas perdu.
Il y a des prompt afin d'expliquer ce que l'utilisateur doit faire ou écrire.
Afin d'éviter les erreurs, les champs de texte de choix de ville sont guidés, on ne peut pas valider un texte qui n'est pas proposé, on ne peut pas mettre des lettres dans le champ "Prix" etc.


## Améliorations

Certaines fonctionnalités manquent d'explication, par exemple les petites flèches à coté des critères pour mettre le critère en choix de tri.


## Contributions de chaque membre du groupe

Gaspard : réalisation de la maquette sur papier, aide à l'implémentation du code fonctionnel qui relie les boutons aux résultats.
Gordon : réalisation du prototype avec figma et des fichiers.fxml avec leurs controlleurs.
Gabriel : aide à la réalisation des fichiers.fxml avec leurs controlleurs, implémentation du code fonctionnel qui relie les boutons aux résultats.

Commande pour executer le jar
java --module-path C:\Users\Admin\Desktop\dev\javafx-sdk-17.0.11\lib --add-modules=javafx.controls,javafx.fxml -jar C:\Users\Admin\Desktop\dev\SAE\E3\Woze.jar
