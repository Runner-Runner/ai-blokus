package blokus;

public class Field
{
  public int x;
  public int y;

  public Field(int x, int y)
  {
    this.x = x;
    this.y = y;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (obj instanceof Field)
    {
      Field otherField = (Field) obj;
      return (x == otherField.x && y == otherField.y);
    }
    return false;
  }

  @Override
  public int hashCode()
  {
    int hash = 5;
    hash = 53 * hash + this.x;
    hash = 53 * hash + this.y;
    return hash;
  }
}
