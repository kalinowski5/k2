package k2;

import k2.aggregate.Game;
import k2.command.StartGameCommand;
import k2.event.BoardSetUpEvent;
import k2.event.GameStartedEvent;
import k2.event.PlayerAddedEvent;
import k2.exception.GameAlreadyStartedException;
import k2.exception.NotEnoughPlayersException;
import k2.valueobject.GameId;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

public class StartGameCommandTest {
    private FixtureConfiguration fixture;

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture(Game.class);
    }

    @Test
    public void startGame() {
        GameId gameId = new GameId("GAME_1");
        fixture.given(
                    new BoardSetUpEvent(gameId),
                    new PlayerAddedEvent(gameId, "John", "red"),
                    new PlayerAddedEvent(gameId, "Kate", "blue")
                )
                .when(new StartGameCommand(gameId))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new GameStartedEvent(gameId));
    }

    @Test
    public void notEnoughPlayersToStart() {
        GameId gameId = new GameId("GAME_2");
        fixture.given(
                    new BoardSetUpEvent(gameId)
                )
                .when(new StartGameCommand(gameId))
                .expectException(NotEnoughPlayersException.class);
    }

    @Test
    public void gameAlreadyStarted() {
        GameId gameId = new GameId("GAME_3");
        fixture.given(
                    new BoardSetUpEvent(gameId),
                    new PlayerAddedEvent(gameId, "John", "red"),
                    new GameStartedEvent(gameId)
                )
                .when(new StartGameCommand(gameId))
                .expectException(GameAlreadyStartedException.class);
    }

}
