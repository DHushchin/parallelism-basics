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
