package k2.entity;

import k2.valueobject.PawnColor;
import org.axonframework.commandhandling.model.EntityId;

public class Player {

    @EntityId
    private PawnColor color;
    private String name;

    public Player(PawnColor color, String name) {
        this.color = color;
        this.name = name;
    }

    public PawnColor getColor() {
        return color;
    }

    public String getName() {
        return name;
    }
}
