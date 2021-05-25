
public interface MobileFunc{
	String getName();
	double getSimBalance();
	void setSimBalance(double simBalance);
	VCall getCurrentVCall();
	void startVCall(VCall vCall);
	void stopVCall();

}
