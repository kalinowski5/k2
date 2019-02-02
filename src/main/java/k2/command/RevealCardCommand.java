package k2.command;

import k2.valueobject.Card;
import k2.valueobject.GameId;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class RevealCardCommand
{
    @TargetAggregateIdentifier
    private final GameId gameId;
    private final Card card;

    public RevealCardCommand(GameId gameId, Card card) {
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
