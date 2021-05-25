package observer;


//Observer
public interface Receiver {
	String getSimNumber();
	void receiveMSG(Sender s, Message msg);
}
