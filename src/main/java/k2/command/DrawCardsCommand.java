package k2.command;

import k2.valueobject.GameId;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class DrawCardsCommand
{
    @TargetAggregateIdentifier
    private GameId gameId;
    private final String player;

    public DrawCardsCommand(GameId gameId, String player) {
        this.gameId = gameId;
        this.player = player;
    }

    public GameId getGameId() {
        return gameId;
    }

    public String getPlayer() {
        return player;
    }
}
