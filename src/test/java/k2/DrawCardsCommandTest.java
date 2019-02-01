package k2;

import k2.aggregate.Game;
import k2.command.DrawCardsCommand;
import k2.event.BoardSetUpEvent;
import k2.event.CardDrawnEvent;
import k2.event.GameStartedEvent;
import k2.event.PlayerAddedEvent;
import k2.valueobject.GameId;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

public class DrawCardsCommandTest {
    private FixtureConfiguration fixture;

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture(Game.class);
    }

    @Test
    public void drawCardsInNewGame() {
        GameId gameId = new GameId("GAME_1");
        fixture.given(
                    new BoardSetUpEvent(gameId),
                    new PlayerAddedEvent(gameId, "John", "red"),
                    new PlayerAddedEvent(gameId, "Kate", "blue"),
                    new GameStartedEvent(gameId)
                )
                .when(new DrawCardsCommand(gameId, "Kate"))
                .expectSuccessfulHandlerExecution()
                .expectEvents(
                        new CardDrawnEvent(gameId, "Kate", "move", 2),
                        new CardDrawnEvent(gameId, "Kate", "move", 2),
                        new CardDrawnEvent(gameId, "Kate", "move", 2),
                        new CardDrawnEvent(gameId, "Kate", "move", 2),
                        new CardDrawnEvent(gameId, "Kate", "move", 2),
                        new CardDrawnEvent(gameId, "Kate", "move", 2)
                );
    }



}
