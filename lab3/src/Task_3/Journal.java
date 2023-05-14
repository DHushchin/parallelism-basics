package Task_3;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class Journal {
    private final ArrayList<Group> groups;
    private final int weeks;
    // ConcurrentHashMap for grades of each student (week, group, student)
    private final ConcurrentHashMap<String, Integer> grades = new ConcurrentHashMap<>();

    public Journal(ArrayList<Group> groups, int weeks) {
        this.groups = groups;
        this.weeks = weeks;
    }

    public void addGrade(int grade, int week, int groupIndex, int studentIndex) {
        String key = getKey(week, groupIndex, studentIndex);
        grades.put(key, grade);
    }

    public int getGrade(int week, int groupIndex, int studentIndex) {
        String key = getKey(week, groupIndex, studentIndex);
        Integer grade = grades.get(key);
        return grade != null ? grade : -1; // return -1 if grade is not set
    }

    private String getKey(int week, int groupIndex, int studentIndex) {
        int groupId = groups.get(groupIndex).getId();
        int studentId = groups.get(groupIndex).getStudent(studentIndex).getId();
        return week + "," + groupId + "," + studentId;
    }
    public int getWeeks() {
        return weeks;
    }
    public ArrayList<Group> getGroups() {
        return groups;
    }
    public void printGrades() {
        System.out.println("\nJournal\n");

        for (int groupIndex = 0; groupIndex < groups.size(); groupIndex++) {

            System.out.println("Group " + groupIndex);

            for (int week = 1; week <= weeks; week++) {
                if (week == 1) {
                    System.out.printf("%-12s", "");
                }
                System.out.printf("Week %-5d", week);
            }

            System.out.println();

            for (int studentIndex = 0; studentIndex < groups.get(groupIndex).getStudentsNumber(); studentIndex++) {
                System.out.print("Student " + studentIndex + ":  ");
                for (int week = 0; week < weeks; week++) {
                    System.out.printf("%-10d", getGrade(week, groupIndex, studentIndex));
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}
