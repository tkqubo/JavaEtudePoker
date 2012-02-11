package com.qubo.challenge.poker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.qubo.challenge.poker.models.CardException;
import com.qubo.challenge.poker.models.Deck;
import com.qubo.challenge.poker.models.Hand;
import com.qubo.challenge.poker.models.TypeOfHand;
import com.qubo.views.AbstractView;
import com.qubo.views.View;

/**
 * コンソールでのポーカーを表現した{@link View}
 * @author Qubo
 */
public class GameView extends AbstractView<int[]> {
	private Deck deck;
	private Hand hand;
	private int changeCount;
	private final View parent;

	/**
	 * コンストラクタ
	 * @param parent 呼び出し元の{@link View}インスタンス
	 */
	GameView(View parent) {
		super("ポーカー");
		this.parent = parent;
		try {
			changeCount = Configuration.changeCount;
			deck = new Deck(Configuration.jokerCount);
			hand = deck.deal();
		} catch (CardException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void renderBody() {
		TypeOfHand typeOfHand = TypeOfHand.getTypeOfHand(hand);

		if (changeCount == Configuration.changeCount)
			printLine("初期手札は次のとおりです。");

		printLine("┏━┓┏━┓┏━┓┏━┓┏━┓");
		printLine("┃{0}┃┃{1}┃┃{2}┃┃{3}┃┃{4}┃",
				hand.get(0).getSuitSymbol2Bytes(),
				hand.get(1).getSuitSymbol2Bytes(),
				hand.get(2).getSuitSymbol2Bytes(),
				hand.get(3).getSuitSymbol2Bytes(),
				hand.get(4).getSuitSymbol2Bytes());
		printLine("┃{0}┃┃{1}┃┃{2}┃┃{3}┃┃{4}┃",
				hand.get(0).getNumberSymbol2Bytes(),
				hand.get(1).getNumberSymbol2Bytes(),
				hand.get(2).getNumberSymbol2Bytes(),
				hand.get(3).getNumberSymbol2Bytes(),
				hand.get(4).getNumberSymbol2Bytes());
		printLine("┗━┛┗━┛┗━┛┗━┛┗━┛");
		printLine("  １    ２    ３    ４    ５  ");

		if (changeCount == 0) {
			printLine("役は[{0}]です。Enterキーを押すとメニューに戻ります", typeOfHand.getName());
		} else if (deck.getRemainings() == 0) {
			changeCount = 0;
			printLine("デッキにカードが残っていないため、これ以上交換できません。");
			printLine("役は[{0}]です。Enterキーを押すとメニューに戻ります", typeOfHand.getName());
		} else {
			printLine("現在の役は[{0}]です。あと{1}回カードを交換できます。", typeOfHand.getName(), changeCount);
			printLine("変更したいカードの番号を入力してください(1～5)。");
			printLine("何も入力せずにEnterキーを押すと、現在の役を確定させます。");
		}
	}

	@Override
	protected int[] requestUserInput() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			outer:
			try {
				String line = reader.readLine();
				if (changeCount > 0) {
					int available = Math.min(5, deck.getRemainings());
					if (line.length() <= available) {
						int[] change = new int[line.length()];
						for (int i = 0; i < line.length(); i++) {
							int value = Integer.parseInt("" + line.charAt(i));
							if (value < 1 || value > 5) {
								print("1から5までの数字だけを入力してください！");
								break outer;
							}
							change[i] = value - 1;
						}
						return change;
					} else {
						if (available < 5)
							print("デッキにカードが足りません。");
						print("一度に交換できるのは{0}枚までです！", available);
					}
				} else {
					return new int[0];
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				print("入力フォーマットがおかしいです！");
			}
		}
	}

	@Override
	protected View view(int[] input) {
		changeCount--;
		if (changeCount == -1) {
			return parent;
		} else {
			if (input.length > 0) {
				try {
					deck.change(hand, input);
					StringBuilder builder = new StringBuilder();
					for (int i : input) {
						if (builder.length() > 0)
							builder.append("、");
						builder.append((i + 1) + "枚目");
					}
					builder.append("を交換しました。");
					print(builder.toString());
				} catch (CardException e) {
					e.printStackTrace();
				}
			} else {
				changeCount = 0;
			}

			return this;
		}
	}
}
