package k2.valueobject;

import k2.exception.WrongCombinationOfCardPointsException;

public class Card {

    private PawnColor pawnColor;
    private Integer upwardMovementPoints;
    private Integer downwardMovementPoints;
    private Integer acclimatizationPoints;

    public Card(PawnColor pawnColor, Integer upwardMovementPoints, Integer downwardMovementPoints, Integer acclimatizationPoints) throws WrongCombinationOfCardPointsException
    {
        if (acclimatizationPoints > 0 && (upwardMovementPoints > 0 || downwardMovementPoints > 0)){
            throw new WrongCombinationOfCardPointsException();
        }

        this.pawnColor = pawnColor;
        this.upwardMovementPoints = upwardMovementPoints;
        this.downwardMovementPoints = downwardMovementPoints;
        this.acclimatizationPoints = acclimatizationPoints;
    }

    public CardType getType() {

        if (this.downwardMovementPoints > 0) {
            return CardType.ROPE;
        }

        if (this.upwardMovementPoints > 0) {
            return CardType.MOVEMENT;
        }

        if (this.acclimatizationPoints > 0) {
            return CardType.ACCLIMATIZATION;
        }

        return CardType.RESCUE;
    }
}
