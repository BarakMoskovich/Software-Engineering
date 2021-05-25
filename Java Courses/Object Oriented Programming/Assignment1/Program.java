

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        ServicesManager.hardCodeToTest(); // HARD CODE

        boolean exit = false;
        while (!exit) {
            ServicesManager.showMenu();
            int choose = s.nextInt();
            switch (choose) {
                case 1:
                    ServicesManager.addBallotBox(getBallotBox(s));
                    break;
                case 2:
                    ServicesManager.addCitizen(getCitizen(s, false));
                    break;
                case 3:
                    ServicesManager.addParty(getParty(s));
                    break;
                case 4:
                    String[] candidate = getCandidate(s);
                    ServicesManager.addCandidate(candidate[0], candidate[1], Integer.parseInt(candidate[2]));
                    break;
                case 5:
                    System.out.println(ServicesManager.showAllBallotBox());
                    break;
                case 6:
                    System.out.println(ServicesManager.showAllCitizens());
                    break;
                case 7:
                    System.out.println(ServicesManager.showAllParties());
                    break;
                case 8:
                    ServicesManager.beginElections(s);
                    break;
                case 9:
                    ServicesManager.showResults();
                    break;
                case 10:
                default:
                    exit = true;
                    break;
            }
        }
    }

    public static Citizen getCitizen(Scanner s, boolean inParty) {
        String name;
        String id;
        int birthYear;
        char ansChar;
        boolean isolation;
        BallotBox ballotBox;

        s.nextLine();
        System.out.print("Enter name : ");
        name = s.nextLine();

        System.out.print("Enter ID :");
        id = s.next();

        System.out.print("Enter Birth Year : ");
        birthYear = s.nextInt();

        System.out.print("You in isolation [Y/N] : ");
        ansChar = s.next().toCharArray()[0];
        if (ansChar == 'y' || ansChar == 'Y')
            isolation = true;
        else if (ansChar == 'n' || ansChar == 'N')
            isolation = false;
        else
            isolation = false;

        System.out.println(ServicesManager.getLegalBallotBoxes(birthYear, isolation));

        System.out.println("Enter Ballot Box Number : ");
        ballotBox = ServicesManager.getBallotBoxByNumber(s.nextInt());

        return new Citizen(name, id, birthYear, isolation, ballotBox);
    }

    public static Party getParty(Scanner s) {
        String name;
        String section;
        int sectionAns;
        int year;
        int month;
        int day;

        s.nextLine();
        System.out.print("Enter name : ");
        name = s.nextLine();

        System.out.print("Enter Section \n1 - Right \n2 - Center \n3 - Left ");
        sectionAns = s.nextInt();
        switch (sectionAns) {
            case 2:
                section = Party.CENTER_SECTION;
                break;
            case 3:
                 section = Party.LEFT_SECTION;
                 break;
            case 1:
            default:
                section = Party.RIGHT_SECTION;
                break;
        }

        System.out.print("Enter Creation Year : ");
        year = s.nextInt();

        System.out.print("Enter Creation Month : ");
        month = s.nextInt();

        System.out.print("Enter Creation Day : ");
        day = s.nextInt();

        return new Party(name, section, year, month, day);
    }

    public static BallotBox getBallotBox(Scanner s) {
        String address;
        int kind;
        s.nextLine();
        System.out.print("Enter Address : ");
        address = s.nextLine();

        System.out.print("Which kind of ballot box \n1 - Regular \n2 - Army \n3 - Corona ");
        kind = s.nextInt();
        switch (kind) {
            case 2:
                return new Army(address);
            case 3:
                return new Corona(address);
            case 1:
            default:
                return new Regular(address);
        }
    }

    public static String[] getCandidate(Scanner s) {
        String[] info = new String[3];

        System.out.print("Enter Citizen ID : ");
        info[0] = s.next();

        System.out.println(ServicesManager.election.getAllPartiesLine());
        s.nextLine();
        System.out.print("Enter Party Name : ");
        info[1] = s.nextLine();

        System.out.print("Enter Place : ");
        info[2] = Integer.toString(s.nextInt());

        return info;
    }

    public static String getVoteParty(Scanner s, String citizenName) {
        System.out.print(citizenName + ", You want to vote ? [Y/N]");
        char ansChar = s.next().toCharArray()[0];
        if (ansChar == 'y' || ansChar == 'Y') {
            s.nextLine();
            System.out.print("You want to vote to : ");
            return s.nextLine();
        } else if (ansChar == 'n' || ansChar == 'N') {
            return "false";
        } else
            return " ";
    }

    public static boolean CoronaQuiz(Scanner s) {
        System.out.print("Are you wearing a protective suit ?  [Y/N] ");
        char ansChar = s.next().toCharArray()[0];
        if (ansChar == 'y' || ansChar == 'Y')
            return true;
        return false;
    }
}
