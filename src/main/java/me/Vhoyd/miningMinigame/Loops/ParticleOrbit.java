package me.Vhoyd.miningMinigame.Loops;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.Vhoyd.miningMinigame.DataHandling.Circle;

public class ParticleOrbit extends BukkitRunnable {
	private Player player;
	private Particle particle;
	private Circle circle;

	public void run() {
		Location playerLoc = player.getLocation();
		playerLoc.add(circle.getCos(), 1, circle.getSin());
		player.getWorld().spawnParticle(particle, playerLoc, 0, 0, 0.001, 0);
//		circle.setRadius(((circle.getSin()/circle.getRadius())*((circle.getCos()/circle.getRadius()))+0.01)*4);
		circle.tick(12);
	}

	public ParticleOrbit(double degree, Particle particle, Player player) {
		this.player = player;
		this.particle = particle;
		circle = new Circle(degree, 0.75, Circle.Direction.CLOCKWISE);
	}
}
