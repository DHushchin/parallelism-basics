package Task_3;

public class Teacher {
    private final String name;
    private final String position;
    private final Journal journal;
    private static int nextId = 0;
    private final int id;
    private int week;

    public Teacher(String name, String position, Journal journal) {
        this.name = name;
        this.position = position;
        this.journal = journal;
        this.id = nextId++;
        week = 1;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

}
