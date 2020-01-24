package integration;

import entities.Course;
import entities.Program;
import entities.Student;
import entities.Teacher;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class PopulateDatabase {
    
//    public static void main(String[] args) {
//        populateDatabase();
//    }

    public static void populateDatabase() {

        EntityManagerFactory emf = Factory.emf;
        EntityManager em = emf.createEntityManager();
        jdbcInsertData();
        Random r = new Random();
        em.getTransaction().begin();
        Stream<Student> listStudent = em.createQuery("from Student s", Student.class).getResultStream();

        listStudent.forEach(s -> s.setProgram(em.find(Program.class, (r.nextInt(6) + 1))));
        em.getTransaction().commit();

        em.getTransaction().begin();
        Stream<Course> listCourse = em.createQuery("from Course c", Course.class).getResultStream();
        listCourse.forEach(c -> c.getTeacherList().add(em.find(Teacher.class, (r.nextInt(10) + 1))));
        em.getTransaction().commit();

        em.getTransaction().begin();
        for (int i = 0; i < 3; i++) {

            Stream<Teacher> listTeacher = em.createQuery("from Teacher s", Teacher.class).getResultStream();
            listTeacher.forEach(t -> {
                int rn = r.nextInt(59) + 1;
                Course c = em.find(Course.class, rn);
                if (!t.getCourseList().contains(c)) {
                    t.getCourseList().add(c);
                    c.getTeacherList().add(t);
                }
            });
        }
        em.getTransaction().commit();

        em.close();
    }

    private static void jdbcInsertData() {
        try {
            Connection connection
                    = DriverManager.getConnection("jdbc:mysql://localhost:3306/schoolmanagement?allowMultiQueries=true", "root", "root");

            Statement stmt = connection.createStatement();

            String insertSQL = "insert into teacher values('1','Bita Jabbari');"
                    + "insert into teacher values('2','Ulf Bilting');"
                    + "insert into teacher values('3','Anders Andersson');"
                    + "insert into teacher values('4','Heidi Karlsson');"
                    + "insert into teacher values('5','Karl-Johan Söder');"
                    + "insert into teacher values('6','Frida Nilsson');"
                    + "insert into teacher values('7','Olle Brorsson');"
                    + "insert into teacher values('8','William Hansson');"
                    + "insert into teacher values('9','Anna Jakobsson');"
                    + "insert into teacher values('10','Monika Håkansson');"
                    + "insert into student values('1','Sebastian Alvarez',null);"
                    + "insert into student values('2','Joel Ståldal',null);"
                    + "insert into student values('3','Caspar Sylwan',null);"
                    + "insert into student values('4','Heidi Fransson',null);"
                    + "insert into student values('5','Tyra Söderström',null);"
                    + "insert into student values('6','Arvin Nilsson',null);"
                    + "insert into student values('7','Julia Öberg',null);"
                    + "insert into student values('8','Mateo Hansson',null);"
                    + "insert into student values('9','Vincent Jakobsson',null);"
                    + "insert into student values('10','Adrian Håkansson',null);"
                    + "insert into student values('11','Philip Norberg',null);"
                    + "insert into student values('12','Egon Gunnarsson',null);"
                    + "insert into student values('13','Alfons Mårtensson',null);"
                    + "insert into student values('14','Jessica Persson',null);"
                    + "insert into student values('15','Jakob Isaksson',null);"
                    + "insert into student values('16','Svante Persson',null);"
                    + "insert into student values('17','Robin Månsson',null);"
                    + "insert into student values('18','Nemi Nilsson',null);"
                    + "insert into student values('19','Stefan Jonasson',null);"
                    + "insert into student values('20','Betty Dahlberg',null);"
                    + "insert into student values('21','Marion Söderström',null);"
                    + "insert into student values('22','Maria Danielsson',null);"
                    + "insert into student values('23','Yasmin Svensson',null);"
                    + "insert into student values('24','Azra Bergman',null);"
                    + "insert into student values('25','Alfons Sandström',null);"
                    + "insert into student values('26','Nima Björk',null);"
                    + "insert into student values('27','Paulina Söderberg',null);"
                    + "insert into student values('28','Gustaf Björk',null);"
                    + "insert into student values('29','Albin Sjögren',null);"
                    + "insert into student values('30','Lucas Blomqvist',null);"
                    + "insert into student values('31','Jason Hermansson',null);"
                    + "insert into student values('32','Anita Lindgren',null);"
                    + "insert into student values('33','Abdullah Jönsson',null);"
                    + "insert into student values('34','Edvard Arvidsson',null);"
                    + "insert into student values('35','Susanna Johansson',null);"
                    + "insert into student values('36','Emilio Eliasson',null);"
                    + "insert into student values('37','Moses Sjöberg',null);"
                    + "insert into student values('38','Tamara Bergqvist',null);"
                    + "insert into student values('39','Kevin Holmberg',null);"
                    + "insert into student values('40','Leo Pettersson',null);"
                    + "insert into student values('41','Edward Bergström',null);"
                    + "insert into student values('42','Jill Lundin',null);"
                    + "insert into student values('43','Nicolai Eriksson',null);"
                    + "insert into student values('44','Aron Lindgren',null);"
                    + "insert into student values('45','Malte Lind',null);"
                    + "insert into student values('46','Emmi Norberg',null);"
                    + "insert into student values('47','Kimi Holmberg',null);"
                    + "insert into student values('48','Lo Månsson',null);"
                    + "insert into student values('49','Liam Fransson',null);"
                    + "insert into student values('50','Karl Mattsson',null);"
                    + "insert into student values('51','Johanna Hellström',null);"
                    + "insert into student values('52','Malvina Lindberg',null);"
                    + "insert into student values('53','Casper Axelsson',null);"
                    + "insert into student values('54','Narin Danielsson',null);"
                    + "insert into student values('55','Frej Wallin',null);"
                    + "insert into student values('56','Dina Nilsson',null);"
                    + "insert into student values('57','Disa Wallin',null);"
                    + "insert into student values('58','Benjamin Bergström',null);"
                    + "insert into student values('59','Melwin Nordin',null);"
                    + "insert into student values('60','Ellen Isaksson',null);"
                    + "insert into student values('61','My Söderström',null);"
                    + "insert into student values('62','Nelly Jansson',null);"
                    + "insert into student values('63','Chanelle Sundström',null);"
                    + "insert into student values('64','Melina Nyström',null);"
                    + "insert into student values('65','Medina Lund',null);"
                    + "insert into student values('66','Astrid Karlsson',null);"
                    + "insert into student values('67','Theo Petersson',null);"
                    + "insert into student values('68','Tom Gunnarsson',null);"
                    + "insert into student values('69','Timmy Sundberg',null);"
                    + "insert into student values('70','Janina Danielsson',null);"
                    + "insert into student values('71','Aleksandar Lindholm',null);"
                    + "insert into student values('72','Nathan Gustafsson',null);"
                    + "insert into student values('73','Ayub Svensson',null);"
                    + "insert into student values('74','Katarina Blom',null);"
                    + "insert into student values('75','Emil Berglund',null);"
                    + "insert into student values('76','Magnus Martinsson',null);"
                    + "insert into student values('77','Izabella Bengtsson',null);"
                    + "insert into student values('78','Edward Söderberg',null);"
                    + "insert into student values('79','Azra Strömberg',null);"
                    + "insert into student values('80','Ninni Öberg',null);"
                    + "insert into student values('81','Mattis Lind',null);"
                    + "insert into student values('82','Malva Forsberg',null);"
                    + "insert into student values('83','Ville Hansson',null);"
                    + "insert into student values('84','Villiam Jonasson',null);"
                    + "insert into student values('85','Zacharias Håkansson',null);"
                    + "insert into student values('86','Lorin Öberg',null);"
                    + "insert into student values('87','Albina Samuelsson',null);"
                    + "insert into student values('88','Zebastian Åberg',null);"
                    + "insert into student values('89','Najma Sjöberg',null);"
                    + "insert into student values('90','Bastian Lundqvist',null);"
                    + "insert into student values('91','Jamie Nordin',null);"
                    + "insert into student values('92','Kajsa Olofsson',null);"
                    + "insert into student values('93','Markus Holmberg',null);"
                    + "insert into student values('94','Henrik Lundgren',null);"
                    + "insert into student values('95','Vigor Hellström',null);"
                    + "insert into student values('96','Nellie Ivarsson',null);"
                    + "insert into student values('97','Natali Magnusson',null);"
                    + "insert into student values('98','Henny Nordström',null);"
                    + "insert into student values('99','Zacharias Gustavsson',null);"
                    + "insert into student values('100','Casper Gustavsson',null);"
                    + "insert into program values(1,'2022-06-12','Javautvecklare','2020-08-31');"
                    + "insert into program values(2,'2021-12-12','Mjukvarutestare', '2020-09-07');"
                    + "insert into program values(3,'2022-06-05','Net-utvecklare', '2020-08-24');"
                    + "insert into program values(4,'2022-05-02','Webbutvecklare', '2020-08-03');"
                    + "insert into program values(5,'2022-12-18','Frontendutvecklare', '2021-01-11');"
                    + "insert into program values(6,'2020-12-04','IT-projektledare', '2021-01-18');"
                    + "insert into course values(1,'2020-06-02','Java programmering','2020-02-02',1);"
                    + "insert into course values(2,'2020-06-02','Agil utveckling','2020-02-02',1);"
                    + "insert into course values(3,'2020-06-02','Clean code och testbar kod','2020-02-02',1);"
                    + "insert into course values(4,'2020-06-02','Utveckling mot databas och databasadministration','2020-02-02',1);"
                    + "insert into course values(5,'2020-06-02','Javaverktyg och byggmiljöer','2020-02-02',1);"
                    + "insert into course values(6,'2020-06-02','Web services och integrationer','2020-02-02',1);"
                    + "insert into course values(7,'2020-06-02','Utveckling av webbapplikationer','2020-02-02',1);"
                    + "insert into course values(8,'2020-06-02','Complex Javaprogramming','2020-02-02',1);"
                    + "insert into course values(9,'2020-06-02','LIA 1','2020-02-02',1);"
                    + "insert into course values(10,'2020-06-02','LIA 2','2020-02-02',1);"
                    + "insert into course values(11,'2020-06-02','Examensarbete','2020-02-02',1);"
                    + "insert into course values(12,'2020-06-02','Testmetoder och testadministration','2020-02-02',2);"
                    + "insert into course values(13,'2020-06-02','Programmering för testare','2020-02-02',2);"
                    + "insert into course values(14,'2020-06-02','Kravhantering','2020-02-02',2);"
                    + "insert into course values(15,'2020-06-02','Databashantering','2020-02-02',2);"
                    + "insert into course values(16,'2020-06-02','Automatiserad testning','2020-02-02',2);"
                    + "insert into course values(17,'2020-06-02','Agil utveckling','2020-02-02',2);"
                    + "insert into course values(18,'2020-06-02','Testprocesser i praktiken','2020-02-02',2);"
                    + "insert into course values(19,'2020-06-02','LIA','2020-02-02',2);"
                    + "insert into course values(20,'2020-06-02','Examensarbete','2020-02-02',2);"
                    + "insert into course values(21,'2020-06-02','Agil utveckling','2020-02-02',3);"
                    + "insert into course values(22,'2020-06-02','Avancerad programmering med C#','2020-02-02',3);"
                    + "insert into course values(23,'2020-06-02','Clean code och testbar code','2020-02-02',3);"
                    + "insert into course values(24,'2020-06-02','Examensarbete','2020-02-02',3);"
                    + "insert into course values(25,'2020-06-02','Kundförståelse, konsultmässighet och rapportering','2020-02-02',3);"
                    + "insert into course values(26,'2020-06-02','LIA 1','2020-02-02',3);"
                    + "insert into course values(27,'2020-06-02','LIA 2','2020-02-02',3);"
                    + "insert into course values(28,'2020-06-02','Programmering med C#','2020-02-02',3);"
                    + "insert into course values(29,'2020-06-02','Utveckling mot databas och databasadministration','2020-02-02',3);"
                    + "insert into course values(30,'2020-06-02','Agil utveckling','2020-02-02',4);"
                    + "insert into course values(31,'2020-06-02','Avancerad webbdesign','2020-02-02',4);"
                    + "insert into course values(32,'2020-06-02','CMS utveckling 1','2020-02-02',4);"
                    + "insert into course values(33,'2020-06-02','CMS utveckling 2','2020-02-02',4);"
                    + "insert into course values(34,'2020-06-02','Examensarbete','2020-02-02',4);"
                    + "insert into course values(35,'2020-06-02','LIA 1','2020-02-02',4);"
                    + "insert into course values(36,'2020-06-02','LIA 2','2020-02-02',4);"
                    + "insert into course values(37,'2020-06-02','PHP, Databaser och versionshantering','2020-02-02',4);"
                    + "insert into course values(38,'2020-06-02','Webbutveckling med JavaScript','2020-02-02',4);"
                    + "insert into course values(39,'2020-06-02','Agil utveckling','2020-02-02',5);"
                    + "insert into course values(40,'2020-06-02','CSM','2020-02-02',5);"
                    + "insert into course values(41,'2020-06-02','Databaser','2020-02-02',5);"
                    + "insert into course values(42,'2020-06-02','Design och frontend-utveckling med Javascript','2020-02-02',5);"
                    + "insert into course values(43,'2020-06-02','Effektiv frontend-utveckling','2020-02-02',5);"
                    + "insert into course values(44,'2020-06-02','Examensarbete','2020-02-02',5);"
                    + "insert into course values(45,'2020-06-02','Grafisk design och UX-design','2020-02-02',5);"
                    + "insert into course values(46,'2020-06-02','HTML och CSS','2020-02-02',5);"
                    + "insert into course values(47,'2020-06-02','JavaScript','2020-02-02',5);"
                    + "insert into course values(48,'2020-06-02','Kommunikation med kravställare, gruppdynamik och projektstyrning','2020-02-02',5);"
                    + "insert into course values(49,'2020-06-02','Software life cycle management','2020-02-02',5);"
                    + "insert into course values(50,'2020-06-02','LIA1','2020-02-02',5);"
                    + "insert into course values(51,'2020-06-02','LIA2','2020-02-02',5);"
                    + "insert into course values(52,'2020-06-02','PHP','2020-02-02',5);"
                    + "insert into course values(53,'2020-06-02','Agil projektledning','2020-02-02',6);"
                    + "insert into course values(54,'2020-06-02','Examensarbete','2020-02-02',6);"
                    + "insert into course values(55,'2020-06-02','IT-projektledning','2020-02-02',6);"
                    + "insert into course values(56,'2020-06-02','Kundförståelse och konsultmässighet','2020-02-02',6);"
                    + "insert into course values(57,'2020-06-02','LIA1','2020-02-02',6);"
                    + "insert into course values(58,'2020-06-02','Projektledning, projektmodeller och grupp-processer','2020-02-02',6);"
                    + "insert into course values(59,'2020-06-02','Projektverktyg och dokumentation','2020-02-02',6);";

            Stream.of(insertSQL.split(";")).forEach(sqlStatement -> {
                try {
                    stmt.addBatch(sqlStatement);
                } catch (SQLException ex) {
                    System.out.println("Something went wrong");
                }
            });
            stmt.executeBatch();

        } catch (SQLException ex) {
            System.out.println("Something went wrong");
            ex.printStackTrace();
        }
    }

}
