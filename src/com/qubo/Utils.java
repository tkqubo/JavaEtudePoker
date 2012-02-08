package com.qubo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;

/**
 * 便利関数を集めたクラス
 * @author Qubo
 */
public class Utils {
	/** 全角文字検出のためのエンコーディング: {@code "Windows-31J"} */
	private static final String WINDOWS31J = "Windows-31J";
	/** {@link #pad(String, int, boolean)}で内部的に使う省略記号: {@code "..."} */
	private static final String ELLIPSIS = "...";
	/** {@link #pad(String, int, boolean)}で内部的に使うパディング文字 */
	private static final String PADDING = "                                                                                ";

	/** 半角記号配列(半角スペースを除く) */
	private static final char[] SIGNS = {
		'!', '#', '$', '%', '&', '(',
		')', '*', '+', ',', '-', '.',
		'/', ':', ';', '<', '=', '>',
		'?', '@', '[', ']', '^', '_',
		'{', '|', '}'
	};
	/** 半角数字の、文字コード上の範囲開始位置: {@code 0x39} */
	private static final int NUM_HALF_END = 0x39;
	/** 半角数字の、文字コード上の範囲終了位置: {@code 0x30} */
	private static final int NUM_HALF_START = 0x30;
	/** 半角大文字アルファベットの、文字コード上の範囲開始位置: {@code 0x41} */
	private static final int CAPITAL_LETTER_START = 0x41;
	/** 半角大文字アルファベットの、文字コード上の範囲終了位置: {@code 0x5A} */
	private static final int CAPITAL_LETTER_END = 0x5A;
	/**半角小文字アルファベットの、文字コード上の範囲開始位置: {@code 0x61} */
	private static final int SMALL_LETTER_START = 0x61;
	/** 半角小文字アルファベットの、文字コード上の範囲終了位置: {@code 0x7A} */
	private static final int SMALL_LETTER_END = 0x7A;
	/** 文字コード上での、任意の半角英数字とそれに対応する全角英数字までの差: {@code 0xFEE0} */
	private static final int OFFSET_TO_FULL_WIDTH = 'Ａ' - 'A';

	/**
	 * 半角の英数記号を全角に変換する。半角英数記号以外のものが与えられた場合、そのまま引数の値を返す。
	 * @param value 半角英数記号
	 * @return {@code value}に対応する全角英数記号
	 */
	public static char toFullWidth(char value) {
		if (isNumber((int) value) || isCapitalLetter((int) value) || isSmallLetter((int) value) || isSign(value)) {
			return (char) ((int) value + OFFSET_TO_FULL_WIDTH);
		} else if (value == ' ') { // 半角スペースの場合
			return '　';
		} else {
			return value;
		}
	}
	/**
	 * 与えられた文字が、半角小文字アルファベットかどうかを取得する
	 * @param c
	 * @return {@code c}が半角小文字アルファベットかどうか
	 */
	private static boolean isSmallLetter(int c) {
		return c >= SMALL_LETTER_START && c <= SMALL_LETTER_END;
	}
	/**
	 * 与えられた文字が、半角大文字アルファベットかどうかを取得する
	 * @param c
	 * @return {@code c}が半角大文字アルファベットかどうか
	 */
	private static boolean isCapitalLetter(int c) {
		return c >= CAPITAL_LETTER_START && c <= CAPITAL_LETTER_END;
	}
	/**
	 * 与えられた文字が、半角数字かどうかを取得する
	 * @param c
	 * @return {@code c}が半角数字かどうか
	 */
	private static boolean isNumber(int c) {
		return c >= NUM_HALF_START && c <= NUM_HALF_END;
	}
	/**
	 * 与えられた文字が、半角記号かどうかを取得する
	 * @param c
	 * @return {@code c}が半角記号かどうか
	 */
	private static boolean isSign(char c) {
		for (char sign : SIGNS) {
			if (sign == c) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 指定された文字列長になるまで、与えられた文字列の右側に空白を追加して返す。
	 * @param text 文字列
	 * @param totalLength 文字列長
	 * @return パディングされた文字列
	 */
	public static String rpad(String text, int totalLength) { return pad(text, totalLength, false); }
	/**
	 * 指定された文字列長になるまで、与えられた文字列の左側に空白を追加して返す。
	 * @param text 文字列
	 * @param totalLength 文字列長
	 * @return パディングされた文字列
	 */
	public static String lpad(String text, int totalLength) { return pad(text, totalLength, true); }
	/**
	 * {@link #rpad(String, int)}および{@link #lpad(String, int)}用の内部関数
	 * @param text 文字列
	 * @param totalLength 文字列長
	 * @param doLeftPad 右詰めか左詰めかを指定
	 * @return パディングされた文字列
	 */
	private static String pad(String text, int totalLength, boolean doLeftPad) {
		String result = text;
		try {
			int byteLength = text.getBytes(WINDOWS31J).length;
			if (byteLength <= totalLength) {
				String padding = PADDING.substring(0, totalLength - byteLength);
				result = doLeftPad ? padding + text : text + padding;
			} else {
				String substring = null;
				for (int endIndex = text.length(); byteLength > totalLength; endIndex--) {
					substring = text.substring(0, endIndex) + ELLIPSIS;
					byteLength = substring.getBytes(WINDOWS31J).length;
				}
				String padding = PADDING.substring(0, totalLength - byteLength);
				result = doLeftPad ? padding + substring : substring + padding;
			}
		} catch (UnsupportedEncodingException e) {
		}
		return result;
	}
	/**
	 * コンソールでユーザーに対して文字の入力を求める。
	 * 入力文字が{@code candidates}に指定された文字配列の中に存在しない場合、再度入力を求める。
	 * また、ユーザーが何も入力せずにEnterキーを押した場合、{@code defaultChar}が入力として適用される。
	 * @param candidates 文字候補配列
	 * @param defaultChar デフォルトの文字
	 * @return ユーザーの入力文字
	 */
	public static char promptChar(char[] candidates, char defaultChar) {
		String candidatesStr = String.copyValueOf(candidates).toLowerCase();

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			while (true) {
				String line = reader.readLine().toLowerCase();
				if (line.length() == 1) {
					if (candidatesStr.contains(line.substring(0, 1))) {
						return line.charAt(0);
					}
				} else if (line.length() == 0) {
					return defaultChar;
				}
				System.out.println("正しい値を入力してください！");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		throw new RuntimeException("到達不能なコードに達しました！");
	}
	/**
	 * コンソールでユーザーに対して整数値の入力を求める。
	 * 数値が{@code min}～{@code max}の範囲外だった場合、および整数値以外が入力された場合、再度入力を求める。
	 * また、ユーザーが何も入力せずにEnterキーを押した場合、{@code null}を返す。
	 * @param min 求める整数の最小値
	 * @param max 求める整数の最大値
	 * @return ユーザーの入力数値
	 */
	public static Integer promptInteger(int min, int max) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			while (true) {
				try {
					String line = reader.readLine();
					if (Utils.isBlank(line)) {
						return null;
					}

					int value = Integer.parseInt(line);
					if (value >= min && value <= max) {
						return value;
					} else {
						System.out.println(MessageFormat.format("{0}~{1}の範囲で数値を入力してください！", min, max));
					}

				} catch (NumberFormatException e) {
					System.out.println("正しい数値を入力してください！");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		throw new RuntimeException("到達不能なコードに達しました！");
	}
	/**
	 * 文字列が空かどうかを取得する
	 * @param text 調べる文字列
	 * @return 文字列が空かどうか
	 */
	public static boolean isBlank(String text) { return text == null || text.trim().length() == 0; }
}
