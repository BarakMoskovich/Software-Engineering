package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static Model.command.Store.ASC;

public class HomeScreen extends MainButtons {

    public static final String CSS_FILES_PATH = "/Assets/";
    public static final String CSS_FILENAME = "stylesheet.css";
    public static final String CSS = HomeScreen.class.getResource(CSS_FILES_PATH + CSS_FILENAME).toExternalForm();

    private static final String VIEW_TITLE = "Store";
    private static final String ASC_TXT = "ASC";
    private static final String DESC_TXT = "DESC";
    private static final String INCOME_TXT = "Income";
    private static final String DIALOG_BUTTON_TXT = "Choose";
    private static final String DIALOG_VBOX_TXT = "Order Product By";

    public static final int WIDTH = 550;
    public static final int HEIGHT = 400;
    public static boolean ACTION_TAKEN = false;
    private Scene scene;
    private final BorderPane borderPane;
    private Stage dialog;
    private final Stage stage;
    private VBox dialogVBox;
    private ChoiceBox<String> dialogCB;
    private Button dialogButton;
//    public static final String css = HomeScreen.class.getResource("/Assets/stylesheet.css").toExternalForm();

    public HomeScreen(Stage stage) {
        super();
        this.stage = stage;

        stage.setTitle(VIEW_TITLE);
        borderPane = new BorderPane();
        borderPane.setCenter(loadMainButtons());

        sortDialog();
    }

    public void loadMainProgram() {
        scene = new Scene(borderPane, WIDTH, HEIGHT);
        scene.getStylesheets().add(CSS);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void sortDialog() {
        dialog = new Stage();

        dialogCB = new ChoiceBox<>();
        dialogCB.getItems().addAll(ASC_TXT, DESC_TXT, INCOME_TXT);
        dialogCB.getSelectionModel().select(ASC);

        dialogButton = new Button(DIALOG_BUTTON_TXT);
        dialogButton.getStyleClass().add("button-order");

        dialogVBox = new VBox(new Text(DIALOG_VBOX_TXT), dialogCB, dialogButton);
        VBox.setMargin(dialogCB, new Insets(10));
        VBox.setMargin(dialogButton, new Insets(5));
        dialogVBox.setAlignment(Pos.CENTER);
        dialogVBox.setPadding(new Insets(30));

        Scene dialogScene = new Scene(dialogVBox);
        dialogScene.getStylesheets().add(CSS);

        dialog.setScene(dialogScene);
        dialog.setResizable(false);
        dialog.show();
    }

    public int getOrderChoice() {
        return dialogCB.getSelectionModel().getSelectedIndex();
    }

    public void closeDialog() {
        dialog.close();
    }

    public void loadMain() {
        borderPane.getChildren().clear();
        borderPane.setCenter(loadMainButtons());
        if (ACTION_TAKEN) {
            assignDisableStyleToButton(getCancelButton());
            ACTION_TAKEN = false;
        }
    }

    public void update(BackButtonView newMainView) {
        borderPane.getChildren().clear();
        borderPane.setCenter(newMainView.update());
    }

    public Scene getScene() {
        return scene;
    }

    public Stage getStage() {
        return stage;
    }

    public void addEventToOrder(EventHandler<ActionEvent> event) {
        dialogButton.setOnAction(event);
    }

}
