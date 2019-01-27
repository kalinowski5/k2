package hello;

public class StartGameCommand
{
    private final String id;
    private final Integer numberOfPlayers;

    public StartGameCommand(String id, Integer numberOfPlayers) {
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
