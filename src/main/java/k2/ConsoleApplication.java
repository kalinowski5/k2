package k2;

import k2.command.*;
import k2.exception.WrongCombinationOfCardPointsException;
import k2.valueobject.Card;
import k2.valueobject.GameId;
import k2.valueobject.PawnColor;
import k2.valueobject.Space;
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
    public void run(String... args) throws WrongCombinationOfCardPointsException {

//        GameId gameId = new GameId("game_" + UUID.randomUUID().toString());
//        commandGateway.sendAndWait(new SetupBoardCommand(gameId));
//        commandGateway.sendAndWait(new AddPlayerCommand(gameId,"Michał", PawnColor.RED));
//        commandGateway.sendAndWait(new AddPlayerCommand(gameId,"Andrew", PawnColor.GREEN));
//
//        commandGateway.sendAndWait(new StartGameCommand(gameId));
//        commandGateway.sendAndWait(new DrawCardsCommand(gameId, PawnColor.RED));
//        commandGateway.sendAndWait(new DrawCardsCommand(gameId, PawnColor.GREEN));

        GameId gameId = new GameId("game_fdab9f31-d8c7-4fa2-92aa-f2e95d5dec69");

        //commandGateway.sendAndWait(new MoveClimberCommand(gameId, PawnColor.RED, Space.S4));
        //commandGateway.sendAndWait(new DrawCardsCommand(gameId, PawnColor.RED));
        //commandGateway.sendAndWait(new RevealCardCommand(gameId, new Card(PawnColor.RED, 3, 0, 0)));
        //commandGateway.sendAndWait(new DrawCardsCommand(gameId, PawnColor.RED));

        //commandGateway.sendAndWait(new RevealCardCommand(gameId, new Card(PawnColor.RED, 2, 0, 0)));

        commandGateway.sendAndWait(new MoveClimberCommand(gameId, PawnColor.RED, Space.SUMMIT));
    }
}
