package Model.command;

import Model.Product;

public class GetProductCommand implements Command {
    private final StoreCommand storeCommand;
    private Product product;
    private final String sku;

    public GetProductCommand(StoreCommand storeCommand, String sku) {
        this.storeCommand = storeCommand;
        this.sku = sku;
        product = null;
    }

    @Override
    public void execute() {
        product = storeCommand.getProduct(sku);
    }

    public Product get() {
        return product;
    }
}
