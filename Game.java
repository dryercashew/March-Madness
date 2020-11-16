
public class Game {

	private Team t1; //The first team that will be playing, will have a smaller seed
	private Team t2;  //The second team that is the game, will have a larger seed
	private double t1ProbWin; //The probability that t1 will win the game
	
	
	public Game(Team newT1, Team newT2) { //Gets sent two Teams which are put into t1 and t2, then calculates the prob
		t1 = newT1; 
		t2 = newT2; 
		findProb(); 
		
	}
	public Game() { //In scenario where to two teams are unsure, no params sent and gets put into name Missing
		t1 = new Team("Missing", 3);
		t2 = new Team("Missing", 3);
	}
	/**
	 * Aidan
	 * @param newT1 Gets sent a team in which it will be assigned to either t1 or t2
	 */
	public void addTeam(Team newT1) {
		if (t1.returnName().equals("Missing")) {
			t1 = newT1;
		}
		else if (t2.returnName().equals("Missing")) {
			t2 = newT1;
		}
	}
	/**
	 * Aidan
	 * @return returns whether or not both teams are still Missing or not
	 */
	public boolean teamsExist() {
		boolean exist = true;
		if (t1.returnName().equals("Missing") || t2.returnName().equals("Missing")) {
			exist = false;
		}
		return exist;
	}
	public String toString() {
		return (t1.returnName() + "(" + t1.returnSeed() + ")" + " with a chance of " + t1ProbWin + " is having a game against " + t2.returnName() + "(" + t2.returnSeed() + ")");
	}
	
	public void findProb() { 
		int team1 = (int) (Math.pow(t1.returnSeed(), .75)); //Rasies both teams seed to the .75th power, making them closer to each other
		int team2 = (int) (Math.pow(t2.returnSeed(), .75));
		int num = (int) ((double) team2 / (team1 + team2) * 100); //Takes the numbers and makes a probability with 2 decimal points
		t1ProbWin = num / 100.0; //Finalizes the probability
	}
	public int returnFirstSeed() { //Returns the seed of the first teamm
		return t1.returnSeed();
	}
	public Team t1Name() { //Returns t1
		return t1; 
	}
	public Team t2Name() { //Returns t2
		return t2;
	}
	public double returnProb() { //Returns the probability of t1 winning
		return t1ProbWin; 
	}
	public String askPlayer() {//Used in the Player asking of which team will win
		return "Who will win " + t1.returnName() + "(1) or " + t2.returnName() + "(2) " + t1.returnName() + " has a " + t1ProbWin + " of winning";
	}
}