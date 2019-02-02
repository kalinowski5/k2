package k2.aggregate;

import k2.command.*;
import k2.entity.Player;
import k2.event.*;
import k2.exception.*;
import k2.valueobject.Card;
import k2.valueobject.GameId;
import k2.valueobject.PawnColor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.commandhandling.model.AggregateMember;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.HashMap;
import java.util.Map;

@Aggregate
public class Game {

    @AggregateIdentifier
    private GameId gameId;

    @AggregateMember
    private Map<PawnColor, Player> players;

    private boolean gameStarted = false;

    private Game() {
    }

    @CommandHandler
    public Game(SetupBoardCommand command)
    {
        AggregateLifecycle.apply(new BoardSetUpEvent(command.getId()));
    }

    @CommandHandler
    public void addPlayer(AddPlayerCommand command) throws ColorAlreadyUsedException {

        if (players.containsKey(command.getColor())) {
            throw new ColorAlreadyUsedException();
        }

        AggregateLifecycle.apply(new PlayerAddedEvent(gameId, command.getName(), command.getColor()));
    }

    @CommandHandler
    public void start(StartGameCommand command) throws NotEnoughPlayersException, GameAlreadyStartedException {

        if (players.size() < 1) {
            throw new NotEnoughPlayersException();
        }

        if (gameStarted) {
            throw new GameAlreadyStartedException();
        }

        AggregateLifecycle.apply(new GameStartedEvent(gameId));
    }

    @CommandHandler
    public void drawCards(DrawCardsCommand command) throws GameNotStartedException, WrongCombinationOfCardPointsException {
        if (!this.gameStarted) {
            throw new GameNotStartedException();
        }
        Player player = this.players.get(command.getPlayer());
        player.getCardsToDraw().iterator().forEachRemaining(
                card -> AggregateLifecycle.apply(new CardDrawnEvent(this.gameId, card))
        );
    }

    @CommandHandler
    public void revealCard(RevealCardCommand command) throws CardRevealViolationException {
        Card card = command.getCard();
        Player player = players.get(card.getPawnColor());

        if (!player.canReveal(card)) {
            throw new CardRevealViolationException();
        }

        AggregateLifecycle.apply(new CardRevealedEvent(this.gameId, command.getCard()));
    }

    @EventSourcingHandler
    public void on(BoardSetUpEvent event) {
        gameId = event.getId();
        players = new HashMap<>();
    }

    @EventSourcingHandler
    public void on(PlayerAddedEvent event) throws WrongCombinationOfCardPointsException {
        players.put(event.getColor(), new Player(event.getColor(), event.getName()));
    }

    @EventSourcingHandler
    public void on(GameStartedEvent event) {
        gameStarted = true;
    }

    @EventSourcingHandler
    public void on(CardDrawnEvent event) {
        Player player = this.players.get(event.getCard().getPawnColor());
        player.drawOneCard(event.getCard());
    }

    @EventSourcingHandler
    public void on(CardRevealedEvent event) {
        Player player = this.players.get(event.getCard().getPawnColor());
        player.revealOneCard(event.getCard());
    }
}
