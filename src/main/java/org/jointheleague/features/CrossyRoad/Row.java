package org.jointheleague.features.CrossyRoad;

import java.util.Random;

public class Row {
	int type; //0 grass, 1 road, 2 = water, 3 = train
	boolean main;
	boolean train = false;
	int trainCounter = 0;
	boolean log = false;
	int woodCounter = 0;
	String burried;
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
public boolean update(boolean mainOrNot) {
	//DO NOT NEED TO shift anything that has been removed from being main as it should no longer exist
	//EVERYTHING shifts over one
	//Check main and if center overlaps with something finish game
	if(type!=0) {
	for(int i = 0; i < gameRow.length-1;i++) {
		gameRow[i] = gameRow[i+1];
		if(i == 3) {
			burried = gameRow[i+1];
		}
		if(main&&mainOrNot&&i==2) {
		gameRow[i] = burried;
		} 
	}
	String er = "";
	if(type==1) {
		if(new Random().nextInt(10)<3) {
			if(new Random().nextBoolean()) {
				er = ":blue_car:";
			} else {
				er = ":red_car:";
			}
		}else {
		er = ":yellow_square:";
		}
	} else if (type==2) {
		if(woodCounter>new Random().nextInt(5)) {
		woodCounter = 0;
		log = false;
		}
		if(log) {
			er = ":wood:";
			woodCounter++;
		} else {
			if(new Random().nextInt(12)<1) {
			log = true;
			}
			er = ":blue_square:";
		}
		
	} else {
		if(trainCounter>9) {
			trainCounter = 0;
			train = false;
			}
			if(train) {
				if(trainCounter==0) {
					er = ":bullettrain_front:";
					trainCounter++;
				}
				else {
				er = ":train:";
				trainCounter++;
				}
			} else {
				if(new Random().nextInt(20)<1) {
				train = true;
				}
				if(gameRow[6].equals(":red_square:")) {
					er = ":white_large_square:";
				} else {
					er = ":red_square:";
				}
			}
	}
	gameRow[6] = er;
	}
	main = mainOrNot;
	if(main&&(gameRow[3]==":red_car:"||gameRow[3]==":blue_car:"||gameRow[3]==":bullettrain_front:"||gameRow[3]==":train:"||gameRow[3]==":blue_square:")) {
	gameRow[3] = ":x:";
	return true;
	} else if (main){
		gameRow[3] = ":bird:";
	}
	return false;
}
}
