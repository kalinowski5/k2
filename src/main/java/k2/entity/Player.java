package k2.entity;

import k2.event.CardDrawnEvent;
import k2.event.CardRevealedEvent;
import k2.event.ClimberMovedEvent;
import k2.event.PassedEvent;
import k2.exception.WrongCombinationOfCardPointsException;
import k2.valueobject.Card;
import k2.valueobject.PawnColor;
import k2.valueobject.Phase;
import k2.valueobject.Space;
import org.axonframework.commandhandling.model.EntityId;
import org.axonframework.eventsourcing.EventSourcingHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player
{
    private static final int MAX_NUMBER_OF_CARDS_ON_HAND = 6;
    private static final int NUMBER_OF_CARDS_TO_REVEAL_IN_ONE_TURN = 3;

    @EntityId(routingKey = "player")
    private PawnColor color;
    private String name;
    private Space currentPosition = Space.BASE_CAMP;
    private Integer availableMovementPoints = 0;
    private Boolean canDrawCards = true;
    private Boolean canRevealCards = false;

    private List<Card> cardsNotDrawn = new ArrayList<>();
    private List<Card> cardsDrawn = new ArrayList<>();
    private List<Card> cardsRevealed = new ArrayList<>();

    public Player(PawnColor color, String name) throws WrongCombinationOfCardPointsException
    {
        this.color = color;
        this.name = name;

        initCards();
    }

    private void initCards() throws WrongCombinationOfCardPointsException
    {
        //Movement cards
        this.cardsNotDrawn.add(new Card(this.color, 1, 0,0));
        this.cardsNotDrawn.add(new Card(this.color, 1, 0,0));
        this.cardsNotDrawn.add(new Card(this.color, 1, 0,0));
        this.cardsNotDrawn.add(new Card(this.color, 1, 0,0));
        this.cardsNotDrawn.add(new Card(this.color, 1, 0,0));
        this.cardsNotDrawn.add(new Card(this.color, 2, 0,0));
        this.cardsNotDrawn.add(new Card(this.color, 2, 0,0));
        this.cardsNotDrawn.add(new Card(this.color, 2, 0,0));
        this.cardsNotDrawn.add(new Card(this.color, 3, 0,0));
        this.cardsNotDrawn.add(new Card(this.color, 3, 0,0));

        //Rope cards
        this.cardsNotDrawn.add(new Card(this.color, 1, 2,0));
        this.cardsNotDrawn.add(new Card(this.color, 1, 3,0));
        this.cardsNotDrawn.add(new Card(this.color, 2, 3,0));

        //Acclimatization cards
        this.cardsNotDrawn.add(new Card(this.color, 0, 0,0));
        this.cardsNotDrawn.add(new Card(this.color, 0, 0,1));
        this.cardsNotDrawn.add(new Card(this.color, 0, 0,1));
        this.cardsNotDrawn.add(new Card(this.color, 0, 0,2));
        this.cardsNotDrawn.add(new Card(this.color, 0, 0,3));

        Collections.shuffle(this.cardsNotDrawn);
    }

    public List<Card> getCardsToDraw() {

        if (cardsNotDrawn.isEmpty()) {
            reuseRevealedCards();
        }

        List<Card> cards = new ArrayList<>();
        Integer cardsToDrawNow = MAX_NUMBER_OF_CARDS_ON_HAND - this.cardsDrawn.size();

        for (Integer index = 0; index < cardsToDrawNow; index++) {
            cards.add(cardsNotDrawn.get(index));
        }

        return cards;
    }

    private void reuseRevealedCards() {
        cardsNotDrawn = new ArrayList<Card>(cardsRevealed);
        cardsRevealed.clear();
        Collections.shuffle(cardsNotDrawn);
    }

    @EventSourcingHandler
    public void on(CardDrawnEvent event) {
        Card card = event.getCard();
        cardsDrawn.add(card);
        cardsNotDrawn.remove(card);
        canRevealCards = true;
    }

    @EventSourcingHandler
    public void on(CardRevealedEvent event) {
        Card card = event.getCard();
        canDrawCards = false;
        cardsRevealed.add(card);
        cardsDrawn.remove(card);
        availableMovementPoints += card.getUpwardMovementPoints();

        if (NUMBER_OF_CARDS_TO_REVEAL_IN_ONE_TURN == MAX_NUMBER_OF_CARDS_ON_HAND - cardsDrawn.size()) {
            canRevealCards = false;
        }
    }

    @EventSourcingHandler
    public void on(ClimberMovedEvent event) {
        currentPosition = event.getTo();
        availableMovementPoints -= event.getMovementPointsUsed();
    }

    @EventSourcingHandler
    public void on(PassedEvent event) {
        availableMovementPoints = 0;
    }

    public void onPhaseStarted(Phase phase) {
        if (phase == Phase.CARD_SELECTION) {
            canDrawCards = true;
        }
    }

    public boolean canDrawCards()
    {
        return canDrawCards;
    }

    public boolean canRevealCards()
    {
        return canRevealCards;
    }

    public boolean canReveal(Card card)
    {
        return cardsDrawn.contains(card);
    }

    public Space getCurrentPosition() {
        return currentPosition;
    }

    public Integer getAvailableMovementPoints() {
        return availableMovementPoints;
    }
}
