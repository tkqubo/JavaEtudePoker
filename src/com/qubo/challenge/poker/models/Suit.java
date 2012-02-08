package com.qubo.challenge.poker.models;

/**
 * カードのマークを表現したクラス。
 * ジョーカーも例外的にマークの一種として定義されている。
 * @author Qubo
 */
public enum Suit {
	/** ハート */
	Heart { @Override public char getSymbol() { return SYMBOL_HEART; } },
	/** スペード */
	Spade { @Override public char getSymbol() { return SYMBOL_SPADE; } },
	/** ダイヤモンド */
	Diamond { @Override public char getSymbol() { return SYMBOL_DIAMOND; } },
	/** クラブ */
	Club { @Override public char getSymbol() { return SYMBOL_CLUB; } },
	/** ジョーカー */
	Joker { @Override public char getSymbol() { return SYMBOL_JOKER; } },
	;
	/** ハートの文字表現: {@code 'H'} */
	public static final char SYMBOL_HEART = 'H';
	/** スペードの文字表現: {@code 'S'} */
	public static final char SYMBOL_SPADE = 'S';
	/** ハートの文字表現: {@code 'D'} */
	public static final char SYMBOL_DIAMOND = 'D';
	/** クラブの文字表現: {@code 'C'} */
	public static final char SYMBOL_CLUB = 'C';
	/** ジョーカーの文字表現: {@code ' '} */
	public static final char SYMBOL_JOKER = ' ';

	/**
	 * マークの文字列表現を取得する
	 * @return マークの文字列表現
	 */
	public abstract char getSymbol();
}
