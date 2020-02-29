package gui;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import java.io.File;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.control.*;
import java.util.ArrayList;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

public class ApplicationStart extends Application {

    private Controller theController = new Controller(this);
    private BorderPane root;
    private Popup descriptionPane;
    private Stage primaryStage;
    private ListView listView;
    private ComboBox dropDownMenu;
    private ComboBox fileStateSaveLoad;
    private  TextArea descriptionField;
    private Popup pop;
    private Stage as2;
    private Button edit;
    private Popup pop1;
    private Popup pop2;
    private VBox secondaryLayout;
    private Button monsterEdit;
    private Button treasureEdit;
    private Stage newWindow;
    private BorderPane thridLayout;
    private Button leftButton;
    private Button rightButton;
    private ObservableList<String> monsterList;
    private ListView<String> viewMonsterList;
    private Stage thirdWindow;
    private ObservableList<String> monsterList2;
    private ListView<String> viewMonsterList2;
    private BorderPane fourthLayout;
    private Stage fourthWindow;
    private Button btn;
    private FileChooser file;
    private ArrayList<String> nameList;
    @Override
    public void start(Stage assignedStage) {
        as2 = assignedStage;
        this.rootMainuplation();
    }
    private void rootMainuplation(){
        root = this.setUpRoot();
        root.setId("r1");
        Scene scene = new Scene(root,1000, 900);
        scene.getStylesheets().add("/res/stylesheet.css");
        as2.setTitle("Level generation");
        as2.setScene(scene);
        as2.show();
    }
    private BorderPane setUpRoot() {
        BorderPane temp = new BorderPane();
        temp.setTop(setUpTopNode());
        temp.setLeft(setUpLeftNode());
        temp.setCenter(setUpCenterNode());
        temp.setRight(setUpRightNode());
        temp.setBottom(setUpBottomNode());
        return temp;
    }
    private Node setUpTopNode(){
        HBox temp = new HBox();
        this.setUpFileState();
        this.checkBoxPressed();
        temp.getChildren().addAll(fileStateSaveLoad);
        return temp;
    }
    private void setUpFileState(){
        fileStateSaveLoad = new ComboBox();
        fileStateSaveLoad.setId("comobox1");
        fileStateSaveLoad.getItems().add("Save File");
        fileStateSaveLoad.getItems().add("Load File");
        fileStateSaveLoad.setPromptText("File");
    }
    private void checkBoxPressed(){
        fileStateSaveLoad.setOnAction((e) ->{
            if(theController.checkIndex(fileStateSaveLoad.getSelectionModel().getSelectedIndex())){
               this.createNewWindow();
               this.showDialogBox();
           }
           else{
               this.createNewWindow();
               this.showLoadBox();
           }
        });
    }
    private void showLoadBox(){
        btn.setOnAction((e)-> {
            file = new FileChooser();
            File pickFile = file.showOpenDialog(fourthWindow);
            if (pickFile != null) {
                theController.loadFile(pickFile);
                this.showSuccessPopUp("Success. File loaded. Press esc to conintue....").show(fourthWindow);
                this.rootMainuplation();
            }
        });
    }
    private void showDialogBox(){
        btn.setOnAction((e)-> {
           file = new FileChooser();
           File pickFile = file.showSaveDialog(fourthWindow);
            if (pickFile != null) {
                this.showSuccessPopUp(theController.saveFile(pickFile)).show(fourthWindow);
            }
        });
    }
    private void createNewWindow(){
        this.setCharacteristicsOfnewLayout();
        Scene thridSence = new Scene(fourthLayout,200, 100);
        thridSence.getStylesheets().add("/res/stylesheet.css");
        fourthWindow = new Stage();
        fourthWindow.setScene(thridSence);
        fourthWindow.initModality(Modality.WINDOW_MODAL);
        fourthWindow.initOwner(as2);
        fourthWindow.show();
    }
    private void setCharacteristicsOfnewLayout(){
        fourthLayout = new BorderPane();
        fourthLayout.setCenter(setCenterNod());
    }

    private Node setCenterNod(){
        VBox c = new VBox();
        c.setId("c");
        btn = new Button("Open dialog box");
        btn.setPrefHeight(200);
        btn.setPrefWidth(200);
        btn.setId("btn4");
        c.getChildren().addAll(new Label(),new Label(),btn,new Label());
        return c;
    }

    private Node setUpLeftNode(){
        listView = new ListView();
        listView.setId("listView1");
        nameList = theController.getNewDescription();
        this.createList(listView,nameList);
        VBox temp = new VBox(listView);
        return temp;
    }
    private void createList(ListView newView,ArrayList<String> namesList){
        for(int i = 0; i < namesList.size();i++){
            newView.getItems().add(namesList.get(i));
        }
    }
    private Node setUpCenterNode(){
        descriptionField = new TextArea();
        descriptionField = new TextArea();
        descriptionField.setId("text");
        listView.setOnMouseClicked((e) -> {
            descriptionField.setText(theController.reactToClick(listView.getSelectionModel().getSelectedItems()));
        });
        VBox temp = new VBox();
        temp.getChildren().add(descriptionField);
        return temp;
    }
    private Node setUpRightNode(){
        dropDownMenu = new ComboBox();
        dropDownMenu.setId("comobox2");
        ArrayList<String> doorList = theController.getDoorDescription();
        this.createComboBox(dropDownMenu,doorList);
        dropDownMenu.setPromptText("List of doors");
        this.selectOption();
        VBox temp = new VBox(dropDownMenu);
        return temp;
    }
    private void createComboBox(ComboBox choice,ArrayList<String> doorList){
        for(int i = 0; i < doorList.size();i++){
            choice.getItems().add(doorList.get(i));
        }
    }
    private void selectOption(){
        dropDownMenu.setOnAction((e) ->{
            pop = this.createPopUp(720,500, theController.comboBoxClicked(dropDownMenu.getSelectionModel().getSelectedIndex()));
            pop.show(as2);
        });

    }
    private Popup createPopUp(int x, int y, String text) {
        Popup popup = new Popup();
        popup.setX(x);
        popup.setY(y);
        TextArea textA = new TextArea(text);
        textA.setId("text2");
        popup.getContent().addAll(textA);
        textA.setMinWidth(80);
        textA.setMinHeight(50);
        popup.setAutoHide(true);
        return popup;
    }
    private Node setUpBottomNode(){
        edit = new Button("Edit");
        edit.setId("btn1");
        this.checkClick();
        VBox temp = new VBox(edit);
        return temp;
    }
    private void checkClick(){
        edit.setOnMouseClicked( (e) ->{
            if(!theController.checkSelectedValue(listView.getSelectionModel().getSelectedItems())) {
                pop1 = this.showErrorPopUp("Please select an entry from the list. Press esc to continue...");
                pop1.show(as2);
            }
            else{
                this.createSelectWindow();
            }

        });
        }
    private Popup showErrorPopUp(String text){
        Popup popup = new Popup();
        TextArea textA = new TextArea(text);
        textA.setStyle("-fx-text-fill: red; -fx-font-size: 20px;");
        popup.getContent().addAll(textA);
        popup.setAutoHide(true);
        return popup;
    }
    private void createSelectWindow(){
        this.setCharacteristicsOfWindows();
        Scene secondScene = new Scene(secondaryLayout,250,130);
        secondScene.getStylesheets().add("/res/stylesheet.css");
        newWindow = new Stage();
        newWindow.setScene(secondScene);
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(as2);
        newWindow.show();
        this.checkIfButtonPress();
    }
    private void setCharacteristicsOfWindows(){
        monsterEdit = new Button("Press to add/remove a monster");
        monsterEdit.setId("btn2");
        treasureEdit = new Button("Press to add/remove a treasure");
        treasureEdit.setId("btn3");
        secondaryLayout = new VBox();
        secondaryLayout.setId("n");
        secondaryLayout.getChildren().addAll(new Label(),monsterEdit,new Label(),treasureEdit,new Label());
    }
    private void checkIfButtonPress(){
        monsterEdit.setOnAction((e) -> {
            this.setListWindow(new Label("Current monster list"),new Label("New monster list"),true);
            this.addOrRemove(true);
        });
        treasureEdit.setOnAction((e)->{
            this.setListWindow(new Label("Current treasure list"),new Label("New treasure list"),false);
            this.addOrRemove(false);
        });
    }
    private void setListWindow(Label a, Label b,Boolean t){
        this.setCharacteristicsOfLayout(a,b,t);
        Scene thridSence = new Scene(thridLayout,700, 500);
        thridSence.getStylesheets().add("/res/stylesheet.css");
        thirdWindow = new Stage();
        thirdWindow.setScene(thridSence);
        thirdWindow.initModality(Modality.WINDOW_MODAL);
        thirdWindow.initOwner(as2);
        thirdWindow.show();

    }
    private void setCharacteristicsOfLayout(Label a, Label b,Boolean t){
        leftButton = new Button(" < ");
        leftButton.setId("btn5");
        rightButton = new Button(" > ");
        rightButton.setId("btn6");
        this.setRoot(a,b,t);
    }
    private void setRoot(Label a, Label b,Boolean t){
        thridLayout = new BorderPane();
        thridLayout.setId("thridlay");
        thridLayout.setCenter(setCenterNode());
        if(t) {
            thridLayout.setLeft(setLeftNode(a));
            thridLayout.setRight(setRightNode(b));
        }
        else{
            thridLayout.setLeft(setLeftNodeTreasure(a));
            thridLayout.setRight(setRightNodeTreasure(b));
        }
    }
    private Node setLeftNode(Label a){
        VBox temp = new VBox();
        a.setId("label1");
        monsterList = FXCollections.observableArrayList(theController.getMonsterList(listView.getSelectionModel().getSelectedItems()));
        viewMonsterList = new ListView(monsterList);
        viewMonsterList.setId("listView2");
        temp.getChildren().addAll(a,viewMonsterList);
        return temp;
    }
    private Node setCenterNode(){
        VBox temp = new VBox();
        Label l = new Label();
        l.setPrefHeight(50);
        l.setPrefWidth(200);
        temp.getChildren().addAll(l,leftButton,rightButton);
        return temp;
    }
    private Node setRightNode(Label b){
        VBox temp = new VBox();
        b.setId("label2");
        monsterList2 = FXCollections.observableArrayList(theController.getAllMonsterDescripton(listView.getSelectionModel().getSelectedItems()));
        viewMonsterList2 = new ListView(monsterList2);
        viewMonsterList2.setId("listView3");
        temp.getChildren().addAll(b,viewMonsterList2);
        return temp;
    }
    private Node setLeftNodeTreasure(Label a){
        a.setId("cheese");
        VBox temp = new VBox();
        monsterList = FXCollections.observableArrayList(theController.getTreasureList(listView.getSelectionModel().getSelectedItems()));
        viewMonsterList = new ListView(monsterList);
        viewMonsterList.setId("vM1");
        temp.getChildren().addAll(a,viewMonsterList);
        return temp;
    }
    private Node setRightNodeTreasure(Label b){
        b.setId("notCheese");
        VBox temp = new VBox();
        monsterList2 = FXCollections.observableArrayList(theController.getAllTreasureDescripton(listView.getSelectionModel().getSelectedItems()));
        viewMonsterList2 = new ListView(monsterList2);
        viewMonsterList2.setId("vM2");
        temp.getChildren().addAll(b,viewMonsterList2);
        return temp;
    }
    private void addOrRemove(Boolean isMonster){
        leftButton.setOnAction((e) -> {
            if (!viewMonsterList2.getSelectionModel().getSelectedItems().isEmpty()) {
                theController.setValues(viewMonsterList,viewMonsterList2,monsterList,monsterList2);
                theController.leftAction(listView.getSelectionModel().getSelectedItems(),isMonster);
                this.showSuccessPopUp("Changes saved. Press esc to conintue...").show(thirdWindow);
                descriptionField.setText(theController.reactToClick(listView.getSelectionModel().getSelectedItems()));
            }
            else {
                this.showErrorPopUp("No value selected. Please you select an item to add from the list. Press esc to continue...").show(thirdWindow);
            }
        });

        rightButton.setOnAction((e) -> {
            if (!viewMonsterList.getSelectionModel().getSelectedItems().isEmpty()) {
                theController.setValues(viewMonsterList,viewMonsterList2,monsterList,monsterList2);
                theController.rightAction(listView.getSelectionModel().getSelectedItems(),isMonster);
                this.showSuccessPopUp("Changes saved. Press esc to conintue...").show(thirdWindow);
                descriptionField.setText(theController.reactToClick(listView.getSelectionModel().getSelectedItems()));
            }
            else {
                this.showErrorPopUp("No value selected. Please you select an item to remove from the list. Press esc to continue...").show(thirdWindow);
            }
        });
    }
    private Popup showSuccessPopUp(String text){
        Popup popup = new Popup();
        TextArea textA = new TextArea(text);
        textA.setStyle("-fx-text-fill: green; -fx-font-size: 30px;");
        popup.getContent().addAll(textA);
        popup.setAutoHide(true);
        return popup;
    }
    public static void main(String[] args) {
        launch(args);
    }

}
