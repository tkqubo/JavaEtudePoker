package com.qubo.views;

import java.text.MessageFormat;

import com.qubo.Utils;

/**
 * ほとんどのビューの基礎となる抽象クラス。構造的に以下の機能を実装する。
 * <ul>
 * <li>枠線によるレイアウト</li>
 * <li>ユーザー入力を要求し、それを{@code T}型に変換し取得する</li>
 * <li>得られた{@code T}型インスタンスを元に、次の画面を取得する</li>
 * </ul>
 * @author Qubo
 * @param <T> ユーザー入力型
 */
public abstract class AbstractView<T> implements View {
	/** 枠線レイアウトのヘッダ部分 */
	public static final String HEAD 			= "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓";
	/** 枠線レイアウトのフッタ部分 */
	public static final String TAIL 			= "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛";
	/** 枠線レイアウトのセパレータ */
	public static final String SEPARATOR 		= "┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫";
	/** 枠線レイアウトのセパレータ（細） */
	public static final String THINE_SEPARATOR	= "┠─────────────────────────────────────┨";
	/** 枠線レイアウトの幅 */
	public static final int LAYOUT_WIDTH = 72;

	/**
	 * 標準のコンストラクタ
	 * @param name ビュー名
	 */
	public AbstractView(String name) {
		this.name = name;
	}

	private final String name;
	@Override
	public final String getName() { return name; }

	@Override
	public final View view() {
		// render header
		printHead();
		printLine("■" + name);
		printSeparator();

		renderBody();

		// render footer
		printTail();

		T input = requestUserInput();

		return view(input);
	}

	/** ビューの内容をレンダリングする。 */
	protected abstract void renderBody();
	/**
	 * ユーザー入力を求め、{@code T}型で返す
	 * @return {@code T}型のインスタンス
	 */
	protected abstract T requestUserInput();
	/**
	 * ユーザー入力に基づいて結果を表示し、次に表示するビューを取得する
	 * @param input ユーザー入力
	 * @return ビュー
	 */
	protected abstract View view(T input);


	/** 枠線レイアウトのヘッダ部分を表示する */
	protected void printHead() { print(HEAD); }
	/** 枠線レイアウトのフッタ部分を表示する */
	protected void printTail() { print(TAIL); }
	/** 枠線レイアウトのセパレータを表示する */
	protected void printSeparator() { print(SEPARATOR); }
	/** 枠線レイアウトのセパレータ（細）を表示する */
	protected void printThinSeparator() { print(THINE_SEPARATOR); }
	/**
	 * 枠線レイアウト内に文字列を表示する
	 * @param pattern 文字列フォーマット
	 * @param objects フォーマットの引数
	 */
	protected void printLine(String pattern, Object...objects) {
		String message = MessageFormat.format(pattern, objects);
		message = Utils.rpad(message, LAYOUT_WIDTH);
		System.out.println("┃ " + message + " ┃");
	}
	/**
	 * 文字列を表示する
	 * @param pattern 文字列フォーマット
	 * @param objects フォーマットの引数
	 */
	protected void print(String pattern, Object...objects) {
		System.out.println(MessageFormat.format(pattern, objects));
	}
}
