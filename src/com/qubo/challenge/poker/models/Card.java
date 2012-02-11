package com.qubo.challenge.poker.models;

import java.text.MessageFormat;

import com.qubo.Utils;

/**
 * トランプのカードを表したクラス。
 * @author Qubo
 */
public class Card {
	/** 文字列のフォーマットがおかしい場合に発生 */
	public static final String ERROR_PARSE = "文字列[{0}]中の[{1}]を処理できませんでした！";
	/** コンストラクタに与えた数値が、範囲外の場合に発生 */
	public static final String ERROR_NUMBER_OUT_OF_RANGE = "ジョーカー以外のカードを生成する場合は、数値を2～14の範囲内で指定してください！";
	/** コンストラクタに与えたマークが{@code null}だった場合に発生 */
	public static final String ERROR_SUIT_NULL = "マークにはnullを指定できません！";

	/** ジャックの文字表現 */
	public static final char SYMBOL_JACK = 'J';
	/** クイーンの文字表現 */
	public static final char SYMBOL_QUEEN = 'Q';
	/** キングの文字表現 */
	public static final char SYMBOL_KING = 'K';
	/** エースの文字表現 */
	public static final char SYMBOL_ACE = 'A';
	/** ジョーカーの文字表現 */
	public static final char SYMBOL_JOKER = ' ';
	/** ジョーカーの数字 */
	public static final int RAW_VALUE_JOKER = -1;

	private final Suit suit;
	private final int number;

	/**
	 * 標準のコンストラクタ。
	 * ジョーカーを生成する場合は、{@code number}の値は無視され、代わりに{@link #RAW_VALUE_JOKER}が適用される。
	 * @param suit マーク
	 * @param number 数字（エースを14として、2～14までで全数字を表現する）
	 * @throws CardException コンストラクタの引数が間違っている場合に発生する
	 */
	public Card(Suit suit, int number) throws CardException {
		if (suit == null) throw new CardException(ERROR_SUIT_NULL);
		if (suit != Suit.Joker && (number < 2 || number > 14)) throw new CardException(ERROR_NUMBER_OUT_OF_RANGE);

		this.suit = suit;
		this.number = (suit != Suit.Joker) ? number : RAW_VALUE_JOKER;
	}
	/**
	 * 文字列を解析して{@link Card}インスタンスを生成する。<br />
	 * 文字列はマーク＋数字から成り立っており、
	 * マークはクラブ、ダイヤモンド、ハート、スペードそれぞれがC, D, H, Sの一文字に対応する。<br />
	 * 数字はエースを14として、2～14までで全数字を表現する。<br />
	 * 例外として、ジョーカーは空白2文字{@code "  "}で生成する。
	 * @param format 解析する文字列
	 * @return 生成された{@link Card}インスタンス
	 * @throws CardException 文字列のフォーマットがおかしい場合に発生
	 */
	public static Card parse(String format) throws CardException {
		Suit suit;
		int number;

		char chSuit = format.charAt(0);
		switch (chSuit) {
		case Suit.SYMBOL_HEART:
			suit = Suit.Heart;
			break;
		case Suit.SYMBOL_SPADE:
			suit = Suit.Spade;
			break;
		case Suit.SYMBOL_DIAMOND:
			suit = Suit.Diamond;
			break;
		case Suit.SYMBOL_CLUB:
			suit = Suit.Club;
			break;
		case Suit.SYMBOL_JOKER:
			suit = Suit.Joker;
			break;
		default:
			throw new CardException(MessageFormat.format(ERROR_PARSE, format, chSuit));
		}
		if (format.length() == 2) {
			char chNumber = format.charAt(1);
			switch (chNumber) {
			case SYMBOL_JACK:
				number = 11;
				break;
			case SYMBOL_QUEEN:
				number = 12;
				break;
			case SYMBOL_KING:
				number = 13;
				break;
			case SYMBOL_ACE:
				number = 14;
				break;
			case SYMBOL_JOKER:
				number = RAW_VALUE_JOKER;
				break;
			default:
				try {
					number = Integer.parseInt("" + chNumber);
				} catch (NumberFormatException e) {
					throw new CardException(MessageFormat.format(ERROR_PARSE, format, chNumber));
				}
				break;
			}
		} else if (format.substring(1).equals("10")) {
			number = 10;
		} else {
			throw new CardException(MessageFormat.format(ERROR_PARSE, format, format.substring(1)));
		}

		return new Card(suit, number);
	}

	/**
	 * カードのマークを取得する
	 * @return カードのマーク
	 */
	public Suit getSuit() { return suit; }
	/**
	 * マークの文字列表現を取得する。<br />
	 * <ul>
	 * <li>{@link Suit#Heart}のカードは{@link Suit#SYMBOL_HEART}</li>
	 * <li>{@link Suit#Spade}のカードは{@link Suit#SYMBOL_SPADE}</li>
	 * <li>{@link Suit#Diamond}のカードは{@link Suit#SYMBOL_DIAMOND}</li>
	 * <li>{@link Suit#Club}のカードは{@link Suit#SYMBOL_CLUB}</li>
	 * </ul>
	 * を文字列として返す。
	 * @return マークの文字列表現
	 */
	public String getSuitSymbol() { return "" + suit.getSymbol(); }
	/**
	 * マークの文字列表現を全角で取得する。<br />
	 * <ul>
	 * <li>{@link Suit#Heart}のカードは{@link Suit#SYMBOL_HEART}の全角</li>
	 * <li>{@link Suit#Spade}のカードは{@link Suit#SYMBOL_SPADE}の全角</li>
	 * <li>{@link Suit#Diamond}のカードは{@link Suit#SYMBOL_DIAMOND}の全角</li>
	 * <li>{@link Suit#Club}のカードは{@link Suit#SYMBOL_CLUB}の全角</li>
	 * </ul>
	 * を文字列として返す。
	 * @return マークの2バイト文字列表現
	 */
	public String getSuitSymbol2Bytes() { return "" + Utils.toFullWidth(suit.getSymbol()); }
	/**
	 * カードの数字を取得する。<br />
	 * <b>※エースは14で表現する</b>。
	 * @return カードの数字
	 */
	public int getRawNumber() { return number; }
	/**
	 * 数値の文字列表現を取得する。<br />
	 * <ul>
	 * <li>11の場合は{@link Card#SYMBOL_JACK}</li>
	 * <li>12の場合は{@link Card#SYMBOL_QUEEN}</li>
	 * <li>13の場合は{@link Card#SYMBOL_KING}</li>
	 * <li>14の場合は{@link Card#SYMBOL_ACE}</li>
	 * <li>{@link #RAW_VALUE_JOKER}の場合は{@link Card#SYMBOL_JOKER}</li>
	 * </ul>
	 * それ以外の場合は数字をそのまま文字列にしたものを返す。
	 * @return 数値の文字列表現
	 */
	public String getNumberSymbol() {
		switch (number) {
		case 11:	return "" + SYMBOL_JACK;
		case 12:	return "" + SYMBOL_QUEEN;
		case 13:	return "" + SYMBOL_KING;
		case 14:	return "" + SYMBOL_ACE;
		case RAW_VALUE_JOKER: return "" + SYMBOL_JOKER;
		default:	return "" + number;
		}
	}
	/**
	 * 数値の文字列表現を2バイトで取得する。<br />
	 * <ul>
	 * <li>10の場合はそのまま{@code "10"}</li>
	 * <li>11の場合は全角の{@link Card#SYMBOL_JACK}</li>
	 * <li>12の場合は全角の{@link Card#SYMBOL_QUEEN}</li>
	 * <li>13の場合は全角の{@link Card#SYMBOL_KING}</li>
	 * <li>14の場合は全角の{@link Card#SYMBOL_ACE}</li>
	 * <li>{@link #RAW_VALUE_JOKER}の場合は{@link Card#SYMBOL_JOKER}</li>
	 * </ul>
	 * それ以外の場合は数字を全角文字にして返す。
	 * @return 数値の2バイト文字列表現
	 */
	public String getNumberSymbol2Bytes() {
		switch (number) {
		case 10: return "10";
		case 11: return "" + Utils.toFullWidth(SYMBOL_JACK);
		case 12: return "" + Utils.toFullWidth(SYMBOL_QUEEN);
		case 13: return "" + Utils.toFullWidth(SYMBOL_KING);
		case 14: return "" + Utils.toFullWidth(SYMBOL_ACE);
		case RAW_VALUE_JOKER: return "" + Utils.toFullWidth(SYMBOL_JOKER);
		default: return "" + Utils.toFullWidth(("" + number).charAt(0));
		}
	}

	/*
	 * (非 Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() { return getSuitSymbol() + getNumberSymbol(); }
	/*
	 * (非 Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Card) {
			Card other = (Card) obj;
			return this.number == other.number && this.suit == other.suit;
		}
		return false;
	}

}
