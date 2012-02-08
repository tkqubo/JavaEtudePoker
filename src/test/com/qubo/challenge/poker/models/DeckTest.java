package test.com.qubo.challenge.poker.models;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.qubo.challenge.poker.models.Card;
import com.qubo.challenge.poker.models.CardException;
import com.qubo.challenge.poker.models.Deck;
import com.qubo.challenge.poker.models.Hand;
import com.qubo.challenge.poker.models.Suit;

/**
 * {@link Deck}用のテストを定義したクラス
 * @author Qubo
 */
public class DeckTest {
	/** {@link Deck#deal()}のテスト */
	@Test
	public void testDeal() {
		doTestDeal(0, 3);
		doTestDeal(1, 2);
		doTestDeal(2, 1);
	}
	/**
	 * {@link #testDeal()}用の内部クラス
	 * @param jokerCount {@link Hand}インスタンスを作る際に指定する、ジョーカーの枚数
	 * @param cardNeeded 最後に{@link Deck#deal()}を呼び出すときに、足りなくなるであろうカードの枚数
	 */
	private void doTestDeal(int jokerCount, int cardNeeded) {
		Deck deck = safeCreateDeck(jokerCount);
		Hand hand, handOld = null;
		try {
			for (int i = 0; i < 10; i++) {
				hand = deck.deal();
				assertNotNull(hand);
				areDifferent(hand, handOld);
				handOld = hand;
			}
		} catch (CardException e) {
			fail(e.getMessage());
		}
		try {
			hand = deck.deal();
			fail();
		} catch (CardException e) {
			assertThat(e.getMessage(), is(MessageFormat.format(Deck.ERROR_DECK_CARD_DEFICIT, cardNeeded)));
		}
	}
	/**
	 * {@link Deck#deal()}を安全に呼び出すための内部メソッド
	 * @param deck {@link Deck#deal()}の呼び出し先となる{@link Deck}インスタンス
	 * @return {@link Hand}インスタンス
	 */
	private Hand safeDeal(Deck deck) {
		try {
			return deck.deal();
		} catch (CardException e) {
			return null;
		}
	}
	/**
	 * {@link Deck}インスタンスを安全に呼び出すための内部メソッド
	 * @return {@link Deck}インスタンス
	 */
	private Deck safeCreateDeck() { return safeCreateDeck(0); }
	/**
	 * {@link Deck}インスタンスを安全に呼び出すための内部メソッド
	 * @param jokerCount ジョーカーの枚数
	 * @return {@link Deck}インスタンス
	 */
	private Deck safeCreateDeck(int jokerCount) {
		Deck deck = null;
		try {
			deck = new Deck(jokerCount);
		} catch (CardException e) { }
		return deck;
	}
	/**
	 * {@link #testDeal()}用の内部メソッド
	 * @param hand 交換後の手札
	 * @param handOld 元の手札
	 */
	private void areDifferent(Hand hand, Hand handOld) {
		if (handOld != null) {
			for (Card card1 : hand) {
				for (Card card2 : handOld) {
					if (card1.getSuit() != Suit.Joker) {
						assertFalse(MessageFormat.format("[{0}] appeared twice!", card1), card1.equals(card2));
					}
				}
			}
		}
	}

	/** {@link Deck#change(Hand, int...)}のテスト */
	@Test
	public void testChange() {
		Deck deck = safeCreateDeck();
		Hand hand = safeDeal(deck);
		doTestChange(deck, hand, 0, 1, 2);
		doTestChange(deck, hand, 1, 3, 4);
		doTestChange(deck, hand);
		doTestChange(deck, hand, 0, 1, 2, 3, 4);
		doTestChange(deck, hand, 1, 2, 3);
		doTestChange(deck, hand, 0);
		doTestChange(deck, hand, 1);
		doTestChange(deck, hand, 2);
		doTestChange(deck, hand, 3);
		doTestChange(deck, hand, 4);
		doTestChange(deck, hand, 0, 1, 2, 3, 4);
		doTestChange(deck, hand, 0, 1, 2, 3, 4);
		doTestChange(deck, hand, 0);
		doTestChange(deck, hand, 0, 1, 2, 3, 4);
		doTestChange(deck, hand, 0, 1, 2, 3, 4);
		doTestChange(deck, hand, 0, 1, 2, 3, 4);
		// 2 cards left in the deck
		doTestChangeNoChange(deck, hand, 3, 0, 1, 2, 3, 4);
		doTestChangeNoChange(deck, hand, 2, 0, 1, 2, 3);
		doTestChangeNoChange(deck, hand, 1, 0, 1, 4);
		doTestChange(deck, hand, 1, 2);
		// the deck exhausted
		doTestChangeNoChange(deck, hand, 1, 0);
		doTestChangeNoChange(deck, hand, 1, 4);

		//
		// with a Joker
		//
		deck = safeCreateDeck(1);
		hand = safeDeal(deck);
		doTestChange(deck, hand, 0, 1, 2);
		doTestChange(deck, hand, 1, 3, 4);
		doTestChange(deck, hand);
		doTestChange(deck, hand, 0, 1, 2, 3, 4);
		doTestChange(deck, hand, 1, 2, 3);
		doTestChange(deck, hand, 0);
		doTestChange(deck, hand, 1);
		doTestChange(deck, hand, 2);
		doTestChange(deck, hand, 3);
		doTestChange(deck, hand, 4);
		doTestChange(deck, hand, 0, 1, 2, 3, 4);
		doTestChange(deck, hand, 0, 1, 2, 3, 4);
		doTestChange(deck, hand, 0);
		doTestChange(deck, hand, 0, 1, 2, 3, 4);
		doTestChange(deck, hand, 0, 1, 2, 3, 4);
		doTestChange(deck, hand, 0, 1, 2, 3, 4);
		// 3 cards left in the deck
		doTestChangeNoChange(deck, hand, 2, 0, 1, 2, 3, 4);
		doTestChangeNoChange(deck, hand, 1, 0, 1, 2, 3);
		doTestChange(deck, hand, 1);
		// 2 cards left in the deck
		doTestChangeNoChange(deck, hand, 3, 0, 1, 2, 3, 4);
		doTestChangeNoChange(deck, hand, 2, 0, 1, 2, 3);
		doTestChangeNoChange(deck, hand, 1, 0, 1, 4);
		doTestChange(deck, hand, 1, 2);
		// the deck exhausted
		doTestChangeNoChange(deck, hand, 1, 0);
		doTestChangeNoChange(deck, hand, 1, 4);

		//
		// with 2 Jokers
		//
		deck = safeCreateDeck(2);
		hand = safeDeal(deck);
		doTestChange(deck, hand, 0, 1, 2);
		doTestChange(deck, hand, 1, 3, 4);
		doTestChange(deck, hand);
		doTestChange(deck, hand, 0, 1, 2, 3, 4);
		doTestChange(deck, hand, 1, 2, 3);
		doTestChange(deck, hand, 0);
		doTestChange(deck, hand, 1);
		doTestChange(deck, hand, 2);
		doTestChange(deck, hand, 3);
		doTestChange(deck, hand, 4);
		doTestChange(deck, hand, 0, 1, 2, 3, 4);
		doTestChange(deck, hand, 0, 1, 2, 3, 4);
		doTestChange(deck, hand, 0);
		doTestChange(deck, hand, 0, 1, 2, 3, 4);
		doTestChange(deck, hand, 0, 1, 2, 3, 4);
		doTestChange(deck, hand, 0, 1, 2, 3, 4);
		// 4 cards left in the deck
		doTestChangeNoChange(deck, hand, 1, 0, 1, 2, 3, 4);
		doTestChange(deck, hand, 1);
		// 3 cards left in the deck
		doTestChangeNoChange(deck, hand, 2, 0, 1, 2, 3, 4);
		doTestChangeNoChange(deck, hand, 1, 0, 1, 2, 3);
		doTestChange(deck, hand, 1, 2);
		// 1 card left in the deck
		doTestChangeNoChange(deck, hand, 4, 0, 1, 2, 3, 4);
		doTestChangeNoChange(deck, hand, 3, 0, 1, 2, 3);
		doTestChangeNoChange(deck, hand, 2, 0, 1, 4);
		doTestChangeNoChange(deck, hand, 1, 2, 4);
		doTestChange(deck, hand, 1);
		// the deck exhausted
		doTestChangeNoChange(deck, hand, 1, 0);
		doTestChangeNoChange(deck, hand, 1, 4);
	}

	/**
	 * {@link #testChange()}用の内部メソッド
	 * @param deck デッキ
	 * @param hand 手札
	 * @param indices 交換したいカードの位置
	 */
	private void doTestChange(Deck deck, Hand hand, int... indices) {
		List<Card> list = new ArrayList<Card>();
		for (Card card : hand) {
			list.add(card);
		}
		try {
			deck.change(hand, indices);
		} catch (CardException e) {
			fail(e.getMessage());
		}
		for (int i : indices) {
			assertNotSame(hand.get(i), list.get(i));
		}
		for (int i = 0; i < 5; i++) {
			int found = Arrays.binarySearch(indices, i);
			if (found == -1) {
				assertThat(hand.get(i), is(list.get(i)));
			}
		}
	}
	/**
	 * {@link #testChange()}用の内部メソッド。デッキ内にカードが足りず、例外が発生する。
	 * @param deck デッキ
	 * @param hand 手札
	 * @param deficits 不足している枚数
	 * @param indices 交換したいカードの位置
	 */
	private void doTestChangeNoChange(Deck deck, Hand hand, int deficits, int... indices) {
		List<Card> list = new ArrayList<Card>();
		for (Card card : hand) {
			list.add(card);
		}
		try {
			deck.change(hand, indices);
		} catch (CardException e) {
			String message = MessageFormat.format(Deck.ERROR_DECK_CARD_DEFICIT, deficits);
			assertThat(e.getMessage(), is(message));
		}
		for (int i = 0; i < 5; i++) {
			assertThat(hand.get(i), is(list.get(i)));
		}
	}
	/** {@link Deck#getRemainings()}のテスト */
	@Test
	public void testGetRemainings() {
		try {
			Deck deck;
			Hand hand;

			deck = new Deck();
			assertThat(deck.getRemainings(), is(52));
			hand = deck.deal();
			assertThat(deck.getRemainings(), is(47));
			hand = deck.deal();
			assertThat(deck.getRemainings(), is(42));
			deck.change(hand, 1);
			assertThat(deck.getRemainings(), is(41));
			deck.change(hand);
			assertThat(deck.getRemainings(), is(41));
			deck.change(hand, 1, 2);
			assertThat(deck.getRemainings(), is(39));
			deck.deal();
			deck.deal();
			deck.deal();
			assertThat(deck.getRemainings(), is(24));
			deck.change(hand, 1, 2, 3, 4);
			assertThat(deck.getRemainings(), is(20));
			deck.change(hand, 1, 2, 4);
			assertThat(deck.getRemainings(), is(17));
			deck.change(hand, 2, 4);
			assertThat(deck.getRemainings(), is(15));
			deck.change(hand, 1, 2, 3, 4, 0);
			deck.change(hand, 1, 2, 3, 4, 0);
			assertThat(deck.getRemainings(), is(5));
			deck.change(hand, 1, 2, 3);
			assertThat(deck.getRemainings(), is(2));
			deck.change(hand, 1);
			deck.change(hand, 1);
			assertThat(deck.getRemainings(), is(0));

			deck = new Deck(0);
			assertThat(deck.getRemainings(), is(52));
			deck = new Deck(1);
			assertThat(deck.getRemainings(), is(53));
			deck = new Deck(2);
			assertThat(deck.getRemainings(), is(54));
			deck.deal();
			deck.deal();
			deck.deal();
			deck.deal();
			deck.deal();
			deck.deal();
			deck.deal();
			deck.deal();
			deck.deal();
			deck.deal();
			assertThat(deck.getRemainings(), is(4));
			deck.change(hand, 1, 3, 4, 0);
			assertThat(deck.getRemainings(), is(0));
		} catch (CardException e) {
			fail(e.getMessage());
		}
	}
}
