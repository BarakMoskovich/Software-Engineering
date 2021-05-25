import Controller.StoreController;
import Model.command.Store;
import javafx.application.Application;
import javafx.stage.Stage;
import View.HomeScreen;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Store store = new Store();
        HomeScreen view = new HomeScreen(stage);
        StoreController controller = new StoreController(view, store);
    }
}