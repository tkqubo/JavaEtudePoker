package com.qubo.challenge.poker.models;

/**
 * ポーカーアプリケーション固有の例外
 * @author Qubo
 */
public class CardException extends Exception {
	/** シリアルバージョンＵＩＤ */
	private static final long serialVersionUID = 3983999438471225539L;

	/** 標準のコンストラクタ */
	public CardException() { }
	/**
	 * コンストラクタ
	 * @param message エラーメッセージ
	 * @param throwable 内部エラー
	 */
	public CardException(String message, Throwable throwable) { super(message, throwable); }
	/**
	 * コンストラクタ
	 * @param message エラーメッセージ
	 */
	public CardException(String message) { super(message); }
	/**
	 * コンストラクタ
	 * @param throwable 内部エラー
	 */
	public CardException(Throwable throwable) { super(throwable); }

}
