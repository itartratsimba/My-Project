# TP Ajax : exercices évolutifs (3 h 30)

**Outils** : HTML, JavaScript natif (`fetch`), PHP (`mysqli`), MySQL
**Point de départ** : le dossier `depart/` (connexion en AJAX déjà fonctionnelle)
**Aboutissement** : une mini application de publications et de commentaires, sans rechargement de page
**À rendre** : le fichier `reponses.md` complété et le dossier du code final

## Planning

| Partie | Exercice | Durée |
|---|---|---|
| A. Explorer | 1. Installer et lancer | 15 min |
| | 2. Observer la requête de connexion | 10 min |
| | 3. Lire le code | 20 min |
| | 4. Casser pour comprendre | 15 min |
| | 5. Apostrophes et injection SQL | 10 min |
| B. Lire | 6. Afficher les publications | 35 min |
| | 7. Le piège du HTML | 10 min |
| C. Écrire | 8. Publier | 30 min |
| | 9. Commenter | 40 min |
| D. Synthèse | 10. Trajet complet et bilan | 15 min |
| | Relecture et rendu | 10 min |
| | **Total** | **3 h 30** |

## Mode d'emploi

- Le fichier `reponses.md` est fourni vide : complétez-le au fur et à mesure, exercice par exercice.
- Toutes les réponses sont des **textes** : valeurs recopiées, extraits de code, requêtes SQL, JSON copié depuis l'onglet Réseau. Aucune capture d'écran n'est demandée.
- Travaillez dans un dossier par exercice (`ex01`, `ex02`, ...) : chaque exercice reprend le code du précédent.
- Respectez les durées indiquées. Un exercice inachevé à la fin de sa durée est noté dans `reponses.md` avec l'état d'avancement, puis on passe au suivant.

---

## Partie A : Explorer le code de départ

### Exercice 1 : Installer et lancer (15 min)

1. Copiez `depart/` dans `ex01/`.
2. Créez la base avec `database.sql`.
3. Adaptez `connection.php` à votre serveur MySQL.
4. Lancez le serveur PHP intégré et connectez-vous avec un compte de test.

**Dans `reponses.md`** : la version de PHP, l'adresse utilisée, le texte affiché sur la page d'accueil après connexion.

### Exercice 2 : Observer la requête de connexion (10 min)

Ouvrez l'onglet **Réseau** des outils de développement. Faites une connexion avec un mauvais mot de passe, puis une connexion réussie.

**Dans `reponses.md`** : le tableau demandé (méthode, URL, code de statut, données envoyées, réponse reçue) pour les deux cas.

### Exercice 3 : Lire le code (20 min)

Lisez `login.html`, `login.php`, `functions.php`, `moi.php` et `index.html`.

**Dans `reponses.md`** (deux ou trois phrases par réponse) :
1. Le trajet d'une connexion réussie, du clic jusqu'à la page d'accueil, sous forme de liste de fichiers et d'étapes.
2. Le rôle de `event.preventDefault()` dans `login.html`.
3. La raison du code 401 dans `login.php`, et pourquoi le JavaScript peut quand même lire le message.
4. L'endroit où est conservée l'information « je suis connecté ».
5. Le rôle de `moi.php` et la raison de son appel par `index.html` au chargement.

### Exercice 4 : Casser pour comprendre (15 min)

Réalisez chaque manipulation une à la fois, puis remettez le code d'origine.

- a. Supprimez l'attribut `name` du champ mot de passe.
- b. Mettez en commentaire `session_start()` dans `login.php`.
- c. Arrêtez MySQL puis tentez une connexion.

**Dans `reponses.md`** : pour chaque manipulation, l'observation et l'explication.

### Exercice 5 : Apostrophes et injection SQL (10 min)

1. Retirez temporairement la protection `esc()` dans `get_membre_by_email`.
2. Dans le formulaire de connexion, saisissez comme email : `x' OR '1'='1`.
3. Remettez la protection.

**Dans `reponses.md`** : l'observation, la requête SQL réellement exécutée lors de l'attaque, le rôle de `esc()`, le nom d'une autre technique de protection.

---

## Partie B : Lire des données sans recharger la page

### Exercice 6 : Afficher les publications (35 min)

1. Importez `tables-suite.sql` : il ajoute les tables `publication` et `commentaire` avec des données de test.
2. Écrivez `get_publications()` dans `functions.php` (avec le nom de l'auteur, plus récentes en premier).
3. Créez `fil.php`, qui renvoie le membre connecté et la liste des publications en JSON (401 si non connecté).
4. Dans `index.html`, construisez la liste avec JavaScript au chargement de la page.

Le fichier `style.css` contient déjà les classes `publication`, `auteur` et `texte`.

**Dans `reponses.md`** : la requête SQL de `get_publications()`, le JSON complet renvoyé par `fil.php` (copié depuis l'onglet Réseau), la structure HTML générée pour une publication.

### Exercice 7 : Le piège du HTML (10 min)

Insérez directement en base (phpMyAdmin ou ligne de commande) une publication dont le texte est :

```
<b>gras</b> <img src=x onerror="alert('piratage')">
```

1. Affichez la liste avec `innerHTML`.
2. Remplacez par `textContent`.

**Dans `reponses.md`** : l'observation pour chaque version, puis l'explication de l'attaque (XSS) et de la protection, avec vos propres mots.

---

## Partie C : Écrire des données sans recharger la page

### Exercice 8 : Publier (30 min)

1. Ajoutez la zone de texte et le bouton « Publier ».
2. Écrivez `publier.php` : refus si l'utilisateur n'est pas connecté (401) ou si le texte est vide (400).
3. Après la publication, la liste se met à jour sans rechargement et la zone de texte est vidée.

**Dans `reponses.md`** : le tableau des cas testés (cas, code obtenu, message), puis la solution choisie pour mettre la liste à jour (tout recharger ou ajouter seulement la nouvelle publication) avec une justification.

### Exercice 9 : Commenter (40 min)

1. Modifiez `fil.php` pour que chaque publication contienne la liste de ses commentaires (avec le nom de l'auteur).
2. Affichez sous chaque publication ses commentaires, avec le nom de l'auteur en bleu (classe `auteur`).
3. Ajoutez sous chaque publication un champ « Votre commentaire... » et un bouton « Commenter ».
4. Écrivez `commenter.php` (mêmes contrôles que `publier.php`, plus l'existence de la publication).
5. Le nouveau commentaire apparaît sans rechargement.

**Dans `reponses.md`** : la forme du JSON d'une publication avec ses commentaires, l'explication de la façon dont `commenter.php` identifie la publication concernée, le tableau des cas testés.

---

## Partie D : Synthèse

### Exercice 10 : Trajet complet et bilan (15 min)

**Dans `reponses.md`** :
1. En 10 lignes maximum, le trajet d'un clic sur « Commenter » : du clic jusqu'à l'affichage du commentaire. Citez chaque fichier traversé, la forme des données échangées et le code de statut en cas de succès.
2. La grille d'auto-évaluation.
3. Les difficultés rencontrées et leur résolution.

---

## Éléments à rendre

| Élément | Obligatoire |
|---|---|
| `reponses.md` complété | Oui |
| Dossier de code final (PHP, HTML, CSS) | Oui |

## Pour aller plus loin (hors durée)

- **Inscription en AJAX** : page d'inscription (nom, email, mot de passe) avec le code 400 pour un champ vide et le code 409 pour un email déjà utilisé.
- **Actualisation automatique** : une publication faite dans un navigateur apparaît dans un autre sans action de l'utilisateur.
- **Suppression** : un membre supprime ses propres publications, et uniquement les siennes.
