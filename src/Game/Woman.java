package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import Game.Game.GameState;
import Utilities.Entity;
import Utilities.GameEngine;

public class Woman extends Entity {

	public final static Font tag = new Font("Trebuchet MS", Font.BOLD, 12);

	public final static double WIDTH = 20;
	public final static double HEIGHT = 30;

	public static Game game;

	private String name;
	private int sceneIndex;

	public Woman(double x, double y, String name, int sceneIndex) {
		super(x, y, WIDTH, HEIGHT);
		this.sceneIndex = sceneIndex;
		this.name = name;
	}

	public int getSceneIndex() {
		return sceneIndex;
	}

	public void setSceneIndex(int sceneIndex) {
		this.sceneIndex = sceneIndex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean highlighted() {
		return this.position.subtract(game.player.position).getMagnitude() < 100;
	}

	public void update() {
		this.update();
	}

	public void draw(Graphics2D win) {
		win.setColor(Color.MAGENTA);
		win.fill(this);
		
		//show highlight
		if (this.highlighted()) {
			win.setColor(Color.WHITE);
		} else {
			win.setColor(Color.BLACK);
		}
		win.draw(this);
		win.setFont(tag);

		//draw nametag below character, centered
		
		FontMetrics metrics = win.getFontMetrics(tag);
		int BUFFER = 5;
		int h = metrics.getHeight();
		int w = metrics.stringWidth(this.getName());

		win.drawString(this.getName(), (int) this.getCenterX() - w / 2, (int) this.getMaxY() + BUFFER + h);

		
	}

}
