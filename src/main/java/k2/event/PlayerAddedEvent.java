package k2.event;

import k2.valueobject.GameId;
import k2.valueobject.PawnColor;

public class PlayerAddedEvent {

    private final GameId gameId;
    private final String name;
    private final PawnColor color;

    public PlayerAddedEvent(GameId gameId, String name, PawnColor color) {
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

    public PawnColor getColor() {
        return color;
    }
}
