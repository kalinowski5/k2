package k2;

import k2.command.AddPlayerCommand;
import k2.command.SetupBoardCommand;
import k2.valueobject.GameId;
import k2.valueobject.PawnColor;
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
            GameId gameId = new GameId("game_" + UUID.randomUUID().toString());
            //String gameId = "game_06fcf964-03eb-4d4c-ae9f-60e40e0d994c";
            commandGateway.sendAndWait(new SetupBoardCommand(gameId));
            commandGateway.sendAndWait(new AddPlayerCommand(gameId,"Michał", PawnColor.RED));
            commandGateway.sendAndWait(new AddPlayerCommand(gameId,"Andrew", PawnColor.GREEN));
            //commandGateway.sendAndWait(new AddPlayerCommand(gameId,"Bob",  PawnColor.BLUE));
        } catch (Exception e) {
            System.out.println("Error" + e.getClass());
        }

    }
}
