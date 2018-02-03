/**
 * 
 */
package com.futurist.labs.game.of.life.logic;

import static com.futurist.labs.game.of.life.entities.CellType.ALIVE;
import static com.futurist.labs.game.of.life.entities.CellType.DEAD;

import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.futurist.labs.game.of.life.entities.CellType;
import com.futurist.labs.game.of.life.entities.GameOfLife;

/**
 * @author Dimitar
 *
 */
@Stateless
@LocalBean
public class GameOfLifeProcessor {

	private static final BiFunction<Integer, Integer, Boolean> IN_RANGE = (e, max) -> e >= 0 && e < max;

	public GameOfLife process(GameOfLife game) {

		int maxX = game.getMaxColumns();
		int maxY = game.getMaxRows();

		Supplier<Stream<Integer>> xStream = () -> IntStream.range(0, maxX).boxed();
		Supplier<Stream<Integer>> yStream = () -> IntStream.range(0, maxY).boxed();

		CellType[][] transformedBoard = xStream.get()
				.map(x -> yStream.get().map(y -> transformCell(game, x, y)).toArray(size -> new CellType[size]))
				.toArray(size -> new CellType[size][]);

		return new GameOfLife(transformedBoard);
	}

	private CellType changeCell(CellType cell, long liveNeighbors) {
		if (liveNeighbors == 3 && cell == DEAD) {
			return ALIVE;
		} else if (liveNeighbors >= 2 && liveNeighbors <= 3) {
			return cell;
		} else {
			return DEAD;
		}
	}

	private CellType transformCell(GameOfLife game, int x, int y) {

		int maxX = game.getMaxColumns();
		int maxY = game.getMaxRows();

		BiFunction<Integer, Integer, CellType> getNeighbor = (xC, yC) -> isValid(x + xC, y + yC, maxX, maxY)
				? game.getCell(x + xC, y + yC) : null;

		Stream<CellType> neighbors = Stream.of(getNeighbor.apply(-1, -1), getNeighbor.apply(-1, 0),
				getNeighbor.apply(-1, 1), getNeighbor.apply(0, -1), getNeighbor.apply(0, 1), getNeighbor.apply(1, -1),
				getNeighbor.apply(1, 0), getNeighbor.apply(1, 1));

		long liveNeighbors = neighbors.filter(e -> e != null && e == ALIVE).count();
		CellType cell = game.getCell(x, y);

		return changeCell(cell, liveNeighbors);
	}

	private boolean isValid(int x, int y, int maxX, int maxY) {
		return IN_RANGE.apply(x, maxX) && IN_RANGE.apply(y, maxY);
	}

}
