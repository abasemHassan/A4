package dungeon;

import dnd.models.ChamberContents;
import dnd.models.ChamberShape;
import dnd.models.Monster;
import dnd.models.Treasure;
import dnd.models.Trap;
import java.util.ArrayList;
import dnd.die.D20;
import java.io.Serializable;

public class Chamber extends Space implements Serializable   {
    /********
    @param myContents is a declareion of the Class ChamberContents
    *****/
    private ChamberContents myContents;
    /*****
    @param mySize is a declaration of the Class ChamberShape
    *****/
    private ChamberShape mySize;
    /*****
    @param exit is a declaration of the Door Class that will be used to add a door to
    the door array.
    ******/
    private Door exit;
    /******
    @param monsterArray is both a declaration and intiliziation of an array of monsters.
    ******/
    private ArrayList<Monster> monsterArray = new ArrayList<Monster>(0);
    /******
    @param treasureArray is both a declaration and intiliziation of an array of treasure.
    ******/
    private ArrayList<Treasure> treasureArray = new ArrayList<Treasure>(0);
    /*******
    @param doorArray is both a declaration and intiliziation of an array of doors.
    *******/
    private ArrayList<Door> doorArray = new ArrayList<Door>(0);
    /******
    @param doorToPassage is a declaration of a varaible of Passage class
    *******/
    private Passage doorToPassage;
    /******
    @param instance is a declaration of a varaible of a class
    *******/
    private Chamber instance;
    /******
    @param ranRoll is a declaration of a varaible of a class
    *******/
    private D20 ranRoll;
    /*******
    @param descriptionOfChamber is a declaration of a varaible of a class
    ********/
    private String descriptionOfChamber;
    /********
    @param treasureInChamber is a declaration of a varaible of a class
    ********/
    private Treasure treasureInChamber;
    /*******
    @param trapInChamber is a declaration of a varaible of a class
    ******/
    private Trap trapInChamber;
    /*******
    @param roll is a declaration of a varaiable of a class
    *******/
    private int roll;

  /**
  * Chamber constructor is used to create muliple objects that were declaraed.
  **/

    public Chamber()  {
        roll = getDieRoll();
        myContents = new ChamberContents();
        mySize = ChamberShape.selectChamberShape(roll);
        exit = new Door();
        myContents.chooseContents(roll);
        getCharateristicsOfChamber();
    }
    /**
    * This is similar to constructor above however the user passes in theShape and theContents of the Chamber.
    * Then they are assigned to local variables.
    *@param theShape is the shape of the chamber
    *@param theContents is the contents in the chamber
    **/
    public Chamber(ChamberShape theShape, ChamberContents theContents)  {
        roll = getDieRoll();
        myContents = theContents;
        mySize = theShape;
        myContents.chooseContents(roll);
        getCharateristicsOfChamber();
    }
    /**
    *Used to set the shape of the chamber.
    *@param theShape is the the shape of the chamber
    ***/
    public void setShape(ChamberShape theShape)  {
        mySize = theShape;
        getCharateristicsOfChamber();
    }
    /**
    *Retrieves the number of doors within the chamber.
    *@return doorArray  returns the door array of the chamber.
    ***/
    public ArrayList<Door> getDoors()  {
        return doorArray;
    }
    /**
    *Used to set the add a monster to the monster array.
    *@param theMonster is the monster object to be added to the monster array
    ***/

    public void addMonster(Monster theMonster)  {
        monsterArray.add(theMonster);
    }
    /**
    *Used to return the arraylist of monsters.
    @return returns an array of monsters
    ***/
    public ArrayList<Monster> getMonsters() {
        return monsterArray;
    }
    /**
    *Used to add treasure to chamber.
    *@param theTreasure is the treasure object to be added to the treasure array
    ***/
    public void addTreasure(Treasure theTreasure) {
        treasureArray.add(theTreasure);
    }
    /**
    *Used to get an array of all the treasures in the chamber.
    @return returns an array of all the treasure in the chamber.
    ***/
    public ArrayList<Treasure> getTreasureList() {
        return treasureArray;
    }
    /**
    *Used to obtain the description of a chamber.
    @return a description of the chamber.
    ***/
    @Override
    public String getDescription() {
        return descriptionOfChamber;
    }
    public void removeMonster(Monster theMonster) {
        descriptionOfChamber = descriptionOfChamber.replaceFirst(("Chamber contains " + theMonster.getDescription()+"."),"");
        monsterArray.remove(theMonster);
    }
    public void removeTreasure(Treasure treasure){
        descriptionOfChamber = descriptionOfChamber.replaceFirst((descriptionOfChamber = "Chamber contains " + treasure.getDescription() +"."),"");
        treasureArray.remove(treasure);
    }
    /**
    *Used to add a new door to the door array.
    *@param newDoor a new door object that will be added to the array.
    ***/

    @Override
    public void setDoor(Door newDoor) {
        //should add a door connection to this room.
        doorArray.add(newDoor);
    }
    public void getCharateristicsOfChamber() {
        int roll = getDieRoll();

        if (roll < 13)  {
            descriptionOfChamber = "Chamber is empty.";
        } else if (roll < 15)  {
            Monster monsterInChamber = new Monster();

            addMonster(monsterInChamber);

            monsterInChamber.setType(getDieRoll());
            descriptionOfChamber = "Chamber contains " + monsterInChamber.getDescription()+".";
        } else if (roll < 18)  {
            treasureInChamber = new Treasure();

            treasureInChamber.setDescription(getDieRoll());
            treasureInChamber.setContainer(getDieRoll());
            addTreasure(treasureInChamber);
            descriptionOfChamber = "Chamber contains " + treasureInChamber.getDescription() + ".";

        } else if (roll < 20)  {
            trapInChamber = new Trap();

            trapInChamber.chooseTrap(getDieRoll());

            descriptionOfChamber = "Trapped chamber." + "This chamber is trapped by a" + trapInChamber.getDescription()+".";

        } else if (roll == 20)  {
            treasureInChamber = new Treasure();

            treasureInChamber.chooseTreasure(getDieRoll());
            treasureInChamber.setContainer(getDieRoll());
            addTreasure(treasureInChamber);

            descriptionOfChamber = "Chamber contains " + treasureInChamber.getDescription() + ".";

        }
    }
    private int getDieRoll() {
      ranRoll = new D20();
      return ranRoll.roll();
    }
    private void setDescription() {
      descriptionOfChamber = mySize.getDescription() + " " + myContents.getDescription();
    }
    public void setDescription(String word){
        descriptionOfChamber += "Chamber contains " + word +".";
    }
}
