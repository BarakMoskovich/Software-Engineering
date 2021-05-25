package view.MainPaneView;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Party;
import model.VoterRegister;
import model.citizens.Citizen;
import view.ViewGUI;

import java.util.HashMap;

public class AssignCandidate extends MainPane {
    private HBox hbParty, hbCitizen, hbPlace;
    private TextField tfPlace;
    private ComboBox parties, citizens;
    private VoterRegister vr;
    private HashMap<Party, Integer> allParties;

    public AssignCandidate(VoterRegister vr, HashMap<Party, Integer> allParties) {
        super();
        this.vr = vr;
        this.allParties = allParties;
        setHeadline("Add Candidate to Party");
        setFields();
    }

    private void setFields() {
        setCitizenField();
        setPartyField();
        setPlaceField();
    }

    private void setPlaceField() {
        hbPlace = new HBox();

        tfPlace = new TextField();
        tfPlace.setPromptText("Place");
        tfPlace.setFont(buttonsFont);
        tfPlace.setMaxWidth(150);

        hbPlace.getChildren().addAll(tfPlace);
        hbPlace.setSpacing(15);
    }

    private void setCitizenField() {
        hbCitizen = new HBox();
        citizens = new ComboBox();
        citizens.setMinWidth(ViewGUI.MIN_BUTTON_WIDTH_VALUE);
        citizens.setPromptText("Choose Citizen");
        citizens.setStyle("-fx-font: 14px \"Tahoma\";");
        citizens.getEditor().setFont(buttonsFont);

        Citizen citizen;
        for(int i = 0; i < vr.getCitizens().size(); i++) {
            citizen = vr.getCitizens().get(i);
            if (citizen.getParty() == null)
                citizens.getItems().add(citizen.getID() + " - " + citizen.getName());
        }

        citizens.getEditor().setFont(buttonsFont);
        hbCitizen.getChildren().addAll(citizens);
    }

    private void setPartyField() {
        hbParty = new HBox();
//        Text txtBallotBoxNumber = new Text("Choose Party :");
        parties = new ComboBox();
        parties.setMinWidth(ViewGUI.MIN_BUTTON_WIDTH_VALUE);
        parties.setPromptText("Choose Party");
        parties.setStyle("-fx-font: 14px \"Tahoma\";");
        parties.getEditor().setFont(buttonsFont);

        for(Party party: allParties.keySet()) {
            parties.getItems().add(party.getName() + " - " + party.getSection());
        }

        parties.getEditor().setFont(buttonsFont);
        hbParty.getChildren().addAll(parties);
    }

    public String getCitizenID() {
        return citizens.getSelectionModel().getSelectedItem().toString();
    }

    public String getPartyName() {
        return parties.getSelectionModel().getSelectedItem().toString();
    }

    public String getPlace() {
        return tfPlace.getText();
    }

    @Override
    public VBox update() {
        super.update();
        mainView.getChildren().addAll(hbCitizen, hbParty, hbPlace, hbSubmit);
        mainView.setMargin(hbCitizen, ViewGUI.DEFAULT_INSETS);
        mainView.setMargin(hbParty, ViewGUI.DEFAULT_INSETS);
        mainView.setMargin(hbPlace, ViewGUI.DEFAULT_INSETS);
        mainView.setMargin(hbSubmit, ViewGUI.DEFAULT_INSETS);

        return mainView;
    }
}
