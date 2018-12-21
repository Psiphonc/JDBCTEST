package JDBCTEST;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        StudentRepository studentRepository = new StudentRepository();
        studentRepository.showStudentList();
        //studentRepository.removeStudent("2011064052");
        studentRepository.addStudent(new Student("2011064052", "周冬雨", "体育", 20));
        studentRepository.commitChanges();

    }
}
