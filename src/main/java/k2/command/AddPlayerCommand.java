package k2.command;

import k2.valueobject.GameId;
import k2.valueobject.PawnColor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class AddPlayerCommand
{
    @TargetAggregateIdentifier
    private GameId gameId;
    private final String name;
    private final PawnColor color;

    public AddPlayerCommand(GameId gameId, String name, PawnColor color) {
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
