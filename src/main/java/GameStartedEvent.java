public class GameStartedEvent {

    private final String id;
    private final Integer numberOfPlayers;

    public GameStartedEvent(String id, Integer numberOfPlayers) {
        this.id = id;
        this.numberOfPlayers = numberOfPlayers;
    }

    public String getId() {
        return id;
    }

    public Integer getNumberOfPlayers() {
        return numberOfPlayers;
    }
}
