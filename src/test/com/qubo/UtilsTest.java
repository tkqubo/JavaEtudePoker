package test.com.qubo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.qubo.Utils;

/**
 * {@link Utils}用のテストを定義したクラス
 * @author Qubo
 */
public class UtilsTest {

	/** {@link Utils#toFullWidth(char)}のテスト */
	@Test
	public void testToFullWidth() {
		doTestToFullWidth('0', '０');
		doTestToFullWidth('1', '１');
		doTestToFullWidth('2', '２');
		doTestToFullWidth('3', '３');
		doTestToFullWidth('4', '４');
		doTestToFullWidth('5', '５');
		doTestToFullWidth('6', '６');
		doTestToFullWidth('7', '７');
		doTestToFullWidth('8', '８');
		doTestToFullWidth('9', '９');
		doTestToFullWidth('a', 'ａ');
		doTestToFullWidth('b', 'ｂ');
		doTestToFullWidth('c', 'ｃ');
		doTestToFullWidth('j', 'ｊ');
		doTestToFullWidth('q', 'ｑ');
		doTestToFullWidth('k', 'ｋ');
		doTestToFullWidth('A', 'Ａ');
		doTestToFullWidth('B', 'Ｂ');
		doTestToFullWidth('C', 'Ｃ');
		doTestToFullWidth('D', 'Ｄ');
		doTestToFullWidth('J', 'Ｊ');
		doTestToFullWidth('Q', 'Ｑ');
		doTestToFullWidth('K', 'Ｋ');
		doTestToFullWidth('+', '＋');
		doTestToFullWidth('*', '＊');
		doTestToFullWidth('^', '＾');
		doTestToFullWidth(' ', '　');
	}
	/**
	 * {@link #testToFullWidth()}用の内部メソッド
	 * @param input 入力値
	 * @param exp 予想値
	 */
	private void doTestToFullWidth(char input, char exp) {
		char result = Utils.toFullWidth(input);
		assertThat(result, is(exp));
	}

	/** {@link Utils#rpad(String, int)}のテスト */
	@Test
	public void testRpad() {
		doTestPad("", 4, true, "    ");
		doTestPad("test", 10, true, "test      ");
		doTestPad("全角", 6, true, "全角  ");
		doTestPad("ThisIsATest", 8, true, "ThisI...");
		doTestPad("全角テスト", 6, true, "全... ");
	}
	/** {@link Utils#lpad(String, int)}のテスト */
	@Test
	public void testLpad() {
		doTestPad("", 4, false, "    ");
		doTestPad("test", 10, false, "      test");
		doTestPad("全角", 6, false, "  全角");
		doTestPad("ThisIsATest", 8, false, "ThisI...");
		doTestPad("全角テスト", 6, false, " 全...");
	}
	/**
	 * {@link #testRpad()}用の内部メソッド
	 * @param text 文字列
	 * @param length 文字列長
	 * @param rpad {@code true}の場合は{@link Utils#rpad(String, int)}を、{@code false}の場合は{@link Utils#lpad(String, int)}を呼び出す
	 * @param expected 予想文字列
	 */
	private void doTestPad(String text, int length, boolean rpad, String expected) {
		assertThat(rpad ? Utils.rpad(text, length) : Utils.lpad(text, length), is(expected));
	}
	/** {@link Utils#isBlank(String)}のテスト */
	@Test
	public void testIsBlank() {
		assertTrue(Utils.isBlank(null));
		assertTrue(Utils.isBlank(""));
		assertTrue(Utils.isBlank("        "));
		assertFalse(Utils.isBlank("a"));
		assertFalse(Utils.isBlank("test test"));
		assertFalse(Utils.isBlank("　　　　")); // 全角非対応！
	}

}
