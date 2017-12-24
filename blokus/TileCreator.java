package blokus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TileCreator
{
//  int maxSizeCalculated = 0;
//  
//  private List<List<Tile>> calculatedTiles; 

  private static final int[][] fullCircle =
  {
    {
      0, 1
    },
    {
      0, -1
    },
    {
      1, 0
    },
    {
      -1, 0
    },
  };

  public TileCreator()
  {
//    calculatedTiles = new ArrayList<>();
  }

  public static List<Tile> createTiles(int maxTileSize, Marker color)
  {
    if (maxTileSize < 1)
    {
      return Collections.emptyList();
    }
    List<Tile> tileVariants = new ArrayList<>();
    Tile tile = new Tile(color);
    tile.addField(0, 0);
    tileVariants.add(tile);
    calculateVariants(tile, tileVariants, maxTileSize, color);
    return tileVariants;
  }

  private static void calculateVariants(Tile currentTile, List<Tile> tiles, int maxTileSize,
        Marker color)
  {
    if(currentTile.getFields().size() == maxTileSize)
    {
      return;
    }
    
    //Recursive
    for (Field field : currentTile.getFields())
    {
      for (int i = 0; i < 4; i++)
      {
        int xField = field.x + fullCircle[i][0];
        int yField = field.y + fullCircle[i][1];
        Tile tileCopy = currentTile.copy();
        boolean copySuccess = tileCopy.addField(xField, yField);
        if(copySuccess && !tiles.contains(tileCopy))
        {
          tiles.add(tileCopy);
          calculateVariants(tileCopy, tiles, maxTileSize, color);
        }
      }
    }
  }
}
