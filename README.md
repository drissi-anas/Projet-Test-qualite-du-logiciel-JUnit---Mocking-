# Projet-Test-qualite-du-logiciel-JUnit---Mocking-
Ce projet a pour objectif de mettre en pratique les connaissances informatiques en développement, en Test et qualité en particulier, acquises par les étudiants durant leurs cursus universitaires.
Il s’agit d’un projet à réaliser en groupe de 4 personnes, durant lequel les étudiants seront amenés à réaliser dans un premier temps des tests, structurels et fonctionnels, d’une application déjà fournie , et rajouter des fonctionnalités à cette application et réaliser les tests qui vont avec dans un second .
La mission du projet est de s’approprier une application dont le modèle manque (en tous les cas les implémentations principales), il faudrait donc :
Être capable de faire tourner une démonstration de l’application telle qu’elle est sans implanter un nouveau modèle. Vous devrez animer des « use cases » avec TestFx. Ainsi, un « use case » sera représenté par une méthode @Test  
Ajouter de nouvelles fonctionnalités à l’application sans développer la partie métier (uniquement les interfaces). Intégrer dans la démo vos nouvelles fonctionnalités. 
Préparer un ensemble de jeu de tests pour les services. Ce jeu de tests devra être facilement exécutable sur n’importe quelle implémentation des services concernés. 
Ce rapport détaillera le projet réalisé, et expliquera en détail les méthodes de test (FX et fonctionnels) développées .

Ce rapport Dans le cadre du développement d’un forum, nous sommes en charge de la partie IHM de la chose. On ne maîtrise pas du tout la partie métier. Nous avons quelques interfaces qui nous permettent normalement de nous en sortir.
Au jour d'aujourd’hui, nous savons qu’il est possible avec l’application (si nous disposons d'une implémentation de l'API modèle) de :
- Demander une inscription hors-connexion avec un rôle (BASIQUE, MODERATEUR ou ADMIN).
- Une fois connecté, un administrateur peut créer des utilisateurs. Il peut aussi traiter toutes les demandes d’inscriptions (accepter ou refuser).
- Il peut également supprimer un utilisateur quelconque (sauf lui même).
- Une fois connecté, un modérateur peut traiter des demandes d’inscriptions mais uniquement celles de futurs modérateurs ou de futurs utilisateurs basiques.
- Une fois connecté, un utilisateur basique ne peut pas faire grand-chose normalement pour le moment.
Il est demandé d’ajouter les fonctionnalités suivantes :
- Naviguer dans le forum. On partira du principe que nous avons un thème racine et que dans ce thème, on peut avoir plusieurs « topics ». Ces topics sont constitués d’un ensemble de messages échangés par les utilisateurs sur le sujet. L'utilisateur devra pouvoir explorer tout ceci et échanger.
- Un Modérateur aura le pouvoir de supprimer n’importe quel topic tout comme n’importe quel message dans le forum. Ce pouvoir, un administrateur le possède également. Par contre un utilisateur basique ne peut supprimer que ses propres messages.
- Un utilisateur quelconque peut poster un message, créer un topic.

Ces fonctionnalités doivent être fonctionnelles « graphiquement ». Elles ne pourront pas l’être en pratique en production car les classes métiers seront manquantes, par contre, en mode test, vous aurez utilisé toutes les astuces de sioux pour simuler les objets manquants.
Il est interdit de créer des classes anonymes (sauf cas de force majeur une fois validé par votre responsable i.e. moi même) ou de développer le modèle. Il est également interdit de modifier les interfaces données.

 
