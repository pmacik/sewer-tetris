package org.pmacik.sewertetris.gui;

import org.pmacik.sewertetris.stones.AbstractStone;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * Panel, do kterého se vykresluje kostka, která příjde jako další.
 *
 * @author Pavel Macík (xmacik01)
 */
public class HelpField extends JPanel {
   /**
    *
    */
   private static final long serialVersionUID = 5828375212729348545L;

   /**
    * Barevný buffer do kterého se vykresluje pole nápovědy.
    */
   private BufferedImage frameBuffer;

   /**
    * Hra tetrisu, ke které náleží tato nápověda.
    */
   private Game game;

   /**
    * Konstruktor nápovědy.
    *
    * @param owner
    *       -
    *       hra tetrisu, které tato nápověda má patřit.
    */
   public HelpField(Game owner) {
      super();

      game = owner;
      initGUI();
   }

   /**
    * Inicializuje GUI.
    */
   private void initGUI() {
      this.setSize(Game.STONE_SEGMENT_SIZE * 4, Game.STONE_SEGMENT_SIZE * 4);
      frameBuffer = new BufferedImage(Game.STONE_SEGMENT_SIZE * 4,
            Game.STONE_SEGMENT_SIZE * 4, BufferedImage.TYPE_INT_RGB);
   }

   /**
    * Vykreslí kostku do framebuferru; jednotlivé segmenty kostky jsou kresleny
    * bud na základě načtených obrázků, nebo jako barevné čtverce.
    *
    * @param stone
    *       -
    *       kostka, která se má vykreslit
    */
   public void renderStone(AbstractStone stone) {
      for (int i = 0; i < 4; i++) {
         for (int j = 0; j < 4; j++) {
            Graphics g = frameBuffer.getGraphics();
            switch (stone.get(i, j)) {
               case 0:
                  g.setColor(new Color(0x000000));
                  g.fillRect(Game.STONE_SEGMENT_SIZE * i,
                        Game.STONE_SEGMENT_SIZE * j,
                        Game.STONE_SEGMENT_SIZE, Game.STONE_SEGMENT_SIZE);
                  break;
               case 1:
                  if (game.imgStone0 != null) {
                     g.drawImage(game.imgStone0,
                           Game.STONE_SEGMENT_SIZE * i,
                           Game.STONE_SEGMENT_SIZE * j, null);
                  } else {
                     g.setColor(new Color(0xff0000));
                     g.fillRect(Game.STONE_SEGMENT_SIZE * (i - 2),
                           Game.STONE_SEGMENT_SIZE * j,
                           Game.STONE_SEGMENT_SIZE,
                           Game.STONE_SEGMENT_SIZE);
                  }
                  break;
               case 2:
                  if (game.imgStone1 != null) {
                     g.drawImage(game.imgStone1,
                           Game.STONE_SEGMENT_SIZE * i,
                           Game.STONE_SEGMENT_SIZE * j, null);
                  } else {
                     g.setColor(new Color(0x00ff00));
                     g.fillRect(i, j, Game.STONE_SEGMENT_SIZE,
                           Game.STONE_SEGMENT_SIZE);
                  }
                  break;
               case 3:
                  if (game.imgStone2 != null) {
                     g.drawImage(game.imgStone2,
                           Game.STONE_SEGMENT_SIZE * i,
                           Game.STONE_SEGMENT_SIZE * j, null);
                  } else {
                     g.setColor(new Color(0x0000ff));
                     g.fillRect(Game.STONE_SEGMENT_SIZE * i,
                           Game.STONE_SEGMENT_SIZE * j,
                           Game.STONE_SEGMENT_SIZE,
                           Game.STONE_SEGMENT_SIZE);
                  }
                  break;
               case 4:
                  if (game.imgStone3 != null) {
                     g.drawImage(game.imgStone3,
                           Game.STONE_SEGMENT_SIZE * i,
                           Game.STONE_SEGMENT_SIZE * j, null);
                  } else {
                     g.setColor(new Color(0x00ffff));
                     g.fillRect(Game.STONE_SEGMENT_SIZE * i,
                           Game.STONE_SEGMENT_SIZE * j,
                           Game.STONE_SEGMENT_SIZE,
                           Game.STONE_SEGMENT_SIZE);
                  }
                  break;
               case 5:
                  if (game.imgStone4 != null) {
                     g.drawImage(game.imgStone4,
                           Game.STONE_SEGMENT_SIZE * i,
                           Game.STONE_SEGMENT_SIZE * j, null);
                  } else {
                     g.setColor(new Color(0xff00ff));
                     g.fillRect(Game.STONE_SEGMENT_SIZE * i,
                           Game.STONE_SEGMENT_SIZE * j,
                           Game.STONE_SEGMENT_SIZE,
                           Game.STONE_SEGMENT_SIZE);
                  }
                  break;
               case 6:
                  if (game.imgStone5 != null) {
                     g.drawImage(game.imgStone5,
                           Game.STONE_SEGMENT_SIZE * i,
                           Game.STONE_SEGMENT_SIZE * j, null);
                  } else {
                     g.setColor(new Color(0xffff00));
                     g.fillRect(Game.STONE_SEGMENT_SIZE * i,
                           Game.STONE_SEGMENT_SIZE * j,
                           Game.STONE_SEGMENT_SIZE,
                           Game.STONE_SEGMENT_SIZE);
                  }
                  break;
               case 7:
                  if (game.imgStone6 != null) {
                     g.drawImage(game.imgStone6,
                           Game.STONE_SEGMENT_SIZE * i,
                           Game.STONE_SEGMENT_SIZE * j, null);
                  } else {
                     g.setColor(new Color(0xffffff));
                     g.fillRect(Game.STONE_SEGMENT_SIZE * i,
                           Game.STONE_SEGMENT_SIZE * j,
                           Game.STONE_SEGMENT_SIZE,
                           Game.STONE_SEGMENT_SIZE);
                  }
                  break;
            }
         }
      }
      repaint();
   }

   /**
    * Získání framebufferu
    *
    * @return framebuffer nápovědy
    */
   public BufferedImage getFrameBuffer() {
      return frameBuffer;
   }

   /*
    * (non-Javadoc)
    *
    * @see javax.swing.JComponent#paint(java.awt.Graphics)
    */
   @Override
   public void paint(Graphics g) {
      // super.paint(g);
      g.drawImage(frameBuffer, 0, 0, null);
   }
}