package Game;

import java.awt.event.KeyEvent;

import Utilities.Entity;
import Utilities.GameEngine;
import Utilities.Vector;

public class Player extends Entity {

	public Player(double x, double y, double w, double h) {
		super(x, y, w, h);
	}

	Vector accelerationBias;
	double accel = 3000;
	double maxSpeed = 300;

	public void update() {

		this.accelerationBias = new Vector(0, 0);
		this.acceleration = new Vector(0, 0);

		if (GameEngine.KeysPressed[KeyEvent.VK_W]) {
			this.accelerationBias.y -= accel;
		}
		if (GameEngine.KeysPressed[KeyEvent.VK_A]) {
				this.accelerationBias.x -= accel;
		}
		if (GameEngine.KeysPressed[KeyEvent.VK_S]) {
			this.accelerationBias.y += accel;
		}
		if (GameEngine.KeysPressed[KeyEvent.VK_D]) {
			this.accelerationBias.x += accel;
		}
		this.acceleration = this.acceleration.add(this.accelerationBias);
		
		this.friction();

		if (this.velocity.add(this.acceleration.scale(1.0 / fps)).getMagnitude() > maxSpeed) {
			this.velocity = this.velocity.normalize().scale(maxSpeed);
		}

		super.update();

	}

}
