package Model.command;

import Model.Product;

import java.util.ArrayList;

public class GetAllProductsCommand implements Command {
    private final StoreCommand storeCommand;
    private ArrayList<Product> allProducts;

    public GetAllProductsCommand(StoreCommand storeCommand) {
        this.storeCommand = storeCommand;
        allProducts = null;
    }

    @Override
    public void execute() {
        allProducts = storeCommand.getAllProducts();
    }

    public ArrayList<Product> get() {
        return allProducts;
    }
}
