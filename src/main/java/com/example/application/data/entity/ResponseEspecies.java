package com.example.application.data.entity;

import java.util.List;

public class ResponseEspecies {
	private List<Especies> items;
	private boolean hasMore;
	public List<Especies> getItems() {
		return items;
	}
	public void setItems(List<Especies> items) {
		this.items = items;
	}
	public boolean isHasMore() {
		return hasMore;
	}
	public void setHasMore(boolean hasMore) {
		this.hasMore = hasMore;
	}
	
	
	
}
