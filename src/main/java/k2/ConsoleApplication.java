package k2;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class ConsoleApplication implements CommandLineRunner {

    @Autowired
    CommandGateway commandGateway;

    public static void main(String[] args) throws Exception {

        SpringApplication.run(ConsoleApplication.class, args);

    }

    //access command line arguments
    @Override
    public void run(String... args) {

        try {
            String gameId = "game_" + UUID.randomUUID().toString();
            //String gameId = "game_06fcf964-03eb-4d4c-ae9f-60e40e0d994c";
            commandGateway.sendAndWait(new SetupBoardCommand(gameId));
            commandGateway.sendAndWait(new AddPlayerCommand(gameId,"Michał", "red"));
            commandGateway.sendAndWait(new AddPlayerCommand(gameId,"Andrew", "green"));
            commandGateway.send(new AddPlayerCommand(gameId,"Bob", "blue"));
        } catch (Exception e) {
            System.out.println("Error" + e.getClass());
        }

    }
}
