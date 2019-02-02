package k2;

import k2.aggregate.Game;
import k2.command.DrawCardsCommand;
import k2.event.BoardSetUpEvent;
import k2.event.CardDrawnEvent;
import k2.event.GameStartedEvent;
import k2.event.PlayerAddedEvent;
import k2.exception.GameNotStartedException;
import k2.exception.WrongCombinationOfCardPointsException;
import k2.valueobject.Card;
import k2.valueobject.GameId;
import k2.valueobject.PawnColor;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import static org.axonframework.test.matchers.Matchers.*;
import static org.hamcrest.CoreMatchers.instanceOf;

public class DrawCardsCommandTest {
    private FixtureConfiguration fixture;

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture(Game.class);
        fixture.setReportIllegalStateChange(false);
    }

    @Test
    public void drawCardsInNewGame() throws WrongCombinationOfCardPointsException {
        GameId gameId = new GameId("GAME_1");
        fixture.given(
                    new BoardSetUpEvent(gameId),
                    new PlayerAddedEvent(gameId, "John",  PawnColor.RED),
                    new PlayerAddedEvent(gameId, "Kate",  PawnColor.BLUE),
                    new GameStartedEvent(gameId)
                )
                .when(new DrawCardsCommand(gameId, PawnColor.BLUE))
                .expectSuccessfulHandlerExecution()
                .expectEventsMatching(payloadsMatching(exactSequenceOf( //Inspired by https://groups.google.com/forum/#!topic/axonframework/kr8AMCqh3io
                    instanceOf(CardDrawnEvent.class),
                    instanceOf(CardDrawnEvent.class),
                    instanceOf(CardDrawnEvent.class),
                    instanceOf(CardDrawnEvent.class),
                    instanceOf(CardDrawnEvent.class),
                    instanceOf(CardDrawnEvent.class),
                    andNoMore()
                )));
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

    @Test
    public void redrawCards() throws WrongCombinationOfCardPointsException {
        GameId gameId = new GameId("GAME_3");
        fixture.given(
                    new BoardSetUpEvent(gameId),
                    new PlayerAddedEvent(gameId, "John",  PawnColor.BLUE),
                    new GameStartedEvent(gameId),
                    new CardDrawnEvent(gameId, new Card(PawnColor.BLUE, 2, 0, 0)),
                    new CardDrawnEvent(gameId, new Card(PawnColor.BLUE, 2, 0, 0)),
                    new CardDrawnEvent(gameId, new Card(PawnColor.BLUE, 2, 0, 0))
                )
                .when(new DrawCardsCommand(gameId, PawnColor.BLUE))
                .expectSuccessfulHandlerExecution()
                .expectEventsMatching(payloadsMatching(exactSequenceOf( //Inspired by https://groups.google.com/forum/#!topic/axonframework/kr8AMCqh3io
                        instanceOf(CardDrawnEvent.class),
                        instanceOf(CardDrawnEvent.class),
                        instanceOf(CardDrawnEvent.class),
                        andNoMore()
                )));
    }
}
