package main;

import controller.Controller;
import exceptions.StringLengthException;
import exceptions.UnderAgeException;
import view.ViewCLI;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) throws StringLengthException, UnderAgeException {
        Scanner s = new Scanner(System.in);
        Controller controller = new Controller();
        TestUtils.hardCodeToTest(controller); // Hard Code
        ViewCLI view = new ViewCLI(controller, s);
        view.showMenu();
    }
}