package k2.event;

import k2.valueobject.GameId;
import k2.valueobject.PawnColor;
public class PassedEvent {

    private final GameId gameId;
    private final PawnColor player;

    public PassedEvent(GameId gameId, PawnColor player) {
        this.gameId = gameId;
        this.player = player;
    }

    public PawnColor getPlayer() {
        return player;
    }
}
