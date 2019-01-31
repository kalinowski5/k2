package k2;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.sql.SQLException;
import java.util.UUID;

@RestController
public class HelloController {

    @Autowired
    CommandGateway commandGateway;

    @RequestMapping("/")
    public String index() throws SQLException {

        LocalTime currentTime = new LocalTime();

        try {
            String gameId = "game_" + UUID.randomUUID().toString();
            commandGateway.sendAndWait(new SetupBoardCommand(gameId));
            commandGateway.sendAndWait(new AddPlayerCommand(gameId,"Michał", "red"));
            commandGateway.sendAndWait(new AddPlayerCommand(gameId,"Andrew", "green"));
            //commandGateway.sendAndWait(new AddPlayerCommand(gameId,"Bob", "blue"));
        } catch (Exception e) {
            return "<h1>Error</h1>" + e.getClass() + " " + e.getMessage();
        }
        return "<h1>Greetings from Spring Boot!</h1>"  + currentTime;
    }

}