package Controller;

import Exceptions.AlertUserException;
import Model.Product;
import Model.command.*;
import View.HomeScreen;
import View.ProductList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;


public class ProductListController extends BackButtonController {

    private static final String PRODUCT_REMOVED = "Product Removed!";
    private static final String PRODUCT_REMOVE_ERROR = "An error occurred while removing product";
    private static final String HOVER_REMOVE_TXT = "Remove";
    private static final String QUICK_STAGE_TITLE = "Quick Show";
    private static final String PRODUCTS_REMOVED_SUCCESSFULLY = "All Product Removed!";
    private static final String PRODUCTS_REMOVED_ERROR = "An error occurred while removing products";

    private final ProductList productList;
    private Stage quickStage;
    private Scene quickShowScene;
    private VBox quickVBox;
    private double profit;
    private Alert alert;

    public ProductListController(HomeScreen homeScreenView, StoreCommand store, ProductList productList) {
        super(homeScreenView, store, productList);
        this.productList = productList;

        setProductInListView();
        addRemoveButtonToTable();
        eventSearchButton();
        eventProductShow();
        eventForBackToMainButton();
        eventRemoveAllButton();
    }

    private void setProductInListView() {
        productList.getTableView().getItems().clear();
        profit = 0;
        GetAllProductsCommand allProductsCommand = new GetAllProductsCommand(store);
        allProductsCommand.execute();

        for (Product p : allProductsCommand.get()) {
            productList.getTableView().getItems().add(p);
            profit += p.getRetailPrice() - p.getWholesalePrice();
        }
        setProfit();
    }

    private void setProfit() {
        productList.setProfit(profit);
    }

    private void addRemoveButtonToTable() {
        TableColumn<Product, Void> remCol = new TableColumn();
        remCol.setGraphic(productList.getRemoveAllButton());

        Callback<TableColumn<Product, Void>, TableCell<Product, Void>> cellFactory = new Callback<TableColumn<Product, Void>, TableCell<Product, Void>>() {
            @Override
            public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
                return new TableCell<Product, Void>() {
                    private final Button revBtn = new Button("");
                    {
                        revBtn.setOnAction((ActionEvent event) -> {
                            closeQuick();
                            RemoveProductCommand removeProductCommand = new RemoveProductCommand(store, getTableView().getItems().get(getIndex()).getSku());
                            removeProductCommand.execute();
                            if (removeProductCommand.isRemoved()) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setContentText(PRODUCT_REMOVED);
                                alert.showAndWait();

                                setProductInListView();
                                HomeScreen.ACTION_TAKEN = true;
                            } else new AlertUserException(PRODUCT_REMOVE_ERROR).showErrorMessage();
                        });
                        revBtn.getStyleClass().add("button-remove");
                        revBtn.setTooltip(new Tooltip(HOVER_REMOVE_TXT));
                        setStyle("-fx-alignment: center;");
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty)
                            setGraphic(null);
                        else
                            setGraphic(revBtn);
                    }
                };
            }
        };
        remCol.setCellFactory(cellFactory);
        productList.getTableView().getColumns().add(remCol);
    }

    private void eventSearchButton() {
        EventHandler<ActionEvent> eventForSearchButton = event -> {
            try {
                closeQuick();

                String searchValue = productList.getSearchValue();
                if (searchValue.isEmpty()) {
                    setProductInListView();
                    return;
                }

                productList.getTableView().getItems().clear();

                GetProductCommand getProductCommand = new GetProductCommand(store, searchValue);
                getProductCommand.execute();

                Product res = getProductCommand.get();
                if (res == null)
                    throw new AlertUserException("Product with " + searchValue + " not found!");

                productList.getTableView().getItems().add(res);
            } catch (AlertUserException i) {
                setProductInListView();
                i.showErrorMessage();
            }
        };
        productList.addEventSearchButton(eventForSearchButton);
    }

    private void eventProductShow() {
        productList.getTableView().setRowFactory(tr -> {
            TableRow<Product> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                closeQuick();

                if (event.getClickCount() == 2 && (!row.isEmpty()) ) {
                    Product rowData = row.getItem();
                    quickVBox = productList.productShowDialog(rowData);

                    quickStage = new Stage();
                    quickStage.setTitle(QUICK_STAGE_TITLE);
                    quickShowScene = new Scene(quickVBox);
                    quickShowScene.getStylesheets().add(HomeScreen.CSS);

                    quickStage.setScene(quickShowScene);
                    quickStage.setResizable(false);
                    quickStage.show();

                    eventQuickCloseButton();
                }
            });
            return row ;
        });
    }

    private void eventQuickCloseButton() {
        EventHandler<ActionEvent> eventForQuickCloseButton = event -> closeQuick();
        productList.addEventToQuickCloseButton(eventForQuickCloseButton);
    }

    private void eventRemoveAllButton() {
        EventHandler<ActionEvent> eventForRemoveAllButton = event -> {
            RemoveAllProductCommand removeAllProductCommand = new RemoveAllProductCommand(store);
            removeAllProductCommand.execute();
            if (removeAllProductCommand.isAllRemoved()) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(PRODUCTS_REMOVED_SUCCESSFULLY);
                alert.showAndWait();

                setProductInListView();
                HomeScreen.ACTION_TAKEN = true;
            } else new AlertUserException(PRODUCTS_REMOVED_ERROR).showErrorMessage();
        };
        productList.addEventToRemoveAllButton(eventForRemoveAllButton);
    }

    private void closeQuick() {
        if (quickStage != null)
            quickStage.close();
    }

    private void eventForBackToMainButton() {
        EventHandler<ActionEvent> eventForBackToMainButton = (e -> {
            closeQuick();
            view.loadMain();
        });
        productList.eventBackToMainButton(eventForBackToMainButton);
    }
}