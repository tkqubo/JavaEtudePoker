package com.qubo.views;

import com.qubo.Utils;

/**
 * ユーザーに対して単純なYES/NO型の質問を行うビュークラス
 * @author Qubo
 */
public abstract class AbstractYesNoView extends AbstractView<Boolean> {
	/** YESに相当する文字: {@code 'y'} */
	private static final char YES = 'y';
	/** NOに相当する文字: {@code 'n'} */
	private static final char NO = 'n';
	/** YES, NO の配列 */
	private static final char[] YESNO = new char[] { YES, NO };
	private final String question;
	private final boolean defaultAnswer;

	/**
	 * 標準のコンストラクタ
	 * @param name ビュー名
	 * @param question 質問内容
	 * @param defaultAnswer ユーザーが何も入力を行わずにEnterキーを押した場合に適用するデフォルトの返答
	 * （{@code true}がYESに、{@code false}がNOに相当する）
	 */
	public AbstractYesNoView(String name, String question, boolean defaultAnswer) {
		super(name);
		this.question = question;
		this.defaultAnswer = defaultAnswer;
	}

	@Override
	protected final void renderBody() {
		printLine(question);
		printLine(" Y=はい/N=いいえ");
		printLine("何も入力せずにEnterキーを押すと、[{0}]になります", defaultAnswer  ? "はい" : "いいえ");
	}

	@Override
	protected final Boolean requestUserInput() { return Utils.promptChar(YESNO, YES) == YES; }

}