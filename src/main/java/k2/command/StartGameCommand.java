package k2.command;

import k2.valueobject.GameId;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class StartGameCommand
{
    @TargetAggregateIdentifier
    private final GameId gameId;

    public StartGameCommand(GameId gameId) {
        this.gameId = gameId;
    }

    public GameId getId() {
        return gameId;
    }
}
