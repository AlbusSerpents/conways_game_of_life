package com.futurist.labs.game.of.life;

import static org.junit.Assert.assertEquals;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.futurist.labs.game.of.life.logic.GameOfLifeController;

public class GameOfLifeControllerTest {

	private static EJBContainer container;
	private static Context context;

	@BeforeClass
	public static void beforeClass() {
		container = EJBContainer.createEJBContainer();
		context = container.getContext();
		System.out.println("Container initialized");
	}

	@Test
	public void testBlockShape() throws Exception {
		String initialBoard = "OOOO\r\nOXXO\r\nOXXO\r\nOOOO\r\n";
		String finalBoard = getController().iterate(initialBoard, 10);
		assertEquals(initialBoard, finalBoard);
	}

	@Test
	public void testBlinkerFullPeriod() throws Exception {
		String initialBoard = "OOOOO\r\nOOOOO\r\nOXXXO\r\nOOOOO\r\nOOOOO\r\n";
		String finalBoard = getController().iterate(initialBoard, 2);
		assertEquals(initialBoard, finalBoard);
	}

	@Test
	public void testBlinkerHalfPeriod() throws Exception {
		String initialBoard = "OOOOO\r\nOOOOO\r\nOXXXO\r\nOOOOO\r\nOOOOO\r\n";
		String finalBoard = getController().iterate(initialBoard, 1);
		String expectedBoard = "OOOOO\r\nOOXOO\r\nOOXOO\r\nOOXOO\r\nOOOOO\r\n";
		assertEquals(expectedBoard, finalBoard);
	}

	private GameOfLifeController getController() throws Exception {
		return (GameOfLifeController) context.lookup("java:global/classes/GameOfLifeController");

	}

	@AfterClass
	public static void afterClass() {
		container.close();
		System.out.println("Container closed");
	}
}
