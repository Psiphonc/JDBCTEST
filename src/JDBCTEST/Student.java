package JDBCTEST;

import java.util.ArrayList;
import java.util.Objects;

public class Student extends RepoItem implements Comparable {
    boolean isChanged = false;
    String sno;
    String sdept;
    String sname;
    int sage;

    public Student(String id) {
        this.sno = id;
    }

    public Student(ArrayList<String> attributes) {
        this(attributes.get(0), attributes.get(1), attributes.get(2), Integer.parseInt(attributes.get(3)));
    }

    public Student(String sno, String sname, String sdept, int sage) {
        this.sno = sno;
        this.sdept = sdept;
        this.sname = sname;
        this.sage = sage;
        updateAttributes();
    }

    @Override
    String getPrimaryKey() {
        return "sno";
    }

    @Override
    void updateAttributes() {
        attributes.put("sno", "\'" + sno + "\'");
        attributes.put("sname", "\'" + sname + "\'");
        attributes.put("sdept", "\'" + sdept + "\'");
        attributes.put("sage", Integer.toString(sage));
    }

    @Override
    public void changeCommitted() {
        isChanged = false;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
        isChanged = true;
        updateAttributes();
    }

    public String getSdept() {
        return sdept;
    }

    public void setSdept(String sdept) {
        this.sdept = sdept;
        isChanged = true;
        updateAttributes();
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
        isChanged = true;
        updateAttributes();
    }

    public int getSage() {
        return sage;
    }

    public void setSage(int sage) {
        this.sage = sage;
        isChanged = true;
        updateAttributes();
    }

    @Override
    public boolean isChanged() {
        return isChanged;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(sno, student.sno);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sno);
    }

    @Override
    public String toString() {
        return "Student{" +
                "isChanged=" + isChanged +
                ", sno='" + sno + '\'' +
                ", sdept='" + sdept + '\'' +
                ", sname='" + sname + '\'' +
                ", sage=" + sage +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        return this.sno.compareTo(((Student) o).sno);
    }
}
