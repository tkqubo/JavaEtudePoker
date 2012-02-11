package test.com.qubo.challenge.poker.models;

import static com.qubo.challenge.poker.models.Suit.Club;
import static com.qubo.challenge.poker.models.Suit.Diamond;
import static com.qubo.challenge.poker.models.Suit.Heart;
import static com.qubo.challenge.poker.models.Suit.SYMBOL_CLUB;
import static com.qubo.challenge.poker.models.Suit.SYMBOL_DIAMOND;
import static com.qubo.challenge.poker.models.Suit.SYMBOL_HEART;
import static com.qubo.challenge.poker.models.Suit.SYMBOL_SPADE;
import static com.qubo.challenge.poker.models.Suit.Spade;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.qubo.challenge.poker.models.Suit;

/**
 * {@link Suit}用のテストを定義したクラス
 * @author Qubo
 */
public class SuitTest {
	/** {@link Suit#getSymbol()}のテスト */
	@Test
	public void testGetSymbol() {
		doTestGetSymbol(Spade, SYMBOL_SPADE);
		doTestGetSymbol(Diamond, SYMBOL_DIAMOND);
		doTestGetSymbol(Club, SYMBOL_CLUB);
		doTestGetSymbol(Heart, SYMBOL_HEART);
	}
	/** {@link #testGetSymbol()}用の内部メソッド */
	private void doTestGetSymbol(Suit suit, char symbolSpade) {
		assertThat(suit.getSymbol(), is(symbolSpade));
	}

}
