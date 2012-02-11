package com.qubo.challenge.poker.models;


/**
 * ポーカーの役を表したクラス。<br />
 * 役の強さは強い順に次のとおりである：<br />
 * <ol>
 * <li>ファイブカード</li>
 * <li>ロイヤルフラッシュ</li>
 * <li>ストレートフラッシュ</li>
 * <li>フォーカード</li>
 * <li>フルハウス</li>
 * <li>フラッシュ</li>
 * <li>ストレート</li>
 * <li>スリーカード</li>
 * <li>ツーペア</li>
 * <li>ワンペア</li>
 * <li>役無し</li>
 * </ol>
 * ジョーカーを使わないルールでは、当然のことながらファイブカードは存在しない。
 * @author Qubo
 */
public enum TypeOfHand {
	/** ファイブカード */
	FiveOfAKind {
		@Override public String getName() { return NAME_FIVE_OF_A_KIND; }
		@Override public boolean isValid(Hand hand) {
			return hand.isOfAKind(5 - hand.getJoker()) == 1;
		}
	},
	/** ロイヤルフラッシュ */
	RoyalFlush {
		@Override public String getName() { return NAME_ROYAL_FLUSH; }
		@Override public boolean isValid(Hand hand) {
			return hand.isSameSuit() && hand.isSequentialFrom(10);
		}
	},
	/** ストレートフラッシュ */
	StraightFlush {
		@Override public String getName() { return NAME_STRAIGHT_FLUSH; }
		@Override public boolean isValid(Hand hand) {
			return hand.isSameSuit() && hand.isSequential() && !hand.isSequentialFrom(10);
		}
	},
	/** フォーカード */
	FourOfAKind {
		@Override public String getName() { return NAME_FOUR_OF_A_KIND; }
		@Override public boolean isValid(Hand hand) {
			return hand.isOfAKind(4 - hand.getJoker()) == 1;
		}
	},
	/** フルハウス */
	FullHouse {
		@Override public String getName() { return NAME_FULL_HOUSE; }
		@Override public boolean isValid(Hand hand) {
			return (hand.isOfAKind(3) == 1 && hand.isOfAKind(2) == 1)
					|| (hand.isOfAKind(2) == 2 && hand.getJoker() == 1);
		}
	},
	/** フラッシュ */
	Flush {
		@Override public String getName() { return NAME_FLUSH; }
		@Override public boolean isValid(Hand hand) {
			return hand.isSameSuit() && !hand.isSequential();
		}
	},
	/** ストレート */
	Straight {
		@Override public String getName() { return NAME_STRAIGHT; }
		@Override public boolean isValid(Hand hand) {
			return hand.isSequential() && !hand.isSameSuit();
		}
	},
	/** スリーカード */
	ThreeOfAKind {
		@Override public String getName() { return NAME_THREE_OF_A_KIND; }
		@Override public boolean isValid(Hand hand) {
			return (hand.isOfAKind(3) == 1 && hand.isOfAKind(1) == 2)
					|| (hand.getJoker() == 1 && hand.isOfAKind(1) == 2 && hand.isOfAKind(2) == 1)
					|| (hand.getJoker() == 2 && hand.isOfAKind(1) == 3 && !hand.isSameSuit() && !hand.isSequential());
		}
	},
	/** ツーペア */
	TwoPair {
		@Override public String getName() { return NAME_TWO_PAIR; }
		@Override public boolean isValid(Hand hand) {
			return hand.isOfAKind(2) == 2 && hand.isOfAKind(1) == 1;
		}
	},
	/** ワンペア */
	OnePair {
		@Override public String getName() { return NAME_ONE_PAIR; }
		@Override public boolean isValid(Hand hand) {
			return (hand.isOfAKind(2) == 1 && hand.isOfAKind(1) == 3)
					|| (hand.getJoker() == 1 && hand.isOfAKind(1) == 4 && !hand.isSameSuit() && !hand.isSequential());
		}
	},
	/** 役無し */
	HighCards {
		@Override public String getName() { return NAME_HIGH_CARDS; }
		@Override public boolean isValid(Hand hand) {
			return !hand.isSameSuit() && !hand.isSequential() && hand.isOfAKind(1) == 5;
		}
	};

	/** 役名 */
	public static final String NAME_FIVE_OF_A_KIND = "ファイブカード";
	/** 役名 */
	public static final String NAME_ROYAL_FLUSH = "ロイヤルフラッシュ";
	/** 役名 */
	public static final String NAME_STRAIGHT_FLUSH = "ストレートフラッシュ";
	/** 役名 */
	public static final String NAME_FOUR_OF_A_KIND = "フォーカード";
	/** 役名ス */
	public static final String NAME_FULL_HOUSE = "フルハウス";
	/** 役名 */
	public static final String NAME_FLUSH = "フラッシュ";
	/** 役名 */
	public static final String NAME_STRAIGHT = "ストレート";
	/** 役名 */
	public static final String NAME_THREE_OF_A_KIND = "スリーカード";
	/** 役名 */
	public static final String NAME_TWO_PAIR = "ツーペア";
	/** 役名 */
	public static final String NAME_ONE_PAIR = "ワンペア";
	/** 役名 */
	public static final String NAME_HIGH_CARDS = "役無し";

	/**
	 * 役の名前を取得する
	 * @return 役の名前
	 */
	public abstract String getName();
	/**
	 * 手札が役の条件を満たしているかどうかを取得する。
	 * このメソッドで
	 * <strong>
	 * {@code true}を取得することは、より高位の役が存在する可能性を排除しない。
	 * </strong>
	 * 最高位の役を知りたい場合は、{@link #getTypeOfHand(Hand)}を使うこと。
	 * @param hand 手札インスタンス
	 * @return 手札が役の条件を満たしているかどうか
	 */
	public abstract boolean isValid(Hand hand);

	/** ポーカーの全ての役を、強いものから順に並べた配列 */
	public static final TypeOfHand[] ALL = {
		FiveOfAKind, RoyalFlush, StraightFlush, FourOfAKind, FullHouse, Flush, Straight, ThreeOfAKind, TwoPair, OnePair, HighCards
	};

	/**
	 * 手札が構成しうる役の中で、最も高位のものを返す。
	 * @param hand 手札
	 * @return 役
	 */
	public static TypeOfHand getTypeOfHand(Hand hand) {
		for (TypeOfHand typeOfHand : ALL) {
			if (typeOfHand.isValid(hand)) return typeOfHand;
		}
		return null;
	}
	/*
	 * (非 Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() { return "{" + getName() + "}"; }
}
