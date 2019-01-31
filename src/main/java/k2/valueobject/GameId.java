package k2.valueobject;


public class GameId {
    private String value;

    public GameId(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}