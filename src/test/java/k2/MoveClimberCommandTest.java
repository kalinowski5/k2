package k2;

import k2.aggregate.Game;
import k2.command.MoveClimberCommand;
import k2.command.RevealCardCommand;
import k2.event.*;
import k2.exception.CardRevealViolationException;
import k2.exception.WrongCombinationOfCardPointsException;
import k2.valueobject.Card;
import k2.valueobject.GameId;
import k2.valueobject.PawnColor;
import k2.valueobject.Space;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

public class MoveClimberCommandTest {
    private FixtureConfiguration fixture;

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture(Game.class);
        fixture.setReportIllegalStateChange(false);
    }

    @Test
    public void moveClimberAfterRevealingCards() throws WrongCombinationOfCardPointsException {
        GameId gameId = new GameId("GAME_1");

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
                    new CardRevealedEvent(gameId, card6)

                )
                .when(new MoveClimberCommand(gameId, PawnColor.RED, Space.S5))
                .expectSuccessfulHandlerExecution()
                .expectEvents(
                        new ClimberMovedEvent(gameId, PawnColor.RED, Space.BASE_CAMP, Space.S5, 4)
                );
    }


}
