package com.qubo.views;

import java.text.MessageFormat;

import com.qubo.Utils;

/**
 * メニュービュー({@link MenuView}クラス)で表示するメニュー項目を表したクラス
 * @author Qubo
 */
public class MenuItem {
	private char accessCharacter;
	private final String name;
	private final View view;

	/**
	 * 遷移先のビュー名を取得する
	 * @return 遷移先のビュー名
	 */
	public String getName() { return name; }
	/**
	 * アクセス文字を取得する
	 * @return アクセス文字
	 */
	public char getAccessCharacter() { return accessCharacter; }
	/**
	 * 遷移先のビューを取得する
	 * @return 遷移先のビュー
	 */
	public View getView() { return view; }
	/**
	 * アクセス文字を設定する。<br />
	 * 本来アクセス文字は固定だが、メニュー項目数がメニュービューで表示できる件数を超えた場合、
	 * メニュービューが自動連番でアクセス数値を割り振ってページング機能を有効にする。
	 * このメソッドはそのための内部用メソッドである。
	 * @param accessCharacter アクセス文字
	 */
	void setAccessCharacter(char accessCharacter) { this.accessCharacter = accessCharacter; }

	/**
	 * コンストラクタ
	 * @param accessCharacter アクセス文字
	 * @param name 項目名
	 * @param view 遷移先ビュー
	 */
	public MenuItem(char accessCharacter, String name, View view) {
		this.name = name;
		this.accessCharacter = accessCharacter;
		this.view = view;
	}
	/**
	 * コンストラクタ。項目名には、遷移先のビュー名が割り当てられる。
	 * @param accessCharacter アクセス文字
	 * @param view 遷移先ビュー
	 */
	public MenuItem(char accessCharacter, View view) {
		this.name = (view != null) ? view.getName() : null;
		this.accessCharacter = accessCharacter;
		this.view = view;
	}

	@Override
	public String toString() {
		return MessageFormat.format("{0} {1}", Utils.lpad(accessCharacter + ":", 4), name);
	}
}
