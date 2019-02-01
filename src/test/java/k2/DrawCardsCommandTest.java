package k2;

import k2.aggregate.Game;
import k2.command.DrawCardsCommand;
import k2.event.BoardSetUpEvent;
import k2.event.CardDrawnEvent;
import k2.event.GameStartedEvent;
import k2.event.PlayerAddedEvent;
import k2.exception.GameNotStartedException;
import k2.valueobject.CardType;
import k2.valueobject.GameId;
import k2.valueobject.PawnColor;
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
                    new PlayerAddedEvent(gameId, "John",  PawnColor.RED),
                    new PlayerAddedEvent(gameId, "Kate",  PawnColor.BLUE),
                    new GameStartedEvent(gameId)
                )
                .when(new DrawCardsCommand(gameId, PawnColor.BLUE))
                .expectSuccessfulHandlerExecution()
                .expectEvents(
                        new CardDrawnEvent(gameId, PawnColor.BLUE, CardType.MOVEMENT, 2),
                        new CardDrawnEvent(gameId, PawnColor.BLUE, CardType.MOVEMENT, 2),
                        new CardDrawnEvent(gameId, PawnColor.BLUE, CardType.MOVEMENT, 2),
                        new CardDrawnEvent(gameId, PawnColor.BLUE, CardType.MOVEMENT, 2),
                        new CardDrawnEvent(gameId, PawnColor.BLUE, CardType.MOVEMENT, 2),
                        new CardDrawnEvent(gameId, PawnColor.BLUE, CardType.MOVEMENT, 2)
                );
    }

    @Test
    public void drawCardsInNotStartedGame() {
        GameId gameId = new GameId("GAME_2");
        fixture.given(
            new BoardSetUpEvent(gameId),
            new PlayerAddedEvent(gameId, "John",  PawnColor.RED)
        )
        .when(new DrawCardsCommand(gameId, PawnColor.RED))
        .expectException(GameNotStartedException.class);
    }

}
