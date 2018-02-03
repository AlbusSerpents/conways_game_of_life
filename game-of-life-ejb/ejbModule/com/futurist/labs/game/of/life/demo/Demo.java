/**
 * 
 */
package com.futurist.labs.game.of.life.demo;

import com.futurist.labs.game.of.life.logic.GameOfLifeController;

/**
 * @author Dimitar
 *
 */
public class Demo {

	public static void main(String[] argv) {
		GameOfLifeController controller = new GameOfLifeController();
		String game = "OOOOO\nOOOOO\nOXXXO\nOOOOO\nOOOOO";
		String result = controller.iterate(game, 3);
		System.out.println(result);
		System.out.println();

	}

}
