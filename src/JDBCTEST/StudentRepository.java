package JDBCTEST;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

class StudentRepository extends Repo<String> {
    ArrayList<Student> studentList;
    ArrayList<String> removeRecord;
    ArrayList<RepoItem> addRecord;

    @Override
    String getURL() {
        return "jdbc:sqlserver://localhost:1433;databaseName=Student";
    }

    @Override
    String getName() {
        return "sa";
    }

    @Override
    String getPassWord() {
        return "rRddvz5@A0Xy";
    }

    @Override
    String getDBMSType() {
        return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    }

    @Override
    String getSchemaName() {
        return "Student";
    }

    @Override
    ArrayList<String> getRemoveRecord() {
        return removeRecord;
    }

    @Override
    ArrayList<RepoItem> getAddRecord() {
        return addRecord;
    }

    @Override
    ArrayList<RepoItem> getEntitySet() {
        return new ArrayList<RepoItem>(studentList);
    }

    @Override
    public String[] getAttribute() {
        String[] ret = {"sno", "sname", "sdept", "sage"};
        return ret;
    }

    @Override
    public String getPrimaryKey() {
        return "sno";
    }

    StudentRepository() {
        connect();
        ResultSet resultSet = getResultSet();
        studentList = new ArrayList<>();
        while (true) {
            try {
                if (!resultSet.next()) break;
                String[] attribute = getAttribute();
                ArrayList<String> attrubuteList = new ArrayList<>();
                for (String atbt :
                        attribute) {
                    attrubuteList.add(resultSet.getString(atbt));

                }
                studentList.add(new Student(attrubuteList));
            } catch (SQLException e) {
                e.printStackTrace();
            }

            removeRecord = new ArrayList<>();
            addRecord = new ArrayList<>();
        }
        try {
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    public void showStudentList() {
        for (Student s :
                studentList) {
            System.out.println(s.toString());
        }
    }

    Student findStudent(String id) {
        return studentList.get(studentList.indexOf(new Student(id)));
    }

    void addStudent(Student stu) {
        studentList.add(stu);
        addRecord.add(stu);
    }

    void removeStudent(String id) {
        int idx = studentList.indexOf(new Student(id));
        if (idx == -1) return;
        studentList.remove(idx);
        removeRecord.add(id);
    }


}