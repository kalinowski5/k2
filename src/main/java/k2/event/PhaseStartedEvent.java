package k2.event;

import k2.valueobject.GameId;
import k2.valueobject.Phase;

public class PhaseStartedEvent {

    private final GameId gameId;
    private final Phase phase;

    public PhaseStartedEvent(GameId gameId, Phase phase) {
        this.gameId = gameId;
        this.phase = phase;
    }

    public GameId getGameId() {
        return gameId;
    }

    public Phase getPhase() {
        return phase;
    }
}
