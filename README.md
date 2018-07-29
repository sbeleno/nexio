Nexio - API Rest
================

Api REST qui permet les scénarios suivants:
- Afficher un catalogue de produits  
- Afficher le détail d’un produit 
- Ajouter un produit au panier 
- Enlever un produit du panier
- Afficher le contenu du panier
- Connexion à un compte utilisateur sans UI


Environnement techniques
------------------------
![](https://img.shields.io/badge/Java_8-✓-blue.svg)
![](https://img.shields.io/badge/Spring_boot_security-✓-blue.svg)
![](https://img.shields.io/badge/Spring_boot_data_jpa-✓-blue.svg)
![](https://img.shields.io/badge/H2_database-✓-blue.svg)
![](https://img.shields.io/badge/jUnit-✓-blue.svg)
![](https://img.shields.io/badge/Jwt-✓-blue.svg)
![](https://img.shields.io/badge/Eclipse-✓-blue.svg)
![](https://img.shields.io/badge/Postman-✓-blue.svg)

Structure du projet
-------------------
```
nexio/
 │
 ├── src/main/java/
 │   └── sb/nexio/test
 │       │
 │       ├── controller
 │       │   ├── LoginController.java
 │       │   ├── ProductController.java
 │       │   └── ShoppingCartController.java
 │       │
 │       ├── domain
 │       │   ├── CartDetail.java
 │       │   ├── Order.java
 │       │   ├── OrderDetail.java
 │       │   ├── Product.java
 │       │   ├── User.java
 |       |   └── extra
 │       │        ├── ResultBuilder.java
 |       │        └── UserApp.java     
 │       │
 │       ├── exception
 │       │   └── StatusException.java
 │       │
 │       ├── repository
 │       │   ├── ProductRepository.java
 │       │   ├── ShoppingCartRepository.java
 │       │   └── UserRepository.java
 │       │
 │       ├── security
 │       │   ├── AppAuthorizationFilter.java
 │       │   ├── ConsSecurity.java
 │       │   └── WebSecurityConfig.java
 │       │
 │       ├── service
 │       │   ├── ProductService.java
 │       │   ├── ShoppingCartService.java
 │       │   ├── UserService.java
 |       |   └── impl
 │       │        ├── ProductServiceImpl.java
 │       │        ├── ShoppingCartServiceImpl.java
 |       │        └── UserServiceImpl.java   
 │       │
 │       └── App.java
 │
 ├── src/main/test/java
 │   └── sb/nexio/test
 │       ├── controller
 │       │   ├── ProductControllerTest.java
 │       │   └── ShoppingCartControllerIntTest.java 
 │       │
 │       ├── service
 │       │   └── ProductServiceImplTest.java 
 │       │
 │       └── AppTest.java
 |
 ├── src/main/resources/
 │   ├── application-test.properties
 │   └── application.properties
 │
 ├── README.md
 └── pom.xml
```

Installation
------------

1. On clone le dépôt ```$ git clone git://github.com/sbeleno/nexio.git```

2. On génère le jar depuis eclipse et maven.
![ScreenShot](https://raw.github.com/sbeleno/nexio/master/src/main/ressources/screenshots/step-2.jpg)

3. On ajoute les "Goals", les "Profiles" et on fait check à "Skip Tests"

![ScreenShot](https://raw.github.com/sbeleno/nexio/master/src/main/ressources/screenshots/step-3.jpg)

4. Nous avons le jar du projet dans le dossier "target". On ouvre une console depuis le dossier target et on exécute comme ci-desous.

![ScreenShot](https://raw.github.com/sbeleno/nexio/master/src/main/ressources/screenshots/step-4.jpg) 

![ScreenShot](https://raw.github.com/sbeleno/nexio/master/src/main/ressources/screenshots/step-4a.jpg)   

5. Sur la console, on attend le message ": Started App in xx.xxx seconds (...)"
![ScreenShot](https://raw.github.com/sbeleno/nexio/master/src/main/ressources/screenshots/step-5.jpg)  


Tester l'API Rest avec Postman
------------------------------

- Login

Configuration des headers et body.
![ScreenShot](https://raw.github.com/sbeleno/nexio/master/src/main/ressources/screenshots/step-6.jpg) 

![ScreenShot](https://raw.github.com/sbeleno/nexio/master/src/main/ressources/screenshots/step-6a.jpg) 

![ScreenShot](https://raw.github.com/sbeleno/nexio/master/src/main/ressources/screenshots/step-6b.jpg) 
On a le token pour faire les requests.

Une fois qu'on aura le token, il est nécessaire de configurer les headers pour toutes les requests
--------------------------------------------------------------------------------------------------

- Products

Endpoint pour lister les produits.
![ScreenShot](https://raw.github.com/sbeleno/nexio/master/src/main/ressources/screenshots/step-7.jpg) 

- ShoppingCart

Configuration des headers.

![ScreenShot](https://raw.github.com/sbeleno/nexio/master/src/main/ressources/screenshots/step-8.jpg) 
On ajoute un produit avec son identifiant et la quantité.

![ScreenShot](https://raw.github.com/sbeleno/nexio/master/src/main/ressources/screenshots/step-8a.jpg) 

On reçois la commande sauvegardé.
 
Endpoint pour voir les details de la commande.
![ScreenShot](https://raw.github.com/sbeleno/nexio/master/src/main/ressources/screenshots/step-9.jpg) 

Pour enlever le produit du panier, on envoie l'identifiant de la commande
![ScreenShot](https://raw.github.com/sbeleno/nexio/master/src/main/ressources/screenshots/step-10.jpg) 

