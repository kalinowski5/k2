package k2.command;

import k2.valueobject.GameId;

public class SetupBoardCommand
{
    private final GameId gameId;

    public SetupBoardCommand(GameId gameId) {
        this.gameId = gameId;
    }

    public GameId getId() {
        return gameId;
    }
}
