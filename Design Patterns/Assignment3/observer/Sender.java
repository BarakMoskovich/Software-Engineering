package observer;


//Observable
public interface Sender {
	String getSimNumber();
	void sendMSG(Receiver r, Message msg);
}
