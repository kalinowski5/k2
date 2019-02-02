package k2.event;

import k2.valueobject.GameId;
import k2.valueobject.PawnColor;
import k2.valueobject.Space;

public class ClimberMovedEvent {

    private final GameId gameId;
    private final PawnColor player;
    private final Space from;
    private final Space to;
    private final Integer movementPointsUsed;

    public ClimberMovedEvent(GameId gameId, PawnColor player, Space from, Space to, Integer movementPointsUsed) {
        this.gameId = gameId;
        this.player = player;
        this.from = from;
        this.to = to;
        this.movementPointsUsed = movementPointsUsed;
    }

    public PawnColor getPlayer() {
        return player;
    }

    public Space getTo() {
        return to;
    }

    public Integer getMovementPointsUsed() {
        return movementPointsUsed;
    }
}
