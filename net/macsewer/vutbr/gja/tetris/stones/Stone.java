package net.macsewer.vutbr.gja.tetris.stones;

/**
 * Kostka tetrisu.
 *
 * @author Pavel Macík (xmacik01)
 */
public class Stone extends AbstractStone {

   /**
    * Vytvoří kostku z matice a umístí jí na výchozí pozici [0; 5] v rámci
    * hracího pole.
    *
    * @param mat
    *       matice 4x4, která popisuje rozmístění segmentů kostky a
    *       prázdných buněk v rámci oblasti kostky.
    */
   public Stone(int mat[][]) {
      matrix = mat;
   }

   /**
    * Vytvoří kostku s prázdnou oblastí a umístí jí do výchozí pozice [0; 5] v
    * rámci hracího pole.
    */
   public Stone() {
      for (int i = 0; i < 4; i++) {
         for (int j = 0; j < 4; j++) {
            matrix[i][j] = 0;
         }
      }
   }

   /**
    * Vytvoří kostku jako kopii kostky <code>stone</code>.
    *
    * @param stone
    *       kopírovaná kostka.
    */
   public Stone(Stone stone) {
      x = stone.x;
      y = stone.y;
      for (int i = 0; i < 4; i++) {
         for (int j = 0; j < 4; j++) {
            matrix[i][j] = stone.get(i, j);
         }
      }
   }

   /**
    * Rotuje kostkou ve směru hodinových ručiček.
    */
   @Override
   public void rotate() {
      int hlp[][] = new int[4][4];
      for (int i = 0; i < 4; i++) {
         for (int j = 0; j < 4; j++) {
            hlp[j][3 - i] = matrix[i][j];
         }
      }
      matrix = hlp;
   }

   /**
    * Vrátí hodnotu buňky v oblasti kostky na pozici <code>i</code>,
    * <code>j</code>.
    */
   @Override
   public int get(int i, int j) {
      return matrix[i][j];
   }
}