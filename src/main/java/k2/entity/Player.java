package k2.entity;

import k2.exception.WrongCombinationOfCardPointsException;
import k2.valueobject.Card;
import k2.valueobject.PawnColor;
import org.axonframework.commandhandling.model.EntityId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player
{

    private static final int MAX_NUMBER_OF_CARDS_ON_HAND = 6;

    @EntityId
    private PawnColor color;
    private String name;

    private List<Card> cardsNotDrawn = new ArrayList<>();
    private List<Card> cardsDrawn = new ArrayList<>();
    private List<Card> cardsUsed = new ArrayList<>();

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

        List<Card> cards = new ArrayList<>();
        Integer cardsToDrawNow = MAX_NUMBER_OF_CARDS_ON_HAND - this.cardsDrawn.size();

        for (Integer index = 0; index < cardsToDrawNow; index++) {
            cards.add(cardsNotDrawn.get(index));
        }

        return cards;
    }

    public void drawOneCard(Card card)
    {
        cardsDrawn.add(card);
        cardsNotDrawn.remove(card);
    }

    public boolean canReveal(Card card)
    {
        return cardsDrawn.contains(card);
    }
}
