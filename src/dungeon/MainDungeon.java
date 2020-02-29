
package dungeon;
import java.util.ArrayList;
import dnd.die.D20;
import dnd.die.D4;
import dnd.models.ChamberShape;
import dnd.models.ChamberContents;
import java.util.HashMap;
import java.io.Serializable;

public class MainDungeon implements Serializable {
    /*****
    @param chamberArray is an array of chambers.
    ******/
    private ArrayList<Chamber> chamberArray;
    /*****
    @param shapeArray is the shape of a chamber.
    ******/
    private ArrayList<ChamberShape> shapeArray;
    /*******
    @param roll is a declaration of a varaiable of a class
    *******/
    private int roll;
    /******
    @param ranRoll is a declaration of a varaible of a class
    *******/
    private D20 ranRoll;
    /******
    @param mapDoorToChambers is a declaration of a varaible of a class
    *******/
    private  ArrayList<Door> instanceArray;
    /******
    @param targetArray is a declaration of a varaible of a class
    *******/
    private  ArrayList<Chamber> targetArray;
    /******
    @param ranRoll1 is a declaration of a varaible of a Class
    ******/
    private D4 ranRoll1;

    private HashMap<Chamber,ArrayList<Chamber>> sourceToTarget;
    private ArrayList<Door> instanceDoor;
    private HashMap<Door, ArrayList<Chamber>> mapDoorToChambers;
    private HashMap<Door,ArrayList<Door>> mapDoorToDoors;
    private ArrayList<Passage> passArray;
    private ArrayList<Door> doorArray;
    private HashMap<Door,String> mapDoorToChamber;
    public MainDungeon() {
        chamberArray = new ArrayList<Chamber>();
        targetArray = new ArrayList<Chamber>();
        instanceDoor = new ArrayList<>();
        shapeArray = new ArrayList<ChamberShape>();
        sourceToTarget = new HashMap<Chamber,ArrayList<Chamber>>();
        mapDoorToChambers = new HashMap<Door, ArrayList<Chamber>>();
        mapDoorToDoors = new HashMap<>();
        passArray = new ArrayList<>();
        doorArray = new ArrayList<>();
    }
    public int getDieRoll() {
      ranRoll = new D20();
      return ranRoll.roll();
    }
    public void createChambers(int count) {
        for (int i = 0; i < count; i++) {
            shapeArray.add(ChamberShape.selectChamberShape(getDieRoll()));
            chamberArray.add(new Chamber(shapeArray.get(i), new ChamberContents()));
        }
    }

    public ArrayList<Chamber> getChamberArray() {
        return chamberArray;
    }

    public void setDoorsInChamber(int count) {
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < shapeArray.get(i).getNumExits(); j++) {
                chamberArray.get(i).setDoor(new Door());
            }
        }
    }
    public void printAll(int count) {
        for (int i = 0; i < count; i++) {
            System.out.println("For chamber " + (i + 1) + " the number of exists is: " + shapeArray.get(i).getNumExits());
        }
    }
    public void getTarget(int j){
        for( int i = 0; i < 5; i++){
            if(chamberArray.get(j)!= chamberArray.get(i)){
                targetArray.add(chamberArray.get(i));
            }
        }
        sourceToTarget.put(chamberArray.get(j),targetArray);
    }
    public void doorToChamber(){
        ArrayList<Chamber> instanceChamber = new ArrayList<>();
        int i = 0;
        for(Chamber cs: chamberArray){
            instanceDoor = cs.getDoors();
            for(Door d: instanceDoor){
                instanceChamber.add(sourceToTarget.get(cs).get(i));
                mapDoorToChambers.put(d,instanceChamber);
                i++;
            }
            i = 0;
        }
    }
    public void connectChamberToChambers(){
        int i = 0;
        for(Chamber cs: chamberArray){
            instanceDoor = cs.getDoors();
            for(Door d: instanceDoor){
                passArray.add(new Passage());
                passArray.get(i).addPassageSection(new PassageSection());
                passArray.get(i).addPassageSection(new PassageSection());
                d.setSpaces(cs,passArray.get(i));
                i++;
            }
        }
    }

    public ArrayList<Passage> getPassArray() {
        return passArray;
    }

    public void printFinalMap(){
        int i = 0;
        System.out.println("Decription of the connection map");
        for(Chamber cs : chamberArray){
            System.out.println("Chamber"+ (i+1) + "contains: "+cs.getDescription());
            instanceDoor = cs.getDoors();
            for(Door d: instanceDoor){
                System.out.println("Doors ");
            }
        }
    }
    public String getChamberDescription(Chamber selectChamber){
        return selectChamber.getDescription();
    }
    public String getPassageDescription(Passage selectPassage){
        return selectPassage.getDescription();
    }
    public void setDoorArray(){
        int i = 0;
        mapDoorToChamber = new HashMap<>();
        for(Chamber cs: chamberArray){
            instanceDoor = cs.getDoors();
            for(Door d: instanceDoor){
                doorArray.add(d);
                mapDoorToChamber.put(d,("door . It is connected to chamber "+ (i+1)+" ."));
            }
            i++;
        }
    }
    public ArrayList<Door> getDoorArray(){
        this.setDoorArray();
        return doorArray;
    }

    public HashMap<Door, String> getMapDoorToChamber() {
        return mapDoorToChamber;
    }

    /*****
    *Main mehtod.
    *@param args args not used.
    ******/
    public static void main(String[] args) {
        MainDungeon  connectionMap = new MainDungeon();
        connectionMap.createChambers(5);
        connectionMap.setDoorsInChamber(5);
        connectionMap.printAll(5);
        connectionMap.getTarget(0);
        connectionMap.getTarget(1);
        connectionMap.getTarget(2);
        connectionMap.getTarget(3);
        connectionMap.getTarget(4);
        connectionMap.doorToChamber();
        connectionMap.connectChamberToChambers();
        connectionMap.printFinalMap();
    }
}
