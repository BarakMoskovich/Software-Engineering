package view.showMenu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.BallotBox;
import model.Party;
import model.VoterRegister;
import model.citizens.Citizen;
import model.citizens.SoldierCorona;
import view.ViewGUI;

public class ShowCitizens extends Show {
    private VoterRegister vr;

    public ShowCitizens(VoterRegister voterRegister) {
        super();
        vr = voterRegister;
        setHeadline("Show All Citizens");
    }

    private void assignCitizens() {
        ObservableList<Citizen> data = FXCollections.observableArrayList(vr.getCitizens().getSet());
        TableView<Citizen> table = new TableView<>();

        TableColumn citizenID = setTableColumn("ID", "ID", 79, "Citizen");
        TableColumn name = setTableColumn("Name", "name", 120, "Citizen");
        TableColumn birthYear = setTableColumn("Birth Year", "birthYear", 80, "Citizen");
        TableColumn ballotBox = setTableColumn("BallotBox", "ballotBox", 130, "Citizen");
        TableColumn party = setTableColumn("Party", "party", 130, "Citizen");

        table.setMinWidth(540);
        table.setMaxWidth(540);

        table.setFixedCellSize(20);
        table.setMaxHeight(40 + (vr.getCitizens().getSet().size() * 20));

        table.setItems(data);
        table.getColumns().addAll(citizenID, name, birthYear, ballotBox, party);

        scrollPaneVB.getChildren().add(table);
        scrollPaneVB.setMargin(table, ViewGUI.DEFAULT_INSETS);
    }

    @Override
    public VBox update() {
        super.update();
        assignCitizens();
        assignScrollPane();
        return mainView;
    }
}
