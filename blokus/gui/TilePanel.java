package blokus.gui;

import blokus.BoardModel;
import blokus.Field;
import blokus.Marker;
import blokus.Tile;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class TilePanel extends JPanel
{
  private Tile tile;

  public TilePanel(Tile tile)
  {
    this.tile = tile;

    setBorder(BorderFactory.createLineBorder(Color.BLACK));
    GridLayout gridLayout = new GridLayout(BoardModel.MAX_TILE_SIZE,
            BoardModel.MAX_TILE_SIZE);
    setLayout(gridLayout);

    int totalFieldCount = BoardModel.MAX_TILE_SIZE * BoardModel.MAX_TILE_SIZE;
    for (int i = 0; i < totalFieldCount; i++)
    {
      JButton fieldButton = new JButton("");
      fieldButton.setPreferredSize(new Dimension(15, 15));
      fieldButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));

      for (Field field : tile.getFields())
      {
        if (field.x == i % BoardModel.MAX_TILE_SIZE
                && field.y == i / BoardModel.MAX_TILE_SIZE)
        {
          fieldButton.setBackground(BoardGui.getMarkerColor(tile.getColor()));
        }
      }

      fieldButton.addMouseListener(new MouseAdapter()
      {
        @Override
        public void mouseEntered(MouseEvent e)
        {
          dispatchEvent(e);
        }

        @Override
        public void mouseExited(MouseEvent e)
        {
          dispatchEvent(e);
        }

        @Override
        public void mouseClicked(MouseEvent e)
        {
          dispatchEvent(e);
        }
      });

      add(fieldButton);
    }

    addMouseListener(new MouseAdapter()
    {
      @Override
      public void mouseEntered(MouseEvent e)
      {
        setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
      }

      @Override
      public void mouseExited(MouseEvent e)
      {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
      }

      @Override
      public void mouseClicked(MouseEvent e)
      {
        if (BoardGui.selectedTilePanel == TilePanel.this)
        {
          BoardGui.selectedTilePanel = null;
        }
        else
        {
          BoardGui.selectedTilePanel = TilePanel.this;
        }
      }
    });
  }

  public Tile getTile()
  {
    return tile;
  }
}
