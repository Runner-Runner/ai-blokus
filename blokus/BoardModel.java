package blokus;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO Maybe extend board and make -1/-1 corner fields colored in respective color?
public class BoardModel
{
  private Marker[][] fields;
  private Map<Marker, List<Field>> cornerColorMap;

  private PropertyChangeSupport pcs;

  public static final int BOARD_LENGTH = 20;
  public static final int MAX_TILE_SIZE = 5;
  private static final String PROPERTY_BOARD = "board";

  public BoardModel()
  {
    pcs = new PropertyChangeSupport(this);

    cornerColorMap = new HashMap<>();
    setDefaultCorners();

    fields = new Marker[BOARD_LENGTH][BOARD_LENGTH];
    for (int i = 0; i < BOARD_LENGTH; i++)
    {
      for (int j = 0; j < BOARD_LENGTH; j++)
      {
        fields[i][j] = Marker.EMPTY;
      }
    }
  }

  private void setDefaultCorners()
  {
    List<Field> blueCorner = new ArrayList<>();
    blueCorner.add(new Field(0, 0));
    cornerColorMap.put(Marker.BLUE, blueCorner);

    List<Field> redCorner = new ArrayList<>();
    redCorner.add(new Field(0, BOARD_LENGTH - 1));
    cornerColorMap.put(Marker.RED, redCorner);

    List<Field> yellowCorner = new ArrayList<>();
    yellowCorner.add(new Field(BOARD_LENGTH - 1, BOARD_LENGTH - 1));
    cornerColorMap.put(Marker.YELLOW, yellowCorner);

    List<Field> greenCorner = new ArrayList<>();
    greenCorner.add(new Field(BOARD_LENGTH - 1, 0));
    cornerColorMap.put(Marker.GREEN, greenCorner);
  }

  public boolean validPlaceForTile(int x, int y, Tile tile)
  {
    Marker tileColor = tile.getColor();

    List<Field> corners = getCorners(tileColor);

//    int[][] adjacentFields = new int[][]
//    {
//      {
//        x - 1, y
//      },
//      {
//        x + 1, y
//      },
//      {
//        x, y - 1
//      },
//      {
//        x, y + 1
//      },
//    };
//
//    int[][] diagonalFields = new int[][]
//    {
//      {
//        x - 1, y - 1
//      },
//      {
//        x + 1, y - 1
//      },
//      {
//        x + 1, y - 1
//      },
//      {
//        x + 1, y + 1
//      },
//    };

    boolean foundMatchingEdge = false;
    for (Field field : tile.getFields())
    {
      int fieldX = x + field.x;
      int fieldY = y + field.y;

//      //If field already occupied or outside the board, illegal position
//      if (getField(fieldX, fieldY) != Marker.EMPTY
//              || getField(fieldX, fieldY) == null)
//      {
//        return false;
//      }
//      //Abort if adjacent nondiagonal fields are of the same color (illegal position)
//      for(int i=0; i<adjacentFields.length; i++)
//      {
//        int[] adjacentField = adjacentFields[i];
//        if(getField(adjacentField[0], adjacentField[1]) == tileColor)
//        {
//          
//        }
//      }
//      
//      if (getField(x - 1, y) == tileColor || getField(x + 1, y) == tileColor
//              || getField(x, y - 1) == tileColor || getField(x, y + 1) == tileColor)
//      {
//        return false;
//      }

//TODO ???
      //Check if first round corner set -> legal position
      if (!foundMatchingEdge)
      {
        for (Field corner : corners)
        {
          if (getField(corner.x, corner.y) == Marker.EMPTY
                  && fieldX == corner.x && fieldY == corner.y)
          {
            foundMatchingEdge = true;
          }
        }
      }

      //If field already occupied or outside the board, illegal position
      if (getField(fieldX, fieldY) != Marker.EMPTY
              || getField(fieldX, fieldY) == null)
      {
        return false;
      }
      //Abort if adjacent nondiagonal fields are of the same color (illegal position)
      if (getField(fieldX - 1, fieldY) == tileColor 
              || getField(fieldX + 1, fieldY) == tileColor
              || getField(fieldX, fieldY - 1) == tileColor 
              || getField(fieldX, fieldY + 1) == tileColor)
      {
        return false;
      }
      //Set flag if diagonal field of same color detected
      if (!foundMatchingEdge
              && (getField(fieldX - 1, fieldY - 1) == tileColor
              || getField(fieldX + 1, fieldY - 1) == tileColor
              || getField(fieldX - 1, fieldY + 1) == tileColor
              || getField(fieldX + 1, fieldY + 1) == tileColor))
      {
        foundMatchingEdge = true;
      }
    }
    //if no illegal fields and found diagonal match -> legal position
    return foundMatchingEdge;
  }

  public List<Field> getAllLegalPositions(Tile tile)
  {
    //TODO Tile: remove works with equal? Then hashmap<Field,Tile> should work
    List<Field> allLegalPositions = new ArrayList<>();

    int[] xCoords = new int[]
    {
      -1, 1, -1, 1
    };
    int[] yCoords = new int[]
    {
      -1, -1, 1, 1
    };

    List<Tile> tileVariants = new ArrayList<>();
    for (int i = 0; i < 4; i++)
    {
      Tile tileCopy = tile.copy();
      tileCopy.rotateClockwise();
      tileVariants.add(tileCopy);
      Tile tileCopy2 = tileCopy.copy();
      tileCopy2.flip();
      tileVariants.add(tileCopy2);
    }

    for (Tile tileVariant : tileVariants)
    {
      Marker tileColor = tileVariant.getColor();
      for (int i = 0; i < BOARD_LENGTH; i++)
      {
        for (int j = 0; j < BOARD_LENGTH; j++)
        {
          if (getField(i, j) == tileColor)
          {
            for (int k = 0; k < xCoords.length; k++)
            {
              if (validPlaceForTile(i + xCoords[k], j + yCoords[k], tileVariant))
              {
                allLegalPositions.add(new Field(i + xCoords[k], j + yCoords[k]));
              }
            }
          }
        }
      }
    }
    return allLegalPositions;
  }

  /**
   * Places a tile, starting with the leftmost upmost field.
   *
   * @param x
   * @param y
   * @param tile
   */
  public void addTile(int x, int y, Tile tile)
  {
    for (Field field : tile.getFields())
    {
      fields[x + field.x][y + field.y] = tile.getColor();
    }
    pcs.firePropertyChange(PROPERTY_BOARD, null, fields);
  }

  public void addPropertyChangeListener(PropertyChangeListener pcl)
  {
    pcs.addPropertyChangeListener(pcl);
  }

  public void removePropertyChangeListener(PropertyChangeListener pcl)
  {
    pcs.removePropertyChangeListener(pcl);
  }

  public Marker getField(int x, int y)
  {
    if (x < 0 || x >= BOARD_LENGTH || y < 0 || y >= BOARD_LENGTH)
    {
      return null;
    }
    return fields[x][y];
  }

  public List<Field> getCorners(Marker color)
  {
    return cornerColorMap.get(color);
  }
}
