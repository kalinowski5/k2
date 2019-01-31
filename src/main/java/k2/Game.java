package k2;

import k2.exception.TooManyPlayersException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class Game {

    private static final int MAX_PLAYERS = 2;

    @AggregateIdentifier
    private String gameId;
    private Integer numberOfPlayers = 0;

    private Game() {
    }

    @CommandHandler
    public Game(SetupBoardCommand command)
    {
        AggregateLifecycle.apply(new BoardSetUpEvent(command.getId()));
    }

    @CommandHandler
    public void addPlayer(AddPlayerCommand command) throws Exception {
        if (numberOfPlayers + 1 > MAX_PLAYERS) {
            throw new TooManyPlayersException();
        }
        AggregateLifecycle.apply(new PlayerAddedEvent(gameId, command.getName(), command.getColor()));
    }

    @EventSourcingHandler
    public void on(BoardSetUpEvent event) {
        gameId = event.getId();
    }

    @EventSourcingHandler
    public void on(PlayerAddedEvent event) {
        numberOfPlayers += 1;
    }
}
