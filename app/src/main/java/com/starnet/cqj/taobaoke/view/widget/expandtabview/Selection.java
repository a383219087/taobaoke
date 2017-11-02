package com.starnet.cqj.taobaoke.view.widget.expandtabview;

public class Selection {

	private int groupPosition;
	
	private int childPosition;

	public int getGroupPosition() {
		return groupPosition;
	}

	public void setGroupPosition(int groupPosition) {
		this.groupPosition = groupPosition;
	}

	public int getChildPosition() {
		return childPosition;
	}

	public void setChildPosition(int childPosition) {
		this.childPosition = childPosition;
	}
	
	public void copy(Selection selection){
		if(selection!=null){
			groupPosition =selection.groupPosition;
			childPosition =selection.childPosition;
		}
	}
	
}
