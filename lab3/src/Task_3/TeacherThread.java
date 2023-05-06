package Task_3;

public class TeacherThread extends Thread {
    Teacher teacher;
    int week;
    int groupIndex;
    private final Journal journal;
    private static final int MAX_GRADE = 10;


    public TeacherThread(Teacher teacher, Journal journal, int week, int groupIndex) {
        this.week = week;
        this.groupIndex = groupIndex;
        this.journal = journal;
        this.teacher = teacher;
    }

    public void run() {
        int numStudents = journal.getStudentsNumber(groupIndex);
        int[] grades = new int[numStudents];
        for (int studentIndex = 0; studentIndex < numStudents; studentIndex++) {
            grades[studentIndex] = (int) (Math.random() * MAX_GRADE) + 1;
        }
        journal.addGrades(grades, week - 1, groupIndex);
        System.out.printf("%-12s %-14s week %-8d group %-10d%n",
                teacher.getName(), teacher.getPosition(), week, groupIndex);
    }
}
