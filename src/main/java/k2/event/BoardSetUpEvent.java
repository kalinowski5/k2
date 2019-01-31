package k2.event;

import k2.valueobject.GameId;

public class BoardSetUpEvent {

    private final GameId id;

    public BoardSetUpEvent(GameId id) {
        this.id = id;
    }

    public GameId getId() {
        return id;
    }
}
