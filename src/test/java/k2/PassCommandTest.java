package k2;

import k2.aggregate.Game;
import k2.command.MoveClimberCommand;
import k2.command.PassCommand;
import k2.event.*;
import k2.exception.NotEnoughMovementPointsException;
import k2.exception.WrongCombinationOfCardPointsException;
import k2.valueobject.*;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

public class PassCommandTest {
    private FixtureConfiguration fixture;

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture(Game.class);
        fixture.setReportIllegalStateChange(false);
    }

    @Test
    public void passTurn() throws WrongCombinationOfCardPointsException {
        GameId gameId = new GameId("GAME");

        Card card1 = new Card(PawnColor.RED, 1, 2, 0);
        Card card2 = new Card(PawnColor.RED, 3, 0, 0);
        Card card3 = new Card(PawnColor.RED, 2, 3, 0);
        Card card4 = new Card(PawnColor.RED, 2, 0, 0);
        Card card5 = new Card(PawnColor.RED, 0, 0, 1);
        Card card6 = new Card(PawnColor.RED, 0, 0, 3);

        Card card7 = new Card(PawnColor.BLUE, 1, 2, 0);
        Card card8 = new Card(PawnColor.BLUE, 3, 0, 0);
        Card card9 = new Card(PawnColor.BLUE, 2, 3, 0);
        Card card10 = new Card(PawnColor.BLUE, 2, 0, 0);
        Card card11 = new Card(PawnColor.BLUE, 0, 0, 1);
        Card card12 = new Card(PawnColor.BLUE, 0, 0, 3);

        fixture.given(
                new BoardSetUpEvent(gameId),
                new PlayerAddedEvent(gameId, "Reinhold Messner",  PawnColor.RED),
                new PlayerAddedEvent(gameId, "Krzysztof Wielicki",  PawnColor.BLUE),
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
                new CardRevealedEvent(gameId, card1),
                new CardRevealedEvent(gameId, card2),
                new CardRevealedEvent(gameId, card6),
                new CardRevealedEvent(gameId, card7),
                new CardRevealedEvent(gameId, card8),
                new CardRevealedEvent(gameId, card9),
                new PhaseStartedEvent(gameId, Phase.ACTION),
                new ClimberMovedEvent(gameId, PawnColor.BLUE, Space.BASE_CAMP, Space.S2, 2)

        )
                .when(new PassCommand(gameId, PawnColor.RED))
                .expectSuccessfulHandlerExecution()
                .expectEvents(
                        new PassedEvent(gameId, PawnColor.RED)
                );
    }

    @Test
    public void movementAfterPass() throws WrongCombinationOfCardPointsException {
        GameId gameId = new GameId("GAME_2");

        Card card1 = new Card(PawnColor.RED, 1, 2, 0);
        Card card2 = new Card(PawnColor.RED, 3, 0, 0);
        Card card3 = new Card(PawnColor.RED, 2, 3, 0);
        Card card4 = new Card(PawnColor.RED, 2, 0, 0);
        Card card5 = new Card(PawnColor.RED, 0, 0, 1);
        Card card6 = new Card(PawnColor.RED, 0, 0, 3);

        fixture.given(
                new BoardSetUpEvent(gameId),
                new PlayerAddedEvent(gameId, "Reinhold Messner",  PawnColor.RED),
                new GameStartedEvent(gameId),
                new CardDrawnEvent(gameId, card1),
                new CardDrawnEvent(gameId, card2),
                new CardDrawnEvent(gameId, card3),
                new CardDrawnEvent(gameId, card4),
                new CardDrawnEvent(gameId, card5),
                new CardDrawnEvent(gameId, card6),
                new CardRevealedEvent(gameId, card1),
                new CardRevealedEvent(gameId, card2),
                new CardRevealedEvent(gameId, card6),
                new PassedEvent(gameId, PawnColor.RED)

        )
                .when(new MoveClimberCommand(gameId, PawnColor.RED, Space.S1))
                .expectExceptionMessage("You need 1 movement points for that move, but you have only 0 available.")
                .expectException(NotEnoughMovementPointsException.class);
    }

    @Test
    public void changePhaseAfterAllPlayersPassed() throws WrongCombinationOfCardPointsException {
        GameId gameId = new GameId("GAME");

        Card card1 = new Card(PawnColor.RED, 1, 2, 0);
        Card card2 = new Card(PawnColor.RED, 3, 0, 0);
        Card card3 = new Card(PawnColor.RED, 2, 3, 0);
        Card card4 = new Card(PawnColor.RED, 2, 0, 0);
        Card card5 = new Card(PawnColor.RED, 0, 0, 1);
        Card card6 = new Card(PawnColor.RED, 0, 0, 3);

        fixture.given(
                new BoardSetUpEvent(gameId),
                new PlayerAddedEvent(gameId, "Reinhold Messner",  PawnColor.RED),
                new GameStartedEvent(gameId),
                new CardDrawnEvent(gameId, card1),
                new CardDrawnEvent(gameId, card2),
                new CardDrawnEvent(gameId, card3),
                new CardDrawnEvent(gameId, card4),
                new CardDrawnEvent(gameId, card5),
                new CardDrawnEvent(gameId, card6),
                new CardRevealedEvent(gameId, card1),
                new CardRevealedEvent(gameId, card2),
                new CardRevealedEvent(gameId, card6),
                new PhaseStartedEvent(gameId, Phase.ACTION)

        )
                .when(new PassCommand(gameId, PawnColor.RED))
                .expectSuccessfulHandlerExecution()
                .expectEvents(
                        new PassedEvent(gameId, PawnColor.RED),
                        new PhaseStartedEvent(gameId, Phase.END_OF_TURN),
                        new PhaseStartedEvent(gameId, Phase.CARD_SELECTION)
                );
    }
}
