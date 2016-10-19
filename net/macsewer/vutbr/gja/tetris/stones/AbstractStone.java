package net.macsewer.vutbr.gja.tetris.stones;

/**
 * Abstraktní předek pro kostkty pro tetris.
 *
 * @author Pavel Macík (xmacik01)
 */
public abstract class AbstractStone {
   /**
    * Souřadnice pozice levého horního rohu oblasti kostky v rámci hracího
    * pole.
    */
   protected int x = 0, y = 0;

   /**
    * Matice, která reprezentuje oblast kostky. Pokud má prvek hodnotu rovnou
    * <code>0</code>, znamená to prázdná buňka. Jinak hodnota, která se
    * nachází v prvcích znamená číslo typu segmentu kostky.
    */
   protected int matrix[][] = { { 0, 0, 0, 0 }, { 0, 0, 0, 0 },
         { 0, 0, 0, 0 }, { 0, 0, 0, 0 } };

   /**
    * Vrátí hodnotu prvku matice kostky na pozici <code>i</code>,
    * <code>j</code>.
    */
   public abstract int get(int i, int j);

   /**
    * Rotuje kostkou.
    */
   public abstract void rotate();

   /**
    * Poskytuje vodorovnou souřadnici oblasti kostky v rámci hracího pole.
    *
    * @return x souřadnice v rámci hracího pole.
    */
   public int getX() {
      return x;
   }

   /**
    * Mění x souřadnici pozice oblasti kostky v rámci hracího pole.
    *
    * @param x
    *       nová pozice oblasti kostky.
    */
   public void setX(int x) {
      this.x = x;
   }

   /**
    * Poskytuje svislou souřadnici oblasti kostky v rámci hracího pole.
    *
    * @return y souřadnice v rámci hracího pole.
    */
   public int getY() {
      return y;
   }

   /**
    * Mění y souřadnici pozice oblasti kostky v rámci hracího pole.
    *
    * @param y
    *       nová pozice oblasti kostky.
    */
   public void setY(int y) {
      this.y = y;
   }
}