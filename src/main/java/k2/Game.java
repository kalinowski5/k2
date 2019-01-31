package k2;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class Game {

    //public static final int maxNumberOfPlayers = 4;
    private static final int maxNumberOfPlayers = 2;

    @AggregateIdentifier
    private String id;
    private Integer numberOfPlayers;

    public Game() {

    }

    @CommandHandler
    public Game(SetupBoardCommand command)
    {


        AggregateLifecycle.apply(new BoardSetUpEvent(command.getId()));
    }

    @CommandHandler
    public void addPlayer(AddPlayerCommand command)
    {
//        if (this.numberOfPlayers >= maxNumberOfPlayers) {
//            throw new IllegalArgumentException("Too many players.");
//        }
        AggregateLifecycle.apply(new PlayerAddedEvent(command.getName(), command.getColor()));
    }

    @EventSourcingHandler
    public void on(BoardSetUpEvent event) {
        id = event.getId();
    }
    @EventSourcingHandler
    public void on(PlayerAddedEvent event) {
        //this.numberOfPlayers = this.numberOfPlayers++;
    }
}
