package net.macsewer.vutbr.gja.tetris.stones;

import net.macsewer.vutbr.gja.tetris.gui.TetrisFrame;

import java.util.Vector;

/**
 * Krabice, která obsahuje kostky tetrisu.
 *
 * @author Pavel Macík (xmacik01)
 */
public class StoneBox {
   /**
    * Reference na objekt hlavního okna aplikace.
    */
   private TetrisFrame application;

   /**
    * Kolekce obsahující kostky.
    */
   private Vector<AbstractStone> stones;

   /**
    * Vytvoří a zinicializuje krabici kostek.
    *
    * @param application
    *       reference na objekt hlavního okna aplikace.
    */
   public StoneBox(TetrisFrame application) {
      this.application = application;
      stones = new Vector<AbstractStone>(7, 3);
   }

   /**
    * Přidá kostku do krabice. // *
    *
    * @param newStone
    *       vkládaná kostka.
    */
   public void addStone(AbstractStone newStone) {
      newStone.x = 5;
      newStone.y = 0;
      stones.add(newStone);
   }

   /**
    * Náhodně vybere kostku z krabice.
    *
    * @return náhodně vybranou kostku.
    */
   public AbstractStone getRandomStone() {
      int i = application.getRandomGenerator().nextInt(1000000)
            % stones.size();
      int rotation = (int) (Math.random() * 4);
      for (int r = 0; r < rotation; r++) {
         stones.get(i).rotate();
      }
      return stones.get(i);
   }

   /**
    * Poskytuje informaci o počtu kostek v krabici.
    *
    * @return počet kostek v krabici.
    */
   public int getStonesCount() {
      return stones.size();
   }
}