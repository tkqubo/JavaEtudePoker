package com.qubo.views;

import java.util.ArrayList;
import java.util.List;

import com.qubo.Utils;

/**
 * 画面遷移用のビュー。
 * ユーザーに1文字入力を求めて、その文字に対応したメニュー項目のビューに遷移する。
 * @author Qubo
 */
public class MenuView extends AbstractView<MenuItem> {
	/** 一画面に表示するメニュー項目の最大数 */
	public static int MAX_RECORDS = 10;
	/** 複数ページになった場合に、前のページに遷移するためのメニュー項目 */
	private final MenuItem previousPage = new MenuItem('<', "前のページ", this);
	/** 複数ページになった場合に、次のページに遷移するためのメニュー項目 */
	private final MenuItem nextPage = new MenuItem('>', "次のページ", this);

	private final List<MenuItem> menuItems;
	private int page;
	private MenuItem defaultMenuItem;

	/**
	 * 標準のコンストラクタ
	 * @param name ビュー名
	 */
	public MenuView(String name) {
		super(name);
		this.menuItems = new ArrayList<MenuItem>();
		this.page = 0;
	}
	/**
	 * メニュー項目を追加する
	 * @param menuItem 追加するメニュー項目
	 */
	public void addMenuItem(MenuItem menuItem) {
		menuItems.add(menuItem);
	}
	/**
	 * 与えられた引数からメニュー項目を生成して追加する
	 * @param accessCharacter アクセス文字
	 * @param view ビュー
	 */
	public void addMenuItem(char accessCharacter, View view) {
		addMenuItem(new MenuItem(accessCharacter, view));
	}
	/**
	 * デフォルトのメニュー項目を取得する。
	 * デフォルトのメニュー項目はビューの一番下に表示され、ユーザーが何も入力せずにEnterキーを押した場合に適用される。
	 * @return デフォルトのメニュー項目
	 */
	public MenuItem getDefaultMenuItem() {
		return defaultMenuItem;
	}
	/**
	 * デフォルトのメニュー項目を設定する。
	 * デフォルトのメニュー項目はビューの一番下に表示され、ユーザーが何も入力せずにEnterキーを押した場合に適用される。
	 * @param defaultMenuItem デフォルトのメニュー項目
	 */
	public void setDefaultMenuItem(MenuItem defaultMenuItem) {
		this.defaultMenuItem = defaultMenuItem;
	}
	/**
	 * 与えられた引数からメニュー項目を生成して、デフォルトのメニュー項目として設定する。
	 * @param accessCharacter アクセス文字
	 * @param view ビュー
	 */
	public void setDefaultMenuItem(char accessCharacter, View view) {
		setDefaultMenuItem(new MenuItem(accessCharacter, view));
	}

	@Override
	protected final void renderBody() {
		printLine("メニューを選択してください。");
		printLine("何も入力せずEnterキーを押すと、[{0}]になります。", defaultMenuItem.getView().getName());
		printThinSeparator();

		for (int i = page * MAX_RECORDS; i < Math.min(menuItems.size(), (page + 1) * MAX_RECORDS); i++) {
			MenuItem menuItem = menuItems.get(i);
			if (menuItems.size() > MAX_RECORDS) {
				char accessCharacter = (char) ((i - page * MAX_RECORDS) + '0');
				menuItem.setAccessCharacter(accessCharacter);
			}
			printLine(menuItem.toString());
		}

		if (menuItems.size() > MAX_RECORDS) {
			printThinSeparator();
			if (hasPreviousPage()) {
				printLine(previousPage.toString());
			}
			if (hasNextPage()) {
				printLine(nextPage.toString());
			}
			printLine("    現在{0}/{1}ページ目を表示しています", page + 1, getPageTotal());
		}
		printThinSeparator();
		printLine(defaultMenuItem.toString());
	}

	@Override
	protected final MenuItem requestUserInput() {
		char[] accessCharacters = getAccessCharacters();
		char selected = Utils.promptChar(accessCharacters, defaultMenuItem.getAccessCharacter());
		for (int i = page * MAX_RECORDS; i < Math.min(menuItems.size(), (page + 1) * MAX_RECORDS); i++) {
			MenuItem menuItem = menuItems.get(i);
			if (selected == menuItem.getAccessCharacter()) {
				return menuItem;
			}
		}

		if (selected == '<') {
			page--;
			return previousPage;
		} else if (selected == '>') {
			page++;
			return nextPage;
		} else if (selected == defaultMenuItem.getAccessCharacter()) {
			return defaultMenuItem;
		}

		return null;
	}

	/**
	 * 有効なアクセス文字を配列として取得する
	 * @return 有効なアクセス文字
	 */
	private char[] getAccessCharacters() {
		List<Character> list = new ArrayList<Character>();
		for (int i = page * MAX_RECORDS; i < Math.min(menuItems.size(), (page + 1) * MAX_RECORDS); i++) {
			MenuItem menuItem = menuItems.get(i);
			list.add(menuItem.getAccessCharacter());
		}
		if (hasNextPage()) {
			list.add(nextPage.getAccessCharacter());
		}
		if (hasPreviousPage()) {
			list.add(previousPage.getAccessCharacter());
		}
		list.add(defaultMenuItem.getAccessCharacter());

		char[] accessCharacters = new char[list.size()];
		for (int i = 0; i < list.size(); i++) {
			accessCharacters[i] = list.get(i);
		}

		return accessCharacters;
	}
	/**
	 * メニュービューが前のページを持つかどうかを取得する
	 * @return メニュービューが前のページを持つかどうか
	 */
	private boolean hasPreviousPage() { return menuItems.size() > MAX_RECORDS && page >= 1; }
	/**
	 * メニュービューが次のページを持つかどうかを取得する
	 * @return メニュービューが次のページを持つかどうか
	 */
	private boolean hasNextPage() { return menuItems.size() > MAX_RECORDS && page < getPageTotal() - 1; }
	/**
	 * メニュービューの総ページ数を取得する
	 * @return メニュービューの総ページ数
	 */
	private double getPageTotal() { return Math.ceil((float)menuItems.size() / MAX_RECORDS); }

	@Override
	protected View view(MenuItem input) {
		print("[{0}]を選択しました。", input.getName());
		return input.getView();
	}
}
