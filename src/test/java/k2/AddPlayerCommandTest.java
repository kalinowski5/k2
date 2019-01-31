package k2;

import k2.exception.TooManyPlayersException;
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
        fixture.given(new BoardSetUpEvent("GAME_ID"))
                .when(new AddPlayerCommand("GAME_ID", "John", "red"))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new PlayerAddedEvent("GAME_ID","John", "red"));
    }

    @Test
    public void tooManyPlayers() {
        fixture.given(
                    new BoardSetUpEvent("GAME_ID"),
                    new PlayerAddedEvent("GAME_ID", "John", "red"),
                    new PlayerAddedEvent("GAME_ID", "Kate", "blue")
                )
                .when(new AddPlayerCommand("GAME_ID", "James", "green"))
                .expectException(TooManyPlayersException.class);
    }
}
