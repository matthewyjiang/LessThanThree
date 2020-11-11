package Utilities;

public class Vector {

	public double x;
	public double y;

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector() {
		this(0, 0);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public Vector add(Vector v) {
		return new Vector(this.x + v.x, this.y + v.y);
	}

	public double getMagnitude() {
		return Math.sqrt(this.x * this.x + this.y * this.y);
	}

	public Vector normalize() {
		if (this.getMagnitude() != 0) {
			return new Vector(this.x / this.getMagnitude(), this.y / this.getMagnitude());
		} else {
			return new Vector(0, 0);
		}
	}

	public Vector scale(double a) {
		return new Vector(a * this.x, a * this.y);
	}

	public Vector subtract(Vector v) {
		return new Vector(this.x - v.x, this.y - v.y);
	}

}
