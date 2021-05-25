package Controller;

import Model.command.StoreCommand;
import View.HomeScreen;
import View.BackButtonView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class BackButtonController extends StoreController {
    private final BackButtonView backButtonView;

    public BackButtonController(HomeScreen homeScreenView, StoreCommand store, BackButtonView backButtonView) {
        super(homeScreenView, store);
        this.backButtonView = backButtonView;

        eventForBackToMainButton();
    }

    private void eventForBackToMainButton() {
        EventHandler<ActionEvent> eventForBackToMainButton = e -> view.loadMain();
        backButtonView.eventBackToMainButton(eventForBackToMainButton);
    }
}
