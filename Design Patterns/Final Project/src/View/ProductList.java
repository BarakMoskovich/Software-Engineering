package View;

import Model.Product;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ProductList extends BackButtonView {
    private static final String CLOSE_BUTTON = "Close";
    private static final String REMOVE_ALL_BUTTON = "Remove All Products";
    private static final String SEARCH_TXT_FIELD = "Enter SKU to search";
    private static final String PROFIT_LABEL_TEXT = "Total Profit: ";
    private static final String PROFIT_TEXT = "0";

    private enum TableColumns {
        PRODUCT("Product"),
        CUSTOMER("Customer"),
        SKU("SKU"),
        NAME("Name"),
        RETAIL("Retail"),
        WHOLESALE("Wholesale"),
        PHONE("Phone"),
        SALE("Sale?");

        private final String text;

        TableColumns(final String text) {
            this.text = text;
        }

        public String toString() {
            return text;
        }
    }

    private TableView tableView;
    private HBox listViewHBox, searchBarHBox, profitHBox;
    private VBox quickShowVBox;
    private TextField searchTF;
    private Button searchBtn, quickCloseButton, removeAllButton;
    private Text profit, profitLabel, quickSortText;
    private final Insets SEARCHBAR_INSETS = new Insets(0, 10, 0, 0);
    private final Insets TABLE_INSETS = new Insets(10, 0, 5, 0);
    private final Insets PROFIT_INSETS = new Insets(0, 0, 0, 15);

    public ProductList() {
        assignAll();
    }

    public void assignAll() {
        assignProductList();
        assignSearchField();
        assignSearchButton();
        assignProfit();
        assignRemoveAllButton();
    }

    private void assignRemoveAllButton() {
        removeAllButton = new Button("");
        removeAllButton.getStyleClass().add("button-remove");
        removeAllButton.setTooltip(new Tooltip(REMOVE_ALL_BUTTON));
    }

    private void assignSearchField() {
        searchTF = new TextField();
        searchTF.setPromptText(SEARCH_TXT_FIELD);
    }

    private void assignSearchButton() {
        searchBtn = new Button();
        searchBtn.getStyleClass().add("button-search");
    }

    private void assignProfit() {
        profitLabel = new Text(PROFIT_LABEL_TEXT);
        profit = new Text(PROFIT_TEXT);
        profitHBox = new HBox();
        profitHBox.getChildren().addAll(profitLabel, profit);
        HBox.setMargin(profitLabel, PROFIT_INSETS);
    }

    private void centerColumn(TableColumn<Product, String> tc) {
        tc.setStyle("-fx-alignment: center;");
    }

    private void assignProductList() {
        listViewHBox = new HBox();
        tableView = new TableView<Product>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Set Columns
        TableColumn<Product, String> product            = new TableColumn<>(TableColumns.PRODUCT.toString());
        TableColumn<Product, String> customer           = new TableColumn<>(TableColumns.CUSTOMER.toString());
        TableColumn<Product, String> prodSku            = new TableColumn<>(TableColumns.SKU.toString());
        TableColumn<Product, String> prodName           = new TableColumn<>(TableColumns.NAME.toString());
        TableColumn<Product, String> prodRetailPrice    = new TableColumn<>(TableColumns.RETAIL.toString());
        TableColumn<Product, String> prodWholesalePrice = new TableColumn<>(TableColumns.WHOLESALE.toString());
        TableColumn<Product, String> cusName            = new TableColumn<>(TableColumns.NAME.toString());
        TableColumn<Product, String> cusPhone           = new TableColumn<>(TableColumns.PHONE.toString());
        TableColumn<Product, String> cusPromotions      = new TableColumn<>(TableColumns.SALE.toString());

        product .getColumns().addAll(prodSku, prodName, prodRetailPrice, prodWholesalePrice);
        customer.getColumns().addAll(cusName, cusPhone, cusPromotions);
        tableView.getColumns().addAll(product, customer);

        // Style Columns
        centerColumn(product);
        centerColumn(customer);
        centerColumn(prodSku);
        centerColumn(prodName);
        centerColumn(prodRetailPrice);
        centerColumn(prodWholesalePrice);
        centerColumn(cusName);
        centerColumn(cusPhone);
        centerColumn(cusPromotions);

        // Values
        prodSku             .setCellValueFactory(new PropertyValueFactory<>("sku"));
        prodName            .setCellValueFactory(new PropertyValueFactory<>("productName"));
        prodRetailPrice     .setCellValueFactory(new PropertyValueFactory<>("retailPrice"));
        prodWholesalePrice  .setCellValueFactory(new PropertyValueFactory<>("wholesalePrice"));
        cusName.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCustomer().getName()));
        cusPhone.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCustomer().getPhoneNumber()));
        cusPromotions.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCustomer().getEventOnSales() ? "Yes" : "No"));

        listViewHBox.setAlignment(Pos.CENTER);
        listViewHBox.getChildren().add(tableView);
        HBox.setMargin(tableView, TABLE_INSETS);
    }

    public void setProfit(double newProfit) {
        profit.setText(Double.toString(newProfit));
    }
    public TableView getTableView() {
        return tableView;
    }

    public Button getSearchBtn() {
        return searchBtn;
    }

    public String getSearchValue() {
        return searchTF.getText();
    }

    public VBox productShowDialog(Product p) {
        quickShowVBox = new VBox();
        quickSortText = new Text();
        quickCloseButton = new Button(CLOSE_BUTTON);

        quickSortText.setText(p.toString());
        quickShowVBox.getChildren().addAll(quickSortText, quickCloseButton);
        quickShowVBox.getStyleClass().add("quick-show");
        quickCloseButton.getStyleClass().add("button-quick-close");
        quickShowVBox.setAlignment(Pos.CENTER);

        return quickShowVBox;
    }

    public Button getRemoveAllButton() {
        return removeAllButton;
    }

    public void addEventToRemoveAllButton(EventHandler<ActionEvent> event) {
        removeAllButton.setOnAction(event);
    }

    public void addEventSearchButton(EventHandler<ActionEvent> event) {
        searchBtn.setOnAction(event);
    }

    public void addEventToQuickCloseButton(EventHandler<ActionEvent> event) {
        quickCloseButton.setOnAction(event);
    }

    @Override
    public VBox update() {
        mainView.getChildren().clear();

        searchBarHBox = new HBox();

        searchBarHBox.getChildren().addAll(backToMainVBox, searchTF, searchBtn);
        HBox.setMargin(backToMainVBox, SEARCHBAR_INSETS);
        HBox.setMargin(searchTF, SEARCHBAR_INSETS);
        searchBarHBox.setAlignment(Pos.CENTER);

        mainView.getChildren().addAll(searchBarHBox, listViewHBox, profitHBox);
        mainView.setAlignment(Pos.CENTER);
        return mainView;
    }
}
