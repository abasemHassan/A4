package dungeon;
import java.io.Serializable;
import dnd.models.Monster;
import dnd.die.D20;
import java.util.NavigableMap;
import java.util.TreeMap;
import dnd.models.Treasure;
import java.lang.NullPointerException;
/* Represents a 10 ft section of passageway */

public class PassageSection implements Serializable  {

    /*******
    *@param monsterInPassage a monster type variable.
    *******/
    private Monster monsterInPassage;
    /*******
    *@param ranRoll a D20 variable.
    *******/
    private D20 ranRoll;
    /*******
    *@param passageDoor a door to the passage section.
    *******/
    private Door passageDoor;
    /*******
    *@param lengthOfPassage lenght of the passage.s
    *******/
    private int lengthOfPassage = 0;
    /*******
    *@param myDescription description of the passage section.
    *******/
    private String myDescription;
    /*******
    *@param isMonsterInPassage used to check if  a monsster is in a passage section.
    *******/
    private boolean isMonsterInPassage = false;
    /*******
    *@param isDoorInPassage used to check if  a door is in a passage section.
    *******/
    private boolean isDoorInPassge = false;
    /*******
    *@param isDoorTrapped used to check if  the door is trapped.
    *******/
    private boolean isDoorTrapped = false;
    /*******
    @param roll is a declaration of a varaiable of a class
    *******/
    private int roll;
    /******
    @param setupPassageSection used to set the properties of the passageSection
    *******/
    private NavigableMap<Integer, String> setupPassageSection;
    /*******
    *Constructor for the class. Also, sets up 10 foot sections.
    *******/
    private Treasure treasureInPassage;
    private boolean isTreasureInPassage = false;
public PassageSection() {
    setupPassageSection = new TreeMap<Integer, String>();
    setTreeMap();
    setRandDescription();
    lengthOfPassage++;
}
/*******
*Constructor for the section.
*@param description is the description of the section.
*******/
public PassageSection(String description) {
    setupPassageSection = new TreeMap<Integer, String>();
    myDescription = description;
    setTreeMap();
    createSpecficObjectInPassageSection();
    lengthOfPassage++;
}
/*******
*Used to get the doors in the passage section.
*@return returns the doors in the section.
*******/
public Door getDoor() {
  //returns the door that is in the passage section, if  there is one
    if (isDoorInPassge) {
        return passageDoor;
    }
    return null;
}
/*******
*Used to get the monsters in the passage section.
*@return returns the monsters in the section.
*******/
public Monster getMonster() {
  //returns the monster that is in the passage section, if  there is one
    if (isMonsterInPassage) {
        return monsterInPassage;
    }
    return null;
}
public void removeMonster(){

     myDescription = myDescription.replaceFirst("Passage contains " + monsterInPassage.getDescription() +".", "");
     monsterInPassage = null;
}
public void removeTreasure(){
     myDescription = myDescription.replaceFirst("Passage contains " + treasureInPassage.getDescription()+".", "");
     treasureInPassage = null;
}
public Treasure getTreasure() {
    if (isTreasureInPassage) {
        return treasureInPassage;
    }
    return null;
}
/*******
*Used to get the description of the passage section. if  there is a monster or a door in the passageway
 then additional information is added to the description.
*@return returns the description of the passage section.
*******/
public String getDescription() {
    return myDescription;
}
/*******
*Adds a monster to the passage section.
*@param monster is the monster to be added.
*******/
public void addMonster(Monster monster) {
    monsterInPassage = monster;
    isMonsterInPassage = true;
}
public void addTreasure(Treasure treasure){
    treasureInPassage = treasure;
    isTreasureInPassage = true;
}
/*******
*Adds a door to the passage section.
*@param door the door to be added.
*******/
public void addDoor(Door door) {
    passageDoor = door;
    isDoorInPassge = true;
    myDescription = "This door is";
    myDescription += passageDoor.getDescription()+". ";
}

private int getDieRoll() {
    ranRoll = new D20();
    return ranRoll.roll();
}
private void createRandDoorInPassageSection() {
    passageDoor = new Door();
    isDoorInPassge = true;
    myDescription = "This door is";
    myDescription += passageDoor.getDescription()+". ";
}
private void createArchwayInPassageSection() {
    passageDoor = new Door();
    passageDoor.setArchway(true);
    isDoorInPassge = true;
}
private void createMonsterInPassageSection() {
    monsterInPassage = new Monster();
    monsterInPassage.setType(getDieRoll());
    isMonsterInPassage = true;
    myDescription += "Passage contains " + monsterInPassage.getDescription() +".";
}
private void createRandObjectInPassageSection(int dieRoll) {
    if  (dieRoll >= 3 && dieRoll < 6) {
        createRandDoorInPassageSection();
    } else if  (dieRoll >= 14 && dieRoll < 17) {
        createArchwayInPassageSection();
    } else if (dieRoll == 20) {
        createMonsterInPassageSection();
    }
 }
 private void setTreeMap() {
    setupPassageSection.put(1, "Coninue straight through the passage for 10 ft. ");
    setupPassageSection.put(3, "Passage ends with a door to a chamber. The door infront of the chamber is ");
    setupPassageSection.put(6, "Archway found to the right of the main passage. Continue straight through the main passage for 10 ft. ");
    setupPassageSection.put(8, "Archway found to the left of the main passage. Continue straight through the main passage for 10 ft. ");
    setupPassageSection.put(10, "Turn left and continue through the main passage for 10 ft. ");
    setupPassageSection.put(12, "Turn Right and coninue through the main passage for 10 ft ");
    setupPassageSection.put(14, "The passage ends in a archway to a chamber. ");
    setupPassageSection.put(17, "Stairs found. Continue through the main passage for 10 ft. ");
    setupPassageSection.put(18, "Dead End. ");
    setupPassageSection.put(20, "Continue straight through the main passage for 10 ft. ");
 }
 private void setRandDescription() {
    roll = getDieRoll();
    myDescription = setupPassageSection.floorEntry(roll).getValue();
    createRandObjectInPassageSection(roll);
 }
 private void createSpecficObjectInPassageSection() {
   if  (myDescription.equals("Passage ends with a door to a chamber. The door infront of the chamber is")) {
       createRandDoorInPassageSection();
   } else if  (myDescription.equals("The passage ends in a archway to a chamber")) {
       createArchwayInPassageSection();
   } else if (myDescription.equals("Continue straight through the main passage for 10 ft. ")) {
       createMonsterInPassageSection();
   }
 }
 public void setDescription(String word){
     myDescription = "Passage contains " + word +".";
 }
}
