import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;

public class Game {

    @AggregateIdentifier
    private String id;
    private Integer numberOfPlayers;

    public Game() {

    }

    @CommandHandler
    public Game(StartGameCommand command) {

        if(command.getNumberOfPlayers() <= 0) {
            throw new IllegalArgumentException("Invalid number of players.");
        }

        AggregateLifecycle.apply(new GameStartedEvent(command.getId(), command.getNumberOfPlayers()));
    }

    @EventSourcingHandler
    public void on(GameStartedEvent event) {
        id = event.getId();
        numberOfPlayers = event.getNumberOfPlayers();
    }
}
