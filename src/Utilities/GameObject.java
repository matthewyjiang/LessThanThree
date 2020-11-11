package Utilities;

import java.awt.Graphics2D;

public interface GameObject {
	public static int fps = 60;
	
	public void update();
	public void draw(Graphics2D win);
}
