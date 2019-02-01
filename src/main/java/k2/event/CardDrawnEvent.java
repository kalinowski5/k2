package k2.event;

import k2.valueobject.CardType;
import k2.valueobject.GameId;
import k2.valueobject.PawnColor;

public class CardDrawnEvent {

    private final GameId gameId;
    private final PawnColor player;
    private final CardType cardType;
    private final Integer points;

    public CardDrawnEvent(GameId gameId, PawnColor player, CardType cardType, Integer points) {
        this.gameId = gameId;
        this.player = player;
        this.cardType = cardType;
        this.points = points;
    }

    public GameId getGameId() {
        return gameId;
    }

    public PawnColor getPlayer() {
        return player;
    }

    public CardType getCardType() {
        return cardType;
    }

    public Integer getPoints() {
        return points;
    }
}
