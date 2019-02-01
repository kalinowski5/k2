package k2.valueobject;

import k2.exception.WrongCombinationOfCardPointsException;
import org.junit.Assert;
import org.junit.Test;

public class CardTest {

    @Test
    public void cardTypeMovement() throws WrongCombinationOfCardPointsException {
        Card card = new Card(PawnColor.RED, 2, 0, 0);
        Assert.assertSame(CardType.MOVEMENT, card.getType());
    }

    @Test
    public void cardTypeRope() throws WrongCombinationOfCardPointsException  {
        Card card = new Card(PawnColor.RED, 1, 2, 0);
        Assert.assertSame(CardType.ROPE, card.getType());
    }

    @Test
    public void cardTypeAcclimatization() throws WrongCombinationOfCardPointsException  {
        Card card = new Card(PawnColor.RED, 0, 0, 3);
        Assert.assertSame(CardType.ACCLIMATIZATION, card.getType());
    }

    @Test(expected = WrongCombinationOfCardPointsException.class)
    public void wrongCombinationOfPoints() throws WrongCombinationOfCardPointsException  {
        new Card(PawnColor.RED, 1, 0, 3);
    }
}
