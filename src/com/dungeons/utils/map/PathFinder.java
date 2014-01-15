package com.dungeons.utils.map;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.dungeons.View.WorldRenderer;
import com.dungeons.entity.Entity;
import com.dungeons.entity.impl.Enemy;
import com.dungeons.entity.impl.Player;

/**
 * Pathfinding using a custom algorithm written for <b>Knight's Destiny</b>.
 * @author A. W.
 * @date 050114
 */
public class PathFinder {
	
	public static List<Vector2> findPath(Entity source, Entity target) {
		return findPath(source.getPosition(), target.getPosition());
	}	
	
	/**
	 * Extremely simple way to create a clipped path from a location to another.
	 * @param source the source location.
	 * @param target the destination.
	 * @return a list of the nodes ("points") of this path.
	 */
	public static List<Vector2> findPath(Vector2 source, Vector2 target) {
		if(source.equals(target)) {
			return null;
		}
		TiledMapTileLayer layer = WorldRenderer.wallCollisionLayer;
		/* Source position */
		Vector2 posSource = source;
		/* Destination */
		Vector2 dstSource = target;
		/* By default, the previous step is the first position */
		Vector2 previousStep = source;
		Vector2 tempLocation = new Vector2();
		float distance = (float) Math.sqrt(Math.pow((dstSource.x - posSource.x), 2) +
									Math.pow((dstSource.y - posSource.y), 2));
		/**
		 * This list is returned and will contain all positions that are visited in this path.
		 */
		ArrayList<Vector2> nodes = new ArrayList<Vector2>();
		for(int i = 0; i < Math.round(distance); i++) {
			if(tempLocation.y == dstSource.y || tempLocation.x == dstSource.x) {
				continue;
			}
			List<Vector2> options = new ArrayList<Vector2>();
			/**
			 * All 8 directions that are walkable will be added to the possible step list.
			 */
			options.add(new Vector2(previousStep.x - 1, previousStep.y));
			options.add(new Vector2(previousStep.x + 1, previousStep.y));
			options.add(new Vector2(previousStep.x, previousStep.y - 1));
			options.add(new Vector2(previousStep.x, previousStep.y + 1));
			options.add(new Vector2(previousStep.x - 1, previousStep.y + 1));
			options.add(new Vector2(previousStep.x - 1, previousStep.y - 1));
			options.add(new Vector2(previousStep.x + 1, previousStep.y + 1));
			options.add(new Vector2(previousStep.x + 1, previousStep.y - 1));
			List<Vector2> toRemove = new ArrayList<Vector2>();
			/**
			 * If the tile in any of the positions is blocked, remove it from the list.
			 */
			for(Vector2 vector : options) {
				try {
					MapProperties propertiesCollision = layer.getCell((int) Math.floor(vector.x / 50), (int) Math.floor(vector.y / 50)).getTile().getProperties();
					if(propertiesCollision != null) {
						if(propertiesCollision.containsKey("blocked")) {
							toRemove.add(vector);
						}
					}
				} catch(NullPointerException exc) {}
			}
			options.removeAll(toRemove);
			Vector2 closest = options.get(0);
			/**
			 * Find the closest tile to source within the candidates left in the list.
			 */
			for(Vector2 vector : options) {
				if(vector.dst(dstSource) < closest.dst(dstSource)) {
					closest = vector;
				}
			}
			/**
			 * Add it to the node list.
			 */
			tempLocation = closest;
			nodes.add(tempLocation);
			previousStep = new Vector2(tempLocation.x, tempLocation.y);
		}
		nodes.trimToSize();
		return nodes;
	}
}
