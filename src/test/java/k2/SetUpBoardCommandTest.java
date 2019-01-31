package k2;

import k2.aggregate.Game;
import k2.command.SetupBoardCommand;
import k2.event.BoardSetUpEvent;
import k2.valueobject.GameId;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

public class SetUpBoardCommandTest {
    private FixtureConfiguration fixture;

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture(Game.class);
    }

    @Test
    public void setUpGameBoard() {
        GameId gameId = new GameId("GAME_1");
        fixture.given()
                .when(new SetupBoardCommand(gameId))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new BoardSetUpEvent(gameId));
    }
}
