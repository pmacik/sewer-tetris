package net.macsewer.vutbr.gja.tetris.gui;

import net.macsewer.vutbr.gja.tetris.sound.Sound;
import net.macsewer.vutbr.gja.tetris.stones.AbstractStone;
import net.macsewer.vutbr.gja.tetris.stones.Stone;
import net.macsewer.vutbr.gja.tetris.stones.StoneBox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Třída hlavního hracího pole hry Sewer's Tetris.
 *
 * @author Pavel Macík (xmacik01)
 */
public class Game extends JPanel {

   /**
    *
    */
   private static final long serialVersionUID = 6507546054144002594L;

   /**
    * Výchozí perioda časovače v ms.
    */
   public static final int INITIAL_TIMER_PERIOD = 800;

   /**
    * Počet řádků hracího pole.
    */
   public static final int GAME_FIELD_ROWS = 20;

   /**
    * Počet sloupců hracího pole.
    */
   public static final int GAME_FIELD_COLS = 10;

   /**
    * Obtížnost hry - Lehká.
    */
   public static final int DIFFICULTY_EASY = 0;

   /**
    * Obtížnost hry - Střední.
    */
   public static final int DIFFICULTY_MEDIUM = 1;

   /**
    * Obtížnost hry - Těžká.
    */
   public static final int DIFFICULTY_HARD = 2;

   /**
    * Klesnutí hrací kostky způsobené časovačem.
    */
   protected static final int MODIFIED_BY_TIMER = 1;

   /**
    * Klesnutí hrací kostky způsobené padáním kostky, které je iniciováno
    * hráčem stisknutím příslušné klávesy.
    */
   protected static final int MODIFIED_BY_STONE_FALL = 2;

   /**
    * Kód klávesy pro rotaci kostky.
    */
   protected static final int STONE_ROTATION_KEY_CODE = 38;

   /**
    * Kód klávesy pro pohyb kostky doleva.
    */
   protected static final int STONE_LEFT_KEY_CODE = 37;

   /**
    * Kód klávesy pro pohyb kostky doprava.
    */
   protected static final int STONE_RIGHT_KEY_CODE = 39;

   /**
    * Kód klávesy pro padání kostky (směrem dolů :)).
    */
   protected static final int STONE_DOWN_KEY_CODE = 40;

   /**
    * Rozměr segmentu kostky v px.
    */
   public static final int STONE_SEGMENT_SIZE = 20;

   /**
    * Zobrazované pole, na základě kterého se vykresluje hrací pole do
    * <code><b>frameBuffer</b></code>u. Hlavní hrací pole.
    */
   private int dislplayField[][];

   /**
    * Pole pozadí. Toto pole se používá k detekci kolizí padající kostky se
    * zbytkem hracího pole. Po pádu kostky a případném odstranění plných řádků
    * je do toho pole zkopírováno zobrazované pole.
    */
   private int backgroundField[][];

   /**
    * Nápověda, která ukazuje, která kostka příjde jako další.
    */
   private HelpField helpField;

   /**
    * Barevný buffer, který se vykresluje do hlavního okna aplikace.
    *
    * @see #render()
    */
   private BufferedImage frameBuffer;

   /**
    * Obrázek segmentu kostky.
    */
   protected BufferedImage imgStone0, imgStone1, imgStone2, imgStone3,
         imgStone4, imgStone5, imgStone6;

   /**
    * Říká, jestli je konec jednoho kola hry; za jedno kolo hry se považuje
    * doba, po kterou se pohybuje kostka v hracím poli od svého vložení až do
    * chvíle kdy spadne, čímž kolo končí (pokud jsou po pádu kostky nějaké plné
    * řádky, kolo končí až po jejich odstranění).
    */
   private boolean endRound;

   /**
    * Říká jestli hra běží. Hra běží od okamžiku, kdy je spuštěna nová hra
    * (případně obnovena hra pozastavená), a přestane běžet, když je hra
    * pozastavena, nebo hra skončí.
    */
   private boolean gameRunning;

   /**
    * Logická proměnná, která říká jestli padá kostka. Kostka padá, když je
    * hráčem stisknuta klávesa pro spadnutí kostky, dokud nedopadne.
    */
   private boolean stoneFalling;

   /**
    * Dílčí skóre, které je kumulativně určeno počtem odstraněných řádků v
    * rámci každého kola.
    *
    * @see #getScore(int)
    */
   private int score;

   /**
    * Počet řádků, které byly odstraněny v průběhu právě probíhající hry od
    * začátku hry.
    */
   private int lines;

   /**
    * Hodnocení hry, které je určováno na základě kvality hry hráče. Pokud hráč
    * hraje takovým způsobem, že odsraňuje řádky pouze po jednom, hodnocení
    * jeho hry je 0%. Oproti tomu hra, při které hráč odstraňuje řádky pouze po
    * čtveřicích, je hodnocena 100%. Pokud hráč hraje tak, že odstraňuje řádky
    * různě, pak hodnocení hry nabývá hodnot z intervalu (0%; 100%). Jeho
    * hodnota je určena vztahem: <br>
    * <code>(score / lines - 100)* 100 / 275</code><br>
    * vzhledem k ohodnocení odstranění jednotlivých skupin odstraňených řádků.
    *
    * @see #getScore(int)
    */
   private float rating;

   /**
    * Konečné skóre, které je určeno součinem dílčího skóre a hodnocení hry.
    */
   private int finalScore;

   /**
    * Perioda časovače.
    */
   private int delay;

   /**
    * Úroveň hry, která je určena dílčím skórem, resp. v kterém intervalu se
    * hodnota dílčího skóre nachází (intervaly mají konstantní délku 10 000
    * bodů).
    */
   private int gameLevel;

   /**
    * Kostka, která se objeví v příštím kole hry v hracím poli.
    */
   private AbstractStone nextStone;

   /**
    * Aktuální kostka, která se pohybuje hracím polem.
    */
   private AbstractStone currentStone;

   /**
    * Krabice, ze které si hra tahá kostky.
    */
   private StoneBox stoneBox;

   /**
    * Časovač pro běh hry.
    */
   private Timer timer;

   /**
    * Popisek, ve kterém se zobrazuje stav skóre.
    */
   private JLabel lblScore;

   /**
    * Reference na hlavní okno aplikace.
    */
   private TetrisFrame application;

   /**
    * Zvuk, která se přehrává při pohyhu kostky.
    *
    * @see #playSound(Sound)
    */
   private Sound sndStoneMove;

   /**
    * Zvuk, která se přehrává při dopadu kostky.
    *
    * @see #playSound(Sound)
    */
   private Sound sndStoneSubFall;

   /**
    * Zvuk, která se přehrává při odstranění plných řádků.
    *
    * @see #playSound(Sound)
    */
   private Sound sndLineDestroy;

   /**
    * Zvuk, která se přehrává při spuštění nové hry.
    *
    * @see #playSound(Sound)
    */
   private Sound sndNewGame;

   /**
    * Zvuk, která se přehrává při konci hry.
    *
    * @see #playSound(Sound)
    */
   private Sound sndGameOver;

   /**
    * Aktuální obtížnost hry.
    */
   private int gameDifficulty = Game.DIFFICULTY_EASY;

   /**
    * Vytvoří a inicializuje tetris.
    *
    * @param stoneBox
    *       -
    *       reference na krabici kostek.
    * @param application
    *       -
    *       reference na hlavní okno aplikace.
    */
   public Game(StoneBox stoneBox, TetrisFrame application) {
      this.application = application;
      this.stoneBox = stoneBox;

      lblScore = new JLabel();
      add(lblScore, BorderLayout.NORTH);

      dislplayField = new int[GAME_FIELD_COLS + 4][GAME_FIELD_ROWS + 2];
      backgroundField = new int[GAME_FIELD_COLS + 4][GAME_FIELD_ROWS + 2];

      frameBuffer = new BufferedImage(GAME_FIELD_COLS * STONE_SEGMENT_SIZE,
            GAME_FIELD_ROWS * STONE_SEGMENT_SIZE,
            BufferedImage.TYPE_INT_RGB);

      helpField = new HelpField(this);
      helpField.setLocation(GAME_FIELD_COLS * STONE_SEGMENT_SIZE + 16, 22);
      initStoneImages();
      this.setSize((GAME_FIELD_COLS + 4) * STONE_SEGMENT_SIZE + 16,
            GAME_FIELD_ROWS * STONE_SEGMENT_SIZE);

      initSounds();

      timer = new Timer(INITIAL_TIMER_PERIOD, new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            modifyStone(MODIFIED_BY_TIMER);
         }
      });
      timer.stop();

      render();
      printActualScore();
   }

   /**
    * Inicializuje zvuky.
    */
   private void initSounds() {
      sndStoneMove = new Sound(getClass().getResource(
            "../sounds/stone_move.wav"));
      sndStoneSubFall = new Sound(getClass().getResource(
            "../sounds/stone_sub_fall.wav"));
      sndLineDestroy = new Sound(getClass().getResource(
            "../sounds/line_destroy.wav"));
      sndNewGame = new Sound(getClass().getResource("../sounds/new_game.wav"));
      sndGameOver = new Sound(getClass().getResource(
            "../sounds/game_over.wav"));
   }

   /**
    * Inicializuje obrázky segmentů kostek, které se používají při vykreslování
    * kostek.. Načítá je z příslušných souborů; pokud se načítání ze souborů
    * nezdaří, přiřadí jim hodnotu <code>null</code>.
    */
   private void initStoneImages() {
      try {
         imgStone0 = ImageIO.read(getClass().getResource("images/s0.png"));
         imgStone1 = ImageIO.read(getClass().getResource("images/s1.png"));
         imgStone2 = ImageIO.read(getClass().getResource("images/s2.png"));
         imgStone3 = ImageIO.read(getClass().getResource("images/s3.png"));
         imgStone4 = ImageIO.read(getClass().getResource("images/s4.png"));
         imgStone5 = ImageIO.read(getClass().getResource("images/s5.png"));
         imgStone6 = ImageIO.read(getClass().getResource("images/s6.png"));

      } catch (Exception e) {
         imgStone0 = null;
         imgStone1 = null;
         imgStone2 = null;
         imgStone3 = null;
         imgStone4 = null;
         imgStone5 = null;
         imgStone6 = null;
      }
   }

   /**
    * Vrací aktuální kostku.
    */
   public AbstractStone getActualStone() {
      return currentStone;
   }

   /**
    * Vrací příští kostku.
    */
   public AbstractStone getNextStone() {
      return nextStone;
   }

   /**
    * Začne novou hru.
    */
   public void newGame() {
      gameRunning = true;
      score = 0;
      lines = 0;
      rating = 0;
      finalScore = 0;
      delay = INITIAL_TIMER_PERIOD - gameDifficulty * 200;
      gameLevel = 1;
      nextStone = stoneBox.getRandomStone();

      initGameFields();

      if (application.playSounds()) {
         playSound(sndNewGame);
      }
      timer.start();
      newStone();
   }

   /**
    * Inicializuje zobrazované pole a pole pozadí. Inicializace spočívá v tom,
    * že se první a poslední dva sloupce a poslední dva řádky polí vyplní
    * hodnotou <code>1</code>, ty ostatní hodnotou <code>0</code>. Ty
    * prvky, které obsahují <code>0</code> jsou vlastní hrací pole, které se
    * zobrazuje. Ty, které mají hodnotu <code>1</code>, se nezobrazují, ale
    * jsou zde nutné, kvůli detekci kolize kostky se stěnami a dnem hracího
    * pole.
    *
    * @see #dislplayField
    * @see #backgroundField
    */
   private void initGameFields() {
      for (int i = 2; i < GAME_FIELD_COLS + 2; i++) {
         for (int j = 0; j < GAME_FIELD_ROWS; j++) {
            backgroundField[i][j] = 0;
            dislplayField[i][j] = 0;
         }
      }

      for (int j = 0; j < GAME_FIELD_ROWS; j++) {
         backgroundField[0][j] = 1;
         backgroundField[1][j] = 1;
         backgroundField[GAME_FIELD_COLS + 2][j] = 1;
         backgroundField[GAME_FIELD_COLS + 3][j] = 1;
         dislplayField[0][j] = 1;
         dislplayField[1][j] = 1;
         dislplayField[GAME_FIELD_COLS + 2][j] = 1;
         dislplayField[GAME_FIELD_COLS + 3][j] = 1;
      }

      for (int i = 0; i < GAME_FIELD_COLS + 4; i++) {
         backgroundField[i][GAME_FIELD_ROWS] = 1;
         backgroundField[i][GAME_FIELD_ROWS + 1] = 1;
         dislplayField[i][GAME_FIELD_ROWS] = 1;
         dislplayField[i][GAME_FIELD_ROWS + 1] = 1;
      }

      for (int i = 0; i < GAME_FIELD_COLS; i++) {
         for (int j = GAME_FIELD_ROWS - gameDifficulty * 5; j < GAME_FIELD_ROWS; j++) {
            if ((application.getRandomGenerator().nextInt(1000)) % 3 != 0) {
               backgroundField[i + 2][j] = (application
                     .getRandomGenerator().nextInt(stoneBox
                           .getStonesCount()));
               dislplayField[i + 2][j] = backgroundField[i + 2][j];
            }
         }
      }
   }

   /**
    * Vloží novou kostku do hracího pole po dopadu předchozí kostky, nebo na
    * začátku nové hry.
    */
   private void newStone() {
      endRound = true;
      if ((score / 1e4) > gameLevel) {
         gameLevel++;
         delay = (int) (delay * 0.75f);
      }
      stoneFalling = false;

      for (int i = 2; i < GAME_FIELD_COLS + 2; i++) {
         System.arraycopy(dislplayField[i], 0, backgroundField[i], 0,
               GAME_FIELD_ROWS);
      }

      currentStone = new Stone((Stone) nextStone);
      nextStone = stoneBox.getRandomStone();
      if (checkStone(currentStone)) {
         helpField.renderStone(nextStone);
      } else {
         gameOver();
      }
      timer.setDelay(delay);
   }

   /**
    * Kontroluje, jesli je možno kostku umístit. Má funkci detekce kolize
    * kostky <code>stone</code>.
    *
    * @param stone
    *       -
    *       kontrolovaná kostka.
    * @return <code>true</code>, pokud ke kolizi nedošlo a kostku je možno
    * umístit.<br>
    * <code>false</code>, pokud došlo ke kolizi, a tudíž nelze
    * kostku umístit
    */
   private boolean checkStone(AbstractStone stone) {
      for (int i = stone.getX(), x = 0; i < (stone.getX() + 4); i++, x++) {
         for (int j = stone.getY(), y = 0; j < (stone.getY() + 4); j++, y++) {
            if ((backgroundField[i][j] != 0) && (stone.get(x, y) != 0)) {
               return false;
            }
         }
      }
      return true;
   }

   /**
    * Ukončí hru.
    */
   private void gameOver() {
      gameRunning = false;
      timer.stop();
      if (application.playSounds()) {
         playSound(sndGameOver);
      }

      int index = application.getHighScores().checkInsertability(finalScore);
      if (index >= 0) {
         application.getLoadPlayersNameDialog().show(finalScore);
      }
   }

   /*
    * (non-Javadoc)
    *
    * @see javax.swing.JComponent#paint(java.awt.Graphics)
    */
   @Override
   public void paint(Graphics g) {
      super.paint(g);
      g.drawImage(frameBuffer, 8, 22, null);
      g.drawImage(helpField.getFrameBuffer(), helpField.getX(), helpField
            .getY(), null);
   }

   /**
    * Vykresluje hrací pole.
    */
   public void render() {
      for (int i = 2; i < GAME_FIELD_COLS + 2; i++) {
         for (int j = 0; j < GAME_FIELD_ROWS; j++) {
            Graphics g = frameBuffer.getGraphics();
            switch (dislplayField[i][j]) {
               case 0:
                  g.setColor(new Color(0x000000));
                  g.fillRect(STONE_SEGMENT_SIZE * (i - 2), STONE_SEGMENT_SIZE
                        * j, STONE_SEGMENT_SIZE, STONE_SEGMENT_SIZE);
                  break;
               case 1:
                  if (imgStone0 != null) {
                     g.drawImage(imgStone0, STONE_SEGMENT_SIZE * (i - 2),
                           STONE_SEGMENT_SIZE * j, null);
                  } else {
                     g.setColor(new Color(0xff0000));
                     g.fillRect(STONE_SEGMENT_SIZE * (i - 2),
                           STONE_SEGMENT_SIZE * j, STONE_SEGMENT_SIZE,
                           STONE_SEGMENT_SIZE);
                  }
                  break;
               case 2:
                  if (imgStone1 != null) {
                     g.drawImage(imgStone1, STONE_SEGMENT_SIZE * (i - 2),
                           STONE_SEGMENT_SIZE * j, null);
                  } else {
                     g.setColor(new Color(0x00ff00));
                     g
                           .fillRect(i, j, STONE_SEGMENT_SIZE,
                                 STONE_SEGMENT_SIZE);
                  }
                  break;
               case 3:
                  if (imgStone2 != null) {
                     g.drawImage(imgStone2, STONE_SEGMENT_SIZE * (i - 2),
                           STONE_SEGMENT_SIZE * j, null);
                  } else {
                     g.setColor(new Color(0x0000ff));
                     g.fillRect(STONE_SEGMENT_SIZE * (i - 2),
                           STONE_SEGMENT_SIZE * j, STONE_SEGMENT_SIZE,
                           STONE_SEGMENT_SIZE);
                  }
                  break;
               case 4:
                  if (imgStone3 != null) {
                     g.drawImage(imgStone3, STONE_SEGMENT_SIZE * (i - 2),
                           STONE_SEGMENT_SIZE * j, null);
                  } else {
                     g.setColor(new Color(0x00ffff));
                     g.fillRect(STONE_SEGMENT_SIZE * (i - 2),
                           STONE_SEGMENT_SIZE * j, STONE_SEGMENT_SIZE,
                           STONE_SEGMENT_SIZE);
                  }
                  break;
               case 5:
                  if (imgStone4 != null) {
                     g.drawImage(imgStone4, STONE_SEGMENT_SIZE * (i - 2),
                           STONE_SEGMENT_SIZE * j, null);
                  } else {
                     g.setColor(new Color(0xff00ff));
                     g.fillRect(STONE_SEGMENT_SIZE * (i - 2),
                           STONE_SEGMENT_SIZE * j, STONE_SEGMENT_SIZE,
                           STONE_SEGMENT_SIZE);
                  }
                  break;
               case 6:
                  if (imgStone5 != null) {
                     g.drawImage(imgStone5, STONE_SEGMENT_SIZE * (i - 2),
                           STONE_SEGMENT_SIZE * j, null);
                  } else {
                     g.setColor(new Color(0xffff00));
                     g.fillRect(STONE_SEGMENT_SIZE * (i - 2),
                           STONE_SEGMENT_SIZE * j, STONE_SEGMENT_SIZE,
                           STONE_SEGMENT_SIZE);
                  }
                  break;
               case 7:
                  if (imgStone6 != null) {
                     g.drawImage(imgStone6, STONE_SEGMENT_SIZE * (i - 2),
                           STONE_SEGMENT_SIZE * j, null);
                  } else {
                     g.setColor(new Color(0xffffff));
                     g.fillRect(STONE_SEGMENT_SIZE * (i - 2),
                           STONE_SEGMENT_SIZE * j, STONE_SEGMENT_SIZE,
                           STONE_SEGMENT_SIZE);
                  }
                  break;
            }
         }
      }
      repaint();
   }

   /**
    * Provede s kostkou příslu operaci (posun, rotace, shození).
    *
    * @param key
    *       -
    *       klíč, na základě kterého se provádí operace s kostkou (viz
    *       následující tabulka)<br>
    *       <table border="1">
    *       <tr>
    *       <th>Hodnota <code>key</code></th>
    *       <th>Operace s kostkou</th>
    *       </tr>
    *       <tr>
    *       <td>{@link #STONE_LEFT_KEY_CODE}</td>
    *       <td>Pohyb kostky doleva</td>
    *       </tr>
    *       <tr>
    *       <td>{@link #STONE_RIGHT_KEY_CODE}</td>
    *       <td>Pohyb kostky doprava</td>
    *       </tr>
    *       <tr>
    *       <td>{@link #STONE_ROTATION_KEY_CODE}</td>
    *       <td>Rotace kostky</td>
    *       </tr>
    *       <tr>
    *       <td>{@link #STONE_DOWN_KEY_CODE}</td>
    *       <td>Iniciování shození kostky</td>
    *       </tr>
    *       <tr>
    *       <td>{@link #MODIFIED_BY_STONE_FALL}</td>
    *       <td>Klesnutí kostky zpusobené padáním kostky</td>
    *       </tr>
    *       <tr>
    *       <td>{@link #MODIFIED_BY_TIMER}</td>
    *       <td>Klesnutí kostky zpusobené vypršením časovače</td>
    *       </tr>
    *
    *       </table>
    */
   public void modifyStone(int key) {
      if (gameRunning) {
         Stone tmp = new Stone((Stone) currentStone);
         if (key == STONE_LEFT_KEY_CODE) {
            tmp.setX(tmp.getX() - 1);
            if ((currentStone.getX() > 0) && (checkStone(tmp))) {
               currentStone = tmp;
            }
         } else if (key == STONE_ROTATION_KEY_CODE && !stoneFalling) {
            tmp.rotate();
            if ((checkStone(tmp))) {
               currentStone = tmp;
            }
         } else if (key == STONE_RIGHT_KEY_CODE && !stoneFalling) {
            tmp.setX(tmp.getX() + 1);
            if ((currentStone.getX() < GAME_FIELD_COLS)
                  && (checkStone(tmp))) {
               currentStone = tmp;
            }
         } else {
            if (key == STONE_DOWN_KEY_CODE && !stoneFalling) {
               stoneFalling = true;
               timer.setDelay(1);
               modifyStone(2);
               return;
            }
            if ((key == STONE_DOWN_KEY_CODE && !stoneFalling)
                  || key == MODIFIED_BY_TIMER
                  || key == MODIFIED_BY_STONE_FALL) {
               tmp.setY(tmp.getY() + 1);
               if ((currentStone.getY() < (GAME_FIELD_ROWS - 2))
                     && (checkStone(tmp))) {
                  currentStone = tmp;
                  endRound = false;
               } else {
                  if (!endRound) {
                     linesCheck();
                     newStone();
                  } else {
                     gameOver();
                  }
               }
            }
         }
         tmp = null;

         for (int i = 0; i < (GAME_FIELD_COLS + 4); i++) {
            System.arraycopy(backgroundField[i], 0, dislplayField[i], 0,
                  GAME_FIELD_ROWS + 2);
         }

         for (int i = currentStone.getX(), ii = 0; (i < (currentStone.getX() + 4)); i++, ii++) {
            for (int j = currentStone.getY(), jj = 0; (j < (currentStone
                  .getY() + 4)); j++, jj++) {
               if (backgroundField[i][j] == 0) {
                  dislplayField[i][j] = currentStone.get(ii, jj);
               }
            }
         }

         // grafika
         if (!stoneFalling) {
            render();
            if (key == STONE_LEFT_KEY_CODE || key == STONE_RIGHT_KEY_CODE
                  || key == STONE_ROTATION_KEY_CODE) {
               if (application.playSounds()) {
                  playSound(sndStoneMove);
               }
            } else if (key == MODIFIED_BY_TIMER) {

            }
            printActualScore();
            repaint();
         }
         // /grafika
         if (!endRound && key == MODIFIED_BY_STONE_FALL) {
            modifyStone(MODIFIED_BY_STONE_FALL);

            if (application.playSounds()) {
               playSound(sndStoneSubFall);
            }

         }
      }
   }

   /**
    * Vypíše hodnoty aktuálního skóre.
    */
   private void printActualScore() {
      lblScore.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
      lblScore.setText("Skóre: " + String.valueOf(score) + "   Linek: "
            + String.valueOf(lines) + "   Rating: "
            + String.valueOf(((float) ((int) (rating * 100)) / 100)) + "%"
            + "   Celkem: " + String.valueOf(finalScore));
   }

   /**
    * Hledá úplné řádky, a pokud nějaké najde, odstraní je a přepočítá skóre.
    */
   private void linesCheck() {
      boolean fullLine;
      int count = 0;
      // tabulka, která ukazuje, které linky se mají smazat
      int[] tab = new int[4];
      for (int j = GAME_FIELD_ROWS - 1; j > 0; j--) {
         fullLine = true;
         for (int i = 2; i < (GAME_FIELD_COLS + 2); i++) {
            if (dislplayField[i][j] == 0) {
               fullLine = false;
               break;
            }
         }
         if (fullLine) {
            tab[count++] = j;// ulož do tabulky číslo linky
         }
         if (count > 3) {
            break; // zkonči, když je počet linek 4
         }
      }

      if (count > 0) {
         for (int i = 0; i < count; i++) {
            destroyLine(tab[i]);
            if (i < 3) {
               // korekce tabulky vzhledem k posunu při mazání linky
               tab[i + 1] += i + 1;
            }
         }

         if (application.playSounds()) {
            playSound(sndLineDestroy);
         }

      }
      computeScore(count);
   }

   /**
    * Přepočítá skóre na základě počtu práve odstraněných úplných řádků.
    *
    * @param count
    *       -
    *       počet odstraněných řádků.
    */
   private void computeScore(int count) {
      lines += count;
      score += getScore(count);
      if (lines != 0) {
         rating = ((((float) score / lines) - 100) / 275) * 100;
      }
      finalScore = (int) (score * (rating / 100));
   }

   /**
    * Přehraje zvuk.
    *
    * @param snd
    *       -
    *       zvuk, který má být přehrán.
    */
   private void playSound(Sound snd) {
      snd.play();
   }

   /**
    * Smaže jeden řádek v hracím poli.
    *
    * @param lineIndex
    *       -
    *       pozice řádku, který má být smazán.
    */
   private void destroyLine(int lineIndex) {
      for (int k = lineIndex; k > 0; k--) {
         for (int i = 0; i < (GAME_FIELD_COLS + 4); i++) {
            dislplayField[i][k] = dislplayField[i][k - 1];
         }
      }
      render();
   }

   /**
    * Počítá skóre na základě počtu odstraněných řádků.
    *
    * @param linesCount
    *       -
    *       počet odstraněných řádků. <br>
    * @return Skóre na základě počtu odstraněných řádků. (viz tabulka)<br>
    * <table border="1">
    * <tr>
    * <td><code>linesCount</code></td>
    * <td>Skóre</td>
    * </tr>
    * <tr>
    * <td>1</td>
    * <td>100</td>
    * </tr>
    * <tr>
    * <td>2</td>
    * <td>300</td>
    * </tr>
    * <tr>
    * <td>3</td>
    * <td>700</td>
    * </tr>
    * <tr>
    * <td>4</td>
    * <td>1500</td>
    * </tr>
    * </table>
    */
   private static int getScore(int linesCount) {
      switch (linesCount) {
         case 1:
            return 100;
         case 2:
            return 300;
         case 3:
            return 700;
         case 4:
            return 1500;
         default:
            return 0;
      }
   }

   /**
    * Zastaví/spustí právě probíhající hru.
    *
    * @param pause
    */
   public void setPausedGame(boolean pause) {
      if (pause) {
         timer.stop();
         gameRunning = false;
      } else {
         gameRunning = true;
         timer.start();
      }
   }

   /**
    * Zjistí, jestli hra běží.
    *
    * @return <code>true</code>, pokud hra běží<br>
    * <code>false</code>, pokud je hra zastavená.
    */
   public boolean isGameRunning() {
      return timer.isRunning();
   }

   /**
    * Mění obtížnost hry.
    *
    * @param difficulty
    *       -
    *       nová obtížnost hry. Může nabývat hodnot
    *       {@link #DIFFICULTY_EASY}, {@link #DIFFICULTY_MEDIUM} nebo
    *       {@link #DIFFICULTY_HARD}.
    */
   protected void setDifficulty(int difficulty) {
      gameDifficulty = difficulty;
   }

   /**
    * Zjišťuje aktuální obtížnost hry.
    *
    * @return Vrací {@link #DIFFICULTY_EASY}, pokud je nastavena lehká
    * obtížnost hry, {@link #DIFFICULTY_MEDIUM}, pokud hra má střední
    * obtížnost, nebo {@link #DIFFICULTY_HARD}, pokud se hraje s
    * těžkou obtížností.
    */
   public int getDifficulty() {
      return gameDifficulty;
   }

}