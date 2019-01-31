package k2;

public class PlayerAddedEvent {

    private final String name;
    private final String color;

    public PlayerAddedEvent(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }
}
