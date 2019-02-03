package k2.aggregate;

import k2.command.*;
import k2.entity.Player;
import k2.event.*;
import k2.exception.*;
import k2.factory.GameBoardFactory;
import k2.valueobject.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.commandhandling.model.AggregateMember;
import org.axonframework.commandhandling.model.ForwardMatchingInstances;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import java.util.*;

@Aggregate
public class Game {

    @AggregateIdentifier
    private GameId gameId;

    @AggregateMember(eventForwardingMode = ForwardMatchingInstances.class)
    private Map<PawnColor, Player> players = new HashMap<>();

    private Graph<Space, DefaultWeightedEdge> board = GameBoardFactory.generate();

    private boolean gameStarted = false;

    private Game() {
    }

    @CommandHandler
    public Game(SetupBoardCommand command)
    {
        AggregateLifecycle.apply(new BoardSetUpEvent(command.getId()));
    }

    @CommandHandler
    public void addPlayer(AddPlayerCommand command) throws ColorAlreadyUsedException, GameAlreadyStartedException {

        if (players.containsKey(command.getColor())) {
            throw new ColorAlreadyUsedException();
        }

        if (gameStarted) {
            throw new GameAlreadyStartedException();
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
        AggregateLifecycle.apply(new PhaseStartedEvent(gameId, Phase.CARD_SELECTION));
    }

    @CommandHandler
    public void drawCards(DrawCardsCommand command) throws GameNotStartedException, CantDrawCardsTwiceInTheSameTurnException {

        if (!this.gameStarted) {
            throw new GameNotStartedException();
        }

        Player player = this.players.get(command.getPlayer());

        if (!player.canDrawCards()) {
            throw new CantDrawCardsTwiceInTheSameTurnException();
        }
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

        boolean allPlayersRevealedCards = players.entrySet().stream().noneMatch(map -> map.getValue().canRevealCards());

        if (allPlayersRevealedCards) {
            AggregateLifecycle.apply(new PhaseStartedEvent(this.gameId, Phase.ACTION));
        }
    }

    @CommandHandler
    public void moveClimber(MoveClimberCommand command) throws NotEnoughMovementPointsException {

        Player player = this.players.get(command.getPlayer());
        Space currentPosition = player.getCurrentPosition();

        DijkstraShortestPath<Space, DefaultWeightedEdge> dijkstraAlg = new DijkstraShortestPath<>(board);
        ShortestPathAlgorithm.SingleSourcePaths<Space, DefaultWeightedEdge> iPaths = dijkstraAlg.getPaths(currentPosition);

        Integer movementCost = (int) iPaths.getWeight(command.getTargetSpace());

        if (player.getAvailableMovementPoints() < movementCost) {
            throw new NotEnoughMovementPointsException(movementCost, player.getAvailableMovementPoints());
        }

        AggregateLifecycle.apply(new ClimberMovedEvent(gameId, command.getPlayer(), currentPosition, command.getTargetSpace(), movementCost));

        finishActionPhase();
    }

    @CommandHandler
    public void pass(PassCommand command) {
        AggregateLifecycle.apply(new PassedEvent(gameId, command.getPlayer()));

        finishActionPhase();
    }

    private void finishActionPhase() {
        boolean allPlayersFinishedActions = players.entrySet().stream().noneMatch(map -> map.getValue().canAct());

        if (allPlayersFinishedActions) {
            AggregateLifecycle.apply(new PhaseStartedEvent(this.gameId, Phase.END_OF_TURN));
            AggregateLifecycle.apply(new PhaseStartedEvent(this.gameId, Phase.CARD_SELECTION));
        }
    }

    @EventSourcingHandler
    public void on(BoardSetUpEvent event) {
        gameId = event.getId();
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
    public void on(PhaseStartedEvent event) {
        players.entrySet().stream().forEach(map -> map.getValue().onPhaseStarted(event.getPhase()));
    }
}
