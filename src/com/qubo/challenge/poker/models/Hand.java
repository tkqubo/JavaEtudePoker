package com.qubo.challenge.poker.models;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * ポーカーの手札を表現したクラス
 * @author Qubo
 */
public class Hand implements Iterable<Card> {
	Card[] cards;

	/**
	 * 標準のコンストラクタ
	 * @param cards 配られた5枚のカードを、配列として表現したもの
	 */
	Hand(Card card1, Card card2, Card card3, Card card4, Card card5) {
		this.cards = new Card[] { card1, card2, card3, card4, card5 };
	}
	/**
	 * カードの文字列を利用したコンストラクタ
	 * @param c1 1枚目のカードの文字列表現
	 * @param c2 2枚目のカードの文字列表現
	 * @param c3 3枚目のカードの文字列表現
	 * @param c4 4枚目のカードの文字列表現
	 * @param c5 5枚目のカードの文字列表現
	 * @throws CardException 文字列のフォーマットがおかしい場合に発生
	 */
	public Hand(String c1, String c2, String c3, String c4, String c5) throws CardException {
		this(Card.parse(c1), Card.parse(c2), Card.parse(c3), Card.parse(c4), Card.parse(c5));
	}

	/**
	 * {@code index}の位置にあるカードを取得する。
	 * @param index カードの位置
	 * @return {@link Card}インスタンス
	 */
	public Card get(int index) { return cards[index]; }

	/*
	 * (非 Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return MessageFormat.format("[{0}][{1}][{2}][{3}][{4}]", cards[0], cards[1], cards[2], cards[3], cards[4]);
	}
	/*
	 * (非 Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Card> iterator() {
		return Collections.unmodifiableList(Arrays.asList(cards)).iterator();
	}
	/**
	 * 手札の中のジョーカーの数を数える
	 * @return ジョーカーの数
	 */
	public int getJoker() {
		int total = 0;
		for (Card card : cards)
			if (card.getSuit() == Suit.Joker)
				total++;
		return total;
	}
	/**
	 * 手札の中から、最小の数を取得する。
	 * ジョーカーは無視される。
	 * @return 最小の数
	 */
	public int getLowestNumber() {
		int min = Integer.MAX_VALUE;
		for (Card card : cards)
			if (card.getSuit() != Suit.Joker)
				min = Math.min(min, card.getRawNumber());
		return min;
	}
	/**
	 * 手札の中から、最大の数を取得する。
	 * ジョーカーは無視される。
	 * @return 最大の数
	 */
	public int getHighestNumber() {
		int max = Integer.MIN_VALUE;
		for (Card card : cards)
			if (card.getSuit() != Suit.Joker)
				max = Math.max(max, card.getRawNumber());
		return max;
	}
	/**
	 * 手札の中に、同じ数字のカードが{@code count}枚揃っているような組が、
	 * 何セットあるかを返す。<br />
	 * 例えば{@code (isOfAkind(hand, 2) == 2)}となった場合はツーペアとなり、
	 * {@code (isOfAkind(hand, 3) == 1 && isOfAkind(hand, 2) == 1)}となった場合はフルハウスとなる。
	 * ジョーカーはカウントに加えない。
	 * @param count 揃っているべき枚数
	 * @return セット数
	 */
	public int isOfAKind(int count) {
		int found = 0;
		Map<Integer, Integer> kinds = new HashMap<Integer, Integer>();
		for (Card card : cards) {
			int number = card.getRawNumber();
			if (number != Card.RAW_VALUE_JOKER) {
				if (!kinds.containsKey(number)) {
					kinds.put(number, 0);
				}
				kinds.put(number, kinds.get(number) + 1);
			}
		}
		for (Integer value : kinds.values()) {
			if (value == count) found++;
		}
		return found;
	}
	public boolean hasNumber(int number) {
		for (Card card : cards) {
			if (card.getRawNumber() == number) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 手札が、指定された数字から連続した数字で構成されているかどうかを取得する
	 * @param start シーケンスの開始数字
	 * @return 手札が指定された数字から連続した数字で構成されているかどうか
	 */
	public boolean isSequentialFrom(int start) {
		int jokerRemain = getJoker();
		for (int num = start; num < start + 5; num++) {
			if (!hasNumber(num))
				jokerRemain--;
			if (jokerRemain < 0)
				return false;
		}
		return true;
	}
	/**
	 * 手札が連続した数字で構成されているかどうかを取得する
	 * @return 手札が連続した数字で構成されているかどうか
	 */
	public boolean isSequential() {
		return isSequentialFrom(Math.min(getLowestNumber(), 10));
	}
	/**
	 * 手札が全て同じマークであるかどうかを取得する
	 * ジョーカーは無視される。。
	 * @return 手札が全て同じマークであるかどうか
	 */
	public boolean isSameSuit() {
		Suit suit = null;
		for (Card card : cards) {
			if (card.getSuit() != Suit.Joker) {
				if (suit != null && suit != card.getSuit())
					return false;
				suit = card.getSuit();
			}
		}
		return true;
	}
}
