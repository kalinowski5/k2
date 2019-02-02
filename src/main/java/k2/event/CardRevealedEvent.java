package k2.event;

import k2.valueobject.Card;
import k2.valueobject.GameId;

public class CardRevealedEvent {

    private final GameId gameId;
    private final Card card;

    public CardRevealedEvent(GameId gameId, Card card) {
        this.gameId = gameId;
        this.card = card;
    }

    public GameId getGameId() {
        return gameId;
    }

    public Card getCard() {
        return card;
    }
}
