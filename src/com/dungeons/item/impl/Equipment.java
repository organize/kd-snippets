package com.dungeons.item.impl;

import com.dungeons.entity.impl.Player;
import com.dungeons.item.Item;
import com.dungeons.item.ItemType;

@SuppressWarnings("serial")
public class Equipment implements Item {
	
	private int index;
	private int quantity;
	
	public Equipment(int index, int quantity) {
		this.index = index;
		this.quantity = quantity;
	}

	@Override
	public Item consume(Player player) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public ItemType getItemType() {
		return ItemType.EQUIPMENT;
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
