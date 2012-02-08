package com.qubo.challenge.poker;

import com.qubo.challenge.poker.models.CardException;
import com.qubo.views.AbstractYesNoView;
import com.qubo.views.IntegerInputView;
import com.qubo.views.MenuView;
import com.qubo.views.View;
import com.qubo.views.ViewApplication;

/**
 * コンソールプログラムとしてポーカーをプレイするためのシステム
 * @author Qubo
 */
public class ConsoleGame {
	/**
	 * ポーカーをプレイする
	 * @throws CardException ゲーム内でエラーがあった場合に発生
	 */
	public void play() throws CardException {
		ViewApplication.start(buildMenu());
	}

	/**
	 * ポーカーゲームのためのメインメニューを生成する
	 * @return メインメニューオブジェクト
	 */
	public View buildMenu() {
		final MenuView menu = new MenuView("メインメニュー");

		View gameStart = new View() {
			@Override public View view() { return new GameView(menu); }
			@Override public String getName() { return "ゲーム開始"; }
		};
		View changeJokerCount = new IntegerInputView(menu, "ジョーカーの枚数", 0, 2) {
			@Override protected void setValue(int value) { Configuration.jokerCount = value; }
			@Override protected int getValue() { return Configuration.jokerCount; }
		};
		View changeChangeCount = new IntegerInputView(menu, "交換回数", 0, 20) {
			@Override protected void setValue(int value) { Configuration.changeCount = value; }
			@Override protected int getValue() { return Configuration.changeCount; }
		};
		View resetSettings = new AbstractYesNoView("設定のリセット", "本当に設定をリセットしますか？", true) {
			@Override protected View view(Boolean input) {
				if (input) {
					Configuration.jokerCount = Configuration.DEFAULT_JOKERCOUNT;
					Configuration.changeCount = Configuration.DEFAULT_CHANGECOUNT;
					print("ジョーカーの枚数を{0}枚、交換回数を{1}回に戻しました。", Configuration.jokerCount, Configuration.changeCount);
				}
				return menu;
			}
		};
		View quit = new AbstractYesNoView("終了", "本当に終了しますか？", true) {
			@Override protected View view(Boolean input) { return input ? null : menu; }
		};

		menu.addMenuItem('g', gameStart);
		menu.addMenuItem('j', changeJokerCount);
		menu.addMenuItem('c', changeChangeCount);
		menu.addMenuItem('r', resetSettings);
		menu.setDefaultMenuItem('q', quit);

		return menu;
	}
}














