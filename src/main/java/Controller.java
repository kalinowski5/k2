import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.Configuration;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;  //In memory
import org.axonframework.eventsourcing.eventstore.jdbc.JdbcEventStorageEngine;          //Basic SQL
import org.axonframework.eventsourcing.eventstore.jpa.JpaEventStorageEngine;            //ORM

public class Controller {

    public static void main(String[] args)
    {
        System.out.println("Configuring axon...");

        final EventStorageEngine storageEngine = new InMemoryEventStorageEngine();

        Configuration configuration = DefaultConfigurer.defaultConfiguration()
                .configureAggregate(Game.class)
                .configureEventStore(c -> new EmbeddedEventStore(storageEngine))
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
