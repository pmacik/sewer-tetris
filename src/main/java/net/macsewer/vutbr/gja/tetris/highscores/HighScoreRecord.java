package net.macsewer.vutbr.gja.tetris.highscores;

/**
 * Jeden záznam nejlepšího výsledku hry.
 *
 * @author Pavel Macík (xmacik01)
 */
public class HighScoreRecord {
   /**
    * Jméno hráče.
    */
   private String playerName;

   /**
    * Dosažené konečné skóre.
    */
   private int finalScore;

   /**
    * Vytvoří nový záznam o výsledku hry.
    *
    * @param playerName
    *       jméno hráče.
    * @param finalScore
    *       dosažené konečné skóre.
    */
   public HighScoreRecord(String playerName, int finalScore) {
      this.playerName = playerName;
      this.finalScore = finalScore;
   }

   /**
    * Poskytne konečné skóre.
    *
    * @return konečné skóre.
    */
   public int getFinalScore() {
      return finalScore;
   }

   /**
    * Poskytne hráčovo jméno.
    *
    * @return jméno hráče.
    */
   public String getPlayerName() {
      return playerName;
   }
}
