# 📘 Guide de Lancement & Test des APIs

Ce projet back-end est composé de deux modules distincts :
- `auth-service` : pour la gestion de l'authentification JWT
- `product-service` : pour la gestion des produits, panier, et wishlist

---

## 🛠️ 1. Prérequis

- Java 17 ou plus
- Maven 3.8+
- Postman pour les tests d'API
- Navigateur pour Swagger UI (documentation interactive)

---

## 🚀 2. Lancer le backend

### 📅 Lancement manuel via terminal

Ouvrir deux terminaux :

```bash
# Terminal 1 : auth-service
cd auth-service
mvn spring-boot:run
```
Ou avec IntelliJ : clic droit sur AuthServiceApplication.java → Run
```bash
# Terminal 2 : product-service
cd product-service
mvn spring-boot:run
```
---
Ou avec IntelliJ : clic droit sur ProductApplication.java → Run
## 🔧 3. Architecture du projet

### 🔑 `auth-service`

```
auth-service/
├── config/                -> Configuration Spring Security (JWT)
├── controller/            -> Authentification (login + inscription)
├── dto/                   -> Requêtes et réponses d'API
├── model/                 -> Entités JPA (User)
├── repository/            -> Accès base de données
├── service/               -> Logique métier
├── util/                  -> JWT utilitaire
```

### 📈 `product-service`

```
product-service/
├── config/                -> Config Sécurité, filtre admin
├── controller/            -> Produits, panier, wishlist
├── dto/                   -> DTO pour cart/wishlist enrichis
├── model/                 -> Entités réactives (R2DBC)
├── repository/            -> Accès base réactive
├── service/               -> Logique métier
├── test/                  -> Tests unitaires et d'intégration
```

---

## ⚙️ 4. Ports & Endpoints

| Module           | Port     | URL principale                    |
|------------------|----------|----------------------------------|
| auth-service     | 8084     | http://localhost:8084            |
| product-service  | 8086     | http://localhost:8086/api        |

### Swagger
- Swagger UI : http://localhost:8086/swagger-ui.html
- API Docs : http://localhost:8086/v3/api-docs

---

## 🔐 5. Authentification JWT

Toutes les routes `/api/**` sont protégées.

### Créer un compte
```http
POST http://localhost:8084/auth/account
Content-Type: application/json

{
  "username": "admin",
  "firstname": "admin",
  "email": "admin@admin.com",
  "password": "admin"
}
```

### Se connecter (obtenir un token)
```http
POST http://localhost:8084/auth/token
Content-Type: application/json

{
  "email": "admin@admin.com",
  "password": "admin"
}
```

Utilisez ensuite le token dans les requêtes :
```
Authorization: Bearer <votre-token>
```

---

## 📦 6. Endpoints Produits

| Méthode | URL                         | Accès requis         |
|---------|------------------------------|----------------------|
| GET     | `/api/products`              | Authentifié          |
| GET     | `/api/products/{id}`         | Authentifié          |
| POST    | `/api/products`              | Admin uniquement     |
| PATCH   | `/api/products/{id}`         | Admin uniquement     |
| DELETE  | `/api/products/{id}`         | Admin uniquement     |
| POST    | `/api/products/batch`        | Admin uniquement     |

---

## 🛒 7. Panier d'achat (Cart)

| Méthode | URL                       | Description                  |
|---------|----------------------------|------------------------------|
| POST    | `/cart`                    | Ajouter un produit au panier |
| GET     | `/cart`                    | Voir le panier               |
| GET     | `/cart/details`           | Détails avec produits        |
| DELETE  | `/cart/{productId}`        | Supprimer un produit         |

---

## 💖 8. Liste d'envie (Wishlist)

| Méthode | URL                          | Description                    |
|---------|-------------------------------|--------------------------------|
| POST    | `/wishlist`                   | Ajouter un produit à la liste |
| GET     | `/wishlist`                   | Voir la liste d'envie          |
| GET     | `/wishlist/details`          | Voir les détails               |
| DELETE  | `/wishlist/{productId}`       | Supprimer de la wishlist       |

---

## 🧪 9. Tests avec Postman

Une collection Postman est fournie avec le projet (fichier `.json` dans l'email).

### Astuce : ajouter un test de token auto
Dans la requête `POST /token`, ajoutez ceci dans l'onglet **Tests** :
```js
const json = pm.response.json();
pm.environment.set("token", json.token);
```

Puis dans toutes les requêtes suivantes, ajoutez ce header :
```
Authorization: Bearer {{token}}
```

---
