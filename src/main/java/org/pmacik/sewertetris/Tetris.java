package org.pmacik.sewertetris;

import org.pmacik.sewertetris.gui.TetrisFrame;

/**
 * Hlavní spouštěcí třída hry Sewer's Tetris.
 *
 * @author Pavel Macík (xmacik01)
 */
public class Tetris {

   /**
    * Spouštěcí metoda aplikace.
    *
    * @param args
    *       parametry příkazové řádky. Žádné parametry nejsou potřeba.
    */
   public static void main(String[] args) {
      new TetrisFrame();
   }
}