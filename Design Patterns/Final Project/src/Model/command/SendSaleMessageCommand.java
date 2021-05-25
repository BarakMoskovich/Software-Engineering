package Model.command;

public class SendSaleMessageCommand implements Command {
    private final StoreCommand storeCommand;
    private final String msg;

    public SendSaleMessageCommand(StoreCommand storeCommand, String msg) {
        this.storeCommand = storeCommand;
        this.msg = msg;
    }

    @Override
    public void execute() {
        storeCommand.sendSaleMessage(msg);
    }
}
