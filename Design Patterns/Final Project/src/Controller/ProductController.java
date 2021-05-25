package Controller;

import Exceptions.AlertUserException;
import Model.Product;
import Model.command.AddProductCommand;
import Model.command.Store;
import Model.command.StoreCommand;
import Model.observer.Customer;
import View.AddProduct;
import View.HomeScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;

public class ProductController extends BackButtonController {

    private static final String EMPTY_SKU_FIELD = "Please fill in the SKU field";
    private static final String RETAIL_PRICE_ERROR = "Please fill a legal retail price";
    private static final String WHOLESALE_PRICE_ERROR = "Please fill a legal wholesale price";
    private static final String PRODUCT_ADDED_SUCCESSFULLY = "Product Added!";
    private static final String PRODUCT_REPLACED = "Product Replaced!";
    private static final String NOTIFICATION_ERROR = "To receive notifications about promotions please enter a Customer";
    private static final String CUSTOMER_NAME_ERROR = "Please fill a legal name";
    private static final String PHONE_ERROR = "Please fill a legal Israeli phone number";
    private static final String PRICE_RGX = "[0-9]*";
    private static final String CUSTOMER_NAME_RGX = "^[a-zA-Z\\s]+";
    private static final String PHONE_RGX = "(05[0-9]|0[12346789])([0-9]{7})";

    private final AddProduct addProductView;

    public ProductController(HomeScreen homeScreenView, StoreCommand store, AddProduct addProductView) {
        super(homeScreenView, store, addProductView);
        this.addProductView = addProductView;

        eventForOrderButton();
    }

    private void eventForOrderButton() {
        EventHandler<ActionEvent> eventForOrderButton = new EventHandler<ActionEvent>() {
            Alert alert;

            @Override
            public void handle(ActionEvent actionEvent) {
                String sku = addProductView.getSKU();

                try {
                    if (sku.isEmpty())
                        throw new AlertUserException(EMPTY_SKU_FIELD);

                    String productName = addProductView.getProductName();
                    String retailPrice = addProductView.getRetailPrice();
                    String wholesalePrice = addProductView.getWholesalePrice();

                    int doubleRetailPrice = 0, doubleWholesalePrice = 0;

                    Customer customer = getCustomer();

                    if (!retailPrice.isEmpty()) {
                        if (!retailPrice.matches(PRICE_RGX))
                            throw new AlertUserException(RETAIL_PRICE_ERROR);
                        else
                            doubleRetailPrice = Integer.parseInt(retailPrice);
                    }

                    if (!wholesalePrice.isEmpty()) {
                        if (!wholesalePrice.matches(PRICE_RGX))
                            throw new AlertUserException(WHOLESALE_PRICE_ERROR);
                        else
                            doubleWholesalePrice = Integer.parseInt(wholesalePrice);
                    }

                    Product product = new Product(sku,
                            productName,
                            doubleRetailPrice,
                            doubleWholesalePrice,
                            customer);

                    AddProductCommand addProductCommand = new AddProductCommand(store, product);
                    addProductCommand.execute();

                    boolean renewProduct = addProductCommand.isRenew();

                    StoreController.checkEnableCancelButton.run();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    if (!renewProduct)
                        alert.setContentText(PRODUCT_ADDED_SUCCESSFULLY);
                    else
                        alert.setContentText(PRODUCT_REPLACED);
                    alert.showAndWait();

                    view.loadMain();
                } catch (AlertUserException i) {
                    i.showErrorMessage();
                } catch (Exception e) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Unexpected error!");
                    alert.showAndWait();
                }
            }
        };
        addProductView.eventOrderButton(eventForOrderButton);
    }

    private Customer getCustomer() throws AlertUserException {

        String customerName = addProductView.getCustomerName();
        String phoneNumber = addProductView.getPhoneNumber();
        boolean promNotification = addProductView.getPromotionNotification();

        if (promNotification && (phoneNumber.isEmpty() || customerName.isEmpty()))
            throw new AlertUserException(NOTIFICATION_ERROR);

        if (!customerName.isEmpty() && !customerName.matches(CUSTOMER_NAME_RGX))
            throw new AlertUserException(CUSTOMER_NAME_ERROR);

        if (!phoneNumber.matches(PHONE_RGX))
            throw new AlertUserException(PHONE_ERROR);

        return new Customer(customerName, phoneNumber, promNotification);
    }
}
