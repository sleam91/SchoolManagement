package view;

import java.time.LocalDate;
import java.util.Scanner;

public class UIScannerImpl implements UI {

    static Scanner sc = new Scanner(System.in);

    @Override
    public int takeChoiceInput(int max) {
        int choice = -1;
        while (choice < 0 || choice > max) {
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
                if (choice < 0 || choice > max) {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Not a valid number, try again!");
            }
        }
        return choice;
    }

    @Override
    public void outputString(String outputString) {
        System.out.println(outputString);

    }

    @Override
    public String inputString() {
        return sc.nextLine().trim();
    }

    @Override
    public void printResult(Object o) {
        System.out.println(o);
    }

    @Override
    public void pressEnterToContinue() {
        System.out.println("");
        System.out.println("Press Enter to continue...");
        sc.nextLine();
    }

    @Override
    public void clear() {
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }

    @Override
    public void exit() {
        System.exit(0);
    }

    @Override
    public String inputDate() {
        boolean loop = true;
        String date = null;
        while(loop) {
            date = inputString();
            LocalDate now = LocalDate.now();
            try {
                LocalDate tryDate = LocalDate.parse(date, FORMATTER);
                
                if (!(tryDate.isEqual(now) || tryDate.isAfter(now))) {
                    throw new Exception();
                }
                loop=false;
            } catch (Exception e) {
                outputString("Not a valid date, try again");
            }

        }
        return date;
//        boolean loop = true;
//        String date = null;
//        String regex = "^((\\d{2}(([02468][048])|([13579][26]))[\\-]((((0[13578])|(1[02]))[\\-]((0[1-9])|([1-2][0-9])|(3[01])))|(((0[469])|(11))[\\-]((0[1-9])|([1-2][0-9])|(30)))|(02[\\-]((0[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-]((((0[13578])|(1[02]))[\\-]((0[1-9])|([1-2][0-9])|(3[01])))|(((0[469])|(11))[\\-]((0[1-9])|([1-2][0-9])|(30)))|(02[\\-]((0[1-9])|(1[0-9])|(2[0-8]))))))$";
//        while (loop) {
//
//            date = sc.nextLine().trim();
//            if (date.matches(regex)) {
//                loop = false;
//            } else {
//                System.out.println("Not a valid date, try again");
//            }
//        }
//        return date;
    }

}
