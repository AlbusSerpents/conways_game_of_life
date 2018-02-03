package com.futurist.labs.game.of.life;

import static com.futurist.labs.game.of.life.entities.CellType.ALIVE;
import static com.futurist.labs.game.of.life.entities.CellType.DEAD;
import static org.junit.Assert.assertEquals;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.futurist.labs.game.of.life.entities.CellType;
import com.futurist.labs.game.of.life.entities.GameOfLife;
import com.futurist.labs.game.of.life.logic.GameOfLifeProcessor;

public class GamOfLifeProcessorTest {

	private static EJBContainer container;
	private static Context context;

	@BeforeClass
	public static void beforeClass() {
		container = EJBContainer.createEJBContainer();
		context = container.getContext();
		System.out.println("Container initialized");
	}

	@Test
	public void testNoChange() throws Exception {
		CellType[][] blockArray = { { DEAD, DEAD, DEAD, DEAD }, { DEAD, ALIVE, ALIVE, DEAD },
				{ DEAD, ALIVE, ALIVE, DEAD }, { DEAD, DEAD, DEAD, DEAD } };
		executeCheck(blockArray, blockArray);
	}

	@Test
	public void testReviveCell() throws Exception {
		CellType[][] reviveArray = { { ALIVE, DEAD, ALIVE }, { DEAD, DEAD, DEAD }, { DEAD, ALIVE, DEAD } };
		CellType[][] expectedArray = { { DEAD, DEAD, DEAD }, { DEAD, ALIVE, DEAD }, { DEAD, DEAD, DEAD } };
		executeCheck(reviveArray, expectedArray);
	}

	@Test
	public void testKeepAliveCell() throws Exception {
		CellType[][] reviveArray = { { ALIVE, DEAD, DEAD }, { DEAD, ALIVE, DEAD }, { DEAD, DEAD, ALIVE } };
		CellType[][] expectedArray = { { DEAD, DEAD, DEAD }, { DEAD, ALIVE, DEAD }, { DEAD, DEAD, DEAD } };
		executeCheck(reviveArray, expectedArray);
	}

	@Test
	public void testKillCellCell() throws Exception {
		CellType[][] reviveArray = { { DEAD, DEAD, DEAD }, { DEAD, ALIVE, DEAD }, { DEAD, DEAD, ALIVE } };
		CellType[][] expectedArray = { { DEAD, DEAD, DEAD }, { DEAD, DEAD, DEAD }, { DEAD, DEAD, DEAD } };
		executeCheck(reviveArray, expectedArray);
	}

	private void executeCheck(CellType[][] originalArray, CellType[][] expectedArray) throws Exception {
		GameOfLife originalGame = new GameOfLife(originalArray);
		GameOfLife result = getProcessor().process(originalGame);
		GameOfLife expected = new GameOfLife(expectedArray);
		assertEquals(expected, result);
	}

	private GameOfLifeProcessor getProcessor() throws Exception {
		return (GameOfLifeProcessor) context.lookup("java:global/classes/GameOfLifeProcessor");
	}

	@AfterClass
	public static void afterClass() {
		container.close();
		System.out.println("Container closed");
	}

}
