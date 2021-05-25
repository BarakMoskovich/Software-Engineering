package view;

import controller.ControllerCLI;
import exceptions.*;
import model.*;
import model.citizens.*;
import java.util.Scanner;

public class ViewCLI {
    private ControllerCLI controllerCLI;
    private Scanner s;

    public ViewCLI(ControllerCLI controllerCLI, Scanner scanner) {
        this.controllerCLI = controllerCLI;
        this.s = scanner;
    }

    public void showMenu() throws StringLengthException, UnderAgeException {
        boolean exit = false;
        while (!exit) {
            System.out.println(controllerCLI.getCLIMenu());
            int choose = s.nextInt();
            switch (choose) {
                case 1:
                    if(!controllerCLI.addBallotBox(getBallotBox(s)))
                        System.out.println("This BallotBox has already been created!!!\n");
                    break;
                case 2:
                    if(!controllerCLI.addCitizen(getCitizen(s)))
                        System.out.println("The citizen shouldn't be added!");
                    break;
                case 3:
                    if(!controllerCLI.addParty(getParty(s)))
                        System.out.println("You are not allowed to add this party");
                    break;
                case 4:
                    String[] candidate = getCandidate(s);
                    if(!controllerCLI.addCandidate(candidate[0], candidate[1], Integer.parseInt(candidate[2]))) {
                        System.out.println("An error has occurred\n");
                    }
                    break;
                case 5:
                    System.out.println(controllerCLI.getAllBallotBox());
                    break;
                case 6:
                    System.out.println(controllerCLI.getAllCitizens());
                    break;
                case 7:
                    System.out.println(controllerCLI.getAllParties());
                    break;
                case 8:
                    controllerCLI.startVoteCLI(s);
                    break;
                case 9:
                    System.out.println(controllerCLI.getResults());
                    break;
                case 10:
                default:
                    exit = true;
                    break;
            }
        }
    }

    private Citizen getCitizen(Scanner s){
        int birthYear = 0, daysInIsolation = 0, tempInt, ballotBoxId;
        boolean isolation, soldierAge, hasLegalID = false, hasLegalAge = false, legalBallotBoxID = false;
        String name, id = "", ballotBoxes;
        String[] splitBallotBoxes;
        char ansChar;
        BallotBox ballotBox = null;

        s.nextLine();
        System.out.print("Enter name : ");
        name = s.nextLine();

        do {
            System.out.print("Enter ID : ");
            try{
                id = s.next();
                boolean convert = Integer.valueOf(id).toString().length() == 9;
                if (convert) {
                    hasLegalID = true;
                }
                else
                    throw new StringLengthException("Error in ID. ID must be with 9 numbers");
            } catch (StringLengthException sle) {
//                System.out.println("Error in ID. ID must be with 9 numbers");
                System.out.println(sle.getMessage());
            }
            catch (Exception e) {
                System.out.println("It is not a number!");
            }
        } while (!hasLegalID);

        String temp;
        do {
            System.out.print("Enter Birth Year : ");
            try {
                temp = s.next();
                birthYear = Integer.parseInt(temp);
                boolean checkLegal = (controllerCLI.getElection().getYear() - birthYear) >= 18 ;
                if (checkLegal)
                    hasLegalAge = true;
                else
                    throw new UnderAgeException("You have not born yet, or your age is not over 18!");
            } catch (UnderAgeException uae) {
                System.out.println(uae.getMessage());
            } catch (Exception e) {
                System.out.println("It is not a number!");
            }
        } while(!hasLegalAge);

        System.out.print("You in isolation [Y/N] : ");
        ansChar = s.next().toCharArray()[0];
        if (ansChar == 'y' || ansChar == 'Y') {
            isolation = true;
            System.out.print("How many days are you in isolation?");
            daysInIsolation = s.nextInt();
        }
        else
            isolation = false;

        ballotBoxes = controllerCLI.getLegalBallotBoxes(birthYear, isolation, false);
        splitBallotBoxes = ballotBoxes.split(",");
        for (String ballot : splitBallotBoxes)
            System.out.println(ballot.substring(1, (ballot.length() - 1) ));

        ballotBoxes = controllerCLI.getLegalBallotBoxes(birthYear, isolation, true);
        splitBallotBoxes = ballotBoxes.split(";");

        while (!legalBallotBoxID) {
            System.out.println("Enter Ballot Box Number : ");
            ballotBoxId = s.nextInt();

            for (String ballot : splitBallotBoxes)
                if (Integer.parseInt(ballot) == ballotBoxId)
                    legalBallotBoxID = true;

            ballotBox = controllerCLI.getElection().getBallotBoxByNumber(ballotBoxId);
        }

        soldierAge = controllerCLI.getElection().getYear() - birthYear <= 21;
        if (isolation && soldierAge)
            return new SoldierCorona(name, id, birthYear, isolation, ballotBox, daysInIsolation);
        else if (isolation)
            return new Corona(name, id, birthYear, isolation, ballotBox, daysInIsolation);
        else if (soldierAge)
            return new Soldier(name, id, birthYear, ballotBox);
        else
            return new Citizen(name, id, birthYear, ballotBox);
    }



    private Party getParty(Scanner s) {
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

    private BallotBox<? extends Citizen> getBallotBox(Scanner s) {
        String address;
        int kind;

        s.nextLine();
        System.out.print("Enter Address : ");
        address = s.nextLine();

        System.out.print("Which kind of ballot box \n1 - Regular \n2 - Corona \n3 - Army\n4 - Army Corona ");
        kind = s.nextInt();
        switch (kind) {
            case 2:
                return new BallotBox<Corona>(address, Corona.class);
            case 3:
                return new BallotBox<Soldier>(address, Soldier.class);
            case 4:
                return new BallotBox<SoldierCorona>(address, SoldierCorona.class);
            case 1:
            default:
                return new BallotBox<Citizen>(address, Citizen.class);
        }
    }

    private String[] getCandidate(Scanner s) {
        String[] info = new String[3];

        System.out.print("Enter Citizen ID : ");
        info[0] = s.next();

        System.out.println(controllerCLI.election.getAllParties());
        s.nextLine();
        System.out.print("Enter Party Name : ");
        info[1] = s.nextLine();

        System.out.print("Enter Place : ");
        info[2] = Integer.toString(s.nextInt());

        return info;
    }
}
