package net.macsewer.vutbr.gja.tetris.gui;

import java.awt.GraphicsEnvironment;
import java.awt.Window;

/**
 * Třída, která umí manipulovat s okny.
 *
 * @author Pavel Macík (xmacik01)
 */
public class WindowManipulator {
   /**
    * Vycentruje dané okno na střed obrazovky.
    */
   public static void centerWindow(Window okno) {
      int screenWidth = GraphicsEnvironment.getLocalGraphicsEnvironment()
                                           .getScreenDevices()[0].getDisplayMode().getWidth();
      int screenHeight = GraphicsEnvironment.getLocalGraphicsEnvironment()
                                            .getScreenDevices()[0].getDisplayMode().getHeight();
      okno.setLocation(screenWidth / 2 - okno.getWidth() / 2, screenHeight
            / 2 - okno.getHeight() / 2);
   }
}
