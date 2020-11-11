package Utilities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import Game.Game;

public class Button extends Rectangle2D.Double {

	public static Game game;
	private Color fill;
	private Color outline;

	public Button(double x, double y, double w, double h, Color fill, Color out) {
		super(x, y, w, h);
		this.fill = fill;
		this.outline = out;
	}

	public void setColors(Color fill, Color out) {
		this.fill = fill;
		this.outline = out;
	}

	public boolean getClicked() {
		if (game.getMouse().getX() > this.getX() && game.getMouse().getX() < this.getMaxY()
				&& game.getMouse().getY() > this.getY() && game.getMouse().getY() < this.getMaxY()
				&& GameEngine.mouseButtons[0]) {
			return true;
		}
		return false;
	}

	public void draw(Graphics2D win) {
		if (this.getClicked()) {
			win.setColor(this.fill.brighter());
		} else {
			win.setColor(this.fill);
		}
		win.fill(this);
		win.setColor(this.outline);
		win.draw(this);
	}

}
