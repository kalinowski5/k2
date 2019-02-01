package k2.aggregate;

import k2.command.AddPlayerCommand;
import k2.command.DrawCardsCommand;
import k2.command.SetupBoardCommand;
import k2.command.StartGameCommand;
import k2.event.BoardSetUpEvent;
import k2.event.CardDrawnEvent;
import k2.event.GameStartedEvent;
import k2.event.PlayerAddedEvent;
import k2.exception.GameAlreadyStartedException;
import k2.exception.NotEnoughPlayersException;
import k2.exception.TooManyPlayersException;
import k2.valueobject.GameId;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.HashMap;
import java.util.Map;

@Aggregate
public class Game {

    private static final int MAX_PLAYERS = 2;

    @AggregateIdentifier
    private GameId gameId;
    private Map<String, String> players;
    private boolean gameStarted = false;


    private Game() {
    }

    @CommandHandler
    public Game(SetupBoardCommand command)
    {
        AggregateLifecycle.apply(new BoardSetUpEvent(command.getId()));
    }

    @CommandHandler
    public void addPlayer(AddPlayerCommand command) throws TooManyPlayersException {
        if (players.size() + 1 > MAX_PLAYERS) {
            throw new TooManyPlayersException();
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
    public void drawCards(DrawCardsCommand command) {
        AggregateLifecycle.apply(new CardDrawnEvent(this.gameId, command.getPlayer(), "move", 2));
        AggregateLifecycle.apply(new CardDrawnEvent(this.gameId, command.getPlayer(), "move", 2));
        AggregateLifecycle.apply(new CardDrawnEvent(this.gameId, command.getPlayer(), "move", 2));
        AggregateLifecycle.apply(new CardDrawnEvent(this.gameId, command.getPlayer(), "move", 2));
        AggregateLifecycle.apply(new CardDrawnEvent(this.gameId, command.getPlayer(), "move", 2));
        AggregateLifecycle.apply(new CardDrawnEvent(this.gameId, command.getPlayer(), "move", 2));
    }

    @EventSourcingHandler
    public void on(BoardSetUpEvent event) {
        gameId = event.getId();
        players = new HashMap<>();
    }

    @EventSourcingHandler
    public void on(PlayerAddedEvent event) {
        players.put(event.getName(), event.getName());
    }

    @EventSourcingHandler
    public void on(GameStartedEvent event) {
        gameStarted = true;
    }

    @EventSourcingHandler
    public void on(CardDrawnEvent event) {

    }
}
