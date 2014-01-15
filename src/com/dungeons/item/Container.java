package com.dungeons.item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <b>Represents an item container.</b>
 * 	Multiple operations that involve the modification of the contents in this <code>Container</code>
 *  are included.
 * @author A.W.
 * @date 040114
 */

@SuppressWarnings("serial")
public class Container implements Serializable {
	
	/**
	 * Collection that holds the item data for this container. 
	 */
	private List<Item> data;
	
	/**
	 * The absolute maximum size this container can reach. 
	 */
	private int maxSize;
	
	/**
	 * This boolean determines if items with the same index stack by quantity when added
	 * to the container.
	 */
	private boolean stackable;
	
	/**
	 * Constructs an all-purpose container with the size of 300.
	 */
	public Container(boolean stackable) {
		this(300, stackable);
	}
	
	/**
	 * Constructs a container with a specified size.
	 * @param size the maximum size of this container.
	 */
	public Container(int size, boolean stackable) {
		this(new ArrayList<Item>(size), size, stackable);
	}
	
	/**
	 * Constructs a container with set data collection and a set size.
	 * @param data the items we want in our container.
	 * @param size the maximum size of this container.
	 */
	public Container(List<Item> data, int size, boolean stackable) {
		if(data.size() > size) {
			throw new RuntimeException("data size cannot exceed maximum size!");
		}
		this.data = data;
		this.maxSize = size;
		this.stackable = stackable;
	}
	
	/**
	 * Goes through our data collection to find get an item instance with the same id as
	 *  our parameter.
	 * @param index the item index that is being looked for.
	 * @return instance of an Item if found, null if not.
	 */
	public Item getItemForIndex(int index) {
		for(Item item : data) {
			if(item.getIndex() == index) {
				return item;
			}
		}
		return null;
	}
	
	/**
	 * Add an item to this container.
	 * @param item the item to be added.
	 * @return true if successfully added, false if add failed.
	 */
	public boolean add(Item item) {
		if(getCurrentSize() == getMaxSize() && !stackable) {
			System.out.println("error: container full!");
			return false;
		}
		/* Gets a little tricky here */
		if(stackable) {
			if (item.getItemType() == ItemType.EQUIPMENT) {
				data.add(item);
				return true;
			}
			if(!containsIndex(item.getIndex()) && getCurrentSize() == getMaxSize()) {
				return false;
			}
			final Item temporaryItem = getItemForIndex(item.getIndex());
			if(temporaryItem == null) {
				data.add(item);
				return true;
			}
			data.remove(temporaryItem);
			item.setQuantity(item.getQuantity() + temporaryItem.getQuantity());
			data.add(item);
			return true;
		} 
		data.add(item);
		return true;
	}
	
	public boolean remove(Item item) {
		if(!contains(item.getIndex(), item.getQuantity())) {
			return false;
		}
		if(stackable) {
			final Item temporaryItem = getItemForIndex(item.getIndex());
			data.remove(temporaryItem);
			temporaryItem.setQuantity(temporaryItem.getQuantity() - item.getQuantity());
			if(temporaryItem.getQuantity() > 0) {
				data.add(temporaryItem);
			}
			return true;
		}
		List<Item> toRemove = new ArrayList<Item>();
		int offset = 0;
		for(Item itemInList : data) {
			if(itemInList.getIndex() == item.getIndex()) {
				if(offset == item.getQuantity()) {
					break;
				}
				toRemove.add(itemInList);
				offset++;
			}
		}
		for(Item itemToRemove : toRemove) {
			data.remove(itemToRemove);
		}
		return true;
	}
	
	/**
	 * Checks if this container contains <code>itemIndex</code> given in the parameter,
	 *  regardless of the quantity,
	 * @param itemIndex the item index you want to check.
	 * @return true if the index if found, false if not.
	 */
	public boolean containsIndex(int itemIndex) {
		for(Item item : data) {
			if(item.getIndex() == itemIndex) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Serves the same purpose as <code>containsIndex</code>, but additionally, quantity
	 *  of items can be required here.
	 * @param itemIndex the item index you want to check.
	 * @param minQuantity item's quantity is required to be at least above this.
	 * @return true if this container contains the itemIndex with a quantity of at least
	 * 			minQuantity.
	 */
	public boolean contains(int itemIndex, int minQuantity) {
		if(stackable) {
			int quantity = 0;
			for(Item item : data) {
				if(item.getIndex() == itemIndex) {
					quantity++;
				}
			}
			if(quantity == 0 || quantity < minQuantity) {
				return false;
			}
			return true;
		}
		for(Item item : data) {
			if(item.getIndex() == itemIndex) {
				if(item.getQuantity() <= minQuantity) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Clear the data of this array.
	 */
	public void clear() {
		data.clear();
	}
	
	/**
	 * Returns the data collection for this container.
	 * @return List<Item>, containing the data for this container.
	 */
	public List<Item> getData() {
		return data;
	}
	
	/**
	 * Current size of the data collection in this container.
	 * @return Item collection's size
	 */
	public int getCurrentSize() {
		return data.size();
	}
	
	/**
	 * Returns the absolute maximum size this container was given. Cannot be changed.
	 * @return maximum number of items this container can hold.
	 */
	public int getMaxSize() {
		return maxSize;
	}
	
	/**
	 * <non-javadoc>
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("current size / max size: " + getCurrentSize() + "/" + getMaxSize());
		builder.append("\n");
		for(Item item : data) {
			builder.append("item, id=" + item.getIndex() + " quantity=" + item.getQuantity());
			builder.append("\n");
		}
		return builder.toString();
	}

}
