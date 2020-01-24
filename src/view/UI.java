package view;

import java.time.format.DateTimeFormatter;

public interface UI {

    DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    int takeChoiceInput(int max);

    void outputString(String output);

    String inputString();

    String inputDate();

    void printResult(Object o);

    void pressEnterToContinue();

    void clear();

    void exit();
}
