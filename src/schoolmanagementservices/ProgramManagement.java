package schoolmanagementservices;

import entities.Course;
import entities.Program;
import entities.Student;
import integration.DAO;
import integration.ProgramDAOImpl;
import static schoolmanagementservices.CourseManagement.daoCourse;
import static schoolmanagementservices.Main.course;
import static schoolmanagementservices.Main.student;
import static schoolmanagementservices.Main.ui;
import static schoolmanagementservices.StudentManagement.daoStudent;

public class ProgramManagement implements ManagementInterface<Program> {

    static DAO<Program> daoProgram = new ProgramDAOImpl();

    @Override
    public Program findByNameOrID() {
        String input = ui.inputString();
        if (input.isEmpty()) {
            ui.outputString("Cancelled, no input detected");
            return null;
        }
        Program p = daoProgram.findByNameOrID(input);

        if (p != null) {
            showDetailedInfo(p);
        } else {
            ui.outputString("No result found");
        }
        return p;
    }

    @Override
    public void showDetailedInfo(Program p) {
        ui.printResult(p);

        ui.outputString("\nCurrent Courses:");

        p.getCourseList().forEach(ui::printResult);

        ui.outputString("\nCurrent Students:");

        p.getStudentList().forEach(ui::printResult);
    }

    @Override
    public void showAll() {
        daoProgram.findAll().sorted((e1, e2) -> e1.getId() - e2.getId()).forEach(ui::printResult);
    }

    @Override
    public Program add() {
        ui.outputString("Enter Program name:");
        String name = ui.inputString();
        if (name.isEmpty()) {
            ui.outputString("Cancelled, no input detected");
            return null;
        } else if (daoProgram.findByName(name) != null) {
            ui.outputString("A Program with that name already exists, choose another name");
            return add();
        } else if (name.matches("^[0-9]\\d*$")) {
            ui.outputString("Program name cannot be only numbers, try again");
            return add();
        }
        ui.outputString("Enter Program Start Date (yyyy-mm-dd):");
        String startDate = ui.inputDate();
        ui.outputString("Enter Program End Date (yyyy-mm-dd):");
        String endDate = ui.inputDate();

        Program p = new Program(name, startDate, endDate);
        daoProgram.create(p);
        ui.outputString("Program created");
        return p;
    }

    @Override
    public void update(String choice) {
        ui.outputString("Enter Name or ID of Program to Update:");
        Program p = findByNameOrID();
        if (p == null) {
            if (choice.equals("addStudents") || choice.equals("addCourses")) {
                ui.pressEnterToContinue();
            }
            return;
        }

        switch (choice) {

            case "updateName":
                updateName(p);
                break;

            case "addStudents":
                addStudents(p);
                break;
            case "removeStudent":
                removeStudent(p);
                break;
            case "addCourses":
                addCourses(p);
                break;
            case "removeCourse":
                removeCourse(p);
                break;
            case "updateStartDate":
                ui.outputString("Enter new Start Date (yyyy-mm-dd):");
                p.setStartDate(ui.inputDate());
                ui.outputString("Program Start Date updated");
                break;
            case "updateEndDate":
                ui.outputString("Enter new End Date (yyyy-mm-dd):");
                p.setEndDate(ui.inputDate());
                ui.outputString("Program End Date updated");
                break;

        }

        daoProgram.update(p);

    }

    @Override
    public void updateName(Program p) {
        ui.outputString("Enter new Program name:");
        String name = ui.inputString();

        if (name.isEmpty()) {
            ui.outputString("Cancelled, no input detected");

        } else if (daoProgram.findByName(name) != null) {
            ui.outputString("A Program with that name already exists, choose another name");

        } else if (name.matches("^[0-9]\\d*$")) {
            ui.outputString("Program name cannot be only numbers, try again");

        } else {
            p.setName(name);
            ui.outputString("Program Name updated");
        }
    }

    @Override
    public void delete() {
        ui.outputString("Enter Name or ID of Program to Delete:");
        Program p = findByNameOrID();
        if (p != null) {
            daoProgram.delete(p);
            ui.outputString(p.getName() + " removed successfully from Programs");
        }
    }

    private void addStudents(Program p) {
        String input = "Y";

        while (input.equalsIgnoreCase("Y")) {
            ui.outputString("\nList of available Students:");
            student.showAll();
            ui.outputString("\nEnter Name or ID of Student to add to Program:");
            Student s = student.findByNameOrID();

            if (s == null) {
                ui.outputString("Create new Student? (Y/N)");
                input = ui.inputString();
                ui.clear();
                if (input.equalsIgnoreCase("Y")) {
                    s = student.add();
                } else {
                    return;
                }
            }
            if (s != null) {
                if (p.getStudentList().contains(s)) {
                    ui.outputString("\nStudent "+s.getName()+" already added");
                } else {
                    p.getStudentList().add(s);
                    s.setProgram(p);
                    daoStudent.update(s);
                    ui.outputString("\nStudent "+s.getName()+" added to Program " + p.getName());
                }
            }
            ui.outputString("Add another student? (Y/N)");

            input = ui.inputString();
            ui.clear();

        }
    }

    private void removeStudent(Program p) {
        ui.outputString("Enter Name or ID of Student to Remove from Program:");
        Student s = student.findByNameOrID();
        if (s != null) {
            p.getStudentList().remove(s);
            s.setProgram(null);
            daoStudent.update(s);
            ui.outputString("Student removed from Program " + p.getName());
        }

    }

    private void addCourses(Program p) {
        String input = "Y";

        while (input.equalsIgnoreCase("Y")) {
            ui.outputString("\nList of available Courses:");
            course.showAll();
            ui.outputString("\nEnter Name or ID of Course to add to Program:");
            Course c = course.findByNameOrID();

            if (c == null) {
                ui.outputString("Create new Course? (Y/N)");
                input = ui.inputString();
                ui.clear();
                if (input.equalsIgnoreCase("Y")) {
                    c = course.add();
                } else {
                    return;
                }
            }

            if (c != null && c.getProgram() == null) {
                String name = c.getName();
                if (p.getCourseList().stream().anyMatch(e -> e.getName().equalsIgnoreCase(name))) {
                    ui.outputString("\nA Course with that name already exists in Program " + p.getName());
                } else {
                    p.getCourseList().add(c);
                    c.setProgram(p);
                    daoCourse.update(c);
                    ui.outputString("\nCourse " + c.getName() + " added to Program " + p.getName());
                }
            } else if (c != null) {
                ui.outputString("Course " + c.getName() + " already added to another Program, cannot add to this Program");
            }

            ui.outputString("Add another course? (Y/N)");

            input = ui.inputString();
            ui.clear();

        }

    }

    private void removeCourse(Program p) {
        ui.outputString("Enter Name or ID of Course to Remove from Program:");
        Course c = course.findByNameOrID();
        if (c != null) {
            p.getCourseList().remove(c);
            c.setProgram(null);
            daoCourse.update(c);
            ui.outputString("Course removed from Program " + p.getName());
        }

    }
}
