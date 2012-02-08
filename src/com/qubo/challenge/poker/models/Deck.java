package com.qubo.challenge.poker.models;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * トランプの１セット（＝デッキ）を表現するクラス。
 * @author Qubo
 */
public class Deck {
	/** デッキ内に含むことのできるジョーカーの最大枚数: {@code 2} */
	public static final int JOKER_COUNT_MAX = 2;
	/** デッキ内に交換できるカードが足りない場合に発生:
	 * <code>
	 * "デッキ内の交換できるカードが{0}枚足りません！"
	 * </code>
	 */
	public static final String ERROR_DECK_CARD_DEFICIT = "デッキにカードが{0}枚足りません！";
	/** ジョーカーの枚数指定がおかしい場合に発生する例外のメッセージ:
	 * <code>
	 * "ジョーカーの数は0枚～2枚の間で設定してください！"
	 * </code>
	 */
	public static final String ERROR_JOKER_COUNT_RANGE = "ジョーカーの数は0枚～2枚の間で設定してください！";
	private static final Random random = new Random();
	private final List<Card> cards;
	private final List<Card> discardPile;
	private final int jokerCount;

	/**
	 * デッキの中に含まれるジョーカーの数を取得する
	 * @return ジョーカーの数
	 */
	public int getJokerCount() { return jokerCount; }

	/**
	 * コンストラクタ。
	 * @param jokerCount ジョーカーの枚数
	 * @throws CardException ジョーカーの枚数指定がおかしい場合に表示
	 */
	public Deck(int jokerCount) throws CardException {
		if (jokerCount < 0 || jokerCount > JOKER_COUNT_MAX) throw new CardException(ERROR_JOKER_COUNT_RANGE);
		this.cards = new ArrayList<Card>();
		this.discardPile = new ArrayList<Card>();
		this.jokerCount = jokerCount;
		for (Suit suit : Suit.values()) {
			if (suit != Suit.Joker) {
				for (int i = 2; i <= 14; i++) {
					Card card = new Card(suit, i);
					cards.add(card);
				}
			}
		}
		for (int i = 0; i < jokerCount; i++) {
			cards.add(new Card(Suit.Joker, Card.RAW_VALUE_JOKER));
		}
	}
	/** 標準のコンストラクタ。ジョーカーを含まない全52枚で構成される。
	 * @throws CardException */
	public Deck() throws CardException { this(0); }

	/**
	 * デッキの中からランダムに5枚抜き出し、それをコンストラクタ引数として
	 * {@link Hand}インスタンスを生成し、返す。<br />
	 * デッキの中のカード枚数が5枚以下の場合は、{@code null}を返す。
	 * @return {@link Hand}インスタンス
	 * @throws CardException カードが足りない場合に発生
	 */
	public Hand deal() throws CardException {
		if (cards.size() < 5) throw new CardException(MessageFormat.format(ERROR_DECK_CARD_DEFICIT, 5 - cards.size()));

		Card[] dealt = new Card[5];

		for (int i = 0; i < 5; i++) {
			Card card = draw();
			dealt[i] = card;
		}

		return new Hand(dealt);
	}
	/**
	 * {@link Hand}インスタンスの手の中から、
	 * {@code indices}で指定された位置にあるカードを捨て、
	 * 捨てた枚数だけ新たにデッキからカードを補充する。<br />
	 * @param hand 交換対象の手札
	 * @param indices 交換したいカードの位置を表した配列
	 * @throws CardException カードが足りない場合に発生
	 */
	public void change(Hand hand, int... indices) throws CardException {
		if (cards.size() < indices.length) throw new CardException(MessageFormat.format(ERROR_DECK_CARD_DEFICIT, indices.length - cards.size()));

		for (int index : indices) {
			Card discarded = hand.cards[index];
			discardPile.add(discarded);
			hand.cards[index] = draw();
		}
	}
	/**
	 * デッキの中に残ったカードの枚数を取得する
	 * @return 残ったカード枚数
	 */
	public int getRemainings() { return cards.size(); }
	/**
	 * デッキからランダムに{@link Card}インスタンスを選んで返す。
	 * 選択された{@link Card}インスタンスは、デッキから取り除かれる。
	 * @return
	 */
	private Card draw() {
		return cards.remove(random.nextInt(cards.size()));
	}
}
