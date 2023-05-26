package Task_3;

import java.util.ArrayList;

public record Group(int id, ArrayList<Student> students) {
    public Student getStudent(int id) {
        return students.get(id);
    }

    public int getStudentsNumber() {
        return students.size();
    }
}
