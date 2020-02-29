package dungeon;

import dnd.models.Monster;
import dnd.models.Treasure;
import java.util.ArrayList;
import java.io.Serializable;

public class Passage extends Space implements Serializable {

  /*******
  *@param thePassage is an arraylist of passage sections.
  *******/
  private ArrayList<PassageSection> thePassage;
  /*******
  *@param theDoor is an arrayList of the doors.
  *******/
  private ArrayList<Door> theDoor;
  /*******
  *@param descriptionOfSection is a description of the section.
  *******/
  private String descriptionOfSection = null;
  /*******
  *@param isThereDoorAlready is a variable that is used to determine if a door
  * is already set.
  *******/
  private boolean isThereDoorAlready = false;

  /*******
  *Consturctor of the passage class.
  *******/

public Passage() {
  thePassage = new ArrayList<PassageSection>();
  theDoor = new ArrayList<Door>();
}

  /*******
  *gets the door arrayList.
  *@return returns the door array.
  *******/
public ArrayList getDoors() {
//gets all of the doors in the entire passage
   return theDoor;
}

  /*******
  *Gets the door at a specfic index.
  *@param i is the index.
  *@return the door at an index.
  *******/
public Door getDoor(int i) {
    return thePassage.get(i).getDoor();
}

  /*******
  *Adds a monster to the passage.
  *@param theMonster  is the the monter to be added.
  *@param i is the index where the monster is to be added.
  *******/
public void addMonster(Monster theMonster, int i) {
  // adds a monster to section 'i' of the passage
  thePassage.get(i).addMonster(theMonster);

}
public void addTreasure(Treasure treasure, int i) {
    thePassage.get(i).addTreasure(treasure);
}
public Treasure getTreasure(int i){
    return thePassage.get(i).getTreasure();
}
  /*******
  *Gets the monster at a specfic section.
  *@param i the index
  *@return it returns the monster at a specfic section of the passage.
  *******/
public Monster getMonster(int i) {
  //returns Monster door in section 'i'. If there is no Monster, returns null
  return thePassage.get(i).getMonster();
}
public void removeMonster(int i){
    thePassage.get(i).removeMonster();
}
public void removeTreasure(int i){
    thePassage.get(i).removeTreasure();
}
  /*******
  *adds a section to a passage.
  *@param toAdd is the section to be added to the passage.
  *******/
public void addPassageSection(PassageSection toAdd) {
  //adds the passage section to the passageway
  thePassage.add(toAdd);
}
public int sizeOfPassage(){
    return thePassage.size();
}
  /*******
  *Adds a new door to the door array.
  *@param newDoor is the new door to be added.
  *******/

@Override
public void setDoor(Door newDoor) {
  //should add a door connection to the current Passage Sect
    theDoor.add(newDoor);
    if (thePassage.size() > 0 && !isThereDoorAlready) {
        thePassage.get(theDoor.size() - 1).addDoor(newDoor);
    }
}

  /*******
  *Gets the description of the passage.
  *@return returns a string description of the passage.
  *******/
@Override
public String getDescription() {
    descriptionOfSection = thePassage.get(0).getDescription();
    for(int i = 1; i < thePassage.size(); i++){
        descriptionOfSection = descriptionOfSection+ thePassage.get(i).getDescription();
    }
    return descriptionOfSection;

}
  /*******
  *Checks if there is currently a door in the passage section.
  * @param truOrFal a flag that is determined by the user.
  *******/
private void isThereCurrentlyADoor(boolean truOrFal) {
    isThereDoorAlready = truOrFal;
}
public void setDescription(String word,int index){
        thePassage.get(index).setDescription(word) ;
}
}
