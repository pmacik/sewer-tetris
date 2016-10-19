package net.macsewer.vutbr.gja.tetris.highscores.gui;

import net.macsewer.vutbr.gja.tetris.gui.TetrisFrame;
import net.macsewer.vutbr.gja.tetris.gui.WindowManipulator;
import net.macsewer.vutbr.gja.tetris.highscores.HighScoreRecord;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author Pavel Macík
 */
public class LoadPlayersNameDialog extends JDialog {

   /**
    *
    */
   private static final long serialVersionUID = -5677280879729107752L;

   /**
    * Panel obsahující textová pole.
    */
   private JPanel pnlTextFields = new JPanel();

   /**
    * Panel obsahující tlačítka.
    */
   private JPanel pnlButtons = new JPanel();

   /**
    * Textové pole pro zadání jména hráče, jehož výsledek z právě skončené hry
    * se má vložit do tabulky nejlepších.
    */
   private JTextField txtPlayerName = new JTextField(25);

   /**
    * Tlačítko - OK.<br>
    * Po jeho stisknutí se přidá záznam do tabulky nejlepších výsledků, který
    * obsahuje vyplněné jméno hráče a dosažené konečné skóre právě skončené
    * hry, a zobrazí dialogové okno s aktualizovanou tabulkou nejlepších
    * výslekdů.
    */
   private JButton btnOK = new JButton("OK");

   /**
    * Tlačítko - Zrušit.<br>
    * Po jeho stisknutí se dialogové okno zavře bez toho, aby se ukládal
    * výsledek právě skončené hry.
    */
   private JButton btnCancel = new JButton("Zrušit");

   /**
    * Reference na objekt hlavního okna aplikace.
    */
   private TetrisFrame application;

   /**
    * Textové pole, do kterého je hráči vypisována zpráva výsledku jeho právě
    * skončené hry.
    */
   private JTextArea txaText = new JTextArea();

   /**
    * Konečné skóre, které hráč získal ve své právě skončené hře; toto skóre
    * bude, pokud to bude potvrzeno hráčem stiskem tlačítka OK, spolu s jeho
    * jménem přídáno do tabulky nejlepších výsledků.
    */
   private int finalScore = 0;

   /**
    * Vytvoří a zinicializuje dialogové okno.
    *
    * @param application
    *       reference na objekt hlavního okna aplikace.
    */
   public LoadPlayersNameDialog(TetrisFrame application) {
      this.application = application;
      initGUI();
   }

   /**
    * Inicializuje GUI.
    */
   private void initGUI() {
      setTitle("Nový záznam do tabulky nejlepších výsledků");
      setDefaultCloseOperation(HIDE_ON_CLOSE);

      pnlTextFields.setLayout(new GridLayout(3, 1));
      txaText.setRows(2);
      txaText.setEditable(false);

      pnlTextFields.add(txaText);
      pnlTextFields.add(new JLabel("Zadej svoje jméno"));
      txtPlayerName.addKeyListener(new KeyAdapter() {
         @Override
         public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
               execute();
            }
         }
      });
      pnlTextFields.add(txtPlayerName);

      getContentPane().add(pnlTextFields, BorderLayout.CENTER);

      btnOK.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            execute();
         }
      });

      btnCancel.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            setVisible(false);
         }
      });

      pnlButtons.setLayout(new FlowLayout(FlowLayout.CENTER));
      pnlButtons.add(btnOK);
      pnlButtons.add(btnCancel);
      getContentPane().add(pnlButtons, BorderLayout.SOUTH);
      pack();

      WindowManipulator.centerWindow(this);
   }

   /**
    * Zobrazí dialogové okno se zprávou pro hráče o výsledku jeho právě
    * skončené hry.
    *
    * @param finalScore
    *       konečné skóre, které hráč získal ve své právě skončené hře.
    */
   public void show(int finalScore) {
      this.finalScore = finalScore;
      txaText.setText("Dosáhl(a) jsi celkového skóre " + finalScore
            + ",\nkteré dosáhne na tabulku nejlepších.");

      setVisible(true);
   }

   /**
    * Zaznamená výsledek do tabulky nejlepších výslekdů a tabulku zobrazí. Je
    * provedeno po hráčově potvrzení stiskem tlačítka OK.
    */
   protected void execute() {
      application.getHighScores().updateHighScore(
            application.getHighScores().getHighScoreCount() - 1,
            new HighScoreRecord(txtPlayerName.getText(), finalScore));
      setVisible(false);
      application.showHighScores();
   }
}
