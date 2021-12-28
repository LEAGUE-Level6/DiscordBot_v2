package org.jointheleague.features.student;

public class SnakeSegment {
	private int xPos;
	private int yPos;
	
	SnakeSegment(int x, int y){
		xPos = x;
		yPos = y;
	}
	
	public int getXPos() {
		return xPos;
	}
	
	public int getYPos() {
		return yPos;
	}
	
	public void setXPos(int x){
		xPos = x;
	}
	
	public void setYPos(int y) {
		yPos = y;
	}
	
	public void followNextSegment(SnakeSegment s) {
		setXPos(s.getXPos());
		setYPos(s.getYPos());
	}
}
