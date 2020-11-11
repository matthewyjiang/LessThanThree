package Utilities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

public class Entity extends Rectangle2D.Double implements GameObject {
	public Vector position;
	public Vector velocity;
	public Vector acceleration;
	double mass;

	public final double deadband = 1;

	public Entity(double x, double y, double w, double h) {
		super(x, y, w, h);
		velocity = new Vector(0, 0);
		acceleration = new Vector(0, 0);
		position = new Vector(x, y);
	}

	public String collision(Entity e) {

		int result = 0;

		if (this.getMaxY() + this.velocity.scale(1.0 / fps).getY() >= e.getY() + e.velocity.scale(1.0 / fps).getY()
				&& this.getMaxY() < e.getY()
				&& this.getMaxX() + this.velocity.scale(1.0 / fps).getX() >= e.getX()
						+ e.velocity.scale(1.0 / fps).getX()
				&& this.getX() + this.velocity.scale(1.0 / fps).getX() <= e.getMaxX()
						+ e.velocity.scale(1.0 / fps).getX()) {
			result += 1;// hit on top
		}
		if (this.getMaxX() + this.velocity.scale(1.0 / fps).getX() >= e.getX() + e.velocity.scale(1.0 / fps).getX()
				&& this.getMaxX() < e.getX()
				&& this.getMaxY() + this.velocity.scale(1.0 / fps).getY() >= e.getY()
						+ e.velocity.scale(1.0 / fps).getY()
				&& this.getY() + this.velocity.scale(1.0 / fps).getY() <= e.getMaxY()
						+ e.velocity.scale(1.0 / fps).getY()) {
			result += 2;// hit on left
		}
		if (this.getX() + this.velocity.scale(1.0 / fps).getX() <= e.getMaxX() + e.velocity.scale(1.0 / fps).getX()
				&& this.getX() > e.getMaxX()
				&& this.getMaxY() + this.velocity.scale(1.0 / fps).getY() >= e.getY()
						+ e.velocity.scale(1.0 / fps).getY()
				&& this.getY() + this.velocity.scale(1.0 / fps).getY() <= e.getMaxY()
						+ e.velocity.scale(1.0 / fps).getY()) {
			result += 4;// hit on right
		}

		if (this.getY() + this.velocity.scale(1.0 / fps).getY() <= e.getMaxY() + e.velocity.scale(1.0 / fps).getY()
				&& this.getY() > e.getMaxY()
				&& this.getMaxX() + this.velocity.scale(1.0 / fps).getX() >= e.getX()
						+ e.velocity.scale(1.0 / fps).getX()
				&& this.getX() + this.velocity.scale(1.0 / fps).getX() <= e.getMaxX()
						+ e.velocity.scale(1.0 / fps).getX()) {
			result += 6;// hit on bottom
		}

		switch (result) {
		case 1:
			return "top";
		case 2:
			return "left";
		case 3:
			return "topleft";
		case 4:
			return "right";
		case 5:
			return "topright";
		case 6:
			return "bottom";
		case 8:
			return "bottomleft";
		case 10:
			return "bottomright";
		default:
			return "none";
		}
	}

	public void friction() {
		this.acceleration = this.acceleration.add(this.velocity.scale(-5));
	}

	public void update() {
		position = position.add(velocity.scale(1.0 / fps));
		velocity = velocity.add(acceleration.scale(1.0 / fps));
		this.x = position.x;
		this.y = position.y;
	}

	public void draw(Graphics2D win) {
		// TODO Auto-generated method stub
		win.setColor(Color.MAGENTA);
		win.fill(this);
		win.setColor(Color.BLACK);
		win.draw(this);

	}

}
