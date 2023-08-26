package com.example.application.data.entity;

import java.util.List;

public class ResponseHabitats {
	private List<Habitats> items;
	private boolean hasMore;
	
	public List<Habitats> getItems() {
		return items;
	}
	public void setItems(List<Habitats> items) {
		this.items = items;
	}
	public boolean isHasMore() {
		return hasMore;
	}
	public void setHasMore(boolean hasMore) {
		this.hasMore = hasMore;
	}
	
}
