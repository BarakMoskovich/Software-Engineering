package Model.command;

import java.util.ArrayList;

public class GetAllConfirmedCustomerNamesCommand implements Command {
    private final StoreCommand storeCommand;
    private ArrayList<String> allCustomersNames;

    public GetAllConfirmedCustomerNamesCommand(StoreCommand storeCommand) {
        this.storeCommand = storeCommand;
        allCustomersNames = null;
    }

    @Override
    public void execute() {
        allCustomersNames = storeCommand.getAllConfirmedCustomerNames();
    }

    public ArrayList<String> get() {
        return allCustomersNames;
    }
}
