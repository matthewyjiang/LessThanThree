package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Utilities.Button;

public class Scene {

	private String label;
	public Button[] buttons;
	
	public BufferedImage photo;

	public Font labelFont = new Font("Trebuchet MS", Font.BOLD, 24);

	public Scene(String label, BufferedImage pic) {

		this.label = label;

		buttons = new Button[4];

		Color buttonFill = Color.LIGHT_GRAY;
		Color buttonOutline = Color.BLACK;
		Font buttonFont = new Font("Trebuchet MS", Font.PLAIN, 12);
		buttons[0] = new Button(20, 480, 610, 100, buttonFill, buttonOutline);
		buttons[1] = new Button(650, 480, 610, 100, buttonFill, buttonOutline);
		buttons[2] = new Button(20, 600, 610, 100, buttonFill, buttonOutline);
		buttons[3] = new Button(650, 600, 610, 100, buttonFill, buttonOutline);

		for (int i = 0; i < buttons.length; i++) {
			buttons[i].setFont(buttonFont.deriveFont(18.0f));
		}

		buttons[0].setText(
				"This is a really long string to test my auto formatting function. It adds characters to each line until it runs out of space, and then moves to the next line.");
		buttons[1].setText(
				"This is another really long string to test this function.");
		
		
		photo = pic;
	}

	public void draw(Graphics2D win) {
		win.setColor(Color.BLACK);
		win.setFont(labelFont);
		FontMetrics fm = win.getFontMetrics(labelFont);
		win.drawString(label, 20, 20 + fm.getHeight());
		win.drawImage(photo, 30, 60, null);

		for (Button b : buttons) {
			if (b != null) {
				b.draw(win);
			}
		}
	}

}
