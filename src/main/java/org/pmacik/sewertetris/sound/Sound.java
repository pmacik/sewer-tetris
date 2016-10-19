package org.pmacik.sewertetris.sound;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

/**
 * Zvuk, který se umí přehrát.
 *
 * @author Pavel Macík (xmacik01)
 */
public class Sound {
   /**
    * Objekt pro přehrávání zvuků.
    */
   private AudioClip clip;

   /**
    * Vytvoří zvuk ze souboru určeného URL <code>resource</code>.
    *
    * @param resource
    *       URL zvukového souboru.
    */
   public Sound(URL resource) {
      try {
         clip = Applet.newAudioClip(resource);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   /**
    * Přehraje zvuk.
    */
   public void play() {
      clip.play();
   }
}
