package org.pmacik.sewertetris.gui;

import org.pmacik.sewertetris.highscores.HighScores;
import org.pmacik.sewertetris.highscores.gui.HighScoresDialog;
import org.pmacik.sewertetris.highscores.gui.LoadPlayersNameDialog;
import org.pmacik.sewertetris.stones.Stone;
import org.pmacik.sewertetris.stones.StoneBox;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Hlavní okno hry Sewer's Tetris.
 *
 * @author Pavel Macík (xmacik01)
 */
public class TetrisFrame extends JFrame implements KeyListener {
   /**
    *
    */
   private static final long serialVersionUID = -7479996774754255378L;

   /**
    * Jádro hry.
    */
   private Game game;

   /**
    * Krabice, ze které si hra tahá kostky.
    */
   private StoneBox stoneBox;

   /**
    * Hlavní lišta menu.
    */
   private JMenuBar mnbMain;

   /**
    * Uživatelské menu - Hra.
    */
   private JMenu mnuGame;

   /**
    * Uživatelské menu - Možnosti.
    */
   private JMenu mnuOptions;

   /**
    * Uživatelské menu - Obtížnost.
    */
   private JMenu mnuDifficulty;

   /**
    * Položka uživatelského menu - Nová hra.
    */
   private JMenuItem mniNewGame;

   /**
    * Položka uživatelského menu - Pozastavit/Obnovit hru.
    */
   private JMenuItem mniPauseGame;

   /**
    * Položka uživatelského menu - Konec.
    */
   private JMenuItem mniExit;

   /**
    * Položka uživatelského menu - Nejlepší výsledky.
    */
   private JMenuItem mniShowHighScores;

   /**
    * Položka uživatelského menu Obtížnost - Lehká.
    */
   private JCheckBoxMenuItem mniDiffEasy;

   /**
    * Položka uživatelského menu Obtížnost - Střední.
    */
   private JCheckBoxMenuItem mniDiffMedium;

   /**
    * Položka uživatelského menu Obtížnost - Těžká.
    */
   private JCheckBoxMenuItem mniDiffHard;

   /**
    * Položka uživatelského menu - Zvuky.
    */
   private JCheckBoxMenuItem mniSoundControl;

   /**
    * Dialogové okno pro zobrazování tabulky nejlepších výsledků.
    */
   private HighScoresDialog highScoresDialog;

   /**
    * Nejlepší výsledky. Používá se k udržení dat pro tabulku nejlepších
    * výsledků.
    */
   private HighScores highScores;

   /**
    * Dialogové okno pro zadání jména uživatele. Zobrazuje se na konci hry,
    * pokud hráč v právě skončené hře získal dostatečně vysoké konečné skóre,
    * aby se dostal do tabulky nejlepších.
    */
   private LoadPlayersNameDialog loadPlayersNameDialog;

   /**
    * Na základě této hodnoty se buď zvuky přehrávají (pokud nabývá hodnoty
    * <code>true</code>), nebo nepřehrávají (pokud obsahuje hodnotu
    * <code>false</code>).
    */
   private boolean playSounds = true;

   /**
    * Indikátor stavu klávesy. Pokud je nějaká klávesa(y) stisknuta(y),
    * <code>keyDown</code> má hodnotu rovnou kódu poslední stisknuté klávesy;
    * při uvolnění všech kláves nabývá <code>keyDown</code> hodnoty rovné
    * <code>-1</code>. Používá se pro zamezení opakované reakce na stisk
    * klávesy při dlouhodobém držení klávesy stisknuté.
    */
   private int keyDown = -1;

   /**
    * Generátor náhodných čísel. Používá se pro generování náhodných čísel pro
    * všechny náhodné jevy během hry, jakými jsou výběr náhodné kostky z
    * krabice kostek, nebo inicializace herních polí při spouštění nové hry s
    * obtížností vyšší, než lehká.
    */
   private static Random randomGenerator;

   /**
    * Inicializuje atributy, vloží do krabice kostky a vytvoří GUI.
    */
   public TetrisFrame() {
      highScores = new HighScores(getClass().getResource("../tetris.dat"));
      randomGenerator = new Random(System.currentTimeMillis());
      initStoneBox();
      initGUI();
      repaint();
   }

   /**
    * Inicializuje krabici kostek a vloží do ní kostky.
    */
   private void initStoneBox() {
      stoneBox = new StoneBox(this);
      int[][] stoneSType = { { 0, 0, 0, 0 }, { 0, 0, 1, 0 }, { 0, 1, 1, 0 },
            { 0, 1, 0, 0 } };
      stoneBox.addStone(new Stone(stoneSType));
      int[][] stoneZType = { { 0, 0, 0, 0 }, { 0, 2, 0, 0 }, { 0, 2, 2, 0 },
            { 0, 0, 2, 0 } };
      stoneBox.addStone(new Stone(stoneZType));
      int[][] stoneIType = { { 0, 0, 3, 0 }, { 0, 0, 3, 0 }, { 0, 0, 3, 0 },
            { 0, 0, 3, 0 } };
      stoneBox.addStone(new Stone(stoneIType));
      int[][] stoneSquareType = { { 0, 0, 0, 0 }, { 0, 4, 4, 0 },
            { 0, 4, 4, 0 }, { 0, 0, 0, 0 } };
      stoneBox.addStone(new Stone(stoneSquareType));
      int[][] stoneLType = { { 0, 0, 0, 0 }, { 0, 5, 0, 0 }, { 0, 5, 0, 0 },
            { 0, 5, 5, 0 } };
      stoneBox.addStone(new Stone(stoneLType));
      int[][] stoneJType = { { 0, 0, 0, 0 }, { 0, 0, 6, 0 }, { 0, 0, 6, 0 },
            { 0, 6, 6, 0 } };
      stoneBox.addStone(new Stone(stoneJType));
      int[][] stoneTType = { { 0, 0, 0, 0 }, { 0, 7, 0, 0 }, { 7, 7, 7, 0 },
            { 0, 0, 0, 0 } };
      stoneBox.addStone(new Stone(stoneTType));
   }

   /**
    * Inicializuje uživatelské menu.
    */
   private void initMenu() {
      mnbMain = new JMenuBar();

      mnuGame = new JMenu("Hra");
      mniNewGame = new JMenuItem("Nová hra");
      mniNewGame.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            startNewGame();
         }
      });
      mnuGame.add(mniNewGame);

      mniPauseGame = new JMenuItem("Pozastavit hru");
      mniPauseGame.setEnabled(false);
      mniPauseGame.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            switchGameRun();
         }
      });
      mnuGame.add(mniPauseGame);
      mnuGame.addSeparator();

      mniExit = new JMenuItem("Konec");
      mniExit.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            System.exit(0);
         }
      });
      mnuGame.add(mniExit);

      mnbMain.add(mnuGame);

      mnuOptions = new JMenu("Možnosti");
      mniSoundControl = new JCheckBoxMenuItem("Zvuky");
      mniSoundControl.setSelected(playSounds);
      mniSoundControl.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            playSounds = !playSounds;
            mniSoundControl.setState(playSounds);
         }
      });
      mnuOptions.add(mniSoundControl);

      mnuDifficulty = new JMenu("Obtížnost");
      mnuOptions.add(mnuDifficulty);

      ButtonGroup mngDifficulty = new ButtonGroup();

      mniDiffEasy = new JCheckBoxMenuItem("Lehká");
      mniDiffEasy.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            setDifficulty(Game.DIFFICULTY_EASY);
         }
      });
      mniDiffEasy.setState(game.getDifficulty() == Game.DIFFICULTY_EASY);
      mngDifficulty.add(mniDiffEasy);
      mnuDifficulty.add(mniDiffEasy);

      mniDiffMedium = new JCheckBoxMenuItem("Střední");
      mniDiffMedium.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            setDifficulty(Game.DIFFICULTY_MEDIUM);
         }
      });
      mniDiffMedium.setState(game.getDifficulty() == Game.DIFFICULTY_MEDIUM);
      mngDifficulty.add(mniDiffMedium);
      mnuDifficulty.add(mniDiffMedium);

      mniDiffHard = new JCheckBoxMenuItem("Těžká");
      mniDiffHard.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            setDifficulty(Game.DIFFICULTY_HARD);
         }
      });
      mniDiffHard.setState(game.getDifficulty() == Game.DIFFICULTY_HARD);
      mngDifficulty.add(mniDiffHard);
      mnuDifficulty.add(mniDiffHard);

      mnuOptions.addSeparator();
      mniShowHighScores = new JMenuItem("Nejlepší výsledky");
      mniShowHighScores.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            showHighScores();
         }
      });
      mnuOptions.add(mniShowHighScores);

      mnbMain.add(mnuOptions);

      setJMenuBar(mnbMain);
   }

   /**
    * Mění obtížnost hry
    *
    * @param difficulty
    *       nová obtížnost hry
    */
   protected void setDifficulty(int difficulty) {
      game.setDifficulty(difficulty);
      switch (difficulty) {
         case Game.DIFFICULTY_EASY:
            mniDiffEasy.setState(true);
            break;
         case Game.DIFFICULTY_MEDIUM:
            mniDiffMedium.setState(true);
            break;
         case Game.DIFFICULTY_HARD:
            mniDiffHard.setState(true);
            break;
         default:
      }

   }

   /**
    * Ukáže dialogové okno s tabulkou nejlepších výsledků. Pokud právě běží
    * hra, je pozastavena.
    */
   public void showHighScores() {
      if (game.isGameRunning()) {
         switchGameRun();
      }
      highScoresDialog.setVisible(true);
   }

   /**
    * Pokud hra běží, je pozastavena; pokud je pozastavena, je obnovena.
    */
   protected void switchGameRun() {
      game.setPausedGame(game.isGameRunning());
      mniPauseGame.setText((game.isGameRunning()) ? "Pozastavit Hru"
            : "Obnovit hru");
   }

   /**
    * Spustí se nová hra.
    */
   protected void startNewGame() {
      mniPauseGame.setEnabled(true);
      game.newGame();
   }

   /**
    * Inicializace GUI
    */
   private void initGUI() {
      setTitle("Sewer's Tetris v " + Game.VERSION);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      game = new Game(stoneBox, this);
      game.setLocation(8, 4);

      getContentPane().add(game, BorderLayout.CENTER);

      initMenu();

      highScoresDialog = new HighScoresDialog(190, 160, highScores);
      loadPlayersNameDialog = new LoadPlayersNameDialog(this);

      addKeyListener(this);
      int frameWidth = 40 + (Game.GAME_FIELD_COLS + 4)
            * Game.STONE_SEGMENT_SIZE;
      int frameHeight = 80 + Game.GAME_FIELD_ROWS * Game.STONE_SEGMENT_SIZE;
      setSize(frameWidth, frameHeight);

      WindowManipulator.centerWindow(this);

      setResizable(false);
      setVisible(true);
   }

   /*
    * (non-Javadoc)
    *
    * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
    */
   public void keyPressed(KeyEvent e) {
      int key = e.getKeyCode();
      if (keyDown != key) {
         game.modifyStone(key);
      }
      if (keyDown < 0) {
         keyDown = key;
      }
   }

   /*
    * (non-Javadoc)
    *
    * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
    */
   public void keyTyped(KeyEvent e) {
      // nop
   }

   /*
    * (non-Javadoc)
    *
    * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
    */
   public void keyReleased(KeyEvent e) {
      if (keyDown == e.getKeyCode()) {
         keyDown = -1;
      }
   }

   /**
    * Poskytuje referenci na objekt obsahující data pro tabulku nejlepších
    * výsledků.
    *
    * @return reference na objekt obsahující nejlepší výsledky.
    */
   public HighScores getHighScores() {
      return highScores;
   }

   /**
    * Poskytne referenci na objekt dialogového okna pro zadání jména hráče,
    * který má být spolu s konečným skórem právě skončené hry, pokud toto skóre
    * je dostatečně vysoké, aby se do tabulky nejlepších výsledků mohlo zapsat.
    *
    * @return reference na objekt dialogového okna pro zadání jména hráče.
    */
   public LoadPlayersNameDialog getLoadPlayersNameDialog() {
      return loadPlayersNameDialog;
   }

   /**
    * Poskytne informaci o tom, jestli je přehrávání zvuků zapnuté či vypnuté.
    *
    * @return <code>true</code>, pokud je přehrávání zvuků v aplikaci
    * zapnuto<br>
    * <code>false</code>, pokud je přehrávání zvuků vypnuto.
    */
   public boolean playSounds() {
      return playSounds;
   }

   /**
    * Poskytuje referenci na generátor náhodných čísel, který je společný pro
    * všechny objekty, u nichž se vyskytují náhodné jevy.
    *
    * @return reference na generátor náhodných čísel
    * @see #randomGenerator
    */
   public Random getRandomGenerator() {
      return randomGenerator;
   }
}