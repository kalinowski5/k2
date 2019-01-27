import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.Configuration;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;

public class Controller {

    public static void main(String[] args)
    {
        System.out.println("Configuring axon...");

        Configuration configuration = DefaultConfigurer.defaultConfiguration()
                .configureAggregate(Game.class)
                .configureEventStore(c -> new EmbeddedEventStore(new InMemoryEventStorageEngine()))
                //.registerModule(eventHandlingConfiguration)
               // .registerQueryHandler(c -> projection) // (4)
                .buildConfiguration();

        configuration.start();
        System.out.println("Axon configured!");

        System.out.println("Sending first command!");
        CommandGateway commandGateway = configuration.commandGateway();
        commandGateway.sendAndWait(new StartGameCommand("game_1", 2));
    }
}
