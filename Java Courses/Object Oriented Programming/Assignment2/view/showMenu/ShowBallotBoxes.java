package view.showMenu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.BallotBox;
import model.citizens.Citizen;
import model.citizens.Corona;
import model.citizens.Soldier;
import model.citizens.SoldierCorona;
import view.ViewGUI;

import java.util.List;

public class ShowBallotBoxes extends Show {
    private List<BallotBox<Citizen>> regular;
    private List<BallotBox<Soldier>> soldier;
    private List<BallotBox<SoldierCorona>> soldierCorona;
    private List<BallotBox<Corona>> corona;
    private int height;

    public ShowBallotBoxes(List<BallotBox<Citizen>> citizen, List<BallotBox<Soldier>> soldier, List<BallotBox<SoldierCorona>> soldierCorona,
                           List<BallotBox<Corona>> corona) {
        super();
        regular = citizen;
        this.soldier = soldier;
        this.soldierCorona = soldierCorona;
        this.corona = corona;
        setHeadline("Show All Ballot Boxes");
        height = 400;
    }

    private void setSubHeadLine(String s) {
        Text text = new Text(s);
        text.setFont(headLineFont);
        scrollPaneVB.getChildren().add(text);
        scrollPaneVB.setMargin(text, ViewGUI.DEFAULT_INSETS);
    }

    private <C extends Citizen> void addToMainView(String type, List<BallotBox<C>> list) {
        setSubHeadLine(type);
        ObservableList<BallotBox<C>> data = FXCollections.observableArrayList(list);
        TableView<BallotBox<C>> table = new TableView<>();

        TableColumn num = setTableColumn("#", "id", 100, "BallotBox");
        TableColumn address = setTableColumn("Address", "address", 438, "BallotBox");

        table.setMaxWidth(540);

        table.setFixedCellSize(20);
        table.setMaxHeight(40 + (list.size() * 20));
        height += list.size() * 20;
        table.setItems(data);
        table.getColumns().addAll(num, address);

        scrollPaneVB.setMinHeight(height);
        scrollPaneVB.getChildren().add(table);
        scrollPaneVB.setMargin(table, ViewGUI.DEFAULT_INSETS);
    }

    private <C extends Citizen> void assignBallotBox(String type) {
        Class<Class<C>> ballotType;
        ObservableList<C> data;

        BallotBox<C> cBallotBox;
        FXCollections fx;

        switch (type){
            case "Corona":
//                setSubHeadLine(type);
                addToMainView(type, corona);
                break;
            case "Soldier":
//                setSubHeadLine("Army");
                addToMainView(type, soldier);
                break;
            case "SoldierCorona":
//                setSubHeadLine("ArmyCorona");
                addToMainView(type, soldierCorona);
                break;
            case "Regular":
            default:
//                setSubHeadLine("Regular");
                addToMainView(type, regular);
                break;
        }
    }

    private void assignAllTypes() {
        assignBallotBox("Regular");
        assignBallotBox("Soldier");
        assignBallotBox("SoldierCorona");
        assignBallotBox("Corona");
    }

    @Override
    public VBox update() {
        super.update();
        assignAllTypes();
        assignScrollPane();

        return mainView;
    }
}
