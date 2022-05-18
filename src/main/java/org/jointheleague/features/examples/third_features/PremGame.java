package org.jointheleague.features.examples.third_features;

public class PremGame {
int matchday=0;
String date="";
String team1;
String score;
String team2;

public void premGame(int match, String date1, String teamOne, String teamTwo, String score1){
	matchday=match;
	date=date1;
	team1=teamOne;
	score=score1;
	team2=teamTwo;
}

int getMatch() {
	return matchday;
}
	
String getDate() {
	return date;
}

String getTeam1() {
	return team1;
}

String getScore() {
	return score;
}

String getTeam2() {
	return team2;
}


}
