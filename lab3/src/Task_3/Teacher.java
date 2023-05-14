package Task_3;

public record Teacher(String name, String position) {
    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public int getGrade() {
        return (int) (Math.random() * 10 + 1);
    }
}
