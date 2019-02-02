package k2;

import k2.aggregate.Game;
import k2.command.DrawCardsCommand;
import k2.event.*;
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

    @Test
    public void finishDeck() throws WrongCombinationOfCardPointsException {
        GameId gameId = new GameId("GAME_4");

        Card card1 = new Card(PawnColor.BLUE, 1, 0, 0);
        Card card2 = new Card(PawnColor.BLUE, 1, 0, 0);
        Card card3 = new Card(PawnColor.BLUE, 1, 0, 0);
        Card card4 = new Card(PawnColor.BLUE, 1, 0, 0);
        Card card5 = new Card(PawnColor.BLUE, 1, 0, 0);

        Card card6 = new Card(PawnColor.BLUE, 2, 0, 0);
        Card card7 = new Card(PawnColor.BLUE, 2, 0, 0);
        Card card8 = new Card(PawnColor.BLUE, 2, 0, 0);

        Card card9 = new Card(PawnColor.BLUE, 3, 0, 0);
        Card card10 = new Card(PawnColor.BLUE, 3, 0, 0);

        Card card11 = new Card(PawnColor.BLUE, 1, 2, 0);
        Card card12 = new Card(PawnColor.BLUE, 1, 3, 0);
        Card card13 = new Card(PawnColor.BLUE, 2, 3, 0);

        Card card14 = new Card(PawnColor.BLUE, 0, 0, 0);
        Card card15 = new Card(PawnColor.BLUE, 0, 0, 1);
        Card card16 = new Card(PawnColor.BLUE, 0, 0, 1);
        Card card17 = new Card(PawnColor.BLUE, 0, 0, 2);
        Card card18 = new Card(PawnColor.BLUE, 0, 0, 3);

        fixture.given(
                    new BoardSetUpEvent(gameId),
                    new PlayerAddedEvent(gameId, "John",  PawnColor.BLUE),
                    new GameStartedEvent(gameId),

                    new CardDrawnEvent(gameId, card4),
                    new CardDrawnEvent(gameId, card1),
                    new CardDrawnEvent(gameId, card10),
                    new CardDrawnEvent(gameId, card11),
                    new CardDrawnEvent(gameId, card3),
                    new CardDrawnEvent(gameId, card2),

                    new CardRevealedEvent(gameId, card1),
                    new CardRevealedEvent(gameId, card10),
                    new CardRevealedEvent(gameId, card2),

                    new CardDrawnEvent(gameId, card5),
                    new CardDrawnEvent(gameId, card18),
                    new CardDrawnEvent(gameId, card9),

                    new CardRevealedEvent(gameId, card4),
                    new CardRevealedEvent(gameId, card9),
                    new CardRevealedEvent(gameId, card3),

                    new CardDrawnEvent(gameId, card17),
                    new CardDrawnEvent(gameId, card12),
                    new CardDrawnEvent(gameId, card8),

                    new CardRevealedEvent(gameId, card17),
                    new CardRevealedEvent(gameId, card12),
                    new CardRevealedEvent(gameId, card8),

                    new CardDrawnEvent(gameId, card13),
                    new CardDrawnEvent(gameId, card14),
                    new CardDrawnEvent(gameId, card6),

                    new CardRevealedEvent(gameId, card13),
                    new CardRevealedEvent(gameId, card14),
                    new CardRevealedEvent(gameId, card6),

                    new CardDrawnEvent(gameId, card7),
                    new CardDrawnEvent(gameId, card15),
                    new CardDrawnEvent(gameId, card16),

                    new CardRevealedEvent(gameId, card7),
                    new CardRevealedEvent(gameId, card15),
                    new CardRevealedEvent(gameId, card16)

                )
                .when(new DrawCardsCommand(gameId, PawnColor.BLUE))
                .expectSuccessfulHandlerExecution();
    }
}
