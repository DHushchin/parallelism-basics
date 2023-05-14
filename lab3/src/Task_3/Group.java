package Task_3;

import java.util.ArrayList;

public class Group {
    private final ArrayList<Student> students;
    private final int id;

    public Group(int id, ArrayList<Student> students) {
        this.students = students;
        this.id = id;
    }
    public Student getStudent(int id) {
        return students.get(id);
    }

    public int getStudentsNumber() {
        return students.size();
    }

    public int getId() {
        return id;
    }
}
