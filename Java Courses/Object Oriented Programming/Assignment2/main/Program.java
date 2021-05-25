package main;

import controller.ControllerGUI;
import exceptions.StringLengthException;
import exceptions.UnderAgeException;
import javafx.application.Application;
import javafx.stage.Stage;
import model.ModelGUI;
import view.ViewGUI;


public class Program extends Application {
    // add for user to choose what he'd like to use
    // 1. GUI
    // 2. CLI

    public static void main(String[] args) throws StringLengthException, UnderAgeException {
        /*Scanner s = new Scanner(System.in);
        ControllerCLI controller = new ControllerCLI();
        TestUtils.hardCodeToTest(controller); // Hard Code
        ViewCLI view = new ViewCLI(controller, s);
        view.showMenu();*/

        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        ModelGUI model = new ModelGUI();
        ViewGUI view = new ViewGUI(stage);
        ControllerGUI controller = new ControllerGUI(model, view);
    }
}