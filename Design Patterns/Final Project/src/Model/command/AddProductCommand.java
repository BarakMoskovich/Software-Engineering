package Model.command;

import Model.Product;

public class AddProductCommand implements Command {
    private final StoreCommand storeCommand;
    private final Product product;
    private boolean renew;

    public AddProductCommand(StoreCommand storeCommand, Product product) {
        this.storeCommand = storeCommand;
        this.product = product;
        renew = false;
    }

    @Override
    public void execute() {
        renew = storeCommand.addProduct(product);
    }

    public boolean isRenew() {
        return renew;
    }
}
