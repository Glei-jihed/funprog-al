package fr.esgi.al.funprog.tiling

/** Calculateur de combinaisons de pavage pour la partie 2 */
object TilingCalculator {
  
  /** Calcule le nombre de façons de paver un panneau n×m avec des blocs 2×1 */
  def calculateTilingCombinations(width: Int, height: Int): Long = {
    if (width <= 0 || height <= 0) 0L
    else if ((width * height) % 2 != 0) 0L // Impossible si nombre impair de cases
    else countTilings(width, height)
  }
  
  /** Compte récursivement les pavages possibles */
  private def countTilings(width: Int, height: Int): Long = {
    // Utilisation de la programmation dynamique avec mémoïsation
    val memo = scala.collection.mutable.Map[(Int, Int), Long]()
    
    def count(w: Int, h: Int): Long = {
      if (w == 0 || h == 0) 1L
      else if (w < 0 || h < 0) 0L
      else {
        memo.getOrElseUpdate((w, h), {
          if (w == 1 && h == 1) 0L // Une seule case ne peut pas être pavée
          else if (w == 1) {
            if (h % 2 == 0) 1L else 0L // Colonne unique : pavage possible si hauteur paire
          }
          else if (h == 1) {
            if (w % 2 == 0) 1L else 0L // Ligne unique : pavage possible si largeur paire
          }
          else {
            // Cas général : placement horizontal ou vertical du premier bloc
            val horizontal = count(w - 2, h) + count(w, h - 2)
            val vertical = if (w >= 2 && h >= 1) count(w - 1, h - 2) else 0L
            horizontal + vertical
          }
        })
      }
    }
    
    // Pour un calcul plus précis, utilisons la formule de récurrence
    calculateWithRecurrence(width, height)
  }
  
  /** Calcul avec récurrence optimisée pour les blocs 2×1 */
  private def calculateWithRecurrence(width: Int, height: Int): Long = {
    // Pour un pavage avec des dominos 2×1, nous utilisons une approche différente
    // basée sur les profils de colonnes
    
    val maxProfile = (1 << height) - 1
    val memo = Array.ofDim[Long](width + 1, maxProfile + 1)
    
    // Initialisation : première colonne
    memo(0)(0) = 1L
    
    for (col <- 0 until width) {
      for (profile <- 0 to maxProfile) {
        if (memo(col)(profile) > 0) {
          fillColumn(profile, 0, 0, height, col + 1, memo)
        }
      }
    }
    
    memo(width)(0)
  }
  
  /** Remplit récursivement une colonne selon le profil */
  private def fillColumn(
    currentProfile: Int,
    nextProfile: Int,
    pos: Int,
    height: Int,
    col: Int,
    memo: Array[Array[Long]]
  ): Unit = {
    if (pos == height) {
      if (col < memo.length) {
        memo(col)(nextProfile) += memo(col - 1)(currentProfile)
      }
    } else if ((currentProfile & (1 << pos)) != 0) {
      // Case déjà occupée par un domino horizontal
      fillColumn(currentProfile, nextProfile, pos + 1, height, col, memo)
    } else {
      // Case libre : placer un domino vertical ou horizontal
      
      // Option 1 : domino vertical (occupe cette case et la suivante)
      if (pos + 1 < height && (currentProfile & (1 << (pos + 1))) == 0) {
        fillColumn(
          currentProfile | (1 << pos) | (1 << (pos + 1)),
          nextProfile,
          pos + 2,
          height,
          col,
          memo
        )
      }
      
      // Option 2 : domino horizontal (occupe cette case dans la colonne suivante)
      fillColumn(
        currentProfile | (1 << pos),
        nextProfile | (1 << pos),
        pos + 1,
        height,
        col,
        memo
      )
    }
  }
  
  /** Affiche le résultat pour un panneau donné */
  def displayResult(width: Int, height: Int): String = {
    val combinations = calculateTilingCombinations(width, height)
    s"Possibilités d'affichage pour le panneau $width x $height: $combinations"
  }
}
