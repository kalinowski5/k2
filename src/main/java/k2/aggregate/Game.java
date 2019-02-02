package k2.aggregate;

import k2.command.*;
import k2.entity.Player;
import k2.event.*;
import k2.exception.*;
import k2.valueobject.Card;
import k2.valueobject.GameId;
import k2.valueobject.PawnColor;
import k2.valueobject.Space;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.commandhandling.model.AggregateMember;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.*;

@Aggregate
public class Game {

    @AggregateIdentifier
    private GameId gameId;

    @AggregateMember
    private Map<PawnColor, Player> players;

    Graph<Space, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);

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
        player.getCardsToDraw().iterator().forEachRemaining(
                card -> AggregateLifecycle.apply(new CardDrawnEvent(this.gameId, card))
        );
    }

    @CommandHandler
    public void revealCard(RevealCardCommand command) throws CardRevealViolationException {
        Card card = command.getCard();
        Player player = players.get(card.getPawnColor());

        if (!player.canReveal(card)) {
            throw new CardRevealViolationException();
        }

        AggregateLifecycle.apply(new CardRevealedEvent(this.gameId, command.getCard()));
    }

    @EventSourcingHandler
    public void on(BoardSetUpEvent event) {
        gameId = event.getId();
        players = new HashMap<>();

        Space spaceBaseCamp = new Space("Basecamp", 5000, 1, 1);
        Space space1 = new Space("1", 5000, 1, 1);
        Space space2 = new Space("2", 5000, 1, 1);
        Space space3 = new Space("3", 5000, 1, 1);
        Space space4 = new Space("4", 5000, 1, 1);
        Space space5 = new Space("5", 5000, 1, 1);
        Space space6 = new Space("6", 5000, 1, 1);

        graph.addVertex(spaceBaseCamp);
        graph.addVertex(space1);
        graph.addVertex(space2);
        graph.addVertex(space3);
        graph.addVertex(space4);
        graph.addVertex(space5);
        graph.addVertex(space6);

        graph.addEdge(spaceBaseCamp, space1);
        graph.addEdge(space1, space2);
        graph.addEdge(space2, space3);
        graph.addEdge(space2, space4);
        graph.addEdge(space3, space5);
        graph.addEdge(space4, space5);

        DijkstraShortestPath<Space, DefaultEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);
        ShortestPathAlgorithm.SingleSourcePaths<Space, DefaultEdge> iPaths = dijkstraAlg.getPaths(spaceBaseCamp);
        System.out.println(iPaths.getPath(space5) + "\n");

    }

    @EventSourcingHandler
    public void on(PlayerAddedEvent event) throws WrongCombinationOfCardPointsException {
        players.put(event.getColor(), new Player(event.getColor(), event.getName()));
    }

    @EventSourcingHandler
    public void on(GameStartedEvent event) {
        gameStarted = true;
    }

    @EventSourcingHandler
    public void on(CardDrawnEvent event) {
        Player player = this.players.get(event.getCard().getPawnColor());
        player.drawOneCard(event.getCard());
    }

    @EventSourcingHandler
    public void on(CardRevealedEvent event) {
        Player player = this.players.get(event.getCard().getPawnColor());
        player.revealOneCard(event.getCard());
    }
}
