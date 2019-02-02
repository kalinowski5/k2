package k2.command;

import k2.valueobject.*;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class MoveClimberCommand
{
    @TargetAggregateIdentifier
    private final GameId gameId;
    private final PawnColor player;
    private final Space targetSpace;

    public MoveClimberCommand(GameId gameId, PawnColor player, Space targetSpace) {
        this.gameId = gameId;
        this.player = player;
        this.targetSpace = targetSpace;
    }

    public GameId getGameId() {
        return gameId;
    }
}
