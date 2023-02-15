package me.Vhoyd.miningMinigame.DataHandling;

public class Circle {
	private double degree = 0;
	private double oldDegree;
	private int n = 0;
	private double radius;
	private Direction direction;

	public void tick(int amount) {
		oldDegree = degree;
		if (direction == Direction.COUNTERCLOCKWISE) {
			degree = (degree + (Math.PI * amount / 180)) % Math.PI;
			if (oldDegree > degree)
				n = (n + 1) % 4;
		} else {
			degree = (degree - (Math.PI * amount / 180)) % Math.PI;
			if (oldDegree < degree)
				n = (n + 1) % 4;
		}
	}

	public enum Direction {
		CLOCKWISE, COUNTERCLOCKWISE,
	}

	public double getCos() {
		return Math.cos(degree) * (n == 1 || n == 3 ? -1 : 1) * radius;
	}

	public double getSin() {
		return Math.sin(degree) * (n == 0 || n == 2 ? -1 : 1) * radius;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Direction getDirection() {
		return direction;
	}

	public Circle(double degree, double radius, Direction direction) {
		this.degree = degree;
		this.radius = radius;
		this.direction = direction;
	}

}
