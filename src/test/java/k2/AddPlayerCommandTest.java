package k2;

import k2.aggregate.Game;
import k2.command.AddPlayerCommand;
import k2.event.BoardSetUpEvent;
import k2.event.PlayerAddedEvent;
import k2.exception.TooManyPlayersException;
import k2.valueobject.GameId;
import k2.valueobject.PawnColor;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

public class AddPlayerCommandTest {
    private FixtureConfiguration fixture;

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture(Game.class);
    }

    @Test
    public void addPlayer() {
        GameId gameId = new GameId("GAME_1");
        fixture.given(new BoardSetUpEvent(gameId))
                .when(new AddPlayerCommand(gameId, "John",  PawnColor.RED))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new PlayerAddedEvent(gameId,"John",  PawnColor.RED));
    }

    @Test
    public void tooManyPlayers() {
        GameId gameId = new GameId("GAME_2");
        fixture.given(
                    new BoardSetUpEvent(gameId),
                    new PlayerAddedEvent(gameId, "John",  PawnColor.RED),
                    new PlayerAddedEvent(gameId, "Kate",  PawnColor.BLUE)
                )
                .when(new AddPlayerCommand(gameId, "James",  PawnColor.GREEN))
                .expectException(TooManyPlayersException.class);
    }

//    @Test
//    public void sameColorPlayer() {
//          .expectException(ColorAlreadyUsedException.class);
//    }
}
