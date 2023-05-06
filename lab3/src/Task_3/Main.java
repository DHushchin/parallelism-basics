package Task_3;

import java.util.ArrayList;

public class Main {

    public static ArrayList<Teacher> createTeachers(Journal journal) {
        ArrayList<Teacher> teachers = new ArrayList<>();
        Teacher lecturer = new Teacher("John", "Lecturer", journal);
        Teacher assistant_1 = new Teacher("Jane", "Assistant", journal);
        Teacher assistant_2 = new Teacher("Jack", "Assistant", journal);
        Teacher assistant_3 = new Teacher("Jill", "Assistant", journal);

        teachers.add(lecturer);
        teachers.add(assistant_1);
        teachers.add(assistant_2);
        teachers.add(assistant_3);
        return teachers;
    }

    public static ArrayList<Student> createStudents(int numStudents) {
        ArrayList<Student> students = new ArrayList<>();
        for (int i = 0; i < numStudents; i++) {
            students.add(new Student("Surname " + i, i));
        }
        return students;
    }

    public static ArrayList<Group> createGroups(int numGroups, int numStudents) {
        ArrayList<Group> groups = new ArrayList<>();
        for (int i = 0; i < numGroups; i++) {
            groups.add(new Group("Group " + i, createStudents(numStudents)));
        }
        return groups;
    }

    public static void main(String[] args) {
        ArrayList<Group> groups = createGroups(3, 5);
        Journal journal = new Journal(groups, 10);
        ArrayList<Teacher> teachers = createTeachers(journal);
        ArrayList<TeacherThread> teacherThreads = new ArrayList<>();

        System.out.printf("%-12s %-14s %-13s %-10s\n", "Teacher", "Group", "Week", "Group");
        for (int week = 1; week <= journal.getWeeks(); week++) {
            for (int groupIndex = 0; groupIndex < journal.getGroups().size(); groupIndex++) {
                for (Teacher teacher : teachers) {
                    TeacherThread teacherThread = new TeacherThread(teacher, journal, week, groupIndex);
                    teacherThreads.add(teacherThread);
                }
            }
        }

        for (TeacherThread teacherThread : teacherThreads) {
            teacherThread.start();
        }

        for (TeacherThread teacherThread : teacherThreads) {
            try {
                teacherThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        journal.printGrades();
    }
}
