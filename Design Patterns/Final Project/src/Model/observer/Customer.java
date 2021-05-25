package Model.observer;

import java.io.Serializable;

public class Customer implements Serializable, Sender, Receiver{

    private static final long serialVersionUID = 7526472295622776147L;
    private final String name;
    private final String phoneNumber;
    private final boolean promotionsNotification;

    public Customer(String name, String phoneNumber, boolean promotionsNotification) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.promotionsNotification = promotionsNotification;
    }

    public boolean getEventOnSales() {
        return promotionsNotification;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Customer" +
                "\nName: " + name +
                "\nPhone Number: " + phoneNumber +
                "\nPromotions Notification? " + (promotionsNotification ? "Yes" : "No") +
                "\n";
    }

    @Override
    public void receiveMSG(Sender s, String msg) {
        sendMSG((Receiver) s, name);
    }

    @Override
    public void sendMSG(Receiver r, String s) {
        r.receiveMSG(this, name);
    }
}