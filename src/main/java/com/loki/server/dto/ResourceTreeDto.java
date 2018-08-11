package com.loki.server.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResourceTreeDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String text;
	private int parentId;
	private boolean selectable = false;
	private List<ResourceTreeDto> nodes=new ArrayList<ResourceTreeDto>();
	
	
	public boolean isSelectable() {
		return selectable;
	}
	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<ResourceTreeDto> getNodes() {
		return nodes;
	}
	public void setNodes(List<ResourceTreeDto> nodes) {
		this.nodes = nodes;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	
	
	
}
