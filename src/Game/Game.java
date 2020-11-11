package Game;

import java.awt.Color;
import java.awt.Graphics2D;

import Utilities.Button;
import Utilities.GameEngine;

public class Game extends GameEngine {

	public Player player;

	public Woman[] women;
	
	public Scene[] scenes;
	
	public Button[] buttons;

	public enum GameState {
		MENU, WORLD, SCENE
	};

	GameState gamestate = GameState.SCENE;
	
	

	public Game() {
		player = new Player(300, 300, 20, 30);
		women = new Woman[1];
		women[0] = new Woman(100, 100, "Claire");
		Button.game = this;
		buttons = new Button[4];
		
		Color buttonFill = Color.LIGHT_GRAY;
		Color buttonOutline = Color.BLACK;
		
		buttons[0] = new Button(20, 600, 610, 100, buttonFill, buttonOutline);
		buttons[1] = new Button(650, 600, 610, 100, buttonFill, buttonOutline);
		Woman.game = this;
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.setBackground(Color.PINK);
		game.start();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		switch (gamestate) {
		case MENU:
			break;
		case WORLD:
			player.update();
			break;
		case SCENE:
			break;
		}

	}

	@Override
	public void draw(Graphics2D win) {
		// TODO Auto-generated method stub
		switch (gamestate) {
		case MENU:
			break;
		case WORLD:
			player.draw(win);
			for (Woman w : women) {
				w.draw(win);
			}
			break;
		case SCENE:
			for(Button b : buttons) {
				if(b != null) {
					b.draw(win);
				}
			}
			break;
		}

	}

}
