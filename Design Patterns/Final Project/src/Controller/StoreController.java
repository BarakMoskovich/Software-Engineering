package Controller;

import Model.command.SetOrderByCommand;
import Model.command.Store;
import Model.command.StoreCommand;
import Model.command.UndoCommand;
import View.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;

public class StoreController {
    private static final String UNDO_SUCCESS = "Undo operation performed successfully!";

    protected StoreCommand store;
    protected HomeScreen view;
    protected static Runnable checkEnableCancelButton;
    private boolean addedProduct;
    private Alert alert;

    public StoreController(HomeScreen view, StoreCommand store) {
        this.view = view;
        this.store = store;

        assignAllEvents();

        // Check if done some Order
        checkEnableCancelButton = this::enableCancelButton;
    }

    private void assignAllEvents() {
        eventOrderButton();
        eventProductButton();
        eventCancelButton();
        eventSaleButton();
        eventProductListButton();
    }

    private void eventOrderButton() {
        EventHandler<ActionEvent> eventForOrderButton = event -> {
            new SetOrderByCommand(store, view.getOrderChoice()).execute();
            view.closeDialog();
            view.loadMainProgram();
        };
        view.addEventToOrder(eventForOrderButton);
    }

    private void eventProductButton() {
        EventHandler<ActionEvent> eventForOrderButton = event -> {
            AddProduct addProductView = new AddProduct();
            view.update(addProductView);
            ProductController productController = new ProductController(view, store, addProductView);
        };
        view.addEventProductButton(eventForOrderButton);
    }

    private void eventCancelButton() {
        EventHandler<ActionEvent> eventForCancelButton = event -> {
            new UndoCommand(store).execute();
            disableCancelButton();

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(UNDO_SUCCESS);
            alert.showAndWait();
        };

        view.addEventCancelButton(eventForCancelButton);
    }

    private void eventSaleButton() {
        EventHandler<ActionEvent> eventForSaleButton = event -> {
            Sale saleView = new Sale();
            view.update(saleView);
            SaleController orderController = new SaleController(view, store, saleView);
        };
        view.addEventSaleButton(eventForSaleButton);
    }

    private void eventProductListButton() {
        EventHandler<ActionEvent> eventForProductListButton = event -> {
            ProductList productList = new ProductList();
            view.update(productList);
            ProductListController productListController = new ProductListController(view, store, productList);
        };
        view.addEventProductListButton(eventForProductListButton);
    }

    private void enableCancelButton() {
        if (!addedProduct) {
            view.getCancelButton().setDisable(false);
            MainButtons.assignStyleToButton(view.getCancelButton());
            addedProduct = true;
        }
    }

    private void disableCancelButton() {
        MainButtons.assignDisableStyleToButton(view.getCancelButton());
        addedProduct = false;
    }
}

