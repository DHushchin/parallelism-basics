package Task_3;

import java.util.ArrayList;

public class Group {
    private final ArrayList<Student> students;
    private final String groupName;

    public Group(String groupName, ArrayList<Student> students) {
        this.students = students;
        this.groupName = groupName;
    }
    public ArrayList<Student> getStudents() {
        return students;
    }
}
