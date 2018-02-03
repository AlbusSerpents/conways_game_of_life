package com.futurist.labs.game.of.life.endpoint;

import static java.lang.System.lineSeparator;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.futurist.labs.game.of.life.logic.GameOfLifeController;

@Path("/api")
@Stateless
public class GameOfLifeEndpoint {

	@EJB
	private GameOfLifeController controller;

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String simpleMethod(@QueryParam("board") String board, @QueryParam("gens") int generations) {

		String originalToHTML = transforForHTML(board);

		String changed = controller.iterate(board, generations);
		String changedToHTML = transforForHTML(changed);

		String message = "<html><body> <h3>Initial state</h3></br>" + originalToHTML + "</br> <h3>State after "
				+ generations + " generations</h3></br>" + changedToHTML + " </body> </html>";
		return message;
	}

	private String transforForHTML(String text) {
		return text.replaceAll(lineSeparator(), "</br>");
	}

}
