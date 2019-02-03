package k2.event;

import k2.valueobject.Card;
import k2.valueobject.GameId;
import k2.valueobject.PawnColor;

public class CardDrawnEvent {

    private final GameId gameId;
    private final Card card;
    private final PawnColor player;

    public CardDrawnEvent(GameId gameId, Card card) {
        this.gameId = gameId;
        this.card = card;
        this.player = card.getPawnColor();//Needed for Entity routing id
    }

    public GameId getGameId() {
        return gameId;
    }

    public Card getCard() {
        return card;
    }

    public PawnColor getPlayer() {
        return player;
    }
}
