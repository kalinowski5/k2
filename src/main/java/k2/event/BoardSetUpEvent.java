package k2.event;

public class BoardSetUpEvent {

    private final String id;

    public BoardSetUpEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
