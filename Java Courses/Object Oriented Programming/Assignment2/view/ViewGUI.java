package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.ModelGUI;
import model.Party;
import model.Set;
import model.citizens.Citizen;
import view.MainPaneView.MainPane;
import view.MainPaneView.WelcomeMenu;
import view.showMenu.Show;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ViewGUI {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 500;
    public static final double TEXT_INPUT_WIDTH_VALUE = 300.d;
    public static final double MIN_BUTTON_WIDTH_VALUE = 180.d;
    public static final double MIN_BUTTON_HEIGHT_VALUE = 30.d;
    public static final double RADIO_BUTTON_SPACING = 10.d;
    public static final double SPACING = 5.d;
    public static final Insets DEFAULT_INSETS = new Insets(10, 0, 0, 20);
//    private TextField tfYear;
    private VBox mainView;
    private MenuButtons menuButtons;
    private BorderPane borderPane;
    private MenuBar menuBar;
    private Menu helpMenu, fileMenu;
    private MenuItem help, about, exit, importD;
    private WelcomeMenu welcomeMenu;

    public ViewGUI(Stage stage) {
        stage.setTitle("Election Interface");
        borderPane = new BorderPane();

        // Menu bar
        menuBar = new MenuBar();
        // Help
        helpMenu = new Menu("Help");
        help = new MenuItem("View Help");
        about = new MenuItem("About");
        exit = new MenuItem("Exit");
        helpMenu.getItems().addAll(help, about, new SeparatorMenuItem(), exit);
        // File
        fileMenu = new Menu("File");
        importD = new MenuItem("Import Fixed Data");
        fileMenu.getItems().addAll(importD);
        menuBar.getMenus().addAll(fileMenu, helpMenu);

        borderPane.setTop(menuBar);

        // Interface Menu
        menuButtons = new MenuButtons(borderPane);

        // Main View
        welcomeMenu = new WelcomeMenu();
        mainView = welcomeMenu.update();

        borderPane.setCenter(mainView);

        // Scene
        Scene scene = new Scene(borderPane, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void clear() {
        mainView.getChildren().clear();
    }

    public void update(MainPane newMainView) {
        mainView.getChildren().clear();
        mainView.getChildren().add(newMainView.update());
    }

    public void update(Show newMainView) {
        mainView.getChildren().clear();
        mainView.getChildren().add(newMainView.update());
    }

    public String getMonthText() {
        return welcomeMenu.getMonthText();
    }

    public String getYearText() {
        return welcomeMenu.getYearText();
    }

    private String takeVote(String citizenName, List<String> partiesList) {
        ChoiceDialog<String> dialog = new ChoiceDialog<>(partiesList.get(0), partiesList);
        dialog.setTitle("Choose Party");
        dialog.setHeaderText("Hey " + citizenName + "!");
        dialog.setContentText("Please Choose your party:");
        Optional<String> result = dialog.showAndWait();
        try {
            if (result.isPresent())
                return result.get();
        } catch (NoSuchElementException e) {
            return null;
        }

        return null;
    }

    private boolean askToVote(String citizenName) {
        Alert alert = new Alert(Alert.AlertType.NONE, citizenName + ", You want to vote ?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Voting");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES)
            return true;
        return false;
    }

    private boolean CoronaQuiz() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Are you wearing a protective suit ?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Voting");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES)
            return true;
        return false;
    }

    public void beginElectionButton(ModelGUI theModel) {
//        theModel.clearVotes();
        Set<Citizen> citizens = theModel.getVoterRegister().getCitizens();
        Party partyVote;
        Alert alert;

        for (Citizen c : citizens.getSet()) {
            if (c != null) {
                if (!c.getVoted()) {
                    if (!askToVote(c.getName())) {
                        alert = new Alert(Alert.AlertType.NONE, "Don't worry, See you in three months", ButtonType.OK);
                        alert.showAndWait();
                    } else {
                        partyVote = theModel.getElection().getPartiesByName(takeVote(c.getName(),
                                theModel.getElection().getPartiesList()));
                        if (partyVote != null) {
                            if (c.getBallotBox().getType().equals("Corona")
                                    || c.getBallotBox().getType().equals("SoldierCorona")) {
                                if (CoronaQuiz())
                                    c.getBallotBox().vote(c, partyVote);
                                else {
                                    alert = new Alert(Alert.AlertType.WARNING, "You have been violated Ministers of health rules against Corona pandemic !!! You have to pay a 15,000 Shekels fee !!!", ButtonType.OK);
                                    alert.showAndWait();
                                }
                            } else
                                c.getBallotBox().vote(c, partyVote);
                            alert = new Alert(Alert.AlertType.NONE, "Thanks for voting!", ButtonType.OK);
                            alert.showAndWait();
                        } else {
                            alert = new Alert(Alert.AlertType.ERROR, "Ooops... Something went wrong with your vote...", ButtonType.OK);
                            alert.showAndWait();
                        }
                    }
                }
            }
        }
    }

    public MenuButtons getMenuButtons() {
        return menuButtons;
    }

    public WelcomeMenu getWelcomeMenu() {
         return welcomeMenu;
    }

    public void addEventCloseButton(EventHandler<ActionEvent> event) {
        exit.setOnAction(event);
    }

    public void addEventImportButton(EventHandler<ActionEvent> event) {
        importD.setOnAction(event);
    }

    public void addEventHelpButton(EventHandler<ActionEvent> event) {
        help.setOnAction(event);
    }

    public void addEventAboutButton(EventHandler<ActionEvent> event) {
        about.setOnAction(event);
    }
}