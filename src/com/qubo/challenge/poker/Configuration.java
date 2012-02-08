package com.qubo.challenge.poker;

public abstract class Configuration {
	private Configuration() { }

	public static final int DEFAULT_CHANGECOUNT = 1;
	public static final int DEFAULT_JOKERCOUNT = 0;
	public static int jokerCount = DEFAULT_JOKERCOUNT;
	public static int changeCount = DEFAULT_CHANGECOUNT;
}
