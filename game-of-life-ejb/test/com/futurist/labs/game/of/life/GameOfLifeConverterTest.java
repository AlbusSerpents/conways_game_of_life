package com.futurist.labs.game.of.life;

import static com.futurist.labs.game.of.life.entities.CellType.ALIVE;
import static com.futurist.labs.game.of.life.entities.CellType.DEAD;
import static java.lang.System.lineSeparator;
import static org.junit.Assert.assertEquals;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.futurist.labs.game.of.life.entities.CellType;
import com.futurist.labs.game.of.life.entities.GameOfLife;
import com.futurist.labs.game.of.life.logic.GameOfLifeConverter;

public class GameOfLifeConverterTest {

	private static EJBContainer container;
	private static Context context;

	@BeforeClass
	public static void beforeClass() {
		container = EJBContainer.createEJBContainer();
		context = container.getContext();
		System.out.println("Container initialized");
	}

	@Test
	public void testConvertToString() throws Exception {
		CellType[][] cells = { { ALIVE, DEAD }, { DEAD, ALIVE } };
		GameOfLife game = new GameOfLife(cells);

		String result = getConverter().convertToString(game);
		String expected = new StringBuilder().append(ALIVE).append(DEAD).append(lineSeparator()).append(DEAD)
				.append(ALIVE).append(lineSeparator()).toString();
		assertEquals(expected, result);
	}

	@Test
	public void testConvertToGame() throws Exception {
		String gameString = "XO\nOX\n";
		CellType[][] expectedArray = { { ALIVE, DEAD }, { DEAD, ALIVE } };

		GameOfLife actual = getConverter().convertToGame(gameString);
		GameOfLife expected = new GameOfLife(expectedArray);

		assertEquals(expected, actual);
	}

	@Test(expected = RuntimeException.class)
	public void testFailConvertToGameIrregularShape() throws Exception {
		String gameString = "XOX\nOX\n";
		getConverter().convertToGame(gameString);
	}

	@Test(expected = RuntimeException.class)
	public void testFailConvertToStringIrregularShape() throws Exception {
		CellType[][] irregularArray = { { ALIVE, DEAD, ALIVE }, { ALIVE, ALIVE } };
		GameOfLife game = new GameOfLife(irregularArray);
		getConverter().convertToString(game);
	}

	private GameOfLifeConverter getConverter() throws Exception {
		return (GameOfLifeConverter) context.lookup("java:global/classes/GameOfLifeConverter");
	}

	@AfterClass
	public static void afterClass() {
		container.close();
		System.out.println("Container closed");
	}
}
