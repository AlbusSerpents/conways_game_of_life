/**
 * 
 */
package com.futurist.labs.game.of.life.entities;

import static java.util.Arrays.deepEquals;

/**
 * @author Dimitar
 *
 */
public class GameOfLife {

	private final CellType[][] board;
	private final int maxColumns;
	private final int maxRows;

	public GameOfLife(CellType[][] board) {
		super();
		this.board = board;
		maxRows = board.length;
		maxColumns = board[0].length;
	}

	public CellType getCell(int x, int y) {
		return board[x][y];
	}

	public int getMaxColumns() {
		return maxColumns;
	}

	public int getMaxRows() {
		return maxRows;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((board == null) ? 0 : board.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameOfLife other = (GameOfLife) obj;
		if (board == null) {
			if (other.board != null)
				return false;
		} else if (!deepEquals(board, other.board))
			return false;
		return true;
	}

}
