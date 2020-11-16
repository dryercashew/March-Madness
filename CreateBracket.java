import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Aidan Spelman and Carson Davy 
 * March Madness Project 
 * Post-AP Final Project
 * 6/7/18
 */

public class CreateBracket {

	
	public static void main(String[] args) throws FileNotFoundException {
		File f = new File("teams.txt");
		Scanner input = new Scanner(f);
		ArrayList<Team> teamList = new ArrayList<>(); 
		
		while (input.hasNextLine()) {
			
			String team = input.nextLine(); 
			String name = team.substring(0, team.length() - 2);
			while (team.length() > 4) {
				team = team.substring(team.indexOf(" ") + 1);
			}
			int seed = Integer.parseInt(team);
			Team t1 = new Team(name, seed);
			teamList.add(t1);
		}

		boolean playAgain = true;
		while (playAgain) {
			ArrayList<Game> games = new ArrayList<>(); 
			Bracket b1 = new Bracket(teamList); //Creates a new Bracket in the Bracket Class
			games = b1.createGames(teamList); //Creates the games 
			games = b1.organizeBracket(); //Organizes the games into an order such as in March Madness
			int count = 0; 
			
			Scanner console = new Scanner(System.in);
			ArrayList<Player> players = new ArrayList<Player>(); 
			System.out.print("There will be 4 players in total, do you want to play? There are currently " + count + " players. \n If you don't want to play press (1)");
			int num = console.nextInt(); 
			
			while (count != 4 && num != 1) {
				count++; 
				Player p1 = new Player(console);
				ArrayList<String> preds = p1.PlayerChoices(games, console, b1);
				p1.changePreds(preds);
				players.add(p1); //Adds it to the ArrayList made up of Players 
				System.out.print("There will be 4 players in total, do you want to play? There are currently " + count + " players. \n If you don't want to play press (1)");
				num = console.nextInt(); 
			}
			double dif = findDif(console);
			for (int i = 0; i < 4 - count; i++) { ;//Creates AIs' based on how many actual players there are
				Player p1 = new Player("Ai Number " + (i + 1)); //Creates AI player 
				ArrayList<String> preds = p1.AiChoices(games, dif, b1); //Creates preferences in AI player
				p1.changePreds(preds); //sets the new preferences
				players.add(p1);
			}
			
			
			Player official = new Player("Official"); //Creates the official bracket which choices are compared to
			ArrayList<String> preds = official.AiChoices(games, 0, b1);
			System.out.println(b1);
			for (int i = 0; i < preds.size(); i++) { //Runs through nested for loops to determine points
				for (int j = 0; j < players.size(); j++) {
					if (players.get(j).getSpecTeam(i).equals(official.getSpecTeam(i))) { 
						if (i == preds.size() - 1) {
							players.get(j).addpoints(10); //If they predict the winning team, give ten points
						}
						else if (findTeamSeed(players.get(j).getSpecTeam(i), teamList) > 11) {
							players.get(j).addpoints(5);
						}
						else { 
							players.get(j).addpoints(2);
						}
					}
				}
			}
			for (int i = 0; i < players.size(); i++) { 
				System.out.println(players.get(i));
			}
			System.out.println(official);
			int max = 0;
			System.out.println("");
			b1.clearTemp();
			
			for (int i = 0; i < players.size(); i++) {
				System.out.println(players.get(i).returnName() + " has " + players.get(i).returnPoints() + " points");
				if (players.get(i).returnPoints() > players.get(max).returnPoints()) {
					max = i;
				}
			}
			System.out.println(players.get(max).returnName() + " won the game with " + players.get(max).returnPoints() + " points");
			System.out.print("Do you want to play again? Press (1) to quit: ");
			int play = console.nextInt(); 
			if (play == 1) {
				playAgain = false;
			}
		}
	}
	/**
	 * Aidan
	 * @param team Gets sent a name of a certain team
	 * @param teams Gets sent the ArrayList of all the Teams
	 * @return returns the seed of the team
	 */
	public static int findTeamSeed(String team, ArrayList<Team> teams) {
		int num = 0;
		for (int i = 0; i < teams.size(); i++) {
			if (team.equals(teams.get(i).returnName())) {
				num = teams.get(i).returnSeed();
			}
		}
		return num;
	}
	/**
	 * Aidan
	 * @param console Gets sent a Scanner to ask what difficulty the player wants
	 * @return Returns a difference to make the Ai either more challenging or easy
	 */
	public static double findDif(Scanner console) {
		System.out.println("What difficulty do you want Easy(1), Medium(2) or Hard(3)");
		int difficulty = console.nextInt(); 
		double dif = 0; //Depending on what the user picks, there is a difficulty assigned to the Ai
		if (difficulty == 1) {
			dif = .1;
		}
		else if (difficulty == 2) {
			dif = .05;
		}
		return dif;
	}
}
