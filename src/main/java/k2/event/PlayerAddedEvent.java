package k2.event;

public class PlayerAddedEvent {

    private final String gameId;
    private final String name;
    private final String color;

    public PlayerAddedEvent(String gameId, String name, String color) {
        this.gameId = gameId;
        this.name = name;
        this.color = color;
    }

    public String getGameId() {
        return gameId;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }
}
