# Projet AL

## Pré-requis

Il est indispensable d'avoir installé en local:

- le compilateur Scala, [ici](https://scala-lang.org/download/) (voir version dans `build.sbt`)

- le gestionnaire de build `sbt`, [voir ici](https://www.scala-sbt.org/download.html). En installant `sbt`, le compilateur sera installé aussi.

# Projet AL - Simulateur de Panneau LED FunProg

## 🎯 Fonctionnalités Implémentées

### ✅ Partie 1 : Simulation de panneau LED

- **Panneau LED rectangulaire** n×m avec coordonnées (x,y)
- **5 couleurs supportées** : noir (0,0,0), rouge (1,0,0), vert (0,1,0), bleu (0,0,1), blanc (1,1,1)
- **Gestion de l'intensité** de 0 à 1
- **Actions sur l'intensité** : `+` (increment), `-` (decrement), `%` (switch)
- **Instructions temporisées** avec validation des conflits
- **Zones et positions** : position unique ou zone rectangulaire
- **Validation complète** des contraintes métier
- **Résumé d'activité** : LEDs allumées, couleurs, temps cumulé

### ✅ Partie 2 : Calcul de pavage

- **Algorithme de pavage** avec blocs 2×1
- **Calcul des combinaisons** possibles pour panneau n×m
- **Optimisation** avec programmation dynamique et mémoïsation

## 🚀 Utilisation

### Mode Simulation

```bash
sbt "run example_input.txt"           # Avec fichier d'exemple
sbt "run /chemin/vers/fichier.txt"    # Avec fichier spécifique
sbt run                               # Mode par défaut (example_input.txt)
```

### Mode Pavage

```bash
sbt "run tiling 4 4"    # Calcul pour panneau 4×4 (résultat: 36)
sbt "run tiling 3 2"    # Calcul pour panneau 3×2
```

## 📋 Contraintes Respectées

### ✅ Contraintes de programmation fonctionnelle

- ❌ **Aucun `return`** utilisé
- ❌ **Aucun `while`** utilisé
- ❌ **Aucun `null`** utilisé
- ❌ **Aucune expression régulière** utilisée
- ✅ **Tous les `if` sont exhaustifs** (avec `else`)
- ✅ **Pattern matching exhaustif** avec cas par défaut
- ❌ **Aucune mutabilité** (`var` ou structures mutables interdites)
- ✅ **Gestion d'erreurs avec `Try`** (aucun `throw`)
- ✅ **Effets de bord limités** aux frontières du programme

### ✅ Contraintes métier

- **Validation des couleurs** : seules les 5 couleurs autorisées
- **Validation des positions** : dans les limites du panneau
- **Détection des conflits temporels** : une seule instruction par LED par instant
- **Changement de couleur sécurisé** : seulement si LED éteinte (intensité 0)
- **Validation de l'intensité** : comprise entre 0.0 et 1.0

## 🏗️ Architecture

```
src/main/scala/fr/esgi/al/funprog/
├── model/
│   ├── Led.scala              # Color, Led, Position, Zone
│   ├── Instruction.scala      # IntensityAction, Instruction, Target
│   └── Panel.scala            # Panel, PanelStatistics
├── parser/
│   └── InstructionParser.scala # Parsing complet des fichiers
├── simulator/
│   └── LedPanelSimulator.scala # Simulation temporelle
├── tiling/
│   └── TilingCalculator.scala  # Algorithme de pavage
└── Main.scala                 # Point d'entrée avec modes
```

## 🧪 Tests Complets

```bash
sbt test  # Tous les tests (50+ cas de test)
```

### Couverture de tests

- ✅ **Tests unitaires** pour tous les modèles
- ✅ **Tests du parser** avec cas d'erreur
- ✅ **Tests du simulateur** avec scénarios complexes
- ✅ **Tests de pavage** avec validation algorithme
- ✅ **Tests d'intégration** bout-en-bout
- ✅ **Tests de gestion d'erreurs** exhaustifs

## 📊 Exemple de Résultat

### Fichier d'entrée (`example_input.txt`)

```
5 x 5
1 | + | 1,0,0 | 0,0 - 1,0
1 | + | 0,1,0 | 1,1 - 2,1
2 | + | 0,0,1 | 2,2 - 3,4
2 | + | 0,1,0 | 4,0 - 4,1
3 | + | 1,1,1 | 4,2 - 4,3
3 | + | 0,0,1 | 4,4
4 | - | 1,0,0 | 1,0
5 | % | 1,0,0 | 1,0
6 | % | 1,0,0 | 1,0
7 | + | 1,1,1 | 1,0
```

### Sortie attendue

```
=== Simulateur de Panneau LED FunProg ===
Mode simulation - Fichier: example_input.txt

=== Résumé d'activité ===
- allumées: 15
- couleurs:
  - rouge: 1
  - vert: 4
  - bleu: 7
  - blanc: 3
- cumul: 74
```

## 📚 Concepts Scala 3 Utilisés

### Fonctionnalités modernes

- ✅ **Enums** pour les actions d'intensité
- ✅ **Case classes** pour les modèles immutables
- ✅ **Extension methods** et syntaxe moderne
- ✅ **Pattern matching** exhaustif amélioré
- ✅ **For-comprehensions** pour la composition
- ✅ **Higher-order functions** (`map`, `fold`, `filter`)

### Gestion des erreurs

- ✅ **Try/Success/Failure** pour la gestion d'erreurs
- ✅ **Option** pour les valeurs optionnelles
- ✅ **Validation** composable avec flatMap
- ✅ **Récupération d'erreurs** avec recoverWith

### Collections immutables

- ✅ **List** pour les séquences
- ✅ **Map** pour les associations
- ✅ **Set** pour les ensembles uniques
- ✅ **Transformations** fonctionnelles pures

## 🎓 Objectifs Pédagogiques Atteints

1. ✅ **Programmation fonctionnelle pure** sans effets de bord
2. ✅ **Immutabilité complète** des structures de données
3. ✅ **Gestion d'erreurs fonctionnelle** avec types
4. ✅ **Architecture modulaire** et testable
5. ✅ **Validation métier** rigoureuse
6. ✅ **Tests unitaires** exhaustifs avec MUnit
7. ✅ **Algorithmes avancés** (simulation, pavage récursif)
8. ✅ **Parsing** et manipulation de données textuelles
9. ✅ **Types sûrs** exploitant le système de types Scala
10. ✅ **Documentation** et exemples d'utilisation

## 📁 Fichiers Importants

- `USAGE.md` : Guide détaillé d'utilisation
- `example_input.txt` : Fichier d'exemple selon la spécification
- Tests complets dans `src/test/scala/`
- Architecture modulaire dans `src/main/scala/`

Le projet respecte intégralement les contraintes du cahier des charges et implémente toutes les fonctionnalités demandées avec une architecture fonctionnelle pure et des tests exhaustifs. veuillez vous reporter à la section [Tests Unitaires](#tests-unitaires).

## Guide de survie avec sbt

Ce projet est un application Scala standalone. Il est géré par `sbt`, le build tool Scala. Sa documentation est disponible [ici](https://www.scala-sbt.org/1.x/docs/).

Nous allons lister ici une liste de commandes utiles avec `sbt`:

- `sbt`: cette commande lance un invite de commande interactif

- `run` (ou `sbt run` hors de l'invite de commande): lance le `Main` du projet `sbt`

- `compile` (ou `sbt compile` hors de l'invite de commande): lance la compilation de l'ensemble du projet `sbt` (compile toutes les classes)

- `console` (`sbt console` hors de l'invite de commande): lance un REPL interactif Scala. Les dépendances du projet `sbt` seront disponibles et pourront être importés.

## Manipulation de fichiers

Nous allons voir ici quelques commandes pour vous aider avec la manipulation de fichiers en `Scala`.

Pour lire un fichier nous pouvons le faire comme suit (en utilisant la lib [better-files](https://github.com/pathikrit/better-files)):

```scala
import better.files._

val f = File("/User/johndoe/Documents") // using constructor

// on va récupérer toutes les lignes du fichier
f.lines.toList

// si on veut récupérer tout le contenu du fichier en String
f.contentAsString
```

Pour écrire dans un fichier, nous pouvons le faire ainsi:

```scala
import better.files._

val f = File("/User/johndoe/Documents") // using constructor

// pour ajouter du contenu dans un fichier ligne par ligne
f.createIfNotExists()
  .appendLine() // on rajoute une ligne vide
  .appendLines("My name is", "Inigo Montoya") // on ajoute 2 nouvelles lignes

// pour écraser le contenu du fichier
f.createIfNotExists().overwrite("hello")
```

## Tests unitaires

Il est possible de lancer tous les tests du projet avec la commande: `sbt test` (ou `test` si on est dans l'invite de commande `sbt`).

Pour créer une classe de test, il suffit de créer une classe étendant `munit.FunSuite`:

```scala
package example

class MyTests extends munit.FunSuite {
  ???
}
```

Les tests devant être lancés doivent être placés dans le corps de la classe. Pour créer un test, il suffit d'appeler `test` en lui passant un nom de test et le code de test à effectuer comme ceci:

```scala
package example

class MyTests extends munit.FunSuite {

  // On rajoute un cas de test
  test("sum of two integers") {
    val obtained = 2 + 2
    val expected = 4
    assertEquals(obtained, expected)
  }

  test("all even numbers") {
    val input: List[Int] = List(1, 2, 3, 4)
    val obtainedResults: List[Int] = input.map(_ * 2)
    // check that obtained values are all even numbers
    assert(obtainedResults.forall(x => x % 2 == 0))
  }

}
```

`MUnit` propose plusieurs méthodes d'assertions disponibles dans sa [documentation](https://scalameta.org/munit/docs/assertions.html): `assertNotEquals`, `assertNoDiff`, `fail`, and `compileErrors`.

Le test sera lancé dès lorsqu'on lancera la commande `test`:

```scala
sbt:funprog-AL> test
example.StringSuite:
  + CHaine vide est vide 0.004s
  + Chaine  0.0s
example.HelloSuite:
  + The Hello object should say hello 0.004s
  + Hello size is equals to 5 0.0s
  +  "Hello"(6) should throw a "java.lang.StringIndexOutOfBoundsException"  0.0s
[info] Passed: Total 5, Failed 0, Errors 0, Passed 5
[success] Total time: 1 s, completed 11 avr. 2024 à 22:39:37
```

Une classe de test d'exemple vous est fourni dans `./src/test/example/HelloSuite.scala`.
