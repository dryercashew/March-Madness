import java.util.ArrayList;

public class Bracket {

	private ArrayList<Game> bracket; //The final Arraylist of Games that are in the bracket
	private ArrayList<Game> tempBracket; //A temporary bracket used to not mess up the bracket field
	private String winningTeam; //The name of the winning team
	
	public Bracket(ArrayList<Team> teams){ 
		bracket = new ArrayList<Game>();
		tempBracket = new ArrayList<Game>();
		winningTeam = "None";
	}
	public ArrayList<Game> getBracket(){
		return bracket;
	}
	/**
	 * 
	 * @param a1 Gets sent an Array of ints to determine the smallest numebr
	 * @return Returns the smallest number out of the array
	 */
	public static int findMin(int[] a1) { //Finds the minimum of an array of ints
		int min = 0;
		for (int i = 1; i < a1.length; i++) {
			if (a1[i] < a1[min]) {
				min = i;
			}
		}
		return min;
	}
	/**
	 * Carson
	 * @param a1 Gets sent an ArrayList of teams to determine the min
	 * @return Returns the smallest seed out of the given
	 */
	public int findMinTeam(ArrayList<Team> a1){ 
		int min = 0; 
		for (int i = 1; i < a1.size(); i++) {
			if (a1.get(i).returnSeed() < a1.get(min).returnSeed() ) {
				min = i;
			}
		}
		return min;
	}
	public void addGames(Game game) { //Adds a new game to the ArrayList of Games bracket
		bracket.add(game);
	}
	public void clearTemp() {//Clears bracket field and fills it with objects in field tempBracket
		bracket.clear();
		for (int i = 0; i < tempBracket.size(); i++) {
			bracket.add(tempBracket.get(i));
		}
	}
	/**
	 * Carson
	 * @param a1 Gets sent an ArrayList of the Teams and finds the max seed
	 * @return Returns the largest seed
	 */
	public int findMaxTeam(ArrayList<Team> a1) { //Gets the max team, used in CreateGames
		int max = 0; 
		for (int i = 0; i < a1.size(); i++) { //Finds the max seed
			if (a1.get(i).returnSeed() >= a1.get(max).returnSeed()) {
				max = i; 
			}
		}
		return max; 
	} 
	/**
	 * Aidan
	 * @param teamList Gets sent an ArrayList of the teams which are going to be put into games
	 * @return bracket Returns those games that have been created
	 */
	public ArrayList<Game> createGames(ArrayList<Team> teamList) { //Creates the bracket and puts teams against each other
		ArrayList<Team> teamList2 = new ArrayList<>(); 
		for (int i = 0; i < teamList.size(); i++) {
			teamList2.add(teamList.get(i));
		}
		while (teamList.size() != 0) { //Runs a while loop until the team arraylist is empty
			int min = findMinTeam(teamList);//Finds the minimum seed value and the maximum
			int max = findMaxTeam(teamList);
			Game g1 = new Game(teamList.get(min), teamList.get(max));
			teamList.remove(min); //After making a game, removes the teams
			if (min < max) {
				teamList.remove(max - 1);
			}
			else { 
				teamList.remove(max);
			}
			bracket.add(g1);
		}
		for (int i = 0; i < teamList2.size(); i++) { //Fills the ArrayList of teams teamList with the temp teamList2
			teamList.add(teamList2.get(i));
		}
		return bracket;
	}
	/**
	 * Carson
	 * @param teamList Sends an ArrayList of teams that will be put together into games
	 * @return Returns the newly made games
	 */
	public ArrayList<Game> nextRounds(ArrayList<Team> teamList) { //Specially made for the PlayerChoices class where it sorts the next round of games
		ArrayList<Game> games = new ArrayList<>(); 
		while (teamList.size() != 0 && teamList.size() != 1) {
			Game g1 = new Game(teamList.get(0), teamList.get(1));
			games.add(g1);
			teamList.remove(0);
			teamList.remove(0);
		}
		return games;
	}
	/**
	 * Aidan
	 * @return Returns the bracket that is now sorted
	 */
	public ArrayList<Game> organizeBracket() { //Organizes the bracket in March Madness form
		ArrayList<Game> finalizedGame = new ArrayList<>(); 
		ArrayList<Game> origGame = bracket;
		for (int i = 0; i < 4; i++) { //Since there are 4 teams per seed, organized to seperate them into their own sections
			int num = 0;
			for (int j = 0; j < origGame.size(); j++) { //Finds the largest seed
				if (origGame.get(j).returnFirstSeed() > origGame.get(num).returnFirstSeed()) {
					num = j;
				}
			}
			finalizedGame.add(origGame.get(0)); //Adds first game and largest seed of the first team.
			finalizedGame.add(origGame.get(num));
			origGame.remove(0);
			origGame.remove(num - 1);
			int[] num2 = new int[6];
			for (int j = 0; j < origGame.size(); j++) {
				if (origGame.get(j).returnFirstSeed() == 4) { //Sorts them based on the real March Madness Bracket
					num2[0] = j;
				}
				if (origGame.get(j).returnFirstSeed() == 5) {
					num2[1] = j; 
				}
				if (origGame.get(j).returnFirstSeed() == 6) {
					num2[2] = j; 
				}
				if (origGame.get(j).returnFirstSeed() == 3) {
					num2[3] = j;
				}
				if (bracket.get(j).returnFirstSeed() == 7) {
					num2[4] = j;
				}
				if (origGame.get(j).returnFirstSeed() == 2) {
					num2[5] = j;
				}
				
			}
			for (int j = 0; j < num2.length; j++) {
				finalizedGame.add(origGame.get(num2[j]));
			}

			int count = 0;
			for (int j = 0; j < num2.length; j++) {
				int min = findMin(num2);
				origGame.remove(num2[min] - count);
				num2[min] = 999;
				count++;
			}
		}
		
		bracket = finalizedGame;
		for (int i = 0; i < bracket.size(); i++) {
			tempBracket.add(bracket.get(i));
		}
		return bracket;
	}
	/**
	 * 
	 * @param gameNum Gets sent a game number to print on the top half
	 * @return Returns the top half of a game with the team number in it
	 */
	private String printTopLn(int gameNum){
		if(bracket.size()>gameNum){
			String answer = "";
			String teamName = bracket.get(gameNum).t1Name().returnName();
			if (teamName.length() >= 14) {
				return teamName.substring(0, 14);
			}
			for(int i=0; i<(14-teamName.length())/2 + (14 - teamName.length()) % 2; i++){
				answer += "_";
			}
			answer += teamName;
			for(int i=0; i<(14-teamName.length())/2; i++){
				answer += "_";
			}
			return answer;
		}
		return "______________";
	}
	/**
	 * Carson
	 * @param gameNum Gets sent the Game number to get the certain game
	 * @return Returns the bottom section of the bracket with the team name 
	 */
	private String printBottomLn(int gameNum){
		if(bracket.size()>gameNum){
			String answer = "";
			String teamName = bracket.get(gameNum).t2Name().returnName();
			if (teamName.length() >= 14) {
				return teamName.substring(0, 14) + "|";
			}
			for(int i=0; i<(14-teamName.length())/2 + (14- teamName.length()) % 2; i++){
				answer += "_";
			}
			answer += teamName;
			for(int i=0; i<(14-teamName.length())/2; i++){
				answer += "_";
			}
			answer += "|";
			return answer;
		}
		return "______________|";
	}
	private String vert(){
		return String.format("%15s","|");
	}
	private String vert2(){
		return String.format("%16s","|");
	}
	/**
	 * Carson
	 * @param num Gets sent a num in which it will put that many spaces in
	 * @return Returns all the spaces
	 */
	private String space(int num){
		String answer = "";
		for(int i=0; i<num; i++){
			answer += " ";
		}
		return answer;
	}
	public void setWinner(String winner) {
		winningTeam = winner;
	}
	public String winningTeam() {
		if(!winningTeam.equals("None")){
			String answer = "";
			if (winningTeam.length() >= 14) {
				return winningTeam.substring(0, 14) + "|";
			}
			for(int i=0; i<(14-winningTeam.length())/2 + (14- winningTeam.length()) % 2; i++){
				answer += "_";
			}
			answer += winningTeam;
			for(int i=0; i<(14-winningTeam.length())/2; i++){
				answer += "_";
			}
			return answer;
		}
		return "______________";
	}
	/**
	 * Carson 
	 */
	public String toString(){ //Prints out the whole bracket in correct format using previous methods
		String answer = "";
		answer += printTopLn(0) + "\n";
		answer += vert() + printTopLn(32) + "\n";
		answer += printBottomLn(0) + vert() + "\n";
		answer += printTopLn(1) + vert2() + printTopLn(48) + "\n";
		answer += vert() + printBottomLn(32) + vert() + "\n";
		answer += printBottomLn(1) + space(15) + vert() + "\n";
		answer += printTopLn(2) + space(16) + vert() + printTopLn(56) + "\n";
		answer += vert() + printTopLn(33) + vert2() + vert() + "\n";
		answer += printBottomLn(2) + vert() + vert() + vert() + "\n";
		answer += printTopLn(3) + vert2() + printBottomLn(48) + vert() + "\n";
		answer += vert() + printBottomLn(33) + space(15) + vert()+ "\n";
		answer += printBottomLn(3) + space(30) + vert() + "\n";
		answer += printTopLn(4) + space(31) + vert() + printTopLn(60) + "\n";
		answer += vert() + printTopLn(34) + space(16) + vert() + vert() + "\n";
		answer += printBottomLn(4) + vert() + space(15) + vert() + vert() + "\n";
		answer += printTopLn(5) + vert2() + printTopLn(49) + vert2() + vert() + "\n";
		answer += vert() + printBottomLn(34) + vert() + vert() + vert() + "\n";
		answer += printBottomLn(5) + space(15) + vert() + vert() + vert() + "\n";
		answer += printTopLn(6) + space(16) + vert() + printBottomLn(56) + vert() + "\n";
		answer += vert() + printTopLn(35) + vert2() + space(15) + vert() + "\n";
		answer += printBottomLn(6) + vert() + vert() + space(15) + vert() + "\n";
		answer += printTopLn(7) + vert2() + printBottomLn(49) + space(15) + vert() + "\n";
		answer += vert() + printBottomLn(35) + space(30) + vert() + "\n";
		answer += printBottomLn(7) + space(45) + vert() + "\n";
		answer += printTopLn(8) + space(46) + vert() + printTopLn(62) + "\n";
		answer += vert() + printTopLn(36) + space(31) + vert() + vert() + "\n";
		answer += printBottomLn(8) + vert() + space(30) + vert() + vert() + "\n";
		answer += printTopLn(9) + vert2() + printTopLn(50) + space(16) + vert() + vert() + "\n";
		answer += vert() + printBottomLn(36) + vert() + space(15) + vert() + vert() + "\n";
		answer += printBottomLn(9) + space(15) + vert() + space(15) + vert() + vert() + "\n";
		answer += printTopLn(10) + space(16) + vert() + printTopLn(57) + vert2() + vert() + "\n";
		answer += vert() + printTopLn(37) + vert2() + vert() + vert() + vert() + "\n";
		answer += printBottomLn(10) + vert() + vert() + vert() + vert() + vert() + "\n";
		answer += printTopLn(11) + vert2() + printBottomLn(50) + vert() + vert() + vert() + "\n";
		answer += vert() + printBottomLn(37) + space(15) + vert() + vert() + vert() + "\n";
		answer += printBottomLn(11) + space(30) + vert() + vert() + vert() + "\n";
		answer += printTopLn(12) + space(31) + vert() + printBottomLn(60) + vert() + "\n";
		answer += vert() + printTopLn(38) + space(16) + vert() + space(15) + vert() + "\n";
		answer += printBottomLn(12) + vert() + space(15) + vert() + space(15) + vert() + "\n";
		answer += printTopLn(13) + vert2() + printTopLn(51) + vert2() + space(15) + vert() + "\n";
		answer += vert() + printBottomLn(38) + vert() + vert() + space(15) + vert() + "\n";
		answer += printBottomLn(13) + space(15) + vert() + vert() + space(15) + vert() + "\n";
		answer += printTopLn(14) + space(16) + vert() + printBottomLn(57) + space(15) + vert() + "\n";
		answer += vert() + printTopLn(39) + vert2() + space(30) + vert() + "\n";
		answer += printBottomLn(14) + vert() + vert() + space(30) + vert() + "\n";
		answer += printTopLn(15) + vert2() + printBottomLn(51) + space(30) + vert() + "\n";
		answer += vert() + printBottomLn(39) + space(45) + vert() + "\n";
		answer += printBottomLn(15) + space(60) + vert() + winningTeam() + "\n";  //halfway
		answer += printTopLn(16) + space(61) + vert() + "\n";
		answer += vert() + printTopLn(40) + space(46) + vert() + "\n";
		answer += printBottomLn(16) + vert() + space(45) + vert() + "\n";
		answer += printTopLn(17) + vert2() + printTopLn(52) + space(31) + vert() + "\n";
		answer += vert() + printBottomLn(40) + vert() + space(30) + vert() + "\n";
		answer += printBottomLn(17) + space(15) + vert() + space(30) + vert() + "\n";
		answer += printTopLn(18) + space(16) + vert() + printTopLn(58) + space(16) + vert() + "\n";
		answer += vert() + printTopLn(41) + vert2() + vert() + space(15) + vert() + "\n";
		answer += printBottomLn(18) + vert() + vert() + vert() + space(15) + vert() + "\n";
		answer += printTopLn(19) + vert2() + printBottomLn(52) + vert() + space(15) + vert() + "\n";
		answer += vert() + printBottomLn(41) + space(15) + vert() + space(15) + vert() + "\n";
		answer += printBottomLn(19) + space(30) + vert() + space(15) + vert() + "\n";
		answer += printTopLn(20) + space(31) + vert() + printTopLn(61) + vert2() + "\n";
		answer += vert() + printTopLn(42) + space(16) + vert() + vert() + vert() + "\n";
		answer += printBottomLn(20) + vert() + space(15) + vert() + vert() + vert() + "\n";
		answer += printTopLn(21) + vert2() + printTopLn(53) + vert2() + vert() + vert() + "\n";
		answer += vert() + printBottomLn(42) + vert() + vert() + vert() + vert() + "\n";
		answer += printBottomLn(21) + space(15) + vert() + vert() + vert() + vert() + "\n";
		answer += printTopLn(22) + space(16) + vert() + printBottomLn(58) + vert() + vert() + "\n";
		answer += vert() + printTopLn(43) + vert2() + space(15) + vert() + vert() + "\n";
		answer += printBottomLn(22) + vert() + vert() + space(15) + vert() + vert() + "\n";
		answer += printTopLn(23) + vert2() + printBottomLn(53) + space(15) + vert() + vert() + "\n";
		answer += vert() + printBottomLn(43) + space(30) + vert() + vert() + "\n";
		answer += printBottomLn(23) + space(45) + vert() + vert() + "\n";
		answer += printTopLn(24) + space(46) + vert() + printBottomLn(62) + "\n";
		answer += vert() + printTopLn(44) + space(31) + vert() + "\n";
		answer += printBottomLn(24) + vert() + space(30) + vert() + "\n";
		answer += printTopLn(25) + vert2() + printTopLn(54) + space(16) + vert() + "\n";
		answer += vert() + printBottomLn(44) + vert() + space(15) + vert() + "\n";
		answer += printBottomLn(25) + space(15) + vert() + space(15) + vert() + "\n";
		answer += printTopLn(26) + space(16) + vert() + printTopLn(59) + vert2() + "\n";
		answer += vert() + printTopLn(45) + vert2() + vert() + vert() + "\n";
		answer += printBottomLn(26) + vert() + vert() + vert() + vert() + "\n";
		answer += printTopLn(27) + vert2() + printBottomLn(54) + vert() + vert() + "\n";
		answer += vert() + printBottomLn(45) + space(15) + vert() + vert() + "\n";
		answer += printBottomLn(27) + space(30) + vert() + vert() + "\n";
		answer += printTopLn(28) + space(31) + vert() + printBottomLn(61) + "\n";
		answer += vert() + printTopLn(46) + space(16) + vert() + "\n";
		answer += printBottomLn(28) + vert() + space(15) + vert() + "\n";
		answer += printTopLn(29) + vert2() + printTopLn(55) + vert2() + "\n";
		answer += vert() + printBottomLn(46) + vert() + vert() + "\n";
		answer += printBottomLn(29) + space(15) + vert() + vert() + "\n";
		answer += printTopLn(30) + space(16) + vert() + printBottomLn(59) + "\n";
		answer += vert() + printTopLn(47) + vert2() + "\n";
		answer += printBottomLn(30) + vert() + vert() + "\n";
		answer += printTopLn(31) + vert2() + printBottomLn(55) + "\n";
		answer += vert() + printBottomLn(47) + "\n";
		answer += printBottomLn(31) + "\n";
		return answer;
	}

}


