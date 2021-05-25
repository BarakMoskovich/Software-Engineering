import java.time.LocalDateTime;

import command.Balance;
import command.BalanceCommand;
import observer.Message;
import observer.Receiver;
import observer.Sender;


public class Mobile implements Sender, Receiver, MobileFunc {
	private String name;
	private String simNumber;
	private BalanceCommand balanceCommand;
	private VCall currentVCall;

	public Mobile(String name, String simNumber) {
		this.name = name;
		this.simNumber = simNumber;
		this.balanceCommand = new BalanceCommand();
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public double getSimBalance() {
		return this.balanceCommand.getBalanceCommand();
	}

	@Override
	public void setSimBalance(double simBalance) {
		balanceCommand.setBalanceCommand(simBalance);
	}

	@Override
	public VCall getCurrentVCall() {
		return this.currentVCall;
	}

	@Override
	public void startVCall(VCall vCall) {
		currentVCall = vCall;
		currentVCall.setSimNumber(getSimNumber());
		currentVCall.setBalanceCommand(balanceCommand);
		currentVCall.start();
	}

	@Override
	public void stopVCall() {
		currentVCall.interrupt();
		currentVCall = null;
	}

	@Override
	public void receiveMSG(Sender s, Message msg) {
		System.out.println("\n------------" + getSimNumber() + "-------------\n" +
				"New Message Has Arrived\n" +
				"------------------------------------\n" +
				"From: " + s.getSimNumber() + "\n" +
				msg.toString() +
				"------------------------------------\n");
	}

	@Override
	public String getSimNumber() {
		return this.simNumber;
	}

	@Override
	public void sendMSG(Receiver r, Message msg) {
		r.receiveMSG(this, msg);
	}
}
