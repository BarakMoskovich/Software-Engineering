import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import command.BalanceCommand;

public class VCall extends Thread {

	private String simNumber;
	private String callNumber;
	private BalanceCommand balanceCommand;
	private double pricePerMin;

	public VCall(String callNumber, double pricePerMin) {
		this.callNumber = callNumber;
		this.pricePerMin = pricePerMin;
	}

	public String getSimNumber() {
		return simNumber;
	}

	public void setSimNumber(String simNumber) {
		this.simNumber = simNumber;
	}

	public String getCallNumber() {
		return callNumber;
	}

	public void setCallNumber(String callNumber) {
		this.callNumber = callNumber;
	}

	public double getSimBalance() {
		return balanceCommand.getBalanceCommand();
	}

	public void setSimBalance(double simBalance) {
		this.balanceCommand.setBalanceCommand(simBalance);
	}

	public double getPricePerMin() {
		return pricePerMin;
	}

	public void setPricePerMin(double pricePerMin) {
		this.pricePerMin = pricePerMin;
	}

	public void setBalanceCommand(BalanceCommand balanceCommand) {
		this.balanceCommand = balanceCommand;
	}

	@Override
	public void run() {
		System.out.printf("Voice Call Started [From: %s To: %s Balance: %.2f]\n", getSimNumber(), callNumber,
				getSimBalance());
		LocalTime duration = LocalTime.of(0, 0, 0);

		while (!Thread.currentThread().isInterrupted() && getSimBalance() - (pricePerMin / 60) > 0) {
			try {
				Thread.sleep(1000);
				duration = duration.plus(1, ChronoUnit.SECONDS);
				setSimBalance(getSimBalance() - (pricePerMin / 60) );
			} catch (InterruptedException e) {
				System.out.printf("\nVoice Call Stopped [From: %s To: %s Balance: %.2f Duration: %s]\n", getSimNumber(), callNumber, getSimBalance(), duration);
				Thread.currentThread().interrupt();
				return;
			}
		}

		System.out.printf("\nVoice Call Finished(No Enough Balance) [From: %s To: %s Balance: %.2f Duration: %s]\n", getSimNumber(), callNumber,
				getSimBalance(), duration);
	}
}
