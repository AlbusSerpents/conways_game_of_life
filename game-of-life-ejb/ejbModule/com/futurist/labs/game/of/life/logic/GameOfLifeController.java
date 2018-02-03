/**
 * 
 */
package com.futurist.labs.game.of.life.logic;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.futurist.labs.game.of.life.entities.GameOfLife;

/**
 * @author Dimitar
 *
 */
@Stateless
@LocalBean
public class GameOfLifeController {

	@EJB
	private GameOfLifeConverter converter;

	@EJB
	private GameOfLifeProcessor processor;

	public String iterate(String game, int iterations) {
		GameOfLife original = converter.convertToGame(game);
		for (int i = 0; i < iterations; i++) {
			GameOfLife transformed = processor.process(original);
			original = transformed;
		}
		return converter.convertToString(original);
	}
}