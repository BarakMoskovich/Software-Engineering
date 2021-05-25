package view.showMenu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.BallotBox;
import model.Party;
import view.ViewGUI;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowParties extends Show {
    private HashMap<Party, Integer> allParties;

    public ShowParties(HashMap<Party, Integer> parties) {
        super();
        allParties = parties;
        setHeadline("Show All Parties");
    }

    private void assignParties() {
        ObservableList<Party> data = FXCollections.observableArrayList(new ArrayList<Party>(allParties.keySet()));
        TableView<Party> table = new TableView<>();

        TableColumn name = setTableColumn("Name", "name", 200, "Party");
        TableColumn section = setTableColumn("Section", "section", 130, "Party");
        TableColumn year = setTableColumn("Year", "year", 70, "Party");
        TableColumn month = setTableColumn("Month", "month", 70, "Party");
        TableColumn day = setTableColumn("Day", "day", 70, "Party");

        table.setMinWidth(540);
        table.setMaxWidth(540);

        table.setFixedCellSize(20);
        table.setMaxHeight(40 + (allParties.size() * 20));

        table.setItems(data);
        table.getColumns().addAll(name, section, year, month, day);

        scrollPaneVB.getChildren().add(table);
        scrollPaneVB.setMargin(table, ViewGUI.DEFAULT_INSETS);
    }

    @Override
    public VBox update() {
        super.update();
        assignParties();
        assignScrollPane();

        return mainView;
    }
}
