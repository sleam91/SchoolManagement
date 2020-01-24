package schoolmanagementservices;

import entities.Course;
import entities.Program;
import entities.Student;
import entities.Teacher;
import view.UI;

public class Main {

//    static UI ui = new view.UIScannerImpl();
    static UI ui = new view.UISwingImpl("School Management System");
    static ManagementInterface<Teacher> teacher = new TeacherManagement();
    static ManagementInterface<Course> course = new CourseManagement();
    static ManagementInterface<Student> student = new StudentManagement();
    static ManagementInterface<Program> program = new ProgramManagement();

    public static void main(String[] args) {
        
//        integration.PopulateDatabase.populateDatabase();

        while (true) {
            ui.outputString("-----------------------------------------");
            ui.outputString("School Management System");
            ui.outputString("-----------------------------------------");
            ui.outputString("1. Teacher Menu");
            ui.outputString("2. Course Menu");
            ui.outputString("3. Student Menu");
            ui.outputString("4. Program Menu");
            
            ui.outputString("0. Exit");
            ui.outputString("-----------------");
            int choice = ui.takeChoiceInput(4);
            ui.clear();
            switch (choice) {
                case 1:
                    teacherMenu();
                    break;
                case 2:
                    courseMenu();
                    break;
                case 3:
                    studentMenu();
                    break;
                case 4:
                    programMenu();
                    break;
                case 0:
                    ui.exit();
            }
            ui.clear();
        }
    }

    private static void teacherMenu() {
        while (true) {

            ui.outputString("---------------------");
            ui.outputString("Teacher Menu");
            ui.outputString("---------------------");
            ui.outputString("1. Find Teacher by Name/ID");
            ui.outputString("2. Show All Teachers");
            ui.outputString("3. Add New Teacher");
            ui.outputString("4. Update Teacher Name");
            ui.outputString("5. Add Courses to Teacher");
            ui.outputString("6. Remove Course from Teacher");
            ui.outputString("7. Delete Teacher");
            ui.outputString("0. Back to main menu");
            ui.outputString("-----------------");
            int choice = ui.takeChoiceInput(7);
            ui.clear();
            switch (choice) {
                case 1:
                    ui.outputString("Enter Name or ID of Teacher:");
                    teacher.findByNameOrID();
                    break;
                case 2:
                    teacher.showAll();
                    break;
                case 3:
                    teacher.add();
                    break;
                case 4:
                    teacher.update("updateName");
                    break;
                case 5:
                    teacher.update("addCourses");
                    break;
                case 6:
                    teacher.update("removeCourse");
                    break;
                case 7:
                    teacher.delete();
                    break;
                case 0:
                    return;
            }

            if (choice != 5) {
                ui.pressEnterToContinue();
            }

            ui.clear();
        }
    }

    private static void courseMenu() {
        while (true) {
            ui.outputString("---------------------");
            ui.outputString("Course Menu");
            ui.outputString("---------------------");
            ui.outputString("1. Find Course by Name/ID");
            ui.outputString("2. Show All Courses");
            ui.outputString("3. Add New Course");
            ui.outputString("4. Update Course Name");
            ui.outputString("5. Add Teachers to Course");
            ui.outputString("6. Remove Teacher from Course");
            ui.outputString("7. Add/Change Program from Course");
            ui.outputString("8. Remove Program from Course");
            ui.outputString("9. Change Start Date of Course");
            ui.outputString("10. Change End Date of Course");
            ui.outputString("11. Delete Course");
            ui.outputString("0. Back to main menu");
            ui.outputString("-----------------");
            int choice = ui.takeChoiceInput(11);
            ui.clear();
            switch (choice) {

                case 1:
                    ui.outputString("Enter Name or ID of Course:");
                    course.findByNameOrID();
                    break;
                case 2:
                    course.showAll();
                    break;
                case 3:
                    course.add();
                    break;
                case 4:
                    course.update("updateName");
                    break;
                case 5:
                    course.update("addTeachers");
                    break;
                case 6:
                    course.update("removeTeacher");
                    break;
                case 7:
                    course.update("addProgram");
                    break;
                case 8:
                    course.update("removeProgram");
                    break;
                case 9:
                    course.update("updateStartDate");
                    break;
                case 10:
                    course.update("updateEndDate");
                    break;
                case 11:
                    course.delete();
                    break;
                case 0:
                    return;

            }

            if (choice != 5 && choice != 7) {
                ui.pressEnterToContinue();
            }
            ui.clear();
        }
    }

    private static void studentMenu() {
        while (true) {
            ui.outputString("---------------------");
            ui.outputString("Student Menu");
            ui.outputString("---------------------");
            ui.outputString("1. Find Student by Name/ID");
            ui.outputString("2. Show All Students");
            ui.outputString("3. Add New Student");
            ui.outputString("4. Update Student Name");
            ui.outputString("5. Add/Change Program from Student");
            ui.outputString("6. Remove Program from Student");
            ui.outputString("7. Delete Student");
            ui.outputString("0. Back to main menu");
            ui.outputString("-----------------");

            int choice = ui.takeChoiceInput(7);
            ui.clear();
            switch (choice) {

                case 1:
                    ui.outputString("Enter Name or ID of Student:");
                    student.findByNameOrID();
                    break;
                case 2:
                    student.showAll();
                    break;
                case 3:
                    student.add();
                    break;
                case 4:
                    student.update("updateName");
                    break;
                case 5:
                    student.update("addProgram");
                    break;
                case 6:
                    student.update("removeProgram");
                    break;
                case 7:
                    student.delete();
                    break;
                case 0:
                    return;
            }

            if (choice != 5) {
                ui.pressEnterToContinue();
            }

            ui.clear();
        }
    }

    private static void programMenu() {
        while (true) {
            ui.clear();
            ui.outputString("---------------------");
            ui.outputString("Program Menu");
            ui.outputString("---------------------");
            ui.outputString("1. Find Program by Name/ID");
            ui.outputString("2. Show All Programs");
            ui.outputString("3. Add New Program");
            ui.outputString("4. Update Program Name");
            ui.outputString("5. Add Students to Program");
            ui.outputString("6. Remove Student from Program");
            ui.outputString("7. Add Courses to Program");
            ui.outputString("8. Remove Course from Program");
            ui.outputString("9. Change Start Date of Program");
            ui.outputString("10. Change End Date of Program");
            ui.outputString("11. Delete Program");
            ui.outputString("0. Back to main menu");
            ui.outputString("-----------------");

            int choice = ui.takeChoiceInput(11);
            ui.clear();
            switch (choice) {

                case 1:
                    ui.outputString("Enter Name or ID of Program:");
                    program.findByNameOrID();
                    break;
                case 2:
                    program.showAll();
                    break;
                case 3:
                    program.add();
                    break;
                case 4:
                    program.update("updateName");
                    break;
                case 5:
                    program.update("addStudents");
                    break;
                case 6:
                    program.update("removeStudent");
                    break;
                case 7:
                    program.update("addCourses");
                    break;
                case 8:
                    program.update("removeCourse");
                    break;
                case 9:
                    program.update("updateStartDate");
                    break;
                case 10:
                    program.update("updateEndDate");
                    break;
                case 11:
                    program.delete();
                    break;
                case 0:
                    return;

            }

            if (choice != 5 && choice != 7) {
                ui.pressEnterToContinue();
            }
            ui.clear();
        }
    }

}
