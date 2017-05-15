## Pierre PEREZ et Loïc TORO

Nous n'avons pas eu le temps d'implémenter l'arborescence avec la base de données afin de pouvoir visualiser chaque fichier.

Actuellement, l'application est multilangue avec francais anglais et japonais (google traduction).

l'application a des options qui sont persistées.
(path d'enregistrement et langue)

L'application peut télécharger des url avec une profondeur allant de 0 à 9 (chaque lien est lancé dans un thread afin d'accelerer le processus).

Nous pouvons télécharger les vidéos et les images (les vidéos présent dans une balise <vidéo>, pas celle proposant une URL
 fragmenté avec un format blob (ex: YouTube))

la web view affichera le dernier site telecharger avec les liens recurssifs si on clique dessus.

difficultés rencontré : elaborer l'arborescence.