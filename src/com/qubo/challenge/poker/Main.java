package com.qubo.challenge.poker;

import com.qubo.challenge.poker.models.CardException;

/**
 * コンソールプログラムのエントリポイントが定義されたクラス
 * @author Qubo
 */
public class Main {
	/**
	 * エントリポイント。
	 * @param args
	 */
	public static void main(String[] args) {
		ConsoleGame game = new ConsoleGame();
		try {
			game.play();
		} catch (CardException e) {
			System.err.println(e.getMessage());
		}
	}
}
