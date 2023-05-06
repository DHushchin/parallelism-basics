package Task_3;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Journal {
    private final ArrayList<Group> groups;
    private final int weeks;
    // grades for each student for each week for each group (group, student, week)
    // grades[group][student][week]
    private final int[][][] grades;
    private final ReentrantLock lock = new ReentrantLock();

    public Journal(ArrayList<Group> groups, int weeks) {
        this.groups = groups;
        this.weeks = weeks;
        grades = new int[groups.size()][][];
        for (int groupIndex = 0; groupIndex < groups.size(); groupIndex++) {
            int numStudents = getStudentsNumber(groupIndex);
            grades[groupIndex] = new int[numStudents][weeks];
        }
    }

    public void addGrades(int[] gradesToAdd, int week, int groupIndex) {
        lock.lock();
        try {
            int numStudents = getStudentsNumber(groupIndex);
            for (int studentIndex = 0; studentIndex < numStudents; studentIndex++) {
                grades[groupIndex][studentIndex][week] = gradesToAdd[studentIndex];
            }
        } finally {
            lock.unlock();
        }
    }
    public int getWeeks() {
        return weeks;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }
    public int getStudentsNumber(int groupIndex) {
        return this.groups.get(groupIndex).getStudents().size();
    }
    public void printGrades() {
        lock.lock();
        try {
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
                for (int studentIndex = 0; studentIndex < getStudentsNumber(groupIndex); studentIndex++) {
                    System.out.print("Student " + studentIndex + ":  ");
                    for (int week = 0; week < weeks; week++) {
                        System.out.printf("%-10d", grades[groupIndex][studentIndex][week]);
                    }
                    System.out.println();
                }
                System.out.println();
            }
        } finally {
            lock.unlock();
        }
    }
}
