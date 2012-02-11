package test.com.qubo.challenge.poker.models;

import static org.junit.Assert.fail;

import com.qubo.challenge.poker.models.CardException;
import com.qubo.challenge.poker.models.Hand;

/**
 * テスト用の手札を定義したクラス
 * @author Qubo
 */
public abstract class Hands {
	public static Hand fiveOfAKind1 = instantiateSafely("D6", "  ", "C6", "S6", "H6");
	public static Hand fiveOfAKind2 = instantiateSafely("  ", "  ", "C8", "S8", "H8");
	public static Hand royalFlush1 = instantiateSafely("H10", "HA", "HQ", "HJ", "HK");
	public static Hand royalFlush2 = instantiateSafely("CA", "CK", "CQ", "C10", "CJ");
	public static Hand straightFlush1 = instantiateSafely("H6", "H5","H7", "H4", "H3");
	public static Hand straightFlush2 = instantiateSafely("DQ", "DJ","D10", "D9", "DK");
	public static Hand fourOfAKind1 = instantiateSafely("DQ", "CQ","D10", "DQ", "SQ");
	public static Hand fourOfAKind2 = instantiateSafely("C8", "CQ","D8", "H8", "S8");
	public static Hand fullHouse1 = instantiateSafely("C4", "HQ","S4", "D4", "SQ");
	public static Hand fullHouse2 = instantiateSafely("CA", "HA","S3", "DA", "H3");
	public static Hand flush1 = instantiateSafely("HA", "H5","H3", "H10", "HK");
	public static Hand flush2 = instantiateSafely("C9", "C2","C8", "CQ", "C4");
	public static Hand straight1 = instantiateSafely("D4", "H2","C5", "S6", "S3");
	public static Hand straight2 = instantiateSafely("D9", "DQ","S10", "D8", "DJ");
	public static Hand threeOfAKind1 = instantiateSafely("D9", "DQ","S9", "D8", "C9");
	public static Hand threeOfAKind2 = instantiateSafely("DA", "SA","H2", "HK", "CA");
	public static Hand twoPair1 = instantiateSafely("D9", "DQ","S9", "D8", "SQ");
	public static Hand twoPair2 = instantiateSafely("S8", "D4","C4", "D8", "SA");
	public static Hand onePair1 = instantiateSafely("H9", "DJ","C4", "D8", "S9");
	public static Hand onePair2 = instantiateSafely("S7", "H7","CQ", "D6", "S2");
	public static Hand highCards1 = instantiateSafely("S7", "HK","CQ", "D6", "S2");
	public static Hand highCards2 = instantiateSafely("H2", "H6","HQ", "HA", "S3");
	public static Hand highCards3 = instantiateSafely("H5", "C4","S7", "S2", "D3");
	public static Hand highCards4 = instantiateSafely("HA", "SK","HQ", "C8", "HJ");

	public static Hand royalFlush3 = instantiateSafely("  ", "HA", "HQ", "HJ", "HK");
	public static Hand royalFlush4 = instantiateSafely("CA", "  ", "  ", "C10", "CJ");
	public static Hand straightFlush3 = instantiateSafely("H6", "H5","H7", "H4", "  ");
	public static Hand straightFlush4 = instantiateSafely("DQ", "D8","D10", "  ", "  ");
	public static Hand fourOfAKind3 = instantiateSafely("DQ", "CQ","D10", "  ", "SQ");
	public static Hand fourOfAKind4 = instantiateSafely("  ", "CQ","D8", "  ", "S8");
	public static Hand fullHouse3 = instantiateSafely("  ", "HQ","S4", "D4", "SQ");
	public static Hand fullHouse4 = instantiateSafely("CA", "HA","S3", "  ", "H3");
	public static Hand flush3 = instantiateSafely("HA", "H5","H3", "  ", "HK");
	public static Hand flush4 = instantiateSafely("C9", "  ","C8", "  ", "C4");
	public static Hand straight3 = instantiateSafely("  ", "H2","C5", "S6", "S3");
	public static Hand straight4 = instantiateSafely("H9", "  ","  ", "D8", "DJ");
	public static Hand threeOfAKind3 = instantiateSafely("D9", "DQ","S9", "D8", "  ");
	public static Hand threeOfAKind4 = instantiateSafely("DA", "  ","H2", "HK", "  ");
	public static Hand onePair3 = instantiateSafely("S7", "HK","CQ", "  ", "S2");
	public static Hand onePair4 = instantiateSafely("HA", "SK","  ", "C8", "HJ");

	private static Hand instantiateSafely(String c1, String c2, String c3, String c4, String c5) {
		try {
			return new Hand(c1, c2, c3, c4, c5);
		} catch (CardException e) {
			fail(e.getMessage());
			return null;
		}
	}
}
