package Task_3;

import java.util.ArrayList;

public class Main {

    public static ArrayList<Teacher> createTeachers() {
        ArrayList<Teacher> teachers = new ArrayList<>();
        Teacher lecturer = new Teacher("John", "Lecturer");
        Teacher assistant_1 = new Teacher("Jane", "Assistant");
        Teacher assistant_2 = new Teacher("Jack", "Assistant");
        Teacher assistant_3 = new Teacher("Jill", "Assistant");

        teachers.add(lecturer);
        teachers.add(assistant_1);
        teachers.add(assistant_2);
        teachers.add(assistant_3);
        return teachers;
    }

    public static ArrayList<Student> createStudents(int numStudents) {
        ArrayList<Student> students = new ArrayList<>();
        for (int i = 0; i < numStudents; i++) {
            students.add(new Student("Student " + i, i));
        }
        return students;
    }

    public static ArrayList<Group> createGroups(int numGroups, int numStudents) {
        ArrayList<Group> groups = new ArrayList<>();
        for (int i = 0; i < numGroups; i++) {
            groups.add(new Group(i, createStudents(numStudents)));
        }
        return groups;
    }

    public static void main(String[] args) {
        ArrayList<Group> groups = createGroups(3, 4);
        Journal journal = new Journal(groups, 8);
        ArrayList<Teacher> teachers = createTeachers();
        ArrayList<TeacherThread> teacherThreads = new ArrayList<>();

        System.out.printf("%-12s %-14s %-13s %-16s %-18s %-10s\n",
                "Teacher", "Position", "Week", "Group", "Student", "Grade");
        for (int week = 0; week < journal.getWeeks(); week++) {
            for (int groupIndex = 0; groupIndex < groups.size(); groupIndex++) {
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
