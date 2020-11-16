
public class Team {

	private String name; //The name of the certain team
	private int rating; //The seed ranking of that team
	
	public Team(String newName, int newRating) { //Gets sent an name and a seed and initializes it
		name = newName; 
		rating = newRating; 
	}
	public String toString() { //Prints out the team and their rating
		return (name + " has a seed of " + rating);
	}
	public int returnSeed() { //Returns the seed
		return rating;
	}
	public String returnName() { //Returns the name
		return name;
	}
}
