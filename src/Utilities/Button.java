package Utilities;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import Game.Game;

public class Button extends Rectangle2D.Double {

	public static Game game;
	private Color fill;
	private Color outline;
	private String[] lines;
	private Font font;
	private String text;

	final int BUFFERX = 10;
	final int BUFFERY = 10;

	public Button(double x, double y, double w, double h, Color fill, Color out) {
		super(x, y, w, h);
		this.fill = fill;
		this.outline = out;
		lines = new String[3];
		text = "";
	}

	public void setText(String text) {
		this.text = text;
	}

	public void autoFormatText(Graphics2D win) {
		int line = 0;
		FontMetrics fm = win.getFontMetrics(font);
		for (int i = 0; i < lines.length; i++) { //reset lines
			lines[i] = new String("");
		}
		for (char c : text.toCharArray()) { //add to lines until full, then overflow
			if (this.lines[line] != null) {
				this.lines[line] += c;
				if (fm.stringWidth(this.lines[line] + "-") >= this.getWidth() - 2 * BUFFERX) {
					if(c != ' ') {
						this.lines[line] += "-";
					}
					if (line < lines.length - 1) {
						line++;
					}
				}
			}
		}
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public void setColors(Color fill, Color out) {
		this.fill = fill;
		this.outline = out;
	}

	public boolean getClicked() {
		if (game.getMouse().getX() > this.getX() && game.getMouse().getX() < this.getMaxX()
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
		FontMetrics fm = win.getFontMetrics(font);
		win.setFont(font);
		this.autoFormatText(win);
		for (int i = 0; i < lines.length; i++) {
			if (lines[i] != null)
				win.drawString(lines[i], (int) this.getX() + BUFFERX,
						(int) this.getY() + BUFFERY + (i + 1) * (fm.getHeight()));
		}
	}

}
