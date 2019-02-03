package k2.command;

import k2.valueobject.GameId;
import k2.valueobject.PawnColor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class PassCommand
{
    @TargetAggregateIdentifier
    private final GameId gameId;
    private final PawnColor player;

    public PassCommand(GameId gameId, PawnColor player) {
        this.gameId = gameId;
        this.player = player;
    }

    public GameId getGameId() {
        return gameId;
    }

    public PawnColor getPlayer() {
        return player;
    }
}
