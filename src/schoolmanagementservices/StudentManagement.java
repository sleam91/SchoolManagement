package schoolmanagementservices;

import entities.Program;
import entities.Student;
import integration.DAO;
import integration.StudentDAOImpl;
import static schoolmanagementservices.Main.program;
import static schoolmanagementservices.Main.ui;
import static schoolmanagementservices.ProgramManagement.daoProgram;

public class StudentManagement implements ManagementInterface<Student> {

    static DAO<Student> daoStudent = new StudentDAOImpl();

    @Override
    public Student findByNameOrID() {
        String input = ui.inputString();
        if (input.isEmpty()) {
            ui.outputString("Cancelled, no input detected");
            return null;
        }
        Student s = daoStudent.findByNameOrID(input);
        if (s != null) {
            showDetailedInfo(s);
        } else {
            ui.outputString("No result found");
        }
        return s;
    }

    @Override
    public void showDetailedInfo(Student s) {
        ui.printResult(s);
        if (s.getProgram() != null) {
            ui.outputString("\nCurrent Program:");
            ui.printResult(s.getProgram());
        }
    }

    @Override
    public void showAll() {
        daoStudent.findAll().sorted((e1, e2) -> e1.getId() - e2.getId()).forEach(s
                -> ui.printResult(s + (s.getProgram() != null ? ". Program: " + s.getProgram().getName() : "")));
    }

    @Override
    public Student add() {
        ui.outputString("Enter Student name:");
        String name = ui.inputString();
        if (name.isEmpty()) {
            ui.outputString("Cancelled, no input detected");
            return null;
        } else if (daoStudent.findByName(name) != null) {
            ui.outputString("Student with that name already exists, choose another name");
            return add();
        } else if (name.matches("^[0-9]\\d*$")) {
            ui.outputString("Student name cannot be only numbers, try again");
            return add();
        }
        Student s = new Student(name);
        daoStudent.create(s);
        ui.outputString("Student created");
        return s;
    }

    @Override
    public void update(String choice) {
        ui.outputString("Enter Name or ID of Student to Update:");
        Student s = findByNameOrID();
        if (s == null) {
            if (choice.equals("addProgram")) {
                ui.pressEnterToContinue();
            }
            return;
        }

        switch (choice) {

            case "updateName":
                updateName(s);

                break;
            case "addProgram":
                addProgram(s);
                break;
            case "removeProgram":
                removeProgram(s);
                break;

        }

        daoStudent.update(s);

    }

    @Override
    public void updateName(Student s) {
        ui.outputString("Enter new Student name:");
        String name = ui.inputString();

        if (name.isEmpty()) {
            ui.outputString("Cancelled, no input detected");

        } else if (daoStudent.findByName(name) != null) {
            ui.outputString("Student with that name already exists, choose another name");

        } else if (name.matches("^[0-9]\\d*$")) {
            ui.outputString("Student name cannot be only numbers, try again");

        } else {
            s.setName(name);
            ui.outputString("Student Name updated");

        }
    }

    @Override
    public void delete() {
        ui.outputString("Enter Name or ID of Student to Delete:");
        Student s = findByNameOrID();
        if (s != null) {
            daoStudent.delete(s);
            ui.outputString(s.getName() + " removed successfully from Students");
        }
    }

    private void addProgram(Student s) {
        ui.outputString("\nList of available Programs:");
        program.showAll();
        ui.outputString("\nEnter Name or ID of Program to add/change from Student:");
        Program p = program.findByNameOrID();
        if (p == null) {
            ui.outputString("Create new Program? (Y/N)");
            String input = ui.inputString();
            ui.clear();
            if (input.equalsIgnoreCase("Y")) {
                p = program.add();
            } else {
                return;
            }

        }
        if (p != null) {
            if (p.getStudentList().contains(s)) {
                ui.outputString("\nStudent already added to Program " + p.getName());
            } else {
                s.setProgram(p);
                p.getStudentList().add(s);
                daoProgram.update(p);
                ui.outputString("\nStudent added to Program " + p.getName());
            }
            ui.pressEnterToContinue();

        }
    }

    private void removeProgram(Student s) {
        Program p = s.getProgram();
        if (p != null) {
            p.getStudentList().remove(s);
            s.setProgram(null);
            daoProgram.update(p);
            ui.outputString("Student removed from Program " + p.getName());
        } else {
            ui.outputString("Student not enrolled in a Program");
        }

    }
}
