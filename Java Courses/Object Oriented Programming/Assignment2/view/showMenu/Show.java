package view.showMenu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.BallotBox;
import model.Party;
import model.citizens.Citizen;
import view.ViewGUI;

public class Show {
    protected Text headline;
    protected VBox mainView;
    protected Font headLineFont = Font.font("Tahoma", FontWeight.BOLD, FontPosture.REGULAR, 16);
    protected ScrollPane scrollPane;
    protected VBox scrollPaneVB;

    public Show() {
        mainView = new VBox(ViewGUI.SPACING);
        mainView.setAlignment(Pos.TOP_LEFT);
        headline = new Text();
        headline.setFont(headLineFont);
        scrollPaneVB = new VBox(ViewGUI.SPACING);
        scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
    }

    public void setHeadline(String s) {
        headline.setText(s);
    }

    protected TableColumn setTableColumn(String text, String var, int size, String type) {
        TableColumn column = new TableColumn(text);
        column.setMinWidth(size);
        column.setMaxWidth(size);
        switch (type) {
            case "Party":
                column.setCellValueFactory(new PropertyValueFactory<Party, String>(var));
                break;
            case "Citizen":
                column.setCellValueFactory(new PropertyValueFactory<Citizen, String>(var));
                break;
            case "BallotBox":
            default:
                column.setCellValueFactory(new PropertyValueFactory<BallotBox, String>(var));
        }
        return column;
    }

    protected void assignScrollPane() {
        scrollPane.setContent(scrollPaneVB);
        scrollPane.setStyle("-fx-background-color:transparent; -fx-min-height: 470px;");
        mainView.getChildren().add(scrollPane);
    }

    public VBox update() {
        mainView.getChildren().clear();
        mainView.getChildren().add(headline);
        mainView.setMargin(headline, ViewGUI.DEFAULT_INSETS);

        return mainView;
    }
}
