package Task_3;

import java.util.ArrayList;

public class TeacherThread extends Thread {
    Teacher teacher;
    ArrayList<Group> groups;
    private final Journal journal;


    public TeacherThread(Teacher teacher, Journal journal, ArrayList<Group> groups) {
        this.groups = groups;
        this.journal = journal;
        this.teacher = teacher;
    }

    public void run() {
        for (int week = 1; week <= journal.getWeeks(); week++) {

            for (int groupIndex = 0; groupIndex < groups.size(); groupIndex++) {

                int numStudents = journal.getGroups().get(groupIndex).getStudentsNumber();

                for (int studentIndex = 0; studentIndex < numStudents; studentIndex++) {
                    int grade = teacher.getGrade();
                    journal.addGrade(grade, week, groupIndex, studentIndex);

                    System.out.printf("%-12s %-14s week %-8d group %-10d student %-10d grade %-10d\n",
                            teacher.getName(), teacher.getPosition(), week, groupIndex, studentIndex, grade);
                    try {
                        Thread.sleep(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }

        }
    }
}
