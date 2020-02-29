package dungeon;
import java.io.Serializable;

public abstract class Space implements Serializable  {

  /*******
  *Gets the description of the something.
  *@return a description of something.
  *******/

  public abstract  String getDescription();

  /*******
  *Sets the door of something.
  *@param theDoor the door that used to set the space.
  *******/
  public abstract void setDoor(Door theDoor);

}
