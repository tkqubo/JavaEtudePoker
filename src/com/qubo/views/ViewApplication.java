package com.qubo.views;

/**
 * {@link View}クラスを利用したコンソールアプリケーションを実行するためのクラス
 * @author Qubo
 */
public abstract class ViewApplication {
	/**
	 * 指定した{@link View}インスタンスを起動ビューとして、
	 * コンソールアプリケーションを実行する。<br />
	 * {@link View}インスタンスは{@link View#view()}を呼び出すことで
	 * 次のビューを取得し、擬似的に画面遷移を行う。<br />
	 * {@link View#view()}が{@code null}を返した時点で終了する。
	 * @param entryView 起動ビュー
	 */
	public static void start(View entryView) {
		View view = entryView;
		while (view != null) {
			view = view.view();
			if (view != null)
				System.out.println();
		}
	}
}
