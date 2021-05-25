package Model.command;

public class RemoveAllProductCommand implements Command {
    private final StoreCommand storeCommand;
    private boolean allRemoved;

    public RemoveAllProductCommand(StoreCommand newStoreCommand) {
        this.storeCommand = newStoreCommand;
        allRemoved = false;
    }

    @Override
    public void execute() {
        allRemoved = storeCommand.removeAllProduct();
    }

    public boolean isAllRemoved() {
        return allRemoved;
    }
}