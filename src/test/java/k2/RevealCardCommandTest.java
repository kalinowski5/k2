package k2;

import k2.aggregate.Game;
import k2.command.RevealCardCommand;
import k2.event.*;
import k2.exception.CardRevealViolationException;
import k2.exception.WrongCombinationOfCardPointsException;
import k2.valueobject.Card;
import k2.valueobject.GameId;
import k2.valueobject.PawnColor;
import k2.valueobject.Phase;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

public class RevealCardCommandTest {
    private FixtureConfiguration fixture;

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture(Game.class);
        fixture.setReportIllegalStateChange(false);
    }

    @Test
    public void revealPreviouslyDrawnCardsGame() throws WrongCombinationOfCardPointsException {
        GameId gameId = new GameId("GAME_1");

        Card card1 = new Card(PawnColor.RED, 1, 2, 0);
        Card card2 = new Card(PawnColor.RED, 3, 0, 0);
        Card card3 = new Card(PawnColor.RED, 2, 3, 0);
        Card card4 = new Card(PawnColor.RED, 2, 0, 0);
        Card card5 = new Card(PawnColor.RED, 0, 0, 1);
        Card card6 = new Card(PawnColor.RED, 0, 0, 3);

        fixture.given(
                    new BoardSetUpEvent(gameId),
                    new PlayerAddedEvent(gameId, "John",  PawnColor.RED),
                    new GameStartedEvent(gameId),
                    new CardDrawnEvent(gameId, card1),
                    new CardDrawnEvent(gameId, card2),
                    new CardDrawnEvent(gameId, card3),
                    new CardDrawnEvent(gameId, card4),
                    new CardDrawnEvent(gameId, card5),
                    new CardDrawnEvent(gameId, card6)

                )
                .when(new RevealCardCommand(gameId, card4))
                .expectSuccessfulHandlerExecution()
                .expectEvents(
                        new CardRevealedEvent(gameId,card4)
                );
    }

    @Test
    public void revealLastDrawnCardInSinglePlayerGame() throws WrongCombinationOfCardPointsException {
        GameId gameId = new GameId("GAME_1");

        Card card1 = new Card(PawnColor.RED, 1, 2, 0);
        Card card2 = new Card(PawnColor.RED, 3, 0, 0);
        Card card3 = new Card(PawnColor.RED, 2, 3, 0);
        Card card4 = new Card(PawnColor.RED, 2, 0, 0);
        Card card5 = new Card(PawnColor.RED, 0, 0, 1);
        Card card6 = new Card(PawnColor.RED, 0, 0, 3);

        fixture.given(
                    new BoardSetUpEvent(gameId),
                    new PlayerAddedEvent(gameId, "John",  PawnColor.RED),
                    new GameStartedEvent(gameId),
                    new CardDrawnEvent(gameId, card1),
                    new CardDrawnEvent(gameId, card2),
                    new CardDrawnEvent(gameId, card3),
                    new CardDrawnEvent(gameId, card4),
                    new CardDrawnEvent(gameId, card5),
                    new CardDrawnEvent(gameId, card6),
                    new CardRevealedEvent(gameId, card2),
                    new CardRevealedEvent(gameId, card6)

                )
                .when(new RevealCardCommand(gameId, card4))
                .expectSuccessfulHandlerExecution()
                .expectEvents(
                        new CardRevealedEvent(gameId,card4),
                        new PhaseStartedEvent(gameId, Phase.ACTION)
                );
    }

    @Test
    public void revealLastDrawnCardInMultiplayerGame() throws WrongCombinationOfCardPointsException {
        GameId gameId = new GameId("GAME_1");

        Card card1 = new Card(PawnColor.RED, 1, 2, 0);
        Card card2 = new Card(PawnColor.RED, 3, 0, 0);
        Card card3 = new Card(PawnColor.RED, 2, 3, 0);
        Card card4 = new Card(PawnColor.RED, 2, 0, 0);
        Card card5 = new Card(PawnColor.RED, 0, 0, 1);
        Card card6 = new Card(PawnColor.RED, 0, 0, 3);

        Card card7 = new Card(PawnColor.YELLOW, 1, 2, 0);
        Card card8 = new Card(PawnColor.YELLOW, 2, 0, 0);
        Card card9 = new Card(PawnColor.YELLOW, 1, 0, 0);
        Card card10 = new Card(PawnColor.YELLOW, 2, 0, 0);
        Card card11 = new Card(PawnColor.YELLOW, 0, 0, 1);
        Card card12 = new Card(PawnColor.YELLOW, 0, 0, 2);

        fixture.given(
                    new BoardSetUpEvent(gameId),
                    new PlayerAddedEvent(gameId, "John",  PawnColor.RED),
                    new PlayerAddedEvent(gameId, "Patrick",  PawnColor.YELLOW),
                    new GameStartedEvent(gameId),
                    new CardDrawnEvent(gameId, card1),
                    new CardDrawnEvent(gameId, card2),
                    new CardDrawnEvent(gameId, card3),
                    new CardDrawnEvent(gameId, card4),
                    new CardDrawnEvent(gameId, card5),
                    new CardDrawnEvent(gameId, card6),
                    new CardDrawnEvent(gameId, card7),
                    new CardDrawnEvent(gameId, card8),
                    new CardDrawnEvent(gameId, card9),
                    new CardDrawnEvent(gameId, card10),
                    new CardDrawnEvent(gameId, card11),
                    new CardDrawnEvent(gameId, card12),
                    new CardRevealedEvent(gameId, card4),
                    new CardRevealedEvent(gameId, card5),
                    new CardRevealedEvent(gameId, card10),
                    new CardRevealedEvent(gameId, card11),
                    new CardRevealedEvent(gameId, card12)

                )
                .when(new RevealCardCommand(gameId, card1))
                .expectSuccessfulHandlerExecution()
                .expectEvents(
                        new CardRevealedEvent(gameId,card1),
                        new PhaseStartedEvent(gameId, Phase.ACTION)
                );
    }

    @Test
    public void revealNotDrawnCardsGame() throws WrongCombinationOfCardPointsException {
        GameId gameId = new GameId("GAME_2");

        Card card1 = new Card(PawnColor.RED, 1, 2, 0);
        Card card2 = new Card(PawnColor.RED, 3, 0, 0);
        Card card3 = new Card(PawnColor.RED, 2, 3, 0);
        Card card4 = new Card(PawnColor.RED, 2, 0, 0);
        Card card5 = new Card(PawnColor.RED, 0, 0, 1);
        Card card6 = new Card(PawnColor.RED, 0, 0, 3);

        Card notDrawnCard = new Card(PawnColor.RED, 0, 0, 4);

        fixture.given(
                    new BoardSetUpEvent(gameId),
                    new PlayerAddedEvent(gameId, "John",  PawnColor.RED),
                    new GameStartedEvent(gameId),
                    new CardDrawnEvent(gameId, card1),
                    new CardDrawnEvent(gameId, card2),
                    new CardDrawnEvent(gameId, card3),
                    new CardDrawnEvent(gameId, card4),
                    new CardDrawnEvent(gameId, card5),
                    new CardDrawnEvent(gameId, card6)
                )
                .when(new RevealCardCommand(gameId, notDrawnCard))
                .expectException(CardRevealViolationException.class);
    }

}
