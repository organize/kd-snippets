package com.dungeons.item;

import java.io.Serializable;

import com.dungeons.entity.impl.Player;

/**
 * Represents an item in the game.
 * @note ground items are entities that encapsulate an instance of <code>Item</code>.
 * @author A. W.
 */
public interface Item extends Serializable {
	
	/**
	 * Consume this item. Called from inventory click.
	 * @param player the player.
	 * @return by default, the same item with a reduced quantity.
	 */
	public Item consume(Player player);
	
	/**
	 * Get the <code>ItemType</code> of this <code>Item</code>.
	 * @return see: <code>ItemType</code>.
	 */
	public ItemType getItemType();
	
	/**
	 * Get the item's id inside the game.
	 * @return index.
	 */
	public int getIndex();
	
	/**
	 * Get the item's quantity.
	 * @return quantity.
	 */
	public int getQuantity();
	
	public void destroy();
	
	public void setQuantity(int quantity);
	
	public void setIndex(int index);

}
