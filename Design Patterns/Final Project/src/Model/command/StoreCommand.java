package Model.command;

import Model.Product;

import java.util.ArrayList;

public interface StoreCommand {

    boolean addProduct(Product newProduct);

    boolean removeProduct(String sku);

    Product getProduct(String sku);

    void setOrderBy(int orderBy);

    ArrayList<String> getAllConfirmedCustomerNames();

    void undo(); // undo add product

    ArrayList<Product> getAllProducts();

    void sendSaleMessage(String msg);

    boolean removeAllProduct();
}
