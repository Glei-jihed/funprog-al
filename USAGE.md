# Guide d'utilisation du Simulateur LED FunProg

## 🎯 Fonctionnalités implémentées

### Partie 1 : Simulation de panneau LED

- **Panneau LED rectangulaire** avec coordonnées (x,y)
- **5 couleurs supportées** : noir, rouge, vert, bleu, blanc
- **Gestion de l'intensité** (0.0 à 1.0)
- **Actions sur intensité** : `+` (increment), `-` (decrement), `%` (switch)
- **Instructions temporisées** avec validation des conflits
- **Zones et positions** : single position ou zone rectangulaire
- **Résumé d'activité** : LEDs allumées, couleurs, temps cumulé

### Partie 2 : Calcul de pavage

- **Algorithme de pavage** avec blocs 2×1
- **Calcul des combinaisons** possibles pour panneau n×m
- **Optimisation** avec programmation dynamique

## 🏗️ Architecture du code

```
src/main/scala/fr/esgi/al/funprog/
├── model/
│   ├── Led.scala              # Modèles Color, Led, Position, Zone
│   ├── Instruction.scala      # Actions et instructions
│   └── Panel.scala            # Panneau LED et statistiques
├── parser/
│   └── InstructionParser.scala # Parsing fichiers d'instructions
├── simulator/
│   └── LedPanelSimulator.scala # Simulation du panneau
├── tiling/
│   └── TilingCalculator.scala  # Calcul combinaisons pavage
└── Main.scala                 # Point d'entrée principal
```

## 🚀 Utilisation

### Mode Simulation (Partie 1)

```bash
# Avec fichier spécifique
sbt "run /chemin/vers/fichier_instructions.txt"

# Avec fichier par défaut
sbt run

# Exemple
sbt "run example_input.txt"
```

### Mode Pavage (Partie 2)

```bash
sbt "run tiling <largeur> <hauteur>"

# Exemples
sbt "run tiling 4 4"    # Résultat: 36 combinaisons
sbt "run tiling 3 2"    # Résultat: 3 combinaisons
```

## 📋 Format du fichier d'instructions

```
5 x 5
1 | + | 1,0,0 | 0,0 - 1,0
1 | + | 0,1,0 | 1,1 - 2,1
2 | + | 0,0,1 | 2,2 - 3,4
3 | % | 1,1,1 | 4,4
```

**Format** : `temps | action | couleur | cible`

- **Temps** : instant d'exécution (entier ≥ 0)
- **Action** : `+` (increment), `-` (decrement), `%` (switch)
- **Couleur** : `r,g,b` avec r,g,b ∈ {0,1}
- **Cible** : `x,y` (position) ou `x1,y1 - x2,y2` (zone)

## ✅ Validation et contraintes

### Contraintes respectées

- ❌ **Pas de `return`**
- ❌ **Pas de `while`**
- ❌ **Pas de `null`**
- ❌ **Pas d'expressions régulières**
- ✅ **Tous les `if` sont exhaustifs** (avec `else`)
- ✅ **Pattern matching exhaustif**
- ❌ **Pas de mutabilité** (`var`, structures mutables)
- ✅ **Gestion d'erreurs avec `Try`** (pas de `throw`)
- ✅ **Effets de bord limités** aux frontières

### Validations métier

- **Couleurs valides** : seules les 5 couleurs autorisées
- **Positions valides** : dans les limites du panneau
- **Pas de conflits temporels** : une seule instruction par LED par instant
- **Changement de couleur** : seulement si LED éteinte
- **Intensité** : entre 0.0 et 1.0

## 🧪 Tests

```bash
# Lancer tous les tests
sbt test

# Tests par module
sbt "testOnly fr.esgi.al.funprog.model.*"
sbt "testOnly fr.esgi.al.funprog.parser.*"
sbt "testOnly fr.esgi.al.funprog.simulator.*"
sbt "testOnly fr.esgi.al.funprog.tiling.*"
```

### Couverture de tests

- **Modèles** : validation des couleurs, LEDs, positions, zones
- **Parser** : parsing complet, détection d'erreurs, conflits
- **Simulateur** : actions, zones, cas d'erreur
- **Pavage** : cas simples et complexes, symétrie

## 📊 Exemple de sortie

### Simulation

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

### Pavage

```
=== Simulateur de Panneau LED FunProg ===
Mode pavage - Panneau: 4x4
Possibilités d'affichage pour le panneau 4 x 4: 36
```

## 🔧 Résolution de problèmes

### Erreur de compilation Java/SBT

Si vous rencontrez des erreurs Java :

1. Vérifiez Java 8+ : `java -version`
2. Vérifiez JAVA_HOME
3. Réinstallez SBT si nécessaire

### Erreurs de validation

- **"Position invalide"** : coordonnées hors panneau
- **"Conflits temporels"** : plusieurs instructions même LED même instant
- **"Couleur invalide"** : triplet RGB non autorisé
- **"Impossible de changer couleur"** : LED allumée, il faut l'éteindre d'abord

## 📚 Concepts Scala utilisés

- **Case classes** et **enums** (Scala 3)
- **Pattern matching** exhaustif
- **Immutabilité** complète
- **Gestion d'erreurs** avec `Try`/`Success`/`Failure`
- **Programmation fonctionnelle** pure
- **Higher-order functions** (`map`, `fold`, `filter`)
- **For-comprehensions** pour la composition
- **Collections immutables** (`List`, `Map`, `Set`)

## 🎓 Objectifs pédagogiques atteints

1. ✅ **Programmation fonctionnelle** sans mutabilité
2. ✅ **Gestion d'erreurs** fonctionnelle
3. ✅ **Architecture modulaire** et testable
4. ✅ **Validation métier** rigoureuse
5. ✅ **Tests unitaires** complets
6. ✅ **Algorithmes** (simulation, pavage)
7. ✅ **Parsing** et manipulation de données
8. ✅ **Types sûrs** avec le système de types Scala
