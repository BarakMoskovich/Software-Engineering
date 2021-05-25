package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import model.Model;
import java.util.ArrayList;

public class ChampionshipView {
    // details window of championship
    private final int BUTTON_WIDTH = 80;
    private final int BUTTON_HEIGHT = 20;
    private final int LINE_WIDTH = 30;
    private int numberOfParallelRounds = Model.NUMBER_OF_PARALLEL_ROUNDS;
    private ArrayList<TextField[]> participants;
    private HBox mainView;
    private Button[] allPlayButtons; // all buttons

    public ChampionshipView(TextField[] participants, HBox mainView) {
        // assign original players text Field
        this.participants = new ArrayList<TextField[]>();
        this.participants.add(participants); // original being assigned
        this.mainView = mainView;

        allPlayButtons = new Button[Model.NUMBER_OF_PARTICIPANTS - 1];
        assignAll();
    }

    private void assignAll() {
        assignAllTextFields();
        assignButtons();
        assignChampionship();
    }

    private VBox getPlayGameButtonVBox(double width, double height, double btnWidth, double btnHeight, Button btn) {
        VBox playGameVBox = new VBox();
        HBox btnHBox = new HBox();
        btn.setMinHeight(btnHeight);
        btn.setMinWidth(btnWidth);

        Line top = new Line(0,0, width, 0);
        Line button = new Line(0,0, width, 0);
        Line rightTop = new Line(0,0, 0, (height / 2) - btnHeight);
        Line rightButton = new Line(0,0, 0, (height / 2) - btnHeight);
        Line midLine = new Line(0,0, (width / 2), 0);

        btnHBox.getChildren().setAll(btn, midLine);
        btnHBox.setMargin(midLine, new Insets((btnHeight / 2) + 3, 0, 0, 0));

        playGameVBox.getChildren().addAll(top, rightTop, btnHBox, rightButton, button);
        playGameVBox.setMargin(top, new Insets(0, width, 0, 0));
        playGameVBox.setMargin(rightTop, new Insets(0, 0, 0, width));
        playGameVBox.setMargin(btnHBox, new Insets(0, 0, 0, (btnWidth / 2) - width ));
        playGameVBox.setMargin(rightButton, new Insets(0, 0, 0, width));
        playGameVBox.setMargin(button, new Insets(0, 60, 0, 0));

        return playGameVBox;
    }

    private void assignButtons() {
        for (int i = 0; i < allPlayButtons.length; i++) {
            allPlayButtons[i] = new Button("Play a Game");
        }
    }

    private void assignAllTextFields() {
        int numberOfTextFields = getStartingParticipants().length / 2;

        for (int i = 0; i < numberOfParallelRounds; i++){ // all other TextFields are assigned
            TextField[] txtFields = new TextField[numberOfTextFields];

            for (int j = 0; j < txtFields.length; j++) {
                TextField txtField = new TextField();
                txtField.setMouseTransparent(true);
                txtField.setFocusTraversable(false);
                txtField.setEditable(false);

                if (i != (numberOfParallelRounds - 1))
                    txtField.setStyle(View.SHADOW);
                else
                    txtField.setStyle(View.WINNER_SHADOW); // Winner shadow

                txtFields[j] = txtField;
            }

            participants.add(txtFields);
            numberOfTextFields /= 2;
        }
    }

    private void assignChampionship() {
        VBox tempVBox, quarterVBox = new VBox(), semiVBox = new VBox(), finalVBox = new VBox();
        HBox tempHBox;

        // Quarter
        for (int i = 0; i < 4; i++) {
            tempHBox = new HBox();
            tempVBox = getPlayGameButtonVBox(LINE_WIDTH, 55, BUTTON_WIDTH, BUTTON_HEIGHT, allPlayButtons[i]);
            tempHBox.getChildren().addAll(tempVBox, participants.get(1)[i]);

            tempHBox.setMargin(tempVBox, new Insets(0, 0, 35, 0));
            tempHBox.setMargin(participants.get(1)[i], new Insets(10, 0, 0, 0));

            quarterVBox.getChildren().addAll(tempHBox);
        }

        // Semi-final
        for (int i = 0; i < 2; i++) {
            tempHBox = new HBox();
            tempVBox = getPlayGameButtonVBox(LINE_WIDTH, 91, BUTTON_WIDTH, BUTTON_HEIGHT, allPlayButtons[i + 4]);
            tempHBox.getChildren().addAll(tempVBox, participants.get(2)[i]);

            tempHBox.setMargin(tempVBox, new Insets(0, 0, 77, 0));
            tempHBox.setMargin(participants.get(2)[i], new Insets(30, 0, 0, 0));

            semiVBox.getChildren().addAll(tempHBox);
        }

        // Final
        tempHBox = new HBox();
        tempVBox = getPlayGameButtonVBox(LINE_WIDTH, 170, BUTTON_WIDTH, BUTTON_HEIGHT, allPlayButtons[6]);
        tempHBox.getChildren().addAll(tempVBox, participants.get(3)[0]);

        tempHBox.setMargin(tempVBox, new Insets(1, 0, 77, 0));
        tempHBox.setMargin(participants.get(3)[0], new Insets(70, 0, 0, 0));

        finalVBox.getChildren().addAll(tempHBox);

        mainView.getChildren().addAll(quarterVBox, semiVBox, finalVBox);
        mainView.setMargin(quarterVBox, new Insets(25, 0, 0, 0));
        mainView.setMargin(semiVBox, new Insets(47, 0, 0, 0));
        mainView.setMargin(finalVBox, new Insets(90, 0, 0, 0));

    }

    private TextField[] getStartingParticipants() {
        return participants.get(0);
    }

    public TextField[] getParticipants(int place) {
        return participants.get(place);
    }

    public Button getButtonNumber(int roundNumber, int buttonPlace) {
        switch (roundNumber) {
            case 1:
                return allPlayButtons[buttonPlace + 4];
            case 2:
                return allPlayButtons[buttonPlace + 6];
            case 0:
            default:
                return allPlayButtons[buttonPlace];
        }
    }

    public TextField getWinnerTextField(int roundNumber, int buttonPlace) {
        switch (roundNumber) {
            case 1:
                return participants.get(2)[buttonPlace];
            case 2:
                return participants.get(3)[0];
            case 0:
            default:
                return participants.get(1)[buttonPlace];
        }
    }
}
