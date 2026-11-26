# Réponses : TP Ajax

| | |
|---|---|
| **Nom et prénom** | |
| **Promotion / groupe** | |
| **Date de rendu** | |
| **Binôme (le cas échéant)** | |

**Sources d'aide utilisées** (documentation, camarades, outils d'IA, avec l'usage fait) :

> À compléter

---

## Partie A : Explorer le code de départ

### Exercice 1 : Installer et lancer

- Version de PHP : 
- Adresse utilisée : 
- Capture de la page d'accueil après connexion :

> ![page d'accueil](captures/ex01-accueil.png)

### Exercice 2 : Observer la requête de connexion

| | Mauvais mot de passe | Connexion réussie |
|---|---|---|
| Méthode | | |
| URL | | |
| Code de statut | | |
| Corps envoyé | | |
| Corps reçu | | |

> ![onglet Réseau](captures/ex02-reseau.png)

### Exercice 3 : Lire le code

**1. Trajet d'une connexion réussie**

> À compléter

**2. Rôle de `event.preventDefault()`**

> À compléter

**3. Code 401 et lecture du message par le JavaScript**

> À compléter

**4. Où est conservée l'information « je suis connecté » ?**

> À compléter

**5. Rôle de `moi.php`**

> À compléter

### Exercice 4 : Casser pour comprendre

| Manipulation | Observation | Explication |
|---|---|---|
| a. Champ mot de passe sans `name` | | |
| b. `session_start()` en commentaire | | |
| c. Accès à `index.html` en navigation privée | | |
| d. MySQL arrêté | | |

---

## Partie B : Étendre la connexion

### Exercice 5 : Inscription en AJAX

- Nouveaux fichiers :
- Nouvelles fonctions de `functions.php` :

| Cas testé | Code obtenu | Message affiché |
|---|---|---|
| Champ vide | | |
| Email déjà utilisé | | |
| Inscription correcte | | |
| (à compléter) | | |

### Exercice 6 : Apostrophes et injection SQL

**Observations**

1. Inscription avec une apostrophe :
2. Connexion avec `x' OR '1'='1` sans protection :
3. Même test avec la protection :

**Requête SQL réellement exécutée lors de l'attaque**

```sql

```

**Explication de l'attaque et rôle de `esc()`**

> À compléter

**Autre technique de protection**

> À compléter

---

## Partie C : Lire des données sans recharger la page

### Exercice 7 : Afficher les publications

**Requête SQL de `get_publications()`**

```sql

```

**Exemple de JSON reçu**

```json

```

> ![liste des publications](captures/ex07-liste.png)

### Exercice 8 : Le piège du HTML

- Avec `innerHTML` :
- Avec `textContent` :

**Explication de l'attaque (XSS) et de la protection**

> À compléter

---

## Partie D : Écrire des données sans recharger la page

### Exercice 9 : Publier

| Cas testé | Code obtenu |
|---|---|
| Non connecté | |
| Texte vide | |
| Publication correcte | |

**Mise à jour de la liste : solution choisie et justification**

> À compléter

### Exercice 10 : Commenter

**Schéma de la base**

```
membre (...)
publication (...)
commentaire (...)
```

**Comment `commenter.php` identifie-t-il la publication concernée ?**

> À compléter

> ![application finale](captures/ex10-final.png)

---

## Partie E : Synthèse

### Exercice 11 : Le trajet complet d'un clic sur « Commenter »

> À compléter (10 lignes maximum)

### Exercice 12 : Bilan

**Auto-évaluation** (cochez avec `x`)

| Compétence | Acquis |
|---|---|
| Lire une requête dans l'onglet Réseau | [ ] |
| Envoyer des données avec `fetch` et `FormData` | [ ] |
| Échanger en JSON | [ ] |
| Gérer les codes HTTP et les erreurs | [ ] |
| Utiliser les sessions pour l'authentification | [ ] |
| Mettre à jour la page sans recharger | [ ] |
| Se protéger (XSS, injection SQL, mots de passe) | [ ] |
| Organiser le code PHP en fonctions d'accès à la base | [ ] |

**Difficultés rencontrées et résolution**

> À compléter

---

## Partie F ★ : Approfondissements (facultatif)

### Exercice 13 ★ : Actualisation automatique

> À compléter

### Exercice 14 ★ : Supprimer

> À compléter

### Exercice 15 ★ : « J'aime »

> À compléter
