/**
 * 
 */
package com.futurist.labs.game.of.life.logic;

import static java.lang.System.lineSeparator;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

import java.util.List;
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
public class GameOfLifeConverter {

	public GameOfLife convertToGame(String gameString) {
		Supplier<Stream<String>> gameStream = () -> stream(gameString.split("\n"));
		checkShape(gameStream.get());

		Stream<Stream<Character>> charsStream = gameStream.get()
				.map(s -> s.chars().mapToObj(i -> (char) i).filter(c -> c != '\r'));
		CellType[][] board = charsStream.map(cs -> cs.map(c -> CellType.resolve(c)).toArray(size -> new CellType[size]))
				.toArray(size -> new CellType[size][]);

		return new GameOfLife(board);
	}

	public String convertToString(GameOfLife game) {
		int maxX = game.getMaxColumns();
		int maxY = game.getMaxRows();

		List<Integer> xList = IntStream.range(0, maxX).boxed().collect(toList());
		List<Integer> yList = IntStream.range(0, maxY).boxed().collect(toList());

		StringBuilder builder = new StringBuilder();
		for (Integer x : xList) {
			for (Integer y : yList) {
				CellType cell = game.getCell(x, y);
				builder.append(cell);
			}
			builder.append(lineSeparator());
		}

		checkShape(stream(builder.toString().split("\n")));
		return builder.toString();
	}

	private static void checkShape(Stream<String> stringStream) {
		IntStream lengths = stringStream.mapToInt(String::length);
		boolean allLengthsMatch = lengths.distinct().count() == 1L;

		if (!allLengthsMatch) {
			throw new RuntimeException("The game board is not a correct rectangle.");
		}
	}

}
