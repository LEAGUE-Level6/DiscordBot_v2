package org.jointheleague.features.CrossyRoad;

import java.util.Random;

public class Row {
	int type; //0 grass, 1 road, 2 = water, 3 = train
	boolean main;
	boolean train = false;
	int trainCounter = 0;
	boolean log = false;
	int woodCounter = 0;
	public String[] gameRow = new String[7];
public Row(int type,boolean main) {
	this.type = type;
	this.main = main;
	setUp();
}
private void setUp() {
	switch(type) {
	case 0:
		for(int i = 0; i < gameRow.length;i++) {
			if(new Random().nextInt(3)<3) {
		gameRow[i] = ":green_square:";	
			} else {
				gameRow[i] = ":evergreen_tree:";
			}
		}
		if(main) {
			gameRow[3] = ":bird:";
		}
		break;
	case 1:
		for(int i = 0; i < gameRow.length;i++) {
		gameRow[i] = ":yellow_square:";	
		}
		break;
	case 2:
	for(int i = 0; i < gameRow.length;i++) {
		gameRow[i] = ":blue_square:";	
		}
		break;
	case 3:
		for(int i = 0; i < gameRow.length;i++) {
			if(i%2==1) {
			gameRow[i] = ":red_square:";
			} else {
			gameRow[i] = ":white_large_square:";

			}
			}
		break;
	}
}
public void update(boolean mainOrNot) {
	//DO NOT NEED TO shift anything that has been removed from being main as it should no longer exist
	main = mainOrNot;
	//EVERYTHING shifts over one
	//Check main and if center overlaps with something finish game
	if(type!=0) {
	for(int i = 0; i < gameRow.length-1;i++) {
		gameRow[i] = gameRow[i+1];
	}
	String er = "";
	if(type==1) {
		if(new Random().nextInt(10)<3) {
			if(new Random().nextBoolean()) {
				er = "blue_car";
			} else {
				er = "red_car";
			}
		}else {
		er = ":yellow_square";
		}
	} else if (type==2) {
		//WATER!
	} else {
		//TRAIN!
	}
	gameRow[7] = er;
	}
}
}
