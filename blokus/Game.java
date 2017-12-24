package blokus;

import blokus.gui.BoardGui;
import java.util.List;

public class Game
{
  public Game()
  {
  }

  public void start()
  {
    //TODO
  }

  public void quicktest()
  {
    BoardModel boardModel = new BoardModel();
    Player humanPlayer = new Player(Marker.BLUE);

    BoardGui boardGui = new BoardGui(boardModel, humanPlayer);

    List<Tile> tiles = TileCreator.createTiles(5, Marker.BLUE);

    boardModel.addTile(17, 17, tiles.get(8));

//    Tile t = tiles.get(1);
//    int x = 16;
//    int y = 15;
//    boolean asd = boardModel.validPlaceForTile(x, y, t);
//    boardModel.addTile(x, y, t);
  }
}
