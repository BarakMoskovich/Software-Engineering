package command;

public class BalanceCommand implements Command {
    private Balance balance;

    public BalanceCommand(Balance balance) {
        this.balance = balance;
    }

    public BalanceCommand() {
        this(new Balance());
    }

    @Override
    public double getBalance() {
        return balance.get();
    }

    @Override
    public void setBalance(double value) {
        balance.set(value);
    }

    public double getBalanceCommand() {
        return getBalance();
    }

    public void setBalanceCommand(double value) {
        setBalance(value);
    }
}
