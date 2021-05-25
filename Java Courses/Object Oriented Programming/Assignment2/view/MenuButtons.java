package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class MenuButtons {
    public final ArrayList<String> menuButtonsTitle = new ArrayList<String>() {{
        add("Add Ballot Box");
        add("Add Citizen");
        add("Add Party");
        add("Assign Candidate");
        add("Show All BallotBoxes");
        add("Show All Citizens");
        add("Show All Parties");
        add("Begin Elections");
        add("Show Results");
    }};
    private LinkedHashMap<String, Button> menuButtons;
    public final int NUMBER_OF_BUTTONS = menuButtonsTitle.size();

    private String borderStyle;
    private VBox interfaceMenu;

    public MenuButtons(BorderPane borderPane) {
        menuButtons = new LinkedHashMap<String, Button>(NUMBER_OF_BUTTONS);

        interfaceMenu = new VBox(ViewGUI.SPACING);

        borderStyle = "-fx-border-color: #808080;\n" +
                    "-fx-border-width: 0 1 0 0;\n" +
                    "-fx-border-style: solid;\n";

        interfaceMenu.setStyle(borderStyle);

        Text menuText = new Text("Menu");
        menuText.setFont(Font.font("Tahoma", FontWeight.LIGHT, FontPosture.REGULAR, 16));

        interfaceMenu.setAlignment(Pos.TOP_CENTER);
        interfaceMenu.setPrefWidth(200);

        interfaceMenu.getChildren().add(menuText);
        assignMenuButtons();

        interfaceMenu.setMargin(menuButtons.get("Begin Elections"), new Insets(130, 0, 0, 0));
        interfaceMenu.setMargin(menuText, new Insets(10, 0, 0, -135));
        borderPane.setLeft(interfaceMenu);
    }

    private void assignMenuButtons() {
        for(int i = 0; i < NUMBER_OF_BUTTONS; i++) {
            Button temp = new Button(menuButtonsTitle.get(i));
            temp.setMinWidth(ViewGUI.MIN_BUTTON_WIDTH_VALUE);
            temp.setMinHeight(ViewGUI.MIN_BUTTON_HEIGHT_VALUE);
            temp.setDisable(true);

            menuButtons.put(temp.getText(), temp);
            interfaceMenu.getChildren().add(temp);
        }
    }

    public LinkedHashMap<String, Button> getAllButtons() {
        return menuButtons;
    }

    public Button getShowAllBallotBoxButton() {
        return menuButtons.get("Show All BallotBoxes");
    }

    public Button getAssignCandidateButton() {
        return menuButtons.get("Assign Candidate");
    }

    public Button getShowAllParties() {
        return menuButtons.get("Show All Parties");
    }

    public Button getShowAllCitizens() {
        return menuButtons.get("Show All Citizens");
    }

    public Button getBeginElectionButton() {
        return menuButtons.get("Begin Elections");
    }

    public Button getShowResults() {
        return menuButtons.get("Show Results");
    }

    public void addEventHandlerToAddBallotBoxButton(EventHandler<ActionEvent> event) {
        menuButtons.get("Add Ballot Box").setOnAction(event);
    }
    public void addEventHandlerToAddCitizenButton(EventHandler<ActionEvent> event) {
        menuButtons.get("Add Citizen").setOnAction(event);
    }

    public void addEventHandlerToAddPartyButton(EventHandler<ActionEvent> event) {
        menuButtons.get("Add Party").setOnAction(event);
    }

    public void addEventHandlerToAssignCandidateButton(EventHandler<ActionEvent> event) {
        menuButtons.get("Assign Candidate").setOnAction(event);
    }

    public void addEventHandlerToShowAllBallotBoxesButton(EventHandler<ActionEvent> event) {
        menuButtons.get("Show All BallotBoxes").setOnAction(event);
    }

    public void addEventHandlerToShowAllCitizensButton(EventHandler<ActionEvent> event) {
        menuButtons.get("Show All Citizens").setOnAction(event);
    }

    public void addEventHandlerToShowAllPartiesButton(EventHandler<ActionEvent> event) {
        menuButtons.get("Show All Parties").setOnAction(event);
    }

    public void addEventHandlerToBeginElectionsButton(EventHandler<ActionEvent> event) {
        menuButtons.get("Begin Elections").setOnAction(event);
    }

    public void addEventHandlerToShowResultsButton(EventHandler<ActionEvent> event) {
        menuButtons.get("Show Results").setOnAction(event);
    }


}