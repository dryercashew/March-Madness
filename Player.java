import java.util.ArrayList;
import java.util.Scanner;

public class Player {

	private int points; //The amount of points the player has
	private String name; //The name of the player
	private ArrayList<String> predictions; //Predictions that the Player thinks will win
	
	public Player(String newName) { //Creates a Player object for the AI player
		this.name = newName;
		points = 0; 
		
	}
	public Player(Scanner console) { //Creates a Player object for an actual Player
		System.out.print("What is your name: ");
		name = console.next(); 
		points = 0; 
	}
	/**
	 * Aidan
	 * @param games Gets sent an ArrayList of Games which the player will choose from
	 * @param console Sent a Scanner to ask the user which team they think will win
	 * @param b1 Gets sent a bracket so it can be put into the bracket to show the player
	 * @return Returns an ArrayList of strings with the name of the teams they believe will win each game
	 */
	public ArrayList<String> PlayerChoices(ArrayList<Game> games, Scanner console, Bracket b1) {
		ArrayList<String> preds = new ArrayList<>(); 
		int counter = 1;
		while (games.size() != 0) { //Decreases game size until there is just 1 winner
			int gameSize = games.size(); 
			System.out.println("Round " + counter);
			ArrayList<Team> a1 = new ArrayList<Team>(); //Creates a new Arraylist of teams that the user will choose who goes on to the next round
			Game newGame = new Game(); 
			
			for (int i = 0; i < gameSize; i++) { 
				int num = 0; 
				while (num != 1 && num != 2) { //Asks the user which team they think will win
					System.out.print(games.get(i).askPlayer() + ": ");
					num = console.nextInt(); 
				} //Won't exit out until the user either selects 1 or 2
				
				if (num == 1) { //Adds the first team to there preference ArrayList if they choose 1
					preds.add(games.get(i).t1Name().returnName());
					a1.add(games.get(i).t1Name());
					newGame.addTeam(games.get(i).t1Name());
				}
				
				else { //Adds the second team to there ArrayList if they chose 2.
					preds.add(games.get(i).t2Name().returnName());
					a1.add(games.get(i).t2Name());
					newGame.addTeam(games.get(i).t2Name());
				}
				
				if (newGame.teamsExist()) { //If the teams in the Game class are filled with real teams, then they are added to the bracket
					b1.addGames(newGame);
					newGame = new Game(); 
				}
			}
			System.out.println(b1);
			counter++;
			
			if (games.size() != 1) {
				Bracket b2 = new Bracket(a1);
				games = b2.nextRounds(a1);
			}	
			else {
				b1.setWinner(preds.get(preds.size() - 1));
				System.out.println(b1);
				b1.clearTemp();
				return preds;
			}
		}
		b1.setWinner(preds.get(preds.size() - 1));
		System.out.println(b1);
		b1.clearTemp();
		return preds;
	}
	public void changePreds(ArrayList<String> preds) {
		predictions = preds;
	}
	/**
	 * 
	 * @param games Gets sent an ArrayList of all the games
	 * @param dif Gets sent the difficulty of the AI
	 * @param b1 Sent the bracket so that it can be printed out when it is the official bracket
	 * @return
	 */
	public ArrayList<String> AiChoices(ArrayList<Game> games, double dif, Bracket b1) {
		ArrayList<String> preds = new ArrayList<String>();  //Creates and arraylist of the preferences the ai wants
		boolean officialBrack = false;
		if (name.equals("Official")) {
			officialBrack = true;
		}
		while (games.size() != 0) { 
			int gameSize = games.size();
			ArrayList<Team> a1 = new ArrayList<Team>(); //Creates a new ArrayList to limit the games and go to the next round 
			Game newGame = new Game();
			
			for (int i = 0; i < gameSize; i++) {
				double random = (Math.random() * 1) - dif;
				
				if (games.get(i).returnProb() > random) { //Gets the random number and if the prob number is higher than add t1
					preds.add(games.get(i).t1Name().returnName());
					a1.add(games.get(i).t1Name());
					if (officialBrack) {
						newGame.addTeam(games.get(i).t1Name());	
					}
				}
				else { //Else adds the other team
					preds.add(games.get(i).t2Name().returnName());
					a1.add(games.get(i ).t2Name());
					if (officialBrack) {
						newGame.addTeam(games.get(i).t2Name());
					}
				}
				if (officialBrack && newGame.teamsExist()) {
					b1.addGames(newGame);
					newGame = new Game();
				}
			}
			
			if (games.size() != 1) { //Will create a new Bracket for future rounds
				Bracket b2 = new Bracket(a1);
				games = b2.nextRounds(a1); //Creates the new games for the following rounds
			}
			else {	
				predictions = preds;
				b1.setWinner(predictions.get(predictions.size() - 1));
				return preds;
			}
		}
		b1.setWinner(predictions.get(predictions.size() - 1)); //Sets the winning team in the final position
		predictions = preds;
		return preds; 
	}
	public String getSpecTeam(int num) { //Returns the team with the specified 
		return predictions.get(num);
	}
	public String toString() {
		String word = name + ": ";
		for (int i = 0; i < predictions.size(); i++) { //Returns string with all the predictions in a row
			word += predictions.get(i) + " ";
		}
		return word;
	}
	public String returnName() { 
		return name;
	}
	public int returnPoints() {
		return points;
	}
	public void addpoints(int newPoints) {
		points += newPoints;
	}
}
