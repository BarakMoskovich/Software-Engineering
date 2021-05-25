package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Model;

public class View {
    public static final int WIDTH = 960;
    public static final int HEIGHT = 384;
    public static final String TENNIS_TYPE = "Tennis";
    public static final String BASKETBALL_TYPE = "Basketball";
    public static final String SOCCER_TYPE = "Soccer";
    public static final Font FONT_HEADER_SIZE = Font.font("Comic Sans MS", 25);
    public static final String SHADOW = "-fx-effect: dropshadow(one-pass-box , rgb(0, 0, 102), 10, 0.1, 2, 2);";
    public static final String WINNER_SHADOW = "-fx-effect: dropshadow(one-pass-box , rgb(153, 0, 76), 10, 0.1, 2, 2);";
    private int numberOfParticipants = Model.NUMBER_OF_PARTICIPANTS;
    private Stage stage;
    private BorderPane borderPane;
    private VBox centerVBox, rightVBox, participantsVBox;
    private HBox headerHBox, participantHBox, buttonsHBox, leftHBox;
    private TextField[] nameFields;
    private TextField participantNameField;
    private Button addParticipantButton, startChampionshipButton;
    private ToggleGroup gameTypeToggle;
    private RadioButton tennisRadioButton, basketballRadioButton, soccerRadioButton;

    public View(Stage stage) {
        this.stage = stage;
        borderPane = new BorderPane();

        assignAll();

        borderPane.setTop(headerHBox);
        borderPane.setLeft(leftHBox);
        borderPane.setCenter(centerVBox);
        borderPane.setRight(rightVBox);

        Scene scene = new Scene(borderPane, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void assignAll() {
        assignHeader("Championship");
        assignParticipantNameFields();
        assignButtons();
        assignGameTypesRadioBoxes();
        assignParticipantsNames();
    }

    private void assignParticipantNameFields() { // Center
        centerVBox = new VBox();
        centerVBox.setAlignment(Pos.CENTER);
        participantHBox = new HBox();
        Text text = new Text("Participant name ");
        participantNameField = new TextField();

        participantHBox.getChildren().addAll(text, participantNameField);
        centerVBox.getChildren().add(participantHBox);
        centerVBox.setMargin(participantHBox, new Insets(0, 0, 0, 200));
    }

    private void assignButtons() {
        // Center
        // add participant
        // start Championship - freeze
        buttonsHBox = new HBox();

        addParticipantButton = new Button("Add Participant");

        startChampionshipButton = new Button("Start Championship");


        buttonsHBox.getChildren().addAll(addParticipantButton, startChampionshipButton);
        buttonsHBox.setMargin(startChampionshipButton, new Insets(0, 0, 0, 20));
        centerVBox.getChildren().add(buttonsHBox);
        centerVBox.setMargin(buttonsHBox, new Insets(10, 0, 0, 200));
    }

    private void assignGameTypesRadioBoxes() {
        //   Right
        // Tennis
        // BasketBall
        // Soccer
        rightVBox = new VBox();
        rightVBox.setAlignment(Pos.CENTER_LEFT);
        gameTypeToggle = new ToggleGroup();

        tennisRadioButton = new RadioButton(TENNIS_TYPE);
        tennisRadioButton.setToggleGroup(gameTypeToggle);
        tennisRadioButton.setSelected(true);

        basketballRadioButton = new RadioButton(BASKETBALL_TYPE);
        basketballRadioButton.setToggleGroup(gameTypeToggle);

        soccerRadioButton = new RadioButton(SOCCER_TYPE);
        soccerRadioButton .setToggleGroup(gameTypeToggle);

        rightVBox.getChildren().addAll(tennisRadioButton, basketballRadioButton, soccerRadioButton);
        rightVBox.setMargin(tennisRadioButton, new Insets(0, 50, 0, 0));
        rightVBox.setMargin(basketballRadioButton, new Insets(5, 50, 0, 0));
        rightVBox.setMargin(soccerRadioButton, new Insets(5, 50, 0, 0));
    }

    private void assignParticipantsNames() {
        // left
        participantsVBox = new VBox();
        leftHBox = new HBox();
        nameFields = new TextField[numberOfParticipants];
        for (int i = 0; i < nameFields.length; i++) {
            nameFields[i] = new TextField();

            nameFields[i].setEditable(false);
            nameFields[i].setFocusTraversable(false);
            nameFields[i].setMouseTransparent(true);
            nameFields[i].setStyle(SHADOW); // Assign shading

            participantsVBox.getChildren().add(nameFields[i]);
            participantsVBox.setMargin(nameFields[i], new Insets(15, 0, 0, 30));
        }
        leftHBox.getChildren().add(participantsVBox);
    }

    public void updateParticipantsNames(String name, int place) {
        participantNameField.clear();
        nameFields[place].setText(name);
    }

    public String getParticipantName() {
        return participantNameField.getText();
    }

    public void disableAddParticipantButton() {
        addParticipantButton.setDisable(true);
    }

    public void addEventHandlerToAddParticipantButton(EventHandler<ActionEvent> event) {
        addParticipantButton.setOnAction(event);
    }

    public void addEventHandlerToStartChampionshipButton(EventHandler<ActionEvent> event) {
        startChampionshipButton.setOnAction(event);
    }

    public String getChampionshipType() {
        String result;
        if (tennisRadioButton.isSelected())
            result = tennisRadioButton.getText();
        else if (basketballRadioButton.isSelected())
            result = basketballRadioButton.getText();
        else
            result = soccerRadioButton.getText();

        return result;
    }

    public HBox getTextFieldHBox() {
        return leftHBox;
    }

    public TextField[] getParticipantTextFields() {
        return nameFields;
    }

    private void assignHeader(String header) {
        headerHBox = new HBox();
        Text text = new Text(header);
        text.setFont(FONT_HEADER_SIZE);

//        text.setStyle(SHADOW);

        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.color(0.4f, 0.4f, 0.4f));

        text.setEffect(ds);

        headerHBox.getChildren().add(text); // Header
        headerHBox.setAlignment(Pos.CENTER);
    }

    public void updateToGameView() {
        // clear everything except TextField[] (left)
        borderPane.setRight(null);
        borderPane.setCenter(null);
    }

    public Stage getStage() {
        return stage;
    }

}
