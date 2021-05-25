package Controller;

import Exceptions.AlertUserException;
import Model.command.GetAllConfirmedCustomerNamesCommand;
import Model.command.SendSaleMessageCommand;
import Model.command.StoreCommand;
import View.HomeScreen;
import View.Sale;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.ArrayList;

public class SaleController extends BackButtonController {
    private static final String FILL_MSG_BOX = "Please fill in the message box";
    private static final String SEND_RESPONSE = "Sending Messages:";
    private static final String ACCEPT_RESPONSE = " Accepted";

    private final Sale saleView;
    private Thread loadingThread;
    private boolean shuttingDown;
    private int status = 0, nameCounter = 0;

    public SaleController(HomeScreen homeScreenView, StoreCommand store, Sale saleView) {
        super(homeScreenView, store, saleView);
        this.saleView = saleView;
        eventSendButton();
    }

    private void eventSendButton() {
        EventHandler<ActionEvent> eventForSendButton = event -> {

            try {
                String msg = saleView.getMsg();
                if (msg.isEmpty())
                    throw new AlertUserException(FILL_MSG_BOX);

                new SendSaleMessageCommand(store, saleView.getMsg()).execute();

                GetAllConfirmedCustomerNamesCommand confirmedCommand = new GetAllConfirmedCustomerNamesCommand(store);
                confirmedCommand.execute();
                ArrayList<String> names = confirmedCommand.get();

                saleView.addResponse(SEND_RESPONSE, -1);

                if (names.size() > 0)
                    loadingBar();

                new Thread(() -> {
                    for (String name : names) {
                        Platform.runLater(() -> {
                            saleView.addResponse(name + ACCEPT_RESPONSE, status);

                            nameCounter++;
                            if (nameCounter >= names.size()) {
                                shuttingDown = true;
                                loadingThread.interrupt();
                                saleView.loadingBar(-1);
                                Thread.currentThread().interrupt();
                            }
                        });

                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }).start();
            } catch (AlertUserException i) {
                i.showErrorMessage();
            }
        };
        saleView.addEventForSendButton(eventForSendButton);
    }

    private void loadingBar() {
        loadingThread = new Thread(() -> {
            while (!shuttingDown && !Thread.interrupted()) {
                Platform.runLater(() -> {
                    if (status == 4)
                        status = 0;
                    status++;

                    saleView.loadingBar(status);
                });

                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        loadingThread.start();
    }
}

