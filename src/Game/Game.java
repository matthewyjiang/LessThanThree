package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

import Game.Game.GameState;
import Utilities.Button;
import Utilities.GameEngine;
import Utilities.Vector;

public class Game extends GameEngine {

	public Player player;

	public Woman[] women;

	public Scene[] scenes;
	public int sceneSelect = 0;

	public Button[] buttons;

	Vector camCoords = new Vector(0, 0);
	final double camFollowIntensity = 0.03;

	public enum GameState {
		MENU, WORLD, SCENE
	};

	GameState gamestate = GameState.WORLD;

	public Game() {
		player = new Player(300, 300, 20, 30);

		women = new Woman[1];
		women[0] = new Woman(100, 100, "Claire", 0);

		scenes = new Scene[1];
		scenes[0] = new Scene("Claire's Dialogue");

		buttons = new Button[1];

		Button.game = this;
		Woman.game = this;
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.setBackground(Color.BLACK);
		game.start();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		switch (gamestate) {
		case MENU:
			break;
		case WORLD:
			camCoords.x -= (camCoords.x - (player.getCenterX()) + resolution.getWidth() / 2) * camFollowIntensity;
			camCoords.y -= (camCoords.y - (player.getCenterY()) + resolution.getHeight() / 2) * camFollowIntensity;

			player.update();
			for (Woman w : women) {
				if (w.highlighted() && KeysTyped[KeyEvent.VK_SPACE]) {
					sceneSelect = w.getSceneIndex();
					gamestate = GameState.SCENE;

				}
			}
			break;
		case SCENE:
			if (KeysTyped[KeyEvent.VK_ESCAPE]) {
				gamestate = GameState.WORLD;

			}
			break;
		}
		KeysTyped[KeyEvent.VK_SPACE] = false;
		KeysTyped[KeyEvent.VK_ESCAPE] = false;

	}

	@Override
	public void draw(Graphics2D win) {
		// TODO Auto-generated method stub

		win.setColor(Color.PINK);
		Rectangle2D.Double space = new Rectangle2D.Double(0, 0, resolution.getWidth(), resolution.getHeight());
		switch (gamestate) {
		case MENU:

			win.fill(space);
			break;
		case WORLD:

			win.translate(-camCoords.getX(), -camCoords.getY());

			Rectangle2D.Double space2 = new Rectangle2D.Double(camCoords.getX(), camCoords.getY(),
					resolution.getWidth(), resolution.getHeight());
			win.fill(space2);
			player.draw(win);
			for (Woman w : women) {
				w.draw(win);
			}

			win.translate(camCoords.getX(), camCoords.getY());

			for (Woman w : women) {
				if (w.highlighted()) {
					Font UI = new Font("Trebuchet MS", Font.BOLD, 24);
					FontMetrics fm1 = win.getFontMetrics(UI);
					win.setFont(UI);
					win.setColor(Color.BLACK);
					win.drawString("press SPACE to interact",
							(int) GameEngine.resolution.getWidth() - fm1.stringWidth("press SPACE to interact") - 20,
							(int) GameEngine.resolution.getHeight() - 20);
				}
			}
			break;
		case SCENE:
			win.fill(space);
			for (Button b : buttons) {
				if (b != null) {
					b.draw(win);
				}
			}
			scenes[sceneSelect].draw(win);

			break;
		}

	}

}
