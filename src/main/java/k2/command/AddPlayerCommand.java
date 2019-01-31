package k2.command;

import k2.valueobject.GameId;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class AddPlayerCommand
{
    @TargetAggregateIdentifier
    private GameId gameId;
    private final String name;
    private final String color;

    public AddPlayerCommand(GameId gameId, String name, String color) {
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

    public String getColor() {
        return color;
    }
}
