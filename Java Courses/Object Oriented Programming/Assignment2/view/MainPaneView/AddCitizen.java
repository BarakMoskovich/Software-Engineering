package view.MainPaneView;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import view.ViewGUI;

public class AddCitizen extends MainPane {
    private HBox hbName, hbID, hbBirthYear, hbIsolation, hbBallotBoxNumber;
    private VBox toggleIsolation;
    private TextField tfName, tfID, tfIsolation;
    private RadioButton inIsolation, notInIsolation;
    private ToggleGroup tg;
    private ComboBox year, ballotBox;
    private int electionYear;

    public AddCitizen(int year) {
        super();
        setHeadline("Add Citizen");
        electionYear = year;
        setFields();
    }

    private void setFields() {
        setNameField();
        setIDField();
        setBirthYearField();
        setIsolationField();
        setBallotBoxNumberField();
    }

    private void setNameField() {
        hbName = new HBox();
//        Text txtID = new Text("Name:");
        tfName = new TextField();
        tfName.setPromptText("Enter Your Name");
        tfName.setFont(buttonsFont);
        tfName.setMinWidth(ViewGUI.TEXT_INPUT_WIDTH_VALUE);
        hbName.getChildren().addAll(tfName);
    }

    private void setIDField() {
        hbID = new HBox();
//        Text txtID = new Text("ID:");
        tfID = new TextField();
        tfID.setPromptText("Enter Your ID");
        tfID.setFont(buttonsFont);
        tfID.setMinWidth(ViewGUI.TEXT_INPUT_WIDTH_VALUE);
        hbID.getChildren().addAll(tfID);
    }

    private void setBirthYearField() {
        hbBirthYear = new HBox();
        Text txtBirthYear = new Text("Birth Year:");
        txtBirthYear.setFont(buttonsFont);

        year = new ComboBox();

        // Year
        year.setPromptText("Year");
        for (int i = 1900; i <= (electionYear - 18); i++)
            year.getItems().add(i);

        hbBirthYear.getChildren().addAll(txtBirthYear, year);
        hbBirthYear.setSpacing(ViewGUI.SPACING);
    }

    private void setIsolationField() {
        hbIsolation = new HBox();
        tg = new ToggleGroup();
        toggleIsolation = new VBox();
        Text txtIsolation = new Text("In Isolation?");
        txtIsolation.setFont(buttonsFont);

        tfIsolation = new TextField();
        tfIsolation.setPromptText("How Many Days?");
        tfIsolation.setFont(buttonsFont);
        tfIsolation.setMaxWidth(150);
        tfIsolation.setVisible(false);

        inIsolation = new RadioButton("Yes");
        inIsolation.setFont(buttonsFont);
        inIsolation.setToggleGroup(tg);

        notInIsolation = new RadioButton("No");
        notInIsolation.setFont(buttonsFont);
        notInIsolation.setToggleGroup(tg);
        notInIsolation.setSelected(true);

        toggleIsolation.getChildren().addAll(inIsolation, notInIsolation);
        toggleIsolation.setMargin(notInIsolation, new Insets(5, 0, 0, 0));

        hbIsolation.getChildren().addAll(txtIsolation, toggleIsolation, tfIsolation);
        hbIsolation.setSpacing(15);
    }

    private void setBallotBoxNumberField() {
        hbBallotBoxNumber = new HBox();
        ballotBox = new ComboBox();
        ballotBox.setMinWidth(ViewGUI.MIN_BUTTON_WIDTH_VALUE);
        ballotBox.setPromptText("Choose BallotBox");
        ballotBox.setStyle("-fx-font: 14px \"Tahoma\";");
        ballotBox.getEditor().setFont(buttonsFont);
        hbBallotBoxNumber.getChildren().addAll(ballotBox);
    }

    public void updateBallotBoxes(ComboBox ballotBox) {
        hbBallotBoxNumber.getChildren().clear();
        ballotBox.setMinWidth(ViewGUI.MIN_BUTTON_WIDTH_VALUE);
        ballotBox.setPromptText("Choose BallotBox");
        ballotBox.setStyle("-fx-font: 14px \"Tahoma\";");
        ballotBox.getEditor().setFont(buttonsFont);
        this.ballotBox = ballotBox;
        hbBallotBoxNumber.getChildren().addAll(this.ballotBox);
    }

    public VBox getMainView() {
        return mainView;
    }

    public String getName() {
        return tfName.getText();
    }

    public ComboBox getBallotBox() {
        return ballotBox;
    }

    public int getSelectedBallotBox() {
        return Integer.parseInt(ballotBox.getSelectionModel().getSelectedItem().toString().split(" - ")[0]);
    }

    public String getID() {
        return tfID.getText();
    }

    public int getYear() {
        return Integer.parseInt(year.getSelectionModel().getSelectedItem().toString());
    }

    public boolean getIsolation() {
        if (inIsolation.isSelected())
            return true;
        return false;
    }

    public int getIsolationDays() {
        return Integer.parseInt(tfIsolation.getText());
    }

    public void isolationDaysVisible() {
        if (inIsolation.isSelected())
            tfIsolation.setVisible(true);
        else
            tfIsolation.setVisible(false);
    }

    public void addEventIsoRadio(ChangeListener<Toggle> chl) {
        tg.selectedToggleProperty().addListener(chl);
    }

    public void addEventYearComboBox(ChangeListener<Integer> chl) {
        year.getSelectionModel().selectedItemProperty().addListener(chl);
    }

    @Override
    public VBox update() {
        super.update();
        mainView.getChildren().addAll(hbName, hbID, hbBirthYear, hbIsolation, hbBallotBoxNumber, hbSubmit);
        mainView.setMargin(hbName, ViewGUI.DEFAULT_INSETS);
        mainView.setMargin(hbID, ViewGUI.DEFAULT_INSETS);
        mainView.setMargin(hbBirthYear, ViewGUI.DEFAULT_INSETS);
        mainView.setMargin(hbIsolation, ViewGUI.DEFAULT_INSETS);
        mainView.setMargin(hbBallotBoxNumber, ViewGUI.DEFAULT_INSETS);
        mainView.setMargin(hbSubmit, ViewGUI.DEFAULT_INSETS);

        return mainView;
    }
}
