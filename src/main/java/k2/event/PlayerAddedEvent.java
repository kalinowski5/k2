package k2.event;

import k2.valueobject.GameId;

public class PlayerAddedEvent {

    private final GameId gameId;
    private final String name;
    private final String color;

    public PlayerAddedEvent(GameId gameId, String name, String color) {
        this.gameId = gameId;
        this.name = name;
        this.color = color;
    }

    public GameId getGameId() {
        return gameId;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }
}
