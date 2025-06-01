# 🎺 FanfareHub

Projet web développé dans le cadre du cours de Programmation Web

---

## 📂 Structure du projet

```
FanfareHub/
├── src/                  # Code source Java
├── web/                  # JSP, HTML, CSS, JS
├── sql/                  # Scripts de création de la base
├── README.md
```

---

## 🧱 Mise en place de la base de données

### 1. Créer la base

```sql
CREATE DATABASE FanfareHub;
```

### 2. Créer l'utilisateur

```sql
CREATE USER groupe7 WITH PASSWORD 'projet';
GRANT ALL PRIVILEGES ON DATABASE FanfareHub TO groupe7;
```

### 3. Importer le script SQL

Exécuter dans le terminal :

```bash
psql -U groupe7 -d FanfareHub -f "chemin/vers/fanfareHubDB.sql"
```

## 🚀 Lancement du site

Exporter le projet et lancer le sur Intellij

## 👤 Comptes de test

| Nom d'utilisateur | Mot de passe | Rôle   |
| ----------------- | ------------ | ------ |
| admin             | admin        | Admin  |
| claraB            | musique44    | Membre |
| soly              | iamthebest   | Membre |

---

## ✨ Fonctionnalités

- Inscription et connexion sécurisée des fanfarons
- Création et inscription à des événements si on appartient à la commission "Prestation"
- Interface d’administration pour les comptes
- Hash des mots de passe avec `SHA-256`

---

## 🧪 Remarques

- Des jeux de données de test sont fournis avec le script SQL.

---

## 🤝 Auteurs

**Groupe 7** – [Chabchoub Salim, EL Abridi Mustapha]  
Année universitaire **2024–2025**
