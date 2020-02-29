package gui;

import dungeon.*;
import java.util.*;
import javafx.collections.ObservableList;
import dnd.models.Treasure;
import dnd.models.Monster;
import dnd.models.ChamberContents;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import java.io.*;

public class Controller {

    private ApplicationStart myGui;
    private MainDungeon  dungeon;
    private static int NUM_CHAMBERS = 5;
    private HashMap<String,Chamber> mapNameToChambers;
    private HashMap<String,Passage> mapNameToPassages;
    private HashMap<String,Door> mapNameToDoors;
    private int i = 0;
    private ArrayList<String> nameList;
    private ArrayList<String> nameDoorList;
    private ArrayList<Chamber> chambersInLevel;
    private ArrayList<Passage> passagesInLevel;
    private ArrayList<Door> doorArray;
    private HashMap<Door,String> mapDoorToChamber;
    private Treasure treasure;
    private Monster mons;
    private HashMap<String,Monster> mapNameToMonsterChamber;
    private HashMap<String,Monster> mapNameToMonsterPassage;
    private HashMap<String,Treasure> mapNameToTreasureChamber;
    private HashMap<String,Treasure> mapNameToTreasurePassage;
    private HashMap<String,Treasure> mapNameToTreasure;
    private ArrayList<String> monsterKey;
    private ArrayList<String> treasureKey;
    private ArrayList<String> monsterList;
    private ArrayList<Monster> monster;
    private ObservableList<String> monsterList1;
    private ListView<String> viewMonsterList;
    private ObservableList<String> monsterList2;
    private ListView<String> viewMonsterList2;
    private ArrayList<String> treasureList;
    private ArrayList<Treasure> treasure1;
    private HashMap<String,Integer> mapToMonster;
    private HashMap<String,Integer> mapToTreasure;
    private HashMap<String,Monster> listToDescription;
    private HashMap<String,Treasure> listToDescription2;
    public Controller(ApplicationStart theGui){
        myGui = theGui;
        dungeon = new MainDungeon ();
        this.setUpLevel();
        mapNameToChambers = new HashMap<>();
        mapNameToPassages = new HashMap<>();
        mapNameToMonsterChamber = new HashMap<>();
        mapNameToMonsterPassage = new HashMap<>();
        mapNameToTreasureChamber = new HashMap<>();
        mapNameToTreasurePassage = new HashMap<>();
        nameDoorList = new ArrayList<>();
        mapNameToDoors = new HashMap<>();
        monster = new ArrayList<>();
        mapToMonster = new HashMap<>();
        treasureList = new ArrayList<>();
        treasure1 = new ArrayList<>();
        mapToTreasure = new HashMap<>();
        treasureKey = new ArrayList<>();
        monsterKey = new ArrayList<>();
        listToDescription= new HashMap<>();
        listToDescription2 = new HashMap<>();
    }
    private void setUpLevel(){
        dungeon.createChambers(NUM_CHAMBERS);
        dungeon.setDoorsInChamber(NUM_CHAMBERS);
        this.setTargets();
        dungeon.doorToChamber();
        dungeon.connectChamberToChambers();
    }
    private void setTargets(){
        for(int i = 0; i < NUM_CHAMBERS;i++){
            dungeon.getTarget(i);
        }
    }
    private ArrayList<String> getNameList(){
        this.setNameList();
        System.out.println("The name list is:" + nameList);
        return nameList;
    }
    public void setNameList(){
        nameList = new ArrayList<>();
        chambersInLevel = dungeon.getChamberArray();
        passagesInLevel = dungeon.getPassArray();
        i = 0;
        for(Chamber c: chambersInLevel){
            System.out.println("Chamber description: "+ c.getDescription());
            nameList.add(("Chamber "+ (i+1)));
            mapNameToChambers.put(nameList.get(i),c);
            i++;
        }
        for(Passage p: passagesInLevel){
            nameList.add(("Passage "+ ((i-NUM_CHAMBERS)+1)));
            mapNameToPassages.put(nameList.get(i),p);
            i++;
        }
    }
    public ArrayList<String> getNewDescription(){
        return getNameList();
    }
    public String getChamberDescription(String chamberKey){
        return dungeon.getChamberDescription(mapNameToChambers.get(chamberKey));
    }
    public String getPassageDescription(String passageKey){
        return dungeon.getPassageDescription(mapNameToPassages.get(passageKey));
    }
    public String reactToClick(ObservableList<String> s){
        if(mapNameToChambers.containsKey(s.get(0))){
            return this.getChamberDescription(s.get(0));
        }
        return this.getPassageDescription(s.get(0));
    }
    public void setNameOfDoors(){
        i = 0;
        doorArray = dungeon.getDoorArray();
        for(Door d: doorArray){
            nameDoorList.add(("Door "+ (i+1)));
            mapNameToDoors.put(nameDoorList.get(i),d);
            i++;
        }
    }
    public ArrayList<String> getDoorList(){
        this.setNameOfDoors();
        return nameDoorList;
    }
    public ArrayList<String> getDoorDescription(){
        return getDoorList();
    }
    public String comboBoxClicked(int index){
        mapDoorToChamber = dungeon.getMapDoorToChamber();
        return mapNameToDoors.get("Door " + (index + 1)).getDescription()+mapDoorToChamber.get(mapNameToDoors.get("Door " + (index + 1)));
    }
    public boolean checkSelectedValue(ObservableList<String>s){
        if(s.isEmpty()) {
            return false;
        }
        return true;
    }
    public boolean checkForValidText(String s){
        if(s.isEmpty()){
            return false;
        }
        else if(!s.matches("[0-9]+")){
            return false;
        }
        else if (Integer.parseInt(s) > 100 || Integer.parseInt(s) <= 0){
            return false;
        }
        return true;
    }

    public ArrayList<String> getMonsterList(ObservableList<String> s){
        i = 0;
        monsterKey = new ArrayList<>();
        if(mapNameToChambers.containsKey(s.get(0))){
            for(Monster m: mapNameToChambers.get(s.get(0)).getMonsters()){
                mapNameToMonsterChamber.put(("Monster " + (i +1)),m);
                monsterKey.add("Monster " + (i +1));
                i++;
           }
        }
        else{
           for(int j = 0; j <(mapNameToPassages.get(s.get(0)).sizeOfPassage());j++){
                if(mapNameToPassages.get(s.get(0)).getMonster(j)!= null) {
                    mapNameToMonsterPassage.put(("Monster " + (i + 1)), mapNameToPassages.get(s.get(0)).getMonster(j));
                    monsterKey.add("Monster " + (i + 1));
                    i++;
                }
           }
        }
        return monsterKey;
    }
    public ArrayList<String> getTreasureList(ObservableList<String> s){
        i = 0;
        treasureKey = new ArrayList<>();
        if(mapNameToChambers.containsKey(s.get(0))){
            for(Treasure t: mapNameToChambers.get(s.get(0)).getTreasureList()){
                mapNameToTreasureChamber.put(("Treasure " + (i +1)),t);
                treasureKey.add("Treasure " + (i +1));
                i++;
            }
        }
        else{
            for(int j = 0; j < (mapNameToPassages.get(s.get(0)).sizeOfPassage());j++){
                if(mapNameToPassages.get(s.get(0)).getTreasure(j)!= null) {
                    mapNameToTreasurePassage.put(("Treasure " + (i + 1)), mapNameToPassages.get(s.get(0)).getTreasure(j));
                    treasureKey.add("Treasure " + (i + 1));
                    i++;
                }
            }
        }
        return treasureKey;
    }
    public ArrayList<String> getAllMonsterDescripton(ObservableList<String> s){
        String prevs = "cheese";
        monsterList = new ArrayList<>();
        for(int i = 1; i <= 100; i++) {

            monster.add(new Monster());
            monster.get(i - 1).setType(i);

            if(monster.get(i-1).getDescription().compareTo(prevs)!=0){
                String description = monster.get(i - 1).getDescription();
                monsterList.add(description);
                prevs = description;
                mapToMonster.put(description,i);
            }
        }
        return monsterList;
    }
    public ArrayList<String> getAllTreasureDescripton(ObservableList<String> s){
         String prevs = "cheese";
        treasureList = new ArrayList<>();
        for(int i = 1; i <= 100; i++) {

            treasure1.add(new Treasure());
            treasure1.get(i - 1).chooseTreasure(i);

            if(treasure1.get(i-1).getDescription().compareTo(prevs)!=0){
                String description = treasure1.get(i - 1).getDescription();
                treasureList.add(description);
                prevs = description;
                mapToTreasure.put(description,i);
            }
        }
        return treasureList;
    }
    public void leftAction(ObservableList<String> s,boolean isMonster){

        String temp = viewMonsterList2.getSelectionModel().getSelectedItems().get(0);

        if(mapNameToChambers.containsKey(s.get(0))){
            if(isMonster){
                Monster m = new Monster();
                m.setType(mapToMonster.get(temp));
                mapNameToChambers.get(s.get(0)).addMonster(m);
                mapNameToChambers.get(s.get(0)).setDescription(temp);
                listToDescription.put("Monster "+(monsterList1.size()+1),m);
                monsterList1.add("Monster "+(monsterList1.size()+1));
            }
            else{
                Treasure t = new Treasure();
                t.chooseTreasure(mapToTreasure.get(temp));
                mapNameToChambers.get(s.get(0)).addTreasure(t);
                mapNameToChambers.get(s.get(0)).setDescription(temp);
                listToDescription2.put("Treasure "+(monsterList1.size()+1),t);
                monsterList1.add("Treasure "+(monsterList1.size()+1));
            }
        }
        else{
            if(isMonster){
                Monster m = new Monster();
                PassageSection ps = new PassageSection();
                m.setType(mapToMonster.get(temp));
                ps.addMonster(m);
                ps.setDescription(temp);
                mapNameToPassages.get(s.get(0)).addPassageSection(ps);
                listToDescription.put("Monster "+(monsterList1.size()+1),m);
                monsterList1.add("Monster "+(monsterList1.size()+1));
            }
            else{
                Treasure t = new Treasure();
                PassageSection ps = new PassageSection();
                t.chooseTreasure(mapToTreasure.get(temp));
                ps.addTreasure(t);
                ps.setDescription(temp);
                mapNameToPassages.get(s.get(0)).addPassageSection(ps);
                listToDescription2.put("Treasure "+(monsterList1.size()+1),t);
                monsterList1.add("Treasure "+(monsterList1.size()+1));
            }
        }

    }
    public void rightAction(ObservableList<String> s,boolean isMonster){
        String temp = viewMonsterList.getSelectionModel().getSelectedItems().get(0);
        if(mapNameToChambers.containsKey(s.get(0))){
            try{
                if(isMonster){
                    mapNameToChambers.get(s.get(0)).removeMonster(listToDescription.get(temp));
                }
                else{
                    mapNameToChambers.get(s.get(0)).removeTreasure(listToDescription2.get(temp));

                }
            }
            catch (NullPointerException e){
                try {
                    mapNameToChambers.get(s.get(0)).removeMonster(mapNameToMonsterChamber.get(temp));
                }
                catch(NullPointerException f){
                    mapNameToChambers.get(s.get(0)).removeTreasure(mapNameToTreasureChamber.get(temp));
                }
            }
        }
        else{
            if(isMonster){
                for(int i = 0; i < mapNameToPassages.get(s.get(0)).sizeOfPassage(); i++){
                    try{
                        if(mapNameToPassages.get(s.get(0)).getMonster(i).equals(listToDescription.get(temp))){
                            mapNameToPassages.get(s.get(0)).removeMonster(i);
                        }
                    }
                    catch(NullPointerException e){}
                }
            }
            else{
                for(int i = 0; i < mapNameToPassages.get(s.get(0)).sizeOfPassage(); i++){

                    try{
                        if(mapNameToPassages.get(s.get(0)).getTreasure(i).equals(listToDescription2.get(temp))){
                            mapNameToPassages.get(s.get(0)).removeTreasure(i);
                        }
                    }
                    catch(NullPointerException e){}
                }
            }
        }
        monsterList1.remove(temp);
    }
    public void setValues(ListView<String> viewMonsterList,ListView<String> viewMonsterList2,ObservableList<String> monsterList,ObservableList<String> monsterList2){
        this.viewMonsterList = viewMonsterList;
        this.viewMonsterList2 = viewMonsterList2;
        this.monsterList1 = monsterList;
        this.monsterList2 = monsterList2;
    }
    public boolean checkIndex(int i){
        if(i == 0){
            return true;
        }
        return false;
    }

   public String saveFile(File file){
        try {

            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream outStream = new ObjectOutputStream(fileOut);
            outStream.writeObject(dungeon);
            outStream.close();
            fileOut.close();
        }
        catch(IOException i) {}
       return "Success. File saved. Press esc to conintue....";
    }
    public void loadFile(File file) {
        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            Object object = in.readObject();
            dungeon = (MainDungeon) object;
        }
        catch(EOFException e) {}
        catch(IOException f){}
        catch (ClassNotFoundException j){}
        System.out.println("");
        System.out.println("Inside the other method");
        System.out.println("");
    }
}



