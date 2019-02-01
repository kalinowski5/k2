package k2.entity;

import k2.valueobject.PawnColor;
import org.axonframework.commandhandling.model.EntityId;

public class Player {

    private static final int MAX_NUMBER_OF_CARDS_ON_HAND = 6;
    private static final int MAX_NUMBER_OF_CARDS_AVAIBALE = 18;

    @EntityId
    private PawnColor color;
    private String name;
    private Integer cardsDrawn;

    public Player(PawnColor color, String name) {
        this.color = color;
        this.name = name;
        this.cardsDrawn = 0;
    }

    public Integer getNumberOfCardToDraw()
    {
        Integer cardsToDrawNow = MAX_NUMBER_OF_CARDS_ON_HAND - this.cardsDrawn;
        this.cardsDrawn = MAX_NUMBER_OF_CARDS_ON_HAND;

        return cardsToDrawNow;
    }

    public void drawOneCard()
    {
        this.cardsDrawn++;
    }
}
