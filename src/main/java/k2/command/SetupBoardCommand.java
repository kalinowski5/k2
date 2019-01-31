package k2.command;

public class SetupBoardCommand
{
    private final String id;

    public SetupBoardCommand(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}