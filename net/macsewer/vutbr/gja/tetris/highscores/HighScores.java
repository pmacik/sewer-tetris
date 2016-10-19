package net.macsewer.vutbr.gja.tetris.highscores;

import net.macsewer.vutbr.gja.tetris.data.encryption.Encryptor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.StringTokenizer;

/**
 * Kontejner obsahující nejlepší výsledky.
 *
 * @author Pavel Macík (xmacik01)
 */
public class HighScores {
   /**
    * Počet záznamů.
    */
   private int highScoresCount;

   /**
    * Výchozí kapacita kontejneru.
    */
   private static final int INITIAL_HIGH_SCORES_COUNT = 10;

   /**
    * Pole obsahující jednotlivé záznamy.
    */
   private HighScoreRecord[] highScoreRecords;

   /**
    * Vytvoří kontejner pro nejlepší výsledky načtením dat ze souboru určeného
    * cestou <code>filePath</code>.
    *
    * @param filePath
    *       cesta k souboru, který obsahuje data o nejlepších výsledcích.
    *       Pokud nastane chyba při čtení dat ze souboru, nebo soubor není
    *       nalezen, kontejner je vyprázdněn a jeho kapacita je daná
    *       implicitní hodnotou {@link #INITIAL_HIGH_SCORES_COUNT}.
    */
   public HighScores(String filePath) {
      try {
         loadHighScores(filePath);
      } catch (IOException e) {
         highScoresCount = INITIAL_HIGH_SCORES_COUNT;
         clear();
      }
   }

   /**
    * Vytvoří kontejner pro nejlepší výsledky načtením dat z URL určeného
    * <code>resource</code>.
    *
    * @param resource
    *       URL souboru, který obsahuje data o nejlepších výsledcích.
    *       Pokud nastane chyba při čtení dat z URL, nebo soubor není
    *       nalezen, kontejner je vyprázdněn a jeho kapacita je daná
    *       implicitní hodnotou {@link #INITIAL_HIGH_SCORES_COUNT}.
    */
   public HighScores(URL resource) {
      try {
         loadHighScores(resource.getPath());
      } catch (NullPointerException e) {
         highScoresCount = INITIAL_HIGH_SCORES_COUNT;
         clear();
      } catch (IOException e) {
         highScoresCount = INITIAL_HIGH_SCORES_COUNT;
         clear();
      }
   }

   /**
    * Vytvoří prázdný kontejner pro nejlepší výsledky s kapacitou
    * <code>count</code>.
    *
    * @param count
    *       kapacita kontejneru.
    */
   public HighScores(int count) {
      highScoresCount = count;
      clear();
   }

   /**
    * Vyprázdní kontejner.
    */
   public void clear() {
      highScoreRecords = new HighScoreRecord[highScoresCount];
      for (int i = 0; i < highScoresCount; i++) {
         highScoreRecords[i] = new HighScoreRecord(" ", 0);
      }
   }

   /**
    * Poskytne informaci o počtu záznamů.
    *
    * @return počet záznamů.
    */
   public int getHighScoreCount() {
      return highScoresCount;
   }

   /**
    * Poskytne referenci na <code>index</code>-tý záznam nejlepšího
    * výsledku.
    *
    * @param index
    *       index požadovaného záznamu.
    * @return pokud existuje záznam na pozici <code>index</code>, je vrácena
    * reference na něj, jinak je vráceno <code>null</code>.
    */
   public HighScoreRecord getHighScore(int index) {
      if (index >= 0 && index < highScoresCount) {
         return highScoreRecords[index];
      } else {
         return null;
      }
   }

   /**
    * Vložení nového záznamu na pozici <code>index</code>.
    *
    * @param index
    *       pozice, na kterou je nový záznam vložen.
    * @param highScore
    *       -
    *       nový záznam.
    */
   public void updateHighScore(int index, HighScoreRecord highScore) {
      if (index >= 0 && index < highScoresCount) {
         highScoreRecords[index] = highScore;
         sort();
         saveHighScores(getClass().getResource("../tetris.dat"));
      }
   }

   /**
    * Uloží data o nejlepších výsledcích na URL dané objektem
    * <code>resoucre</code>.
    *
    * @param resource
    *       URL, kam se mají data uložit.
    */
   private void saveHighScores(URL resource) {
      saveHighScores(resource.getPath());
   }

   /**
    * Kontroluje, zda-li je možné vložit nový záznam mezi nejlepší výsledky na
    * základě jeho konečného skóre.
    *
    * @param finalScore
    *       konečné skóre kontrolovaného záznamu.
    * @return Pokud exisuje existuje alespoň jeden záznam, jehož konečné skóre
    * je nižší, než konečné skóre kontrolovaného záznamu, je vrácen
    * index toho nejvyššího z nich. Pokud takový záznam neexisuje, je
    * vrácena hodnota <code>-1</code>, což signalizuje, že
    * kontrolovaný záznam nelze mezi nejlepší výsledky zařadit.
    */
   public int checkInsertability(int finalScore) {
      for (int i = highScoresCount - 1; i >= 0; i--) {
         if (finalScore > highScoreRecords[i].getFinalScore()) {
            return i;
         }
      }
      return -1;
   }

   /**
    * Uspořádá záznamy nejlepších výsledků sestupně podle hodnoty konečného
    * skóre.
    */
   private void sort() {
      for (int i = 0; i < highScoresCount - 1; i++) {
         for (int j = 0; j < highScoresCount - 1 - i; j++) {
            if (highScoreRecords[j].getFinalScore() < highScoreRecords[j + 1]
                  .getFinalScore()) {
               HighScoreRecord hlp = highScoreRecords[j];
               highScoreRecords[j] = highScoreRecords[j + 1];
               highScoreRecords[j + 1] = hlp;
            }
         }
      }
   }

   /**
    * Načte data o nejlepších výsledcích ze souboru určeného cestou
    * <code>filePath</code>.
    *
    * @param filePath
    *       cesta k soubru, od kud se mají data číst.
    * @throws IOException
    *       pokud se vyskytne jakákoliv I/O vyjímka.
    */
   public void loadHighScores(String filePath) throws IOException {

      BufferedReader br = new BufferedReader(new FileReader(filePath));

      highScoresCount = Integer.parseInt(br.readLine());
      clear();

      String line;
      for (int i = 0; i < highScoresCount; i++) {
         line = Encryptor.decrypt(br.readLine());
         StringTokenizer st = new StringTokenizer(line, ";");
         highScoreRecords[i] = new HighScoreRecord(st.nextToken(), Integer
               .parseInt(st.nextToken()));
      }
      sort();

   }

   /**
    * Uloží data o nejlepších výsledcích do souboru určeného cestou
    * <code>filePath</code>.
    *
    * @param filePath
    *       cesta k soubru, kam se mají data uložit.
    */
   public void saveHighScores(String filePath) {
      try {
         BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
         bw.write(String.valueOf(highScoresCount) + "\n");
         for (int i = 0; i < highScoresCount; i++) {
            bw.write(Encryptor.encrypt(highScoreRecords[i].getPlayerName()
                  + ";" + highScoreRecords[i].getFinalScore())
                  + "\n");
         }
         bw.close();
      } catch (IOException e) {
         e.printStackTrace();
      }

   }
}
