package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class AddProduct extends BackButtonView {
    private static final String RETAIL_PRICE_TXT = "Enter Retail Price";
    private static final String ORDER_TITLE_TXT = "New Order";
    private static final String SKU_TXT = "Enter SKU";
    private static final String PRODUCT_TXT = "Enter Product Name";
    private static final String WHOLESALE_TXT = "Enter Wholesale Price";
    private static final String NAME_TXT = "Enter Customer Name";
    private static final String PHONE_TXT = "Enter Phone Number";
    private static final String ORDER_BUTTON_TXT = "Add!";
    private static final String SALE_TXT = "Notification on promotions?";
    private TextField nameTF, phoneNumberTF, skuTF, productNameTF, retailPriceTF, wholesalePriceTF;
    private HBox headerHBox, orderHBox, nameHBox, phoneNumberHBox, skuHBox, productNameHBox, retailPriceHBox, wholesalePriceHBox, saleHBox;
    private CheckBox saleCB;
    private Button orderButton;
    private Text orderTitle;
    private final Insets TEXT_FIELD_INSETS = new Insets(0, 0, 5, 0);
    private final Insets ORDER_INSETS = new Insets(0, 0, 0, 120);
    private final Insets BACK_INSETS = new Insets(0, 0, 0, -170);
    private final Insets SALE_INSETS = new Insets(0, 0, 0, 70);


    public AddProduct() {
        super();
        assignAll();
    }

    private void assignAll() {
        setPhoneNumberField();
        setNameField();
        setWholesalePriceField();
        setSKUField();
        setProductNameField();
        setRetailPriceField();
        setSaleField();
        assignOrderButton();
        setOrderTitle();
    }

   private void setOrderTitle() {
       headerHBox = new HBox();
       orderTitle = new Text();
       orderTitle.setText(ORDER_TITLE_TXT);
       orderTitle.getStyleClass().add("text-header");

       headerHBox.getChildren().addAll(backToMainVBox, orderTitle);
       HBox.setMargin(orderTitle, ORDER_INSETS);
       HBox.setMargin(backToMainVBox, BACK_INSETS);
       headerHBox.setAlignment(Pos.CENTER);
   }

    private void setSKUField() {
        skuHBox = new HBox();
        skuTF = new TextField();
        skuTF.setPromptText(SKU_TXT);
        skuHBox.getChildren().addAll(skuTF);
        skuHBox.setAlignment(Pos.CENTER);
    }

    private void setProductNameField() {
        productNameHBox = new HBox();
        productNameTF = new TextField();
        productNameTF.setPromptText(PRODUCT_TXT);
        productNameHBox.getChildren().addAll(productNameTF);
        productNameHBox.setAlignment(Pos.CENTER);

    }

    private void setRetailPriceField() {
        retailPriceHBox = new HBox();
        retailPriceTF = new TextField();
        retailPriceTF.setPromptText(RETAIL_PRICE_TXT);
        retailPriceHBox.getChildren().addAll(retailPriceTF);
        retailPriceHBox.setAlignment(Pos.CENTER);

    }

    private void setWholesalePriceField() {
        wholesalePriceHBox = new HBox();
        wholesalePriceTF = new TextField();
        wholesalePriceTF.setPromptText(WHOLESALE_TXT);
        wholesalePriceHBox.getChildren().addAll(wholesalePriceTF);
        wholesalePriceHBox.setAlignment(Pos.CENTER);

    }

    private void setNameField() {
        nameHBox = new HBox();
        nameTF = new TextField();
        nameTF.setPromptText(NAME_TXT);
        nameHBox.getChildren().addAll(nameTF);
        nameHBox.setAlignment(Pos.CENTER);

    }

    private void setPhoneNumberField() {
        phoneNumberHBox = new HBox();
        phoneNumberTF = new TextField();
        phoneNumberTF.setPromptText(PHONE_TXT);
        phoneNumberHBox.getChildren().addAll(phoneNumberTF);
        phoneNumberHBox.setAlignment(Pos.CENTER);

    }

    private void assignOrderButton() {
        orderHBox = new HBox();
        orderButton = new Button(ORDER_BUTTON_TXT);
        orderButton.getStyleClass().add("button-order");
        orderHBox.getChildren().add(orderButton);
        orderHBox.setAlignment(Pos.CENTER);
    }

    private void setSaleField() {
        saleHBox = new HBox();
        saleCB = new CheckBox();
        saleCB.setText(SALE_TXT);
        saleHBox.getChildren().addAll(saleCB);
        HBox.setMargin(saleCB, SALE_INSETS);
    }

    @Override
    public VBox update() {
        mainView.getChildren().clear();
        mainView.getChildren().addAll(headerHBox, skuHBox, productNameHBox, retailPriceHBox, wholesalePriceHBox, nameHBox, phoneNumberHBox, saleHBox, orderHBox);
        VBox.setMargin(skuHBox, TEXT_FIELD_INSETS);
        VBox.setMargin(productNameHBox, TEXT_FIELD_INSETS);
        VBox.setMargin(retailPriceHBox, TEXT_FIELD_INSETS);
        VBox.setMargin(wholesalePriceHBox, TEXT_FIELD_INSETS);
        VBox.setMargin(nameHBox, TEXT_FIELD_INSETS);

        return mainView;
    }

    public String getSKU() {
        return skuTF.getText();
    }

    public String getProductName() {
        return productNameTF.getText();
    }

    public String getRetailPrice() {
        return retailPriceTF.getText();
    }

    public String getWholesalePrice() {
        return wholesalePriceTF.getText();
    }

    public String getCustomerName() {
        return nameTF.getText();
    }

    public String getPhoneNumber() {
        return phoneNumberTF.getText();
    }

    public Boolean getPromotionNotification() {
        return saleCB.isSelected();
    }

    public void eventOrderButton(EventHandler<ActionEvent> event) {
        orderButton.setOnAction(event);
    }

}