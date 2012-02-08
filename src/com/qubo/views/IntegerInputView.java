package com.qubo.views;

import com.qubo.Utils;

/**
 * ユーザーに対して整数値の入力を求めて処理を行うビュークラス
 * @author Qubo
 */
public abstract class IntegerInputView extends AbstractView<Integer> {
	private final String itemName;
	private final int min;
	private final int max;
	private final View forward;

	/**
	 * 標準のコンストラクタ
	 * @param forward 遷移先の画面
	 * @param name ビュー名
	 * @param min 入力値の下限
	 * @param max 入力値の上限
	 */
	public IntegerInputView(View forward, String name, int min, int max) {
		super(name + "の変更");
		this.forward = forward;
		this.itemName = name;
		this.min = min;
		this.max = max;
	}

	@Override
	protected final void renderBody() {
		printLine("{0}の値を入力して下さい({1}～{2})。", itemName, min, max);
		printLine("そのままEnterキーを押せば、変更を行わずに{0}に戻ります。", forward.getName());
		printLine("現在の値は{0}です。", getValue());
	}

	@Override
	protected final Integer requestUserInput() {
		return Utils.promptInteger(min, max);
	}

	@Override
	protected final View view(Integer input) {
		if (input != null) {
			print("{0}を{1}に設定しました。", itemName, input);
			setValue(input);
		}
		return forward;
	}

	/**
	 * 値の入力があった場合に、それを処理する
	 * @param value
	 */
	protected abstract void setValue(int value);
	/**
	 * 処理前の値を取得する
	 * @return 変更前の値
	 */
	protected abstract int getValue();
}
