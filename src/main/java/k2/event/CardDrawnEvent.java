package k2.event;

import k2.valueobject.Card;
import k2.valueobject.GameId;

public class CardDrawnEvent {

    private final GameId gameId;
    private final Card card;

    public CardDrawnEvent(GameId gameId, Card card) {
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
