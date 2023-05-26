package Task_3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Journal {
    private final ArrayList<Group> groups;
    private final int weeks;
    private final HashMap<String, ConcurrentHashMap<Integer, Integer>> grades = new HashMap<>();

    public Journal(ArrayList<Group> groups, int weeks) {
        this.groups = groups;
        this.weeks = weeks;
    }

    public void addGrade(int grade, int week, int groupIndex, int studentIndex) {
        Group group = groups.get(groupIndex);
        Student student = group.getStudent(studentIndex);
        String key = student.getId() + "-" + group.id();
        ConcurrentHashMap<Integer, Integer> weekGrades = grades.computeIfAbsent(key, k -> new ConcurrentHashMap<>());
        weekGrades.put(week, grade);
    }

    public int getGrade(int week, int groupIndex, int studentIndex) {
        Group group = groups.get(groupIndex);
        Student student = group.getStudent(studentIndex);
        String key = student.getId() + "-" + group.id();
        ConcurrentHashMap<Integer, Integer> weekGrades = grades.get(key);
        if (weekGrades != null) {
            return weekGrades.getOrDefault(week, -1);
        }
        return -1;
    }

    public int getWeeks() {
        return weeks;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public void printGrades() {
        System.out.println("\nJournal\n");

        for (Group group : groups) {
            System.out.println("Group " + group.id());

            for (int week = 1; week <= weeks; week++) {
                if (week == 1) {
                    System.out.printf("%-12s", "");
                }
                System.out.printf("Week %-5d", week);
            }

            System.out.println();

            for (Student student : group.students()) {
                System.out.print("Student " + student.getId() + ":  ");
                for (int week = 1; week <= weeks; week++) {
                    int grade = getGrade(week, group.id(), student.getId());
                    System.out.printf("%-10d", grade);
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}

