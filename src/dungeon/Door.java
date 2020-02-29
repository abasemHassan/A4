package dungeon;
import dnd.models.Trap;
import java.util.ArrayList;
import java.util.Random;
import dnd.die.D20;
import java.io.Serializable;

public class Door implements Serializable  {
  /*******
  *@param myTrap is the trap that will be declared later on.
  *******/
  private Trap myTrap;
  /*******
  *@param roomDoorConnect is a door that connects a chamber to a passage
  *in setSpaces method.
  *******/
  private Door roomDoorConnect;
  /*******
  *@param trap a trap counter.
  *******/
  private int trap = 0;
  /*******
  *@param open a door open counter.
  *******/
  private int open = 0;
  /*******
  *@param archway an archway counter.
  *******/
  private int archway = 0;
  /*******
  *@param valOfChance is a varibale to get door properties based on the modif ied
  * table.
  *******/
  private Random valOfChance;
  /*******
  *@param ranRoll a die object
  *******/
  private D20 ranRoll;
  /*******
  *@param spacVar an array list of class space are declared and initlized.
  *******/
  private ArrayList<Space> spacVar;
  /*******
  *@param myDescriptionOfDoor is a description of the door.
  *******/
  private String myDescritpionOfDoor;
  /*******
  @param roll is a declaration of a varaiable of a class
  *******/
  private int roll;
  /*******
  @param randChance is a declaration of a varaiable of a class
  *******/
  private int randChance;

  /*******
  *Door consturctor.
  *******/
  public Door() {
    myTrap = new Trap();
    spacVar = new ArrayList<Space>(10);
    getCharateristicsOfDoor();
  }
  /*******
  *Sets a trap passed on an a number and the flag based in.
  *@param flag the flag that is passed to variables.
  *@param rollArray is an array of integers that takes in the die roll.
  *******/

  public void setTrapped(boolean flag,  int... rollArray) {
    if (flag) {
        if (rollArray.length ==  0) {
            myTrap.chooseTrap(getDieRoll());
            trap = 1;
        } else  {
            myTrap.chooseTrap(rollArray[0]);
            trap = 1;
        }
    } else {
       trap = 0;
    }
  }
  /*******
  *Sets a door to be open if  the flag is true or if  archway was set.
  *@param flag is the flag that is passed to variables.
  *******/
  public void setOpen(boolean flag) {
    if  (flag) {
      open = 1;
      myDescritpionOfDoor = "Open door";
    } else if  (archway == 1) {
      open = 1;
      archway = 0;
    } else if (!flag) {
      open = 0;
      myDescritpionOfDoor = "Locked door";
    }
  }
  /*******
  *Sets an archway if  the flag is true.
  *@param flag is the flag that is passed to variables.
  *******/
  public void setArchway(boolean flag) {
    if (flag) {
      archway = 1;
      myDescritpionOfDoor = "Archway";
    } else {
      archway = 0;
    }
  }
  /*******
  *Checks if  there is a trap.
  *@return returns either true or false if  there is a trap.
  *******/
  public boolean isTrapped() {
    if (trap ==  1) {
      return true;
    }
    return false;
  }
  /*******
  *Checks if  the door is open.
  *@return returns either true or false if  there the door is open.
  *******/
  public boolean isOpen() {
    if (open ==  1 || archway == 1) {
        return true;
    }
    return false;
  }
  /*******
  *Checks if  the archway is set already.
  *@return returns either true or false if  there an archway already set.
  *******/
  public boolean isArchway() {
    if (archway ==  1) {
      return true;
    }
    return false;
  }
  /*******
  *Gets the description of the trap.
  *@return returns a description of the trap.
  *******/
  public String getTrapDescription() {
    myDescritpionOfDoor = myTrap.getDescription();
    return myDescritpionOfDoor;
  }
  /*******
  *Gets the array list of spaces.
  *@return returns the array list.
  *******/
  public ArrayList<Space> getSpaces() {
    return spacVar;
  }
  /*******
  *Sets two spaces with each other with a door in between them.
  *@param spaceOne is a space.
  *@param spaceTwo is a space
  *******/
  public void setSpaces(Space spaceOne,  Space spaceTwo) {
     spacVar.add(spaceOne);
     spacVar.add(spaceTwo);
  }
  /*******
  *Gets the dexcription of the door.
  *@return returns the description fo the door.
  *******/
  public String getDescription() {
      return myDescritpionOfDoor;
  }
  private void getCharateristicsOfDoor() {
        randChance = getDoorChance();
        if  (randChance < 2)  {
            setRandTrap();
        } else if  (randChance < 8)  {
            setRandDoorToLocked();
        } else if  (randChance < 12)  {
            setRandDoorToArchway();
        } else if  (randChance < 14)  {
            setRandDoorToOpen();
        } else  {
            setRandDoorToClosed();
        }
  }
  private int getDieRoll() {
      ranRoll = new D20();
      return ranRoll.roll();
  }
  private int getDoorChance() {
      valOfChance = new Random();
      return valOfChance.nextInt(20);
  }
  private void setRandTrap() {
      setTrapped(true, getDieRoll());
      myDescritpionOfDoor = "trap " + getTrapDescription();
  }
  private void setRandDoorToLocked() {
      setOpen(false);
      myDescritpionOfDoor = "  locked ";
  }
  private void setRandDoorToArchway() {
      setArchway(true);
      myDescritpionOfDoor = " an archway ";
  }
  private void setRandDoorToOpen() {
      setOpen(true);
      myDescritpionOfDoor = " opened ";
  }
  private void setRandDoorToClosed() {
      setOpen(false);
      myDescritpionOfDoor = " closed ";
  }
}
