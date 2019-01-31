package hello;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class AddPlayerCommand
{
    @TargetAggregateIdentifier
    private String gameId;
    private final String name;
    private final String color;

    public AddPlayerCommand(String gameId, String name, String color) {
        this.gameId = gameId;
        this.name = name;
        this.color = color;
    }

    public String getGameId() {
        return gameId;
    }


    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }
}
