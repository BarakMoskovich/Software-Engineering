package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GameView {
    public static final int WIDTH = 400;
    public static final int HEIGHT = 170;
    public static final int BASKETBALL_ROUNDS = 4;
    public static final int SOCCER_TIE_ROUNDS = 1;
    public static final int SOCCER_FREE_KICKS = 5;
    public static final int SOCCER_FREE_KICKS_TIE = 1;
    public static final int SOCCER_ROUNDS = 2;
    public static final int TENNIS_MAXIMUM_ROUNDS = 5;
    public static final int TENNIS_MINIMUM_ROUNDS = 3;
    private final int PARTICIPANTS = 2;
    private final int TEXT_FIELD_WIDTH = 50;
    private final int BUTTON_FREE_KICK_WIDTH = 50;
    private final int LABEL_WIDTH = 80;
    private final int DONE_BUTTON_MARGIN = 30;
    private final int SPACE = 10;

    private BorderPane borderPane;
    private Stage newStage;
    private TextField[] resultFields;
    private Button[] buttonsFreeKicks;
    private String[] participantNames;
    private HBox[] hbResult;
    private HBox hbButton;
    private VBox vbResult;
    private Label participantNameLabel;
    private Button doneButton;
    private final String gameType;
    private int rounds;
    private boolean isTieRound;
    private boolean isFreeKick;

    public GameView(String firstParticipant, String secondParticipant, Stage oldStage, String gameType) {
        hbResult = new HBox[PARTICIPANTS];
        participantNames = new String[PARTICIPANTS];
        participantNames[0] = firstParticipant;
        participantNames[1] = secondParticipant;

        this.gameType = gameType;
        borderPane = new BorderPane();
        newStage = new Stage();
        isTieRound = false;
        isFreeKick = false;

        assignAllFields();

        newStage.initOwner(oldStage);
        newStage.initModality(Modality.APPLICATION_MODAL);


        Scene scene = new Scene(borderPane, WIDTH, HEIGHT);
        newStage.setScene(scene);
        newStage.setResizable(false);

        // disable X button
        newStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
            }
        });

        newStage.show();
    }

    private void assignAllFields() {
        assignTitle();
        assignRoundsNumbers();
        assignDoneButton();
        assignView();
    }

    private void assignTitle() {
        HBox headerHBox = new HBox();
        Text txt = new Text(gameType + " Game");
        txt.setFont(View.FONT_HEADER_SIZE);
        headerHBox.getChildren().add(txt);
        headerHBox.setAlignment(Pos.CENTER);
        headerHBox.setMargin(txt, new Insets(0, 0, SPACE, 0));
        borderPane.setTop(headerHBox);
    }

    private void assignRoundsNumbers() {
        switch (gameType) {
            case View.SOCCER_TYPE:
                if (isFreeKick & isTieRound) {
                    rounds = SOCCER_TIE_ROUNDS; // 1
                } else if (isFreeKick) {
                    rounds = SOCCER_FREE_KICKS;
                } else if (isTieRound) {
                    rounds = SOCCER_TIE_ROUNDS;
                } else {
                    rounds = SOCCER_ROUNDS;
                }
                break;
            case View.BASKETBALL_TYPE:
                rounds = BASKETBALL_ROUNDS;
                break;
            case View.TENNIS_TYPE:
            default:
                rounds = TENNIS_MAXIMUM_ROUNDS;
                break;
        }
    }

    private void assignParticipantNameLabelToHBox(HBox hBox, int participantPlace) {
        participantNameLabel = new Label(participantNames[participantPlace]);
        participantNameLabel.setMinWidth(LABEL_WIDTH);
        participantNameLabel.setMaxWidth(LABEL_WIDTH);
        hBox.getChildren().add(participantNameLabel);
    }

    private void assignResultFieldsToHBox(HBox hBox, TextField[] resultFields, int participantPlace, int textFieldPlace) {
        int place;
        if (participantPlace == 1)
            place = textFieldPlace + rounds;
        else
            place = textFieldPlace;

        if (!isFreeKick) {
            resultFields[place] = new TextField();
            resultFields[place].setMaxWidth(TEXT_FIELD_WIDTH);
            hBox.getChildren().add(resultFields[place]);
            hBox.setMargin(resultFields[place], new Insets(0, 0, 0, SPACE));
        } else {
            buttonsFreeKicks[place] = new Button();
            buttonsFreeKicks[place].setMinWidth(BUTTON_FREE_KICK_WIDTH);
            buttonsFreeKicks[place].setMaxWidth(BUTTON_FREE_KICK_WIDTH);
            buttonsFreeKicks[place].setText("Kick!");

            if (place != 0)
                buttonsFreeKicks[place].setDisable(true);
            hBox.getChildren().add(buttonsFreeKicks[place]);
            hBox.setMargin(buttonsFreeKicks[place], new Insets(0, 0, 0, SPACE));
        }
    }

    private void assignDoneButton() {
        hbButton = new HBox();
        doneButton = new Button("Done");
        hbButton.getChildren().add(doneButton);
        hbButton.setAlignment(Pos.TOP_CENTER);
        hbButton.setMargin(doneButton, new Insets(0, 0, DONE_BUTTON_MARGIN, 0));
        borderPane.setBottom(hbButton);
    }

    private void assignView() {
        vbResult = new VBox();

        if (!isFreeKick)
            resultFields = new TextField[rounds * 2];
        else
            buttonsFreeKicks = new Button[rounds * 2];

        for (int i = 0; i < PARTICIPANTS; i++) {
            hbResult[i] = new HBox();
            assignParticipantNameLabelToHBox(hbResult[i], i);

            for (int j = 0; j < rounds; j++)
                assignResultFieldsToHBox(hbResult[i], resultFields, i, j);

            hbResult[i].setAlignment(Pos.CENTER);
            vbResult.getChildren().add(hbResult[i]);
            vbResult.setMargin(hbResult[i], new Insets(0, 0, 20, 0));
        }

        vbResult.setAlignment(Pos.CENTER);
        borderPane.setCenter(vbResult);
    }

    public void closeWindow() {
        newStage.close();
    }

    public String getFirstParticipant() {
        return participantNames[0];
    }

    public String getSecondParticipant() {
        return participantNames[1];
    }

    public TextField[] getResultFields() {
        return resultFields;
    }

    public Button[] getButtonsFreeKicks() {
        return buttonsFreeKicks;
    }

    public int getRounds() {
        return rounds;
    }

    public void updateToTieRounds() {
        isTieRound = true;
        borderPane.setCenter(null); // clears the Center
        assignRoundsNumbers(); // creates new Center
        assignView();
    }

    public void updateFreeKicks(boolean isTie) {
        isFreeKick = true;
        isTieRound = isTie;
        borderPane.setCenter(null); // clears the Center
        assignRoundsNumbers(); // creates new Center
        assignView();
    }

    public Button getButtonByNumber(int buttonNumber) {
        return buttonsFreeKicks[buttonNumber];
    }

    public void changeKickButtonValue(int buttonNumber) {
        String buttonText = buttonsFreeKicks[buttonNumber].getText();
        if (buttonText.equals("Kick!") || buttonText.equals("O"))
            buttonsFreeKicks[buttonNumber].setText("X");
        else
            buttonsFreeKicks[buttonNumber].setText("O");
    }

    public void enableKickButton(int buttonNumber) {
        buttonsFreeKicks[buttonNumber].setDisable(false);
    }

    public void addEventHandlerToDoneButton(EventHandler<ActionEvent> event) {
        doneButton.setOnAction(event);
    }
}