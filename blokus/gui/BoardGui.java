package blokus.gui;

import blokus.BoardModel;
import blokus.Field;
import blokus.Marker;
import blokus.Player;
import blokus.Tile;
import blokus.gui.TilePanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

/**
 *
 * @author Daniel
 */
public class BoardGui extends javax.swing.JFrame
{
  private static final long serialVersionUID = 1L;
  private BoardModel boardModel;
  private JButton[][] fields;

  private JPanel blokusBoard;
  private JPanel tileInventory;
  private Player player;

  public static TilePanel selectedTilePanel;

  public BoardGui(BoardModel boardModel, Player player)
  {
    this.boardModel = boardModel;
    this.player = player;
    initComponents();

    boardPanel.setLayout(new BoxLayout(boardPanel, BoxLayout.LINE_AXIS));
    blokusBoard = new JPanel();
    boardPanel.add(blokusBoard);
    tileInventory = new JPanel();
    tileInventory.setLayout(new BoxLayout(tileInventory, BoxLayout.PAGE_AXIS));
    boardPanel.add(tileInventory);
    initInventory();

    initialize();
    setVisible(true);

    boardModel.addPropertyChangeListener(new PropertyChangeListener()
    {
      @Override
      public void propertyChange(PropertyChangeEvent evt)
      {
        updateBoard();
      }
    });
  }

  public static Color getMarkerColor(Marker marker)
  {
    Color guiColor;
    switch (marker)
    {
      case BLUE:
        guiColor = Color.BLUE;
        break;
      case GREEN:
        guiColor = Color.GREEN;
        break;
      case RED:
        guiColor = Color.RED;
        break;
      case YELLOW:
        guiColor = Color.YELLOW;
        break;
      case EMPTY:
        guiColor = Color.LIGHT_GRAY;
        break;
      default:
        throw new AssertionError(marker.name());
    }
    return guiColor;
  }

  private void initInventory()
  {
    Color playerColor = getMarkerColor(player.getMarker());

    for (Tile tile : player.getTiles())
    {
      JPanel tilePanel = new TilePanel(tile);

      tileInventory.add(tilePanel);
    }
  }

  private void updateBoard()
  {
    for (int x = 0; x < BoardModel.BOARD_LENGTH; x++)
    {
      for (int y = 0; y < BoardModel.BOARD_LENGTH; y++)
      {
        //TODO still needed?
        fields[x][y].setBackground(getMarkerColor(boardModel.getField(x, y)));
      }
    }
  }

  private void initialize()
  {
    //Create board
    this.fields = new JButton[BoardModel.BOARD_LENGTH][BoardModel.BOARD_LENGTH];
    blokusBoard.setLayout(new GridLayout(BoardModel.BOARD_LENGTH, BoardModel.BOARD_LENGTH));
    for (int x = 0; x < BoardModel.BOARD_LENGTH; x++)
    {
      for (int y = 0; y < BoardModel.BOARD_LENGTH; y++)
      {
        JButton fieldButton = new JButton("");
        fieldButton.setPreferredSize(new Dimension(50, 50));
        fieldButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        final int finalX = x;
        final int finalY = y;
        fieldButton.addMouseListener(new MouseInputAdapter()
        {
          private Color restoredColor;

          @Override
          public void mouseEntered(MouseEvent e)
          {
            Color selectColor = Color.MAGENTA;
            if (selectedTilePanel != null)
            {
              boolean valid = boardModel.validPlaceForTile(finalX, finalY,
                      selectedTilePanel.getTile());
              if (valid)
              {
                selectColor = Color.GREEN;
              }
              else
              {
                selectColor = Color.RED;
              }

              for (Field field : selectedTilePanel.getTile().getFields())
              {
                int fieldX = finalX + field.x;
                if (fieldX >= BoardModel.BOARD_LENGTH)
                {
                  continue;
                }
                int fieldY = finalY + field.y;
                if (fieldY >= BoardModel.BOARD_LENGTH)
                {
                  continue;
                }
                fields[fieldX][fieldY].setBackground(selectColor);
              }
            }
            else
            {
              restoredColor = fieldButton.getBackground();
              fieldButton.setBackground(selectColor);
            }
          }

          @Override
          public void mouseExited(MouseEvent e)
          {
//            fieldButton.setBackground(restoredColor);
            updateBoard();
          }

          @Override
          public void mouseClicked(MouseEvent e)
          {
            if (selectedTilePanel == null)
            {
              return;
            }

            if (SwingUtilities.isLeftMouseButton(e))
            {
              boolean valid = boardModel.validPlaceForTile(finalX, finalY,
                      selectedTilePanel.getTile());
              if (valid)
              {
                Tile selectedTile = selectedTilePanel.getTile();
                boardModel.addTile(finalX, finalY, selectedTile);
                
                tileInventory.remove(selectedTilePanel);
                tileInventory.revalidate();
                selectedTilePanel = null;
              }
            }
            else if (SwingUtilities.isRightMouseButton(e))
            {
              selectedTilePanel.getTile().rotateClockwise();
            }
            else if (SwingUtilities.isMiddleMouseButton(e))
            {
              selectedTilePanel.getTile().flip();
            }
            updateBoard();
          }
        });
        fields[x][y] = fieldButton;
        blokusBoard.add(fieldButton);
      }
    }
    updateBoard();
  }

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents()
  {

    boardPanel = new javax.swing.JPanel()
    {
      private static final long serialVersionUID = 1L;

      @Override
      public void paint(Graphics g)
      {
        super.paint(g);
      }
    };
    ;

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    javax.swing.GroupLayout boardPanelLayout = new javax.swing.GroupLayout(boardPanel);
    boardPanel.setLayout(boardPanelLayout);
    boardPanelLayout.setHorizontalGroup(
      boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 520, Short.MAX_VALUE)
    );
    boardPanelLayout.setVerticalGroup(
      boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 520, Short.MAX_VALUE)
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(boardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(boardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addContainerGap())
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JPanel boardPanel;
  // End of variables declaration//GEN-END:variables
}
