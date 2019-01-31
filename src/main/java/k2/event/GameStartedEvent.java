package k2.event;

import k2.valueobject.GameId;

public class GameStartedEvent {

    private final GameId gameId;

    public GameStartedEvent(GameId gameId) {
        this.gameId = gameId;
    }

    public GameId getGameId() {
        return gameId;
    }
}
