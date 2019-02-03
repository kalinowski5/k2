package k2;

import k2.aggregate.Game;
import k2.command.MoveClimberCommand;
import k2.event.*;
import k2.exception.NotEnoughMovementPointsException;
import k2.exception.WrongCombinationOfCardPointsException;
import k2.valueobject.*;
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

        Card card1 = new Card(PawnColor.RED, 2, 0, 0);
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
                .when(new MoveClimberCommand(gameId, PawnColor.RED, Space.S5))
                .expectSuccessfulHandlerExecution()
                .expectEvents(
                        new ClimberMovedEvent(gameId, PawnColor.RED, Space.BASE_CAMP, Space.S5, 4)
                );
    }

    @Test
    public void moveClimberTwice() throws WrongCombinationOfCardPointsException {
        GameId gameId = new GameId("GAME_2");

        Card card1 = new Card(PawnColor.RED, 1, 2, 0);
        Card card2 = new Card(PawnColor.RED, 3, 0, 0);
        Card card3 = new Card(PawnColor.RED, 2, 3, 0);
        Card card4 = new Card(PawnColor.RED, 2, 0, 0);
        Card card5 = new Card(PawnColor.RED, 0, 0, 1);
        Card card6 = new Card(PawnColor.RED, 0, 0, 3);

        fixture.given(
                new BoardSetUpEvent(gameId),
                new PlayerAddedEvent(gameId, "Krzysztof Wielicki",  PawnColor.RED),
                new GameStartedEvent(gameId),
                new CardDrawnEvent(gameId, card1),
                new CardDrawnEvent(gameId, card2),
                new CardDrawnEvent(gameId, card3),
                new CardDrawnEvent(gameId, card4),
                new CardDrawnEvent(gameId, card5),
                new CardDrawnEvent(gameId, card6),
                new CardRevealedEvent(gameId, card1),
                new CardRevealedEvent(gameId, card2),
                new CardRevealedEvent(gameId, card3),
                new PhaseStartedEvent(gameId, Phase.ACTION),
                new ClimberMovedEvent(gameId, PawnColor.RED, Space.BASE_CAMP, Space.S2, 2)

        )
                .when(new MoveClimberCommand(gameId, PawnColor.RED, Space.S7))
                .expectSuccessfulHandlerExecution()
                .expectEvents(
                        new ClimberMovedEvent(gameId, PawnColor.RED, Space.S2, Space.S7, 3)
                );
    }

    @Test
    public void lastMovementInActionPhase() throws WrongCombinationOfCardPointsException {
        GameId gameId = new GameId("GAME");

        Card card1 = new Card(PawnColor.RED, 1, 2, 0);
        Card card2 = new Card(PawnColor.RED, 2, 0, 0);
        Card card3 = new Card(PawnColor.RED, 3, 3, 0);
        Card card4 = new Card(PawnColor.RED, 2, 0, 0);
        Card card5 = new Card(PawnColor.RED, 0, 0, 1);
        Card card6 = new Card(PawnColor.RED, 0, 0, 3);

        fixture.given(
                new BoardSetUpEvent(gameId),
                new PlayerAddedEvent(gameId, "Krzysztof Wielicki",  PawnColor.RED),
                new GameStartedEvent(gameId),
                new CardDrawnEvent(gameId, card1),
                new CardDrawnEvent(gameId, card2),
                new CardDrawnEvent(gameId, card3),
                new CardDrawnEvent(gameId, card4),
                new CardDrawnEvent(gameId, card5),
                new CardDrawnEvent(gameId, card6),
                new CardRevealedEvent(gameId, card1),
                new CardRevealedEvent(gameId, card2),
                new CardRevealedEvent(gameId, card3),
                new PhaseStartedEvent(gameId, Phase.ACTION),
                new ClimberMovedEvent(gameId, PawnColor.RED, Space.BASE_CAMP, Space.S2, 2),
                new ClimberMovedEvent(gameId, PawnColor.RED, Space.S2, Space.S7, 3)

        )
                .when(new MoveClimberCommand(gameId, PawnColor.RED, Space.S8))
                .expectSuccessfulHandlerExecution()
                .expectEvents(
                        new ClimberMovedEvent(gameId, PawnColor.RED, Space.S7, Space.S8, 1),
                        new PhaseStartedEvent(gameId, Phase.END_OF_TURN),
                        new PhaseStartedEvent(gameId, Phase.CARD_SELECTION)
                );
    }

    @Test
    public void lastMovementInActionPhaseMultiplayer() throws WrongCombinationOfCardPointsException {
        GameId gameId = new GameId("GAME");

        Card card1 = new Card(PawnColor.RED, 1, 2, 0);
        Card card2 = new Card(PawnColor.RED, 2, 0, 0);
        Card card3 = new Card(PawnColor.RED, 3, 3, 0);
        Card card4 = new Card(PawnColor.RED, 2, 0, 0);
        Card card5 = new Card(PawnColor.RED, 0, 0, 1);
        Card card6 = new Card(PawnColor.RED, 0, 0, 3);

        Card card7 = new Card(PawnColor.YELLOW, 1, 2, 0);
        Card card8 = new Card(PawnColor.YELLOW, 2, 0, 0);
        Card card9 = new Card(PawnColor.YELLOW, 3, 3, 0);
        Card card10 = new Card(PawnColor.YELLOW, 2, 0, 0);
        Card card11 = new Card(PawnColor.YELLOW, 0, 0, 1);
        Card card12 = new Card(PawnColor.YELLOW, 0, 0, 3);

        fixture.given(
                new BoardSetUpEvent(gameId),
                new PlayerAddedEvent(gameId, "James",  PawnColor.RED),
                new PlayerAddedEvent(gameId, "Chris",  PawnColor.YELLOW),
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
                new CardRevealedEvent(gameId, card3),
                new CardRevealedEvent(gameId, card7),
                new CardRevealedEvent(gameId, card8),
                new CardRevealedEvent(gameId, card9),
                new PhaseStartedEvent(gameId, Phase.ACTION),
                new ClimberMovedEvent(gameId, PawnColor.YELLOW, Space.BASE_CAMP, Space.S2, 2),
                new ClimberMovedEvent(gameId, PawnColor.YELLOW, Space.S2, Space.S7, 3),
                new ClimberMovedEvent(gameId, PawnColor.YELLOW, Space.S7, Space.S8, 1),
                new ClimberMovedEvent(gameId, PawnColor.RED, Space.BASE_CAMP, Space.S2, 2),
                new ClimberMovedEvent(gameId, PawnColor.RED, Space.S2, Space.S7, 3)

        )
                .when(new MoveClimberCommand(gameId, PawnColor.RED, Space.S8))
                .expectSuccessfulHandlerExecution()
                .expectEvents(
                        new ClimberMovedEvent(gameId, PawnColor.RED, Space.S7, Space.S8, 1),
                        new PhaseStartedEvent(gameId, Phase.END_OF_TURN),
                        new PhaseStartedEvent(gameId, Phase.CARD_SELECTION)
                );
    }

    //@TODO: "You need 2147483647 movement points for that move, but you have only 2 available." - Cover moving down

    @Test
    public void moveClimberWithoutEnoughMovementPoints() throws WrongCombinationOfCardPointsException {
        GameId gameId = new GameId("GAME_3");

        Card card1 = new Card(PawnColor.RED, 1, 2, 0);
        Card card2 = new Card(PawnColor.RED, 3, 0, 0);
        Card card3 = new Card(PawnColor.RED, 2, 3, 0);
        Card card4 = new Card(PawnColor.RED, 2, 0, 0);
        Card card5 = new Card(PawnColor.RED, 0, 0, 1);
        Card card6 = new Card(PawnColor.RED, 0, 0, 3);

        fixture.given(
                    new BoardSetUpEvent(gameId),
                    new PlayerAddedEvent(gameId, "Edmund Hillary",  PawnColor.RED),
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
                .when(new MoveClimberCommand(gameId, PawnColor.RED, Space.SUMMIT))
                .expectException(NotEnoughMovementPointsException.class)
                .expectExceptionMessage("You need 24 movement points for that move, but you have only 4 available.");
    }


    @Test
    public void moveClimberTwiceWithoutEnoughMovementPoints() throws WrongCombinationOfCardPointsException {
        GameId gameId = new GameId("GAME_4");

        Card card1 = new Card(PawnColor.RED, 1, 2, 0);
        Card card2 = new Card(PawnColor.RED, 3, 0, 0);
        Card card3 = new Card(PawnColor.RED, 2, 3, 0);
        Card card4 = new Card(PawnColor.RED, 2, 0, 0);
        Card card5 = new Card(PawnColor.RED, 0, 0, 1);
        Card card6 = new Card(PawnColor.RED, 0, 0, 3);

        fixture.given(
                    new BoardSetUpEvent(gameId),
                    new PlayerAddedEvent(gameId, "Krzysztof Wielicki",  PawnColor.RED),
                    new GameStartedEvent(gameId),
                    new CardDrawnEvent(gameId, card1),
                    new CardDrawnEvent(gameId, card2),
                    new CardDrawnEvent(gameId, card3),
                    new CardDrawnEvent(gameId, card4),
                    new CardDrawnEvent(gameId, card5),
                    new CardDrawnEvent(gameId, card6),
                    new CardRevealedEvent(gameId, card1),
                    new CardRevealedEvent(gameId, card5),
                    new CardRevealedEvent(gameId, card3),
                    new ClimberMovedEvent(gameId, PawnColor.RED, Space.BASE_CAMP, Space.S2, 2)

                )
                .when(new MoveClimberCommand(gameId, PawnColor.RED, Space.S5))
                .expectException(NotEnoughMovementPointsException.class)
                .expectExceptionMessage("You need 2 movement points for that move, but you have only 1 available.");
    }


}
