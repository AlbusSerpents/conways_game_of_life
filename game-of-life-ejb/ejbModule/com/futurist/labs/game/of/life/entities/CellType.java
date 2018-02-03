/**
 * 
 */
package com.futurist.labs.game.of.life.entities;

/**
 * @author Dimitar
 *
 */
public enum CellType {

	ALIVE('X'), DEAD('O');

	private CellType(char symbol) {
		this.symbol = symbol;
	}

	private final char symbol;

	public static CellType resolve(char symbol) {
		for (CellType cellType : CellType.values()) {
			if (symbol == cellType.symbol) {
				return cellType;
			}
		}
		throw new RuntimeException("Unknown symbol: " + symbol);
	}

	@Override
	public String toString() {
		return String.valueOf(symbol);
	}
}
