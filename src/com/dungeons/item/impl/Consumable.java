package com.dungeons.item.impl;

import com.dungeons.entity.impl.Player;
import com.dungeons.item.Item;
import com.dungeons.item.ItemType;

@SuppressWarnings("serial")
public class Consumable implements Item {
	
	public int index, quantity;
	
	public Consumable(int index, int quantity) {
		this.index = index;
		this.quantity = quantity;
	}

	@Override
	public Item consume(Player player) {
		if(quantity - 1 != 0) {
			quantity -= 1;
			return this;
		}
		return null;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public ItemType getItemType() {
		return ItemType.CONSUMABLE;
	}

	@Override
	public int getIndex() {
		return index;
	}

	@Override
	public int getQuantity() {
		return quantity; 
	}

	@Override
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public void setIndex(int index) {
		this.index = index;
	}

}
