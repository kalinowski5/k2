package k2.valueobject;

import k2.exception.WrongCombinationOfCardPointsException;

import java.util.Objects;

public class Card {

    private final PawnColor pawnColor;
    private final Integer upwardMovementPoints;
    private final Integer downwardMovementPoints;
    private final Integer acclimatizationPoints;

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

    public PawnColor getPawnColor() {
        return pawnColor;
    }

    public Integer getUpwardMovementPoints() {
        return upwardMovementPoints;
    }

    public Integer getDownwardMovementPoints() {
        return downwardMovementPoints;
    }

    public Integer getAcclimatizationPoints() {
        return acclimatizationPoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card compareTo = (Card) o;

        return Objects.equals(compareTo.getPawnColor(), this.pawnColor) &&
               Objects.equals(compareTo.getUpwardMovementPoints(), this.upwardMovementPoints) &&
               Objects.equals(compareTo.getDownwardMovementPoints(), this.downwardMovementPoints) &&
               Objects.equals(compareTo.getAcclimatizationPoints(), this.acclimatizationPoints);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pawnColor, upwardMovementPoints, downwardMovementPoints, acclimatizationPoints);
    }
}
