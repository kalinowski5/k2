package k2.aggregate;

import k2.command.AddPlayerCommand;
import k2.command.DrawCardsCommand;
import k2.command.SetupBoardCommand;
import k2.command.StartGameCommand;
import k2.entity.Player;
import k2.event.BoardSetUpEvent;
import k2.event.CardDrawnEvent;
import k2.event.GameStartedEvent;
import k2.event.PlayerAddedEvent;
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
        Integer numberOfCardsToDraw = player.getNumberOfCardToDraw();
        for (Integer i = 1; i <= numberOfCardsToDraw; i++) {
            AggregateLifecycle.apply(new CardDrawnEvent(this.gameId, new Card(command.getPlayer(), 2, 0, 0)));
        }
    }

    @EventSourcingHandler
    public void on(BoardSetUpEvent event) {
        gameId = event.getId();
        players = new HashMap<>();
    }

    @EventSourcingHandler
    public void on(PlayerAddedEvent event) {
        players.put(event.getColor(), new Player(event.getColor(), event.getName()));
    }

    @EventSourcingHandler
    public void on(GameStartedEvent event) {
        gameStarted = true;
    }

    @EventSourcingHandler
    public void on(CardDrawnEvent event) {
        Player player = this.players.get(event.getCard().getPawnColor());
        player.drawOneCard();
    }
}
