package k2;

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
