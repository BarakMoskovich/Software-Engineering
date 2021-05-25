package HW02;

import java.util.Scanner;

public class Program {
    private static boolean isFinished = false;

    public static void main(String [] args) {
        Lab lab = new Lab();
        Scanner scan = new Scanner(System.in);
        while (!isFinished) {
            loadMenu(lab, scan);
        }
        System.out.println("Program Closed");
    }

    private static void loadMenu(Lab lab, Scanner scan) {
        System.out.println("\n==================== Lab ====================\n" +
                "1. Add Mobile To Lab\n" +
                "2. Remove Mobile From Lab\n" +
                "3. Change Status By Number\n" +
                "4. Get Status By Number\n" +
                "5. Print All Mobiles in Lab\n" +
                "6. Print Repaired Mobiles\n" +
                "7. Print Mobiles In Repair\n" +
                "8. Print Set Of Clients\n" +
                "Enter your Option or Press any other key to Exit.\n" +
                "=============================================\n");

        System.out.print("Enter your option: ");
        String choose = scan.nextLine();
        switch (choose) {
            case "1":
                addMobileInLab(lab, scan);
                break;
            case "2":
                removeMobileFromLab(lab, scan);
                break;
            case "3":
                changeStatusByNumber(lab, scan);
                break;
            case "4":
                getStatusByNumber(lab, scan);
                break;
            case "5":
                printTitle("Print All Mobiles");
                lab.printAllMobiles();
                break;
            case "6":
                printTitle("Print Repaired Mobiles");
                lab.printRepaired();
                break;
            case "7":
                printTitle("Print Mobiles In Repair");
                lab.printInRepair();
                break;
            case "8":
                printTitle("Print Set Of Clients");
                lab.printClientsSet();
                break;
            default:
                isFinished = true;
                break;
        }
    }

    private static void getStatusByNumber(Lab lab, Scanner scan) {
        printTitle("Get Status By Number");
        String status = lab.getStatus(getStringFromClient("Enter Mobile Number:", scan));

        if (status != null)
            System.out.println(status);
        else
            System.out.println("Mobile Not Found!");
    }

    private static void changeStatusByNumber(Lab lab, Scanner scan) {
        printTitle("Change Status By Number");
        String mobileNumber = getStringFromClient("Enter Mobile Number:", scan);
        if (!lab.isExist(mobileNumber)) {
            System.out.println("Mobile Not Found!");
            return;
        }

        String status = getStringFromClient("Enter Mobile Status:", scan);
        if (lab.setStatus(mobileNumber, status))
            System.out.println("Status Changed!");
    }

    private static void removeMobileFromLab(Lab lab, Scanner scan) {
        printTitle("Remove Mobile In Lab");
        String mobileNumber = getStringFromClient("Enter Mobile Number:", scan);

        if (lab.removeMobile(mobileNumber))
            System.out.println("Mobile Removed!");
        else
            System.out.println("Mobile Not Found!");
    }

    private static void addMobileInLab(Lab lab, Scanner scan) {
        Client client;
        Mobile mobile;
        String clientId, fName, lName, mobileNumber, mobileInfo, status;
        printTitle("Add Mobile In Lab");

        clientId = getStringFromClient("Enter Client Id:", scan);
        fName = getStringFromClient("Enter Client First Name:", scan);
        lName = getStringFromClient("Enter Client Last Name:", scan);
        mobileNumber = getStringFromClient("Enter Mobile Number:", scan);

        // Check for duplicates
        if (lab.isExist(mobileNumber)) {
            System.out.println("The mobile is already in the lab.");
            return;
        }

        mobileInfo = getStringFromClient("Enter Mobile Info:", scan);

        client = new Client(clientId, fName, lName);
        mobile = new Mobile(mobileNumber, client,mobileInfo, lab.getNextEQueue());

        System.out.println(lab.getEQueue());
        lab.addMobile(mobile);

        status = getStringFromClient("Enter Mobile Status:", scan);
        lab.setStatus(mobile.getNumber(), status);
        System.out.println("Mobile Added!");
    }

    private static String getStringFromClient(String text, Scanner scan) {
        System.out.print(text);
        return scan.nextLine();
    }

    private static void printTitle(String text) {
        System.out.println("============= " + text + " =============");
    }
}
