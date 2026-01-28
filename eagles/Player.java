/**
 * Simple Player model for football team demo.
 */
public class Player {
    private final int number;
    private final String name;
    private final String position;
    private final String nationality;

    public Player(int number, String name, String position, String nationality) {
        this.number = number;
        this.name = name;
        this.position = position;
        this.nationality = nationality;
    }

    public int getNumber() { return number; }
    public String getName() { return name; }
    public String getPosition() { return position; }
    public String getNationality() { return nationality; }

    @Override
    public String toString() {
        return String.format("#%d %s — %s (%s)", number, name, position, nationality);
    }
}
