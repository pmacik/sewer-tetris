package net.macsewer.vutbr.gja.tetris.highscores.gui;

import net.macsewer.vutbr.gja.tetris.highscores.HighScores;

import javax.swing.table.AbstractTableModel;

/**
 * Datový model pro tabulku nejlepších výsledků.
 *
 * @author Pavel Macík (xmacik01)
 */
public class HighScoresTableModel extends AbstractTableModel {

   /**
    *
    */
   private static final long serialVersionUID = -5008845970509312436L;

   /**
    * Nejlepší výsledky.
    */
   private HighScores highScores;

   /**
    * Pole obsahující názvy jednotlivých sloupců tabulky.
    */
   private String[] columnNames = { "Jméno hráče", "Skóre" };

   /**
    * @param highScores
    */
   public HighScoresTableModel(HighScores highScores) {
      this.highScores = highScores;
   }

   /*
    * (non-Javadoc)
    *
    * @see javax.swing.table.TableModel#getColumnCount()
    */
   public int getColumnCount() {
      return 2;
   }

   /*
    * (non-Javadoc)
    *
    * @see javax.swing.table.TableModel#getRowCount()
    */
   public int getRowCount() {
      return highScores.getHighScoreCount();
   }

   /*
    * (non-Javadoc)
    *
    * @see javax.swing.table.TableModel#getValueAt(int, int)
    */
   public Object getValueAt(int rowIndex, int columnIndex) {
      switch (columnIndex) {
         case 0:
            return highScores.getHighScore(rowIndex).getPlayerName();
         case 1:
            return String.valueOf(highScores.getHighScore(rowIndex)
                                            .getFinalScore());
      }
      return null;
   }

   /*
    * (non-Javadoc)
    *
    * @see javax.swing.table.AbstractTableModel#getColumnName(int)
    */
   @Override
   public String getColumnName(int column) {
      return columnNames[column];
   }
}
