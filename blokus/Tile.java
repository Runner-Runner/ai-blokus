package blokus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Tile
{

  private Marker color;

  /**
   * Fields not immutable, make deep copies
   */
  private List<Field> fields;

  /**
   * Convenience value for rotation algorithm
   */
  private final static int X = 0;

  /**
   * Convenience value for rotation algorithm
   */
  private final static int Y = 1;

  public Tile(Marker color)
  {
    this.color = color;
    fields = new ArrayList<>();
  }

  public boolean addField(int x, int y)
  {
    Field newField = new Field(x, y);
    
    //TODO why is this necessary?
    for(Field field : fields)
    {
      if(field.equals(newField))
      {
        return false;
      }
    }
    
    fields.add(newField);
    
    int shiftX = Math.min(0, x) * -1;
    int shiftY = Math.min(0, y) * -1;
    if(shiftX > 0 || shiftY > 0)
    {
      //For negative values, shift until positive
      for(Field field : fields)
      {
        field.x += shiftX;
        field.y += shiftY;
      }
    }
    return true;
  }

  /**
   * Rotates the tile 90 degrees clockwise.
   */
  public void rotateClockwise()
  {
    int lowestX = Integer.MAX_VALUE;
    //Rotate 90 degrees clockwise around origin 0,0
    for (Field field : fields)
    {
      int x = field.x;
      int y = -field.y;
      field.x = y;
      field.y = x;

      if (y < lowestX)
      {
        lowestX = y;
      }
    }

    //Shift all coordinates to the right until all of them are positive
    //(y never needs to be shifted, because for a y value to become negative
    //an x value needs to be negative, which is never true)
    if (lowestX != 0)
    {
      int xDiff = -lowestX;
      for (Field field : fields)
      {
        field.x += xDiff;
      }
    }
  }

  /**
   * Always flips around the vertical axis ("flipping horizontally").
   */
  public void flip()
  {
    //Get highest y-value to subtract from ("flipping horizontally")
    int highestY = 0;
    for (Field field : fields)
    {
      if (highestY < field.y)
      {
        highestY = field.y;
      }
    }

    for (Field field : fields)
    {
      field.y = highestY - field.y;
    }
  }

  @Override
  public boolean equals(Object obj)
  {
    if (!(obj instanceof Tile))
    {
      return false;
    }
    Tile otherTile = (Tile) obj;
    if (!getColor().equals(otherTile.getColor()))
    {
      return false;
    }

    if (getFields().size() != otherTile.getFields().size())
    {
      return false;
    }

    if (compareFields(otherTile))
    {
      return true;
    }

    for (int i = 0; i < 2; i++)
    {
      for (int j = 0; j < 4; j++)
      {
        if (compareFields(otherTile))
        {
          return true;
        }
        rotateClockwise();
      }
      flip();
    }
    
    return false;
  }

  private boolean compareFields(Tile otherTile)
  {
    for (Field ownField : getFields())
    {
      boolean matched = false;
      for (Field otherField : otherTile.getFields())
      {
        if (ownField.x == otherField.x && ownField.y == otherField.y)
        {
          matched = true;
          break;
        }
      }
      if (!matched)
      {
        return false;
      }
    }
    return true;
  }

  @Override
  public int hashCode()
  {
    int hash = 3;
    hash = 23 * hash + Objects.hashCode(this.color);
    hash = 23 * hash + Objects.hashCode(this.fields);
    return hash;
  }

  public Marker getColor()
  {
    return color;
  }

  public List<Field> getFields()
  {
    return fields;
  }

  public Tile copy()
  {
    Tile copy = new Tile(getColor());
    for (Field field : fields)
    {
      copy.addField(field.x, field.y);
    }
    return copy;
  }
}
