package com.qubo.views;

/**
 * コンソール上での画面遷移を表現したクラス
 * @author Qubo
 */
public interface View {
	/**
	 * ビューの名前を取得する
	 * @return ビュー名
	 */
	String getName();
	/**
	 * 次のビューを取得する
	 * @return 次のビュー
	 */
	View view();
}
