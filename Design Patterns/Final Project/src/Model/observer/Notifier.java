package Model.observer;

import java.util.ArrayList;

public class Notifier implements Sender, Receiver{

    private static Notifier notifier = null;
    private final ArrayList<String> received;

    public Notifier() {
        received = new ArrayList<>();
    }

    @Override
    public void sendMSG(Receiver r, String s) {
        r.receiveMSG(this, s);
    }

    public static Notifier getInstance() {
        if(notifier == null)
            notifier = new Notifier();
        return notifier;
    }

    @Override
    public void receiveMSG(Sender s, String msg) {
        received.add(msg);
    }
}
