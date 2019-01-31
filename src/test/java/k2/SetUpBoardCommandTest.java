package k2;

import k2.aggregate.Game;
import k2.command.SetupBoardCommand;
import k2.event.BoardSetUpEvent;
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
        fixture.given()
                .when(new SetupBoardCommand("GAME_ID"))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new BoardSetUpEvent("GAME_ID"));
    }
}
