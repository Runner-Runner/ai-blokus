package blokus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
  private List<Tile> inventory;
  private Marker marker;
  
  public Player(Marker marker)
  {
    this.marker = marker;
    inventory = new ArrayList<>();
    inventory.addAll(TileCreator.createTiles(BoardModel.MAX_TILE_SIZE, marker));
  }
  
  public List<Tile> getTiles()
  {
    return Collections.unmodifiableList(inventory);
  }

  public Marker getMarker()
  {
    return marker;
  }
}
