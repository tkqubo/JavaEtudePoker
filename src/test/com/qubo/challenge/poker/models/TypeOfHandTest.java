package test.com.qubo.challenge.poker.models;

import static com.qubo.challenge.poker.models.TypeOfHand.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.text.MessageFormat;

import org.junit.Test;

import com.qubo.challenge.poker.models.Hand;
import com.qubo.challenge.poker.models.TypeOfHand;

/**
 * {@link TypeOfHand}用のテストを定義したクラス
 * @author Qubo
 */
public class TypeOfHandTest {
	/** {@link TypeOfHand#getName()}のテスト */
	@Test
	public void testGetName() {
		doTestGetName(StraightFlush, NAME_STRAIGHT_FLUSH);
		doTestGetName(FourOfAKind, NAME_FOUR_OF_A_KIND);
		doTestGetName(FullHouse, NAME_FULL_HOUSE);
		doTestGetName(Flush, NAME_FLUSH);
		doTestGetName(Straight, NAME_STRAIGHT);
		doTestGetName(ThreeOfAKind, NAME_THREE_OF_A_KIND);
		doTestGetName(TwoPair, NAME_TWO_PAIR);
		doTestGetName(OnePair, NAME_ONE_PAIR);
		doTestGetName(HighCards, NAME_HIGH_CARDS);
	}
	/**
	 * {@link #testGetName()}用の内部メソッド
	 * @param typeOfHand 役
	 * @param name 役名
	 */
	private void doTestGetName(TypeOfHand typeOfHand, String name) {
		assertThat(typeOfHand.getName(), is(name));
	}

	/** {@link TypeOfHand#isValid(Hand)}のテスト */
	@Test
	public void testIsValid() {
		doTestIsValid(Hands.fiveOfAKind1, 0);
		doTestIsValid(Hands.fiveOfAKind2, 0);
		doTestIsValid(Hands.royalFlush1, 1);
		doTestIsValid(Hands.royalFlush2, 1);
		doTestIsValid(Hands.royalFlush3, 1);
		doTestIsValid(Hands.royalFlush4, 1);
		doTestIsValid(Hands.straightFlush1, 2);
		doTestIsValid(Hands.straightFlush2, 2);
		doTestIsValid(Hands.straightFlush3, 2);
		doTestIsValid(Hands.straightFlush4, 2);
		doTestIsValid(Hands.fourOfAKind1, 3);
		doTestIsValid(Hands.fourOfAKind2, 3);
		doTestIsValid(Hands.fourOfAKind3, 3);
		doTestIsValid(Hands.fourOfAKind4, 3);
		doTestIsValid(Hands.fullHouse1, 4);
		doTestIsValid(Hands.fullHouse2, 4);
		doTestIsValid(Hands.fullHouse3, 4);
		doTestIsValid(Hands.fullHouse4, 4);
		doTestIsValid(Hands.flush1, 5);
		doTestIsValid(Hands.flush2, 5);
		doTestIsValid(Hands.flush3, 5);
		doTestIsValid(Hands.flush4, 5);
		doTestIsValid(Hands.straight1, 6);
		doTestIsValid(Hands.straight2, 6);
		doTestIsValid(Hands.straight3, 6);
		doTestIsValid(Hands.straight4, 6);
		doTestIsValid(Hands.threeOfAKind1, 7);
		doTestIsValid(Hands.threeOfAKind2, 7);
		doTestIsValid(Hands.threeOfAKind3, 7);
		doTestIsValid(Hands.threeOfAKind4, 7);
		doTestIsValid(Hands.twoPair1, 8);
		doTestIsValid(Hands.twoPair2, 8);
		doTestIsValid(Hands.onePair1, 9);
		doTestIsValid(Hands.onePair2, 9);
		doTestIsValid(Hands.onePair3, 9);
		doTestIsValid(Hands.onePair4, 9);
		doTestIsValid(Hands.highCards1, 10);
		doTestIsValid(Hands.highCards2, 10);
		doTestIsValid(Hands.highCards3, 10);
		doTestIsValid(Hands.highCards4, 10);
	}
	/**
	 * {@link #testIsValid()}用の内部メソッド
	 * @param hand 手札
	 * @param validIndex 正しいと予想される役の、{@link TypeOfHand#ALL}内における位置
	 */
	private void doTestIsValid(Hand hand, int validIndex) {
		for (int i = 0; i < TypeOfHand.ALL.length; i++) {
			TypeOfHand typeOfHand = TypeOfHand.ALL[i];
			boolean result = typeOfHand.isValid(hand);
			if (i == validIndex) {
				assertTrue(MessageFormat.format("{0} expected but we got {1}!", ALL[validIndex], ALL[i]), result);
			} else {
				assertFalse(MessageFormat.format("{0} expected and {1} is not expected!", ALL[validIndex], ALL[i]), result);
			}
		}
	}

	/** {@link TypeOfHand#getTypeOfHand(Hand)}のテスト */
	@Test
	public void testGetTypeOfHand() {
		doTestGetTypeOfHand(Hands.fiveOfAKind1, FiveOfAKind);
		doTestGetTypeOfHand(Hands.fiveOfAKind2, FiveOfAKind);
		doTestGetTypeOfHand(Hands.royalFlush1, RoyalFlush);
		doTestGetTypeOfHand(Hands.royalFlush2, RoyalFlush);
		doTestGetTypeOfHand(Hands.royalFlush3, RoyalFlush);
		doTestGetTypeOfHand(Hands.royalFlush4, RoyalFlush);
		doTestGetTypeOfHand(Hands.straightFlush1, StraightFlush);
		doTestGetTypeOfHand(Hands.straightFlush2, StraightFlush);
		doTestGetTypeOfHand(Hands.straightFlush3, StraightFlush);
		doTestGetTypeOfHand(Hands.straightFlush4, StraightFlush);
		doTestGetTypeOfHand(Hands.fourOfAKind1, FourOfAKind);
		doTestGetTypeOfHand(Hands.fourOfAKind2, FourOfAKind);
		doTestGetTypeOfHand(Hands.fourOfAKind3, FourOfAKind);
		doTestGetTypeOfHand(Hands.fourOfAKind4, FourOfAKind);
		doTestGetTypeOfHand(Hands.fullHouse1, FullHouse);
		doTestGetTypeOfHand(Hands.fullHouse2, FullHouse);
		doTestGetTypeOfHand(Hands.fullHouse3, FullHouse);
		doTestGetTypeOfHand(Hands.fullHouse4, FullHouse);
		doTestGetTypeOfHand(Hands.flush1, Flush);
		doTestGetTypeOfHand(Hands.flush2, Flush);
		doTestGetTypeOfHand(Hands.flush3, Flush);
		doTestGetTypeOfHand(Hands.flush4, Flush);
		doTestGetTypeOfHand(Hands.straight1, Straight);
		doTestGetTypeOfHand(Hands.straight2, Straight);
		doTestGetTypeOfHand(Hands.straight3, Straight);
		doTestGetTypeOfHand(Hands.straight4, Straight);
		doTestGetTypeOfHand(Hands.threeOfAKind1, ThreeOfAKind);
		doTestGetTypeOfHand(Hands.threeOfAKind2, ThreeOfAKind);
		doTestGetTypeOfHand(Hands.threeOfAKind3, ThreeOfAKind);
		doTestGetTypeOfHand(Hands.threeOfAKind4, ThreeOfAKind);
		doTestGetTypeOfHand(Hands.twoPair1, TwoPair);
		doTestGetTypeOfHand(Hands.twoPair2, TwoPair);
		doTestGetTypeOfHand(Hands.onePair1, OnePair);
		doTestGetTypeOfHand(Hands.onePair2, OnePair);
		doTestGetTypeOfHand(Hands.onePair3, OnePair);
		doTestGetTypeOfHand(Hands.onePair4, OnePair);
		doTestGetTypeOfHand(Hands.highCards1, HighCards);
		doTestGetTypeOfHand(Hands.highCards2, HighCards);
		doTestGetTypeOfHand(Hands.highCards3, HighCards);
		doTestGetTypeOfHand(Hands.highCards4, HighCards);
	}
	/**
	 * {@link #testGetTypeOfHand()}用の内部メソッド
	 * @param hand 手札
	 * @param typeOfHand 予想される役
	 */
	private void doTestGetTypeOfHand(Hand hand, TypeOfHand typeOfHand) {
		assertThat(TypeOfHand.getTypeOfHand(hand), is(typeOfHand));
	}
}
