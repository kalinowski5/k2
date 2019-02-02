package k2.exception;

public class NotEnoughMovementPointsException extends Exception {
    final private Integer movementPointsNeeded;
    final private Integer movementPointsAvailable;

    public NotEnoughMovementPointsException(Integer movementPointsNeeded, Integer movementPointsAvailable){
        this.movementPointsNeeded = movementPointsNeeded;
        this.movementPointsAvailable = movementPointsAvailable;
    }

    @Override
    public String getMessage() {
        return "You need " + movementPointsNeeded + " movement points for that move, but you have only " + movementPointsAvailable + " available.";
    }
}
