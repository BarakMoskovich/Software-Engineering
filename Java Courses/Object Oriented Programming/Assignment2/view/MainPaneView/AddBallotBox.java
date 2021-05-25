package view.MainPaneView;

import javafx.geometry.Insets;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import view.ViewGUI;

public class AddBallotBox extends MainPane {
    private HBox hbAddress;
    private TextField tfAddress;
    private RadioButton rbRegular, rbCorona, rbArmy, rbArmyCorona;
    private ToggleGroup tgBallotBoxType;
    private VBox vbType;

    public AddBallotBox() {
        super();
        setHeadline("Add Ballot Box");
        setFields();
    }

    private void setFields() {
        setAddressField();
        setBallotBoxType();
    }

    private void setAddressField() {
        hbAddress = new HBox();
        tfAddress = new TextField();
        tfAddress.setPromptText("Enter Your Address");
        tfAddress.setFont(buttonsFont);
        tfAddress.setMinWidth(ViewGUI.TEXT_INPUT_WIDTH_VALUE);
        tfAddress.setFocusTraversable(false);

        hbAddress.getChildren().add(tfAddress);
    }

    private void setBallotBoxType() {
        vbType = new VBox();
        VBox typeButtons = new VBox();
        Text txtType = new Text("BallotBox Type :");
        txtType.setFont(buttonsFont);
        tgBallotBoxType = new ToggleGroup();
        rbRegular = new RadioButton("Regular");
        rbRegular.setUserData("Regular");
        rbRegular.setFont(buttonsFont);
        rbRegular.setToggleGroup(tgBallotBoxType);

        rbCorona = new RadioButton("Corona");
        rbCorona.setUserData("Corona");
        rbCorona.setToggleGroup(tgBallotBoxType);
        rbCorona.setFont(buttonsFont);

        rbArmy = new RadioButton("Army");
        rbArmy.setUserData("Army");
        rbArmy.setToggleGroup(tgBallotBoxType);
        rbArmy.setFont(buttonsFont);

        rbArmyCorona = new RadioButton("ArmyCorona");
        rbArmyCorona.setUserData("ArmyCorona");
        rbArmyCorona.setToggleGroup(tgBallotBoxType);
        rbArmyCorona.setFont(buttonsFont);

        typeButtons.getChildren().addAll(rbRegular, rbCorona, rbArmy, rbArmyCorona);
        typeButtons.setSpacing(ViewGUI.RADIO_BUTTON_SPACING);

        VBox.setMargin(txtType, new Insets(0, 0, 10, 0));

        vbType.getChildren().addAll(txtType, typeButtons);
    }

    public String getAddress() {
        return tfAddress.getText();
    }

    public String getType() {
        return tgBallotBoxType.getSelectedToggle().getUserData().toString();
    }

    @Override
    public VBox update() {
        super.update();
        mainView.getChildren().addAll(hbAddress, vbType, hbSubmit);
        mainView.setMargin(hbAddress, ViewGUI.DEFAULT_INSETS);
        mainView.setMargin(vbType, ViewGUI.DEFAULT_INSETS);
        mainView.setMargin(hbSubmit, ViewGUI.DEFAULT_INSETS);
        return mainView;
    }
}
