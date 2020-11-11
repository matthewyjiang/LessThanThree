package Utilities;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;

/**
 * @(#)GameDriverV4.java
 *
 *
 * updates V4:  jframe included, keylistener included, switch to render, game loop
 *    from tasktimer to thread  - needs more testing sorry
 * Updates V5:  keyTyped, switched to update and render
 * 
 * Possible Changes for V6: full-screen (win.scale) 
 * 
 * @version 5.0 9/13/2019
 */

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.event.MouseInputListener;

public abstract class GameEngine extends Canvas implements Runnable, KeyListener, MouseInputListener {

	protected int FramesPerSecond;
	public static boolean[] KeysPressed;
	public static boolean[] KeysPressed_Last;
	public static Vector mouse;
	protected static boolean[] mouseButtons;
	public static boolean[] mouseClicked;

	// it is your responsibility to handle the release on keysTyped
	public static boolean[] KeysTyped;
	private JFrame frame;
	private String title = "Default";
	private boolean cleanCanvas = true;
	public boolean fullscreen = false;

	public static long lastSysTime = 0;
	public static long timeElapsed = 0;
	public static int framesElapsed = 0;
	public static int currentFps = 0;
	public double screenScale = 1;
	
	public boolean cursorVisible = true;

	int[] pixels = new int[16 * 16];
	Image image = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(16, 16, pixels, 0, 16));
	Cursor transparentCursor = Toolkit.getDefaultToolkit().createCustomCursor(image, new Point(0, 0),
			"invisibleCursor");

	public final static Dimension resolution = new Dimension(1280, 720);

	public GameEngine(int frames, boolean fullscreen) {
		// set up all variables related to the game
		FramesPerSecond = frames;
		this.fullscreen = fullscreen;

		this.setBackground(Color.BLACK);

		this.addKeyListener(this);
		this.addMouseMotionListener(this);
		this.addMouseListener(this);

		// key setup
		KeysPressed = new boolean[KeyEvent.KEY_LAST];
		KeysPressed_Last = new boolean[KeyEvent.KEY_LAST];
		KeysTyped = new boolean[KeyEvent.KEY_LAST];
		mouseButtons = new boolean[3];
		mouseClicked = new boolean[3];
		mouse = new Vector();

	}

	public GameEngine() {
		// default setting (60 frames per second)
		this(60, false);
	}

	public void start() {

		if (this.getWidth() == 0) {
			if (fullscreen) {
				this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
			} else {
				this.setSize(1100, 618);
			}
		}

		frame = new JFrame();

		if (fullscreen) {
			frame.setUndecorated(true);
		}

		frame.add(this);
		frame.pack();
		frame.setTitle(title);
		// frame.setCursor(frame.getToolkit().createCustomCursor(
		// new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),
		// "null"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		frame.setVisible(true);

		this.startThread();

	}

	private synchronized void startThread() {
		Thread t1 = new Thread(this);
		t1.start(); // calls run method after paint
		this.setFocusable(true);
	}

	public void setFrames(int num) {
		this.FramesPerSecond = num;
	}

	public abstract void update();

	public abstract void draw(Graphics2D win);

	private void render() {

		screenScale = ((this.getWidth() / resolution.getWidth()) < (this.getHeight() / resolution.getHeight()))
				? (this.getWidth() / resolution.getWidth())
				: (this.getHeight() / resolution.getHeight());

		BufferStrategy buffs = this.getBufferStrategy();
		if (buffs == null) {
			this.createBufferStrategy(3);
			buffs = this.getBufferStrategy();
		}

		Graphics g = buffs.getDrawGraphics();

		if (this.cleanCanvas) {
			g.setColor(this.getBackground());
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
		((Graphics2D) g).scale(screenScale, screenScale);

		draw((Graphics2D) g);

		g.dispose();

		buffs.show();

	}

	public void run() {

		long lastTime = System.nanoTime(); // long 2^63
		double nanoSecondConversion = 1000000000.0 / this.FramesPerSecond; // 60 frames per second
		double changeInSeconds = 0;

		while (true) {
			
			if(!cursorVisible) {
				this.setCursor(transparentCursor);
			}
			
			long now = System.nanoTime();

			changeInSeconds += (now - lastTime) / nanoSecondConversion;
			while (changeInSeconds >= 1) {
				update();
				for (int i = 0; i < KeysPressed.length; i++) {
					KeysPressed_Last[i] = KeysPressed[i];
				}
				changeInSeconds--;
			}

			render();
			lastTime = now;
		}
	}

	public Font getFont(String name) {
		Font font = null;
		String fName = "Fonts/" + name;
		try {
			InputStream is = this.getClass().getResourceAsStream(fName);
			font = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println(fName + " not loaded.  Using serif font.");
			font = new Font("serif", Font.PLAIN, 24);
		}
		return font;
	}

	public BufferedImage addImage(String name) {

		BufferedImage img = null;
		try {

			img = ImageIO.read(this.getClass().getResourceAsStream(name));

		} catch (IOException e) {
			System.out.println(e);
		}

		return img;

	}

	public static BufferedImage scale(BufferedImage imageToScale, int dWidth, int dHeight) {
		BufferedImage scaledImage = null;
		if (imageToScale != null) {
			scaledImage = new BufferedImage(dWidth, dHeight, imageToScale.getType());
			Graphics2D graphics2D = scaledImage.createGraphics();
			graphics2D.drawImage(imageToScale, 0, 0, dWidth, dHeight, null);
			graphics2D.dispose();
		}
		return scaledImage;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		KeysPressed[e.getKeyCode()] = true;

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		KeysPressed[e.getKeyCode()] = false;
		KeysTyped[e.getKeyCode()] = true;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		mouse.x = e.getX() / screenScale;
		mouse.y = e.getY() / screenScale;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		mouse.x = e.getX() / screenScale;
		mouse.y = e.getY() / screenScale;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		int btn = 0;

		if (e.getButton() == MouseEvent.BUTTON1) {
			btn = 0;
		} else if (e.getButton() == MouseEvent.BUTTON2) {
			btn = 1;
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			btn = 2;
		}

		mouseButtons[btn] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		int btn = 0;

		if (e.getButton() == MouseEvent.BUTTON1) {
			btn = 0;
		} else if (e.getButton() == MouseEvent.BUTTON2) {
			btn = 1;
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			btn = 2;
		}

		mouseButtons[btn] = false;
		mouseClicked[btn] = true;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	
	public Vector getMouse() {
		return mouse;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public boolean mouseClicked(int btnId, int x1, int y1, int x2, int y2) {
		return mouseButtons[btnId] == true && mouse.getX() >= x1 && mouse.getX() <= x2 && mouse.getY() >= x1
				&& mouse.getY() <= y2;
	}

	public boolean mouseClicked(int btnId, Rectangle2D.Double rect) {
		return mouseClicked(btnId, (int) rect.x, (int) rect.y, (int) (rect.x + rect.width),
				(int) (rect.y + rect.height));
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setCleanCanvas(boolean option) {
		this.cleanCanvas = option;
	}

}
