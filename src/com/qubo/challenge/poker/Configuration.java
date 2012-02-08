package com.qubo.challenge.poker;

/**
 * ゲームの設定を保存するクラス
 * @author Qubo
 */
public abstract class Configuration {
	private Configuration() { }

	/** カードの交換回数のデフォルト値 */
	public static final int DEFAULT_CHANGECOUNT = 1;
	/** ジョーカー枚数のデフォルト値 */
	public static final int DEFAULT_JOKERCOUNT = 0;
	/** ジョーカー枚数 */
	public static int jokerCount = DEFAULT_JOKERCOUNT;
	/** カードの交換回数 */
	public static int changeCount = DEFAULT_CHANGECOUNT;
}
