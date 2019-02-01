package k2.event;

import k2.valueobject.GameId;

public class CardDrawnEvent {

    private final GameId gameId;
    private final String player;
    private final String cardType;
    private final Integer points;

    public CardDrawnEvent(GameId gameId, String player, String cardType, Integer points) {
        this.gameId = gameId;
        this.player = player;
        this.cardType = cardType;
        this.points = points;
    }

    public GameId getGameId() {
        return gameId;
    }

    public String getPlayer() {
        return player;
    }

    public String getCardType() {
        return cardType;
    }

    public Integer getPoints() {
        return points;
    }
}
