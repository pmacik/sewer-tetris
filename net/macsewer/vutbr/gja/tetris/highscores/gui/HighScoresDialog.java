package net.macsewer.vutbr.gja.tetris.highscores.gui;

import net.macsewer.vutbr.gja.tetris.gui.WindowManipulator;
import net.macsewer.vutbr.gja.tetris.highscores.HighScores;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * Dialogové okno pro zobrazování tabulky nejlepších výsledků.
 *
 * @author Pavel Macík (xmacik01)
 */
public class HighScoresDialog extends JDialog {
   /**
    *
    */
   private static final long serialVersionUID = 6273215930220459189L;

   /**
    * Panel, na kterém je umístěna tabulka nejlepších výsledků.
    */
   private JPanel pnlHighScores = new JPanel();

   /**
    * Panel, na kterém jsou umístěna tlačítka.
    */
   private JPanel pnlButtons = new JPanel();

   /**
    * Tlačítko - OK. Po jeho stisknutí je vlasntické dialogové okno schováno.
    */
   private JButton btnOK = new JButton("OK");

   /**
    * Obalující pole pro tabulku nejlepších výsledků.
    */
   private JScrollPane spnHighScoresTable = new JScrollPane();

   /**
    * Tabulka, ve které jsou zobrazovány nejlepší výsledky spolu se jmény
    * hráčů, kteří jich dosáhli.
    */
   private JTable tblHighScores = new JTable();

   /**
    * Datový model pro tabulku nejlepších výsledků.
    */
   private HighScoresTableModel tbmHighScoresModel;

   /**
    * Kontejner obsahující nejlepší výsledky.
    */
   private HighScores highScores;

   /**
    * Vytvoří objekt dialogového okna.
    *
    * @param width
    *       šířka tabulky v px.
    * @param height
    *       výška tabulky v px.
    * @param highScores
    *       reference na nejlepší výsledky.
    */
   public HighScoresDialog(int width, int height, HighScores highScores) {
      this.highScores = highScores;
      initGUI(width, height);
   }

   /**
    * Inicializuje GUI.
    *
    * @param width
    *       šířka tabulky v px.
    * @param height
    *       výška tabulky v px.
    */
   private void initGUI(int width, int height) {
      setTitle("Tabulka nejlepších výsledků");
      setDefaultCloseOperation(HIDE_ON_CLOSE);

      tbmHighScoresModel = new HighScoresTableModel(highScores);
      tblHighScores.setModel(tbmHighScoresModel);
      tblHighScores.setPreferredScrollableViewportSize(new Dimension(width,
            height));
      spnHighScoresTable.setViewportView(tblHighScores);
      pnlHighScores.add(spnHighScoresTable, null);
      getContentPane().add(pnlHighScores, BorderLayout.CENTER);

      pnlButtons.add(btnOK, null);
      btnOK.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            setVisible(false);
         }
      });
      getContentPane().add(pnlButtons, BorderLayout.SOUTH);

      addWindowFocusListener(new WindowFocusListener() {
         public void windowLostFocus(WindowEvent e) {
            setVisible(false);
         }

         public void windowGainedFocus(WindowEvent e) {
         }
      });

      pack();

      WindowManipulator.centerWindow(this);
   }
}
