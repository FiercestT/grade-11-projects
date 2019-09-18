import java.util.*;

public class Bunco 
{
	//ANSI Colors Enum
	public static enum Ansi
	{
		//Stores all of the values of the colors
		//They are all caps even though they arent final but it makes life easier.
		RESET("\u001B[0m"),
		BLACK("\u001B[30m"),
		RED("\u001B[31m"),
		GREEN("\u001B[32m"),
		YELLOW("\u001B[33m"),
		BLUE("\u001B[34m"),
		PURPLE("\u001B[35m"),
		CYAN("\u001B[36m"),
		WHITE("\u001B[37m"),
		BLACK_BACKGROUND("\u001B[40m"),
		RED_BACKGROUND("\u001B[41m"),
		GREEN_BACKGROUND("\u001B[42m"),
		YELLOW_BACKGROUND("\u001B[43m"),
		BLUE_BACKGROUND("\u001B[44m"),
		PURPLE_BACKGROUND("\u001B[45m"),
		CYAN_BACKGROUND("\u001B[46m"),
		WHITE_BACKGROUND("\u001B[47m");
		
		private String code;
		
		//Get the code of each value
		public String GetCode()
		{
			return this.code;
		}
		
		private Ansi(String code)
		{
			this.code = code;
		}
	}
	
	//Player variables, not initialized until playersamt is set.
	public static String[] playernames; //Name
	public static String[] playercolors; //Ansi Color name
	public static int[] playerscore; //Self Explanitory
	public static int[] playerorder; //How the player is randomly ordered
	public static boolean[] playerrolled; //If they rolled yet in the round
	public static boolean[] playertie; //If the player is part of a tie
	public static int playersamt; //How many players there are
	public static boolean starting = false; //Variables for command restrictions
	public static boolean menu = true;
	public static boolean canroll = false;
	public static boolean ending = false;
	public static Scanner sc = new Scanner(System.in); //The main scanner used in the program.
	public static String choice; //The input of the scanner
	public static int[] dice = new int[3]; //The dice
	public static int round = 1; //The round
	
	//An array of colors that are displayed and can be chosen from when choosing colors.
	public static String[] colors = new String[]
	{
		"Red",
		"Green",
		"Yellow",
		"Blue",
		"Purple",
		"Cyan",
		"Black",
	};
	
	//Entry
	public static void main(String[] args)
	{
		//Create commands and go to menu
		CommandConfig.InitializeCommands();
		Util.Clear();
		Menu();
	}

	public static void Menu()
	{
		//Set defaults
		menu = true;
		starting = false;
		ending = false;
		canroll = false;
		Commands.RefreshCommands();
		//Welcome the user in color
		System.out.println("Welcome to " + Ansi.GREEN.GetCode() + "Bunko!" + Ansi.RESET.GetCode());
		System.out.println("By Fiercest");
		System.out.println("Please enter one of the following commands!");
		System.out.println("");
		Util.Help(); //Not invoking because menu = true and that is a restriction of CallCommand("Command", "Help", Bunko.menu, true);
		choice = sc.nextLine(); //User input
		var command = false; //Local variable that says if there was a command or not.
		for(int i = 0 ; i < Commands.commands; i++) //For all of the commands
		{
			if(choice.equalsIgnoreCase(Commands.name[i]) && Commands.restriction[i] == Commands.type[i]) //If their choice was equal to a command name and is within its restriction.
			{
				//CallCommand(input)
				Commands.CallCommand(choice);
				command = true; //Set command to true and break.
				break;
			}
		}
		//If there wasnt a command or it was invalid.l
		if(command == false)
		{
			Util.Clear();
			System.out.println("Invalid Command. Please Try Again.");
			System.out.println("");
			Menu();
			//Reprompt
		}
	}
	
	//GameCreation is called in CommandConfig.Play()
	public static void GameCreation()
	{
		//Ask how many users there will be
		System.out.println("How many peaople will be playing today (2-4)");
		try //Put in try so that any errors will be caught
		{
			//The amount of players = the users input. 
			playersamt = Integer.parseInt(sc.nextLine()); //I did not use nextInt() to avoid the whitespace issue. Just as easy to parse
			Util.Clear();
			if(playersamt >= 2 && playersamt <= 4) //Check if they chose within 2-4
			{
				//Create all the player arrays according to amount of players. Easier to loop through if its consistent.
				playernames = new String[playersamt];
				playercolors = new String[playersamt];
				playerorder = new int[playersamt];
				playerscore = new int[playersamt];
				playerrolled = new boolean[playersamt];
				playertie = new boolean[playersamt];
				//As for each players name and color in their methods. Sends the users index.
				for(int i = 0; i < playersamt; i++)
				{
					ChooseName(i);
					ChooseColors(i);
				}
			}
			//If they chose less than or greater than 4, tell them they are dumb.
			else
			{
				System.out.println("Please enter a number between 2-4!");
				System.out.println("");
				GameCreation();
			}
			//If all goes well, Call Order
			Order();
		}
		catch(Exception ex)
		{
			//Notify of an error.
			Util.Clear();
			System.out.println("There was an error. Please try again.");
			System.out.println("");
			GameCreation();
		}
	}
	
	//Chose the users name
	public static void ChooseName(int val)
	{
		System.out.println("Player " + (val+1) + ", Please enter your name"); //Asks user + index what their name is
		playernames[val] = sc.nextLine(); //Allows them to input
		if(playernames[val].length() > 20) //Cuts their name off at 20 characters if it is greater than that (I <3 formatting)
		{
			playernames[val] = playernames[val].substring(0, 20);
		}
		playerrolled[val] = false; //set their rolled to false just in case.
		Util.Clear();
		//And then move on to the next method in the loop
	}
	
	public static void ChooseColors(int val)
	{
		System.out.println(playernames[val] + ", Choose your color!"); //Asks them on their newly bestowed name what their color is
		for(int k = 0; k < colors.length; k++)
		{
			//Runs through the colors array to list the names.
			if(k == colors.length - 1)
			{
				System.out.print(colors[k] + "."); //Put a period at the end
			}
			else
			{
				System.out.print(colors[k] + ", "); //Commas in between
			}

		}
		System.out.println("");
		
		String color = sc.nextLine(); //Get their choice
		
		var colorchosen = false; //Set a temp variable to check for errors
		for(int k = 0; k < colors.length; k++)	//Run through all of the colors
		{
			if(color.equalsIgnoreCase(colors[k])) //If their color = one from the array
			{
				playercolors[val] = colors[k].toUpperCase() + "_BACKGROUND"; //Set their playercolor to their color + _BACKGROUND since thats the format of the Ansi Enum.
				colorchosen = true; //Yes color was chosen
			}
		}
		//If no color was chosen after the end of the loop
		if(colorchosen == false)
		{
			//They messed up
			Util.Clear();
			System.out.println("Invalid Color, Please Try Again");
			System.out.println("");
			ChooseColors(val);
		}
		else
		{
			Util.Clear();
		}
		//Move on
	}
	
	public static void Order()
	{
		//Determine player order.
		//Since replay comes back here, reset variables back to defaults.
		for(int i = 0; i < playersamt; i++)
		{
			playerscore[playerorder[i]] = 0;
		}
		round = 1;
		ending = false;
		starting = true;
		menu = false;
		Commands.RefreshCommands();

		//Create a new arraylist of ints
		ArrayList<Integer> values = new ArrayList<Integer>();
		for(int i = 0; i < playersamt; i++) //For how many players there are, 
		{
			values.add(i); //add numbers from 0-playersamt.
		}
		Collections.shuffle(values); //Shuffle the list
		for(int i = 0; i < playersamt; i++) //For each player
		{
			playerorder[i] = values.get(i); //Set their order to the shuffled value with an index of i
		}
		DisplayPlayers();	//When thats all done display the players in their order
	}
	public static void DisplayPlayers()
	{
		System.out.println(String.format("%-21s%8s", "Players", "Order"));
		System.out.println("-----------------------------");
		var place = "ERROR"; //If this shows up something bad happened
		for(int i = 0; i < playersamt; i++) //Run a loop setting a local to their order to correspond with the player order.
		{
			if(i == 0)
			{
				place = "First";
			}
			if(i == 1)
			{
				place = "Second";
			}
			if(i == 2)
			{
				place = "Third";
			}
			if(i == 3)
			{
				place = "Fourth";
			}
			//Print out the users in their order. Nested Array.
			System.out.println(String.format("%-30s%8s", Ansi.valueOf(playercolors[playerorder[i]]).GetCode() + playernames[playerorder[i]] + Ansi.RESET.GetCode(), place));
		}
		//When thats done, call Starting
		Starting();
	}
	
	public static void Starting()
	{
		System.out.println("");
		Util.Help(); //Display commands
		choice = sc.nextLine();
		var command = false; //Get their input
		for(int i = 0 ; i < Commands.commands; i++) //I described all of this above
		{
			if(choice.equalsIgnoreCase(Commands.name[i]) && Commands.restriction[i] == Commands.type[i]) //This time we can call Continue from CallCommands
			{
				Commands.CallCommand(choice);
				command = true;
				break;
			}
		}
		if(command == false)
		{
			Util.Clear();
			System.out.println("Invalid Command. Please Try Again.");
			System.out.println("");
			DisplayPlayers(); //This time if they mess up redisplay the players
		}
	}
	
	//This method lists the players and their score.
	public static void ListPlayers(boolean roll, boolean overtime)
	{
		//If they are being displayed during a roll or not, determine if there is a clear before.
		if(roll == false)
		{
			Util.Clear();
		}
		//If we are in overtime, say we are in overtime rounds + the round
		if(overtime)
		{
			System.out.println("Overtime Round: " + round);
		}
		else
		{
			System.out.println("Round " + round);
		}
		System.out.println("------------------------------------");
		System.out.println(String.format("%-10s%-20s%6s", "Rolled", "Name", "Score"));
		for(int i = 0; i < playersamt; i++) //For each player
		{
			var val = "N";
			if(playerrolled[playerorder[i]]) //If they have rolled, set the local to Y otherwise N
			{
				val = "Y";
			}
			else
			{
				val = "N";
			}
			//If we are in overtime, only show the players in a tie
			if(overtime)
			{
				if(playertie[playerorder[i]])
				{
					System.out.println(String.format("%-10s%-30s%5s", val, Ansi.valueOf(playercolors[playerorder[i]]).GetCode() + playernames[playerorder[i]] + Ansi.RESET.GetCode(), playerscore[playerorder[i]]));
				}
			}
			else
			{
				//Show the players and color in their order, with their rolled status(val) beforehand and score afterward.
				System.out.println(String.format("%-10s%-30s%5s", val, Ansi.valueOf(playercolors[playerorder[i]]).GetCode() + playernames[playerorder[i]] + Ansi.RESET.GetCode(), playerscore[playerorder[i]]));
			}
		}
		System.out.println("------------------------------------");
		System.out.println("");
		//If roll is false say who is going first and start the main loop for rounds.
		if(roll == false)
		{
			System.out.println(Ansi.valueOf(playercolors[playerorder[0]]).GetCode() + playernames[playerorder[0]] + Ansi.RESET.GetCode() + " Is going first!");
			RoundLoop();
		}
	}
	
	//This runs the rolling and rounds.
	public static void RoundLoop()
	{
		Util.Clear();
		for(int i = 0; i < 6; i++) //For 6 rounds
		{
			for(int k = 0; k < playersamt; k++) //Reset player rolling every round
			{
				playerrolled[k] = false;
			}
			for(int k = 0; k < playersamt; k++) //For each user
			{
				Round(k, false); //Start a round that isnt in overtime for the user k
			}
			//At the end of each cycle of rounds, increase the round.
			round++;
		}
		//At the end of 6 rounds, call EndRounds
		EndRounds();
	}
	
	//Round is the start of the roll for each player
	public static void Round(int val, boolean overtime)
	{
		//Set the restrictions each time
		starting = false;
		menu = false;
		canroll = true;
		ending = false;
		
		//List the players for a roll round, set overtime arg to Round's overtime arg
		ListPlayers(true, overtime);
		System.out.println("");
		//Tell which user will roll
		System.out.println(Ansi.valueOf(playercolors[playerorder[val]]).GetCode() + playernames[playerorder[val]] + Ansi.RESET.GetCode() + " Is going to roll!");
		System.out.println("Type 'r' or 'roll' to roll.");
		choice = sc.nextLine(); //Prompt them and take input.
		if(choice.equalsIgnoreCase("r") || choice.equalsIgnoreCase("roll")) //If they choose r or roll, continue
		{
			//If overtime is true, do an overtime roll, else do a normal one
			if(overtime)
			{
				Roll(val, true);
			}
			else
			{
				Roll(val, false);
			}
		}
		else //Tell them they messed up
		{
			Util.Clear();
			System.out.println("Invalid Command, Please Try Again.");
			System.out.println("");
			Round(val, false);
		}
	}
		
	//This determines the rolls and results for the user
	public static void Roll(int val, boolean overtime)
	{
		Util.Clear();
		Random rand = new Random(); //Instantiate Random object.
		var tmpround = 1; //Set tmpround to 1. This will say which round we are in and determines if the user will get points or not
		if(!overtime)
		{
			//If we are not in overtime, tmpround = round and use the term Round
			tmpround = round;
			System.out.println("Round " + round + " Results: ");
		}
		else
		{
			//Otherwise, if we are in overtime, the user has to roll 6
			tmpround = 6;
			System.out.println("Overtime Round: " + round + " Results: ");
		}
		//The user rolled (1 line)
		System.out.print(Ansi.valueOf(playercolors[playerorder[val]]).GetCode() + playernames[playerorder[val]] + Ansi.RESET.GetCode() + " Rolled ");
		for(int i = 0; i < 3; i++) //Set random numbers between 1-6 for each die.
		{
			dice[i] = rand.nextInt(6) + 1;
		}
		for(int i = 0; i < 3; i++) //Check the values of ecah die
		{
			//If they are all equal but not equal to the round. Set the color to yellow (it checks 3 times but I had to for the else if, does not change outcome either way.
			if((dice[0] == dice[1] && dice[1] == dice[2]) && dice[0] != tmpround && dice[1] != tmpround && dice[2] != tmpround)
			{
				System.out.print("[" + Ansi.YELLOW.GetCode() + dice[i] + Ansi.RESET.GetCode() + "] ");
			}
			else if(dice[i] == tmpround) //If they are not equal to each other, but equal to the round, make them green
			{
				System.out.print("[" + Ansi.GREEN.GetCode() + dice[i] + Ansi.RESET.GetCode() + "] ");
			}
			else //Otherwise they become red
			{
				System.out.print("[" + Ansi.RED.GetCode() + dice[i] + Ansi.RESET.GetCode() + "] ");
			}
		}
	
		// If all dice are equal to the round
		if(dice[0] == tmpround && dice[1] ==tmpround && dice[2] == tmpround)
		{			
			Util.AddScore(21, playerorder[val]); //Addscore
			System.out.println("BUNCO! They get 21 score for rolling 3 numbers equal to the round!"); //Tell them what happened
			System.out.println("They will roll again!");
			System.out.println("");
			Round(val, false); //Restart the round for that user
		}
		else if(dice[0] == dice[1] && dice[1] == dice[2]) //If they get three that match without equalling the round (in the else if)
		{
			Util.AddScore(5, playerorder[val]); //Add score
			System.out.println("They get 5 score for rolling 3 of the same numbers!"); //Tell them
			System.out.println("They will roll again!");
			System.out.println("");
			Round(val, false); // Restart the round for that user
		}
		else
		{
			//If one or no dice are equal to round.
			var total = 0; //Set a local total
			for(int i = 0; i < 3; i++) //For each die
			{
				if(dice[i] == tmpround) //If it is equal to the round number
				{
					Util.AddScore(1, playerorder[val]); //Addscore
					total++; //Add to total local.
				}
				//Do nothing if it doesnt
			}
			
			if(total == 0) //If total is null
			{
				System.out.println("They did not get any score.");
				System.out.println("");
				//Dont start their round again
			}
			else //If it equalled something
			{
				System.out.println("They got " + total + " score for rolling " + total + " dice equal to the round!"); //Tell them what they earned
				System.out.println("They will roll again!");
				System.out.println("");
				Round(val, false); //Restart the round
			}
		}
		playerrolled[playerorder[val]] = true; //Once their roll is over and they dont score anything, they are done and rolled.
	}
	
	public static void EndRounds() //For when all the rounds are done
	{
		var max = -1; //Set a max value, score threshold
		var winnerval = -1; //The index of the winning player
		for(int i = 0; i < playersamt; i++) //Foreach player
		{
			playertie[playerorder[i]] = false; //Set their tie status to false
			playerrolled[playerorder[i]] = false; //Set their roll status to false
			if(playerscore[playerorder[i]] > max) //If they have a greater score than max (default -1),
			{
				max = playerscore[playerorder[i]]; //Set their value to max
				winnerval = i; //Set the winner index to that of i
			}
		}
		var tiedplayers = 0; //Set local tiedplayers to 0
		for(int i = 0; i < playersamt; i++) //For each players
		{
			if(playerscore[playerorder[i]] == max) //If their score == max
			{
				playertie[playerorder[i]] = true; //The user is tied
				tiedplayers++; //Increase the amount of tied players
			}
			else
			{
				//Else they arent tied
				playertie[playerorder[i]] = false;
			}
		}
		
		for(int i = 0; i < playersamt; i++) //For all players
		{
			if(tiedplayers > 1) //If there are 2 or more tied players (max is always counted as one initially)
			{
				TieBreaker(max); //Run a tiebreaker instantly
				break; //Then break
			}
		}
		DeclareWinner(winnerval, max); //If TieBreaker is never run (meaning the end of this method). Declare a winner of index winnerval with a max score of max.
		
		//I had to run the for loops separately after another since it had to be in order rather than all done in one.
		//E.g. I couldnt determine tied players before I set the true max, not a temp.
	}
	
	public static void DeclareWinner(int winner, int maxscore)
	{
		//Restriction Variables
		canroll = false;
		menu = false;
		starting = false;
		ending = true;
		Commands.RefreshCommands();
		//Give the final leaderboards, removed the rolled column so I didnt run ListPlayers().
		System.out.println("We Have A Winner!");
		System.out.println("");
		System.out.println("Final Leaderboards!");
		System.out.println("--------------------------");
		System.out.println(String.format("%-20s%6s", "Name", "Score"));
		for(int i = 0; i < playersamt; i++)
		{
			System.out.println(String.format("%-30s%5s", Ansi.valueOf(playercolors[playerorder[i]]).GetCode() + playernames[playerorder[i]] + Ansi.RESET.GetCode(), playerscore[playerorder[i]]));
		}
		System.out.println("--------------------------");
		System.out.println(""); 
		//Say who won.
		System.out.println(Ansi.valueOf(playercolors[playerorder[winner]]).GetCode() + playernames[playerorder[winner]] + Ansi.RESET.GetCode() + " WINS! They had a score of " + maxscore);
		System.out.println("");
		Util.Help(); //Give them options to move on from the winning screen. Menu, Replay, Close
		choice = sc.nextLine(); //We covered this before. Didnt want to make a Method for command input since I didnt want to reflect args.
		var command = false;
		for(int i = 0 ; i < Commands.commands; i++)
		{
			if(choice.equalsIgnoreCase(Commands.name[i]) && Commands.restriction[i] == Commands.type[i])
			{
				Commands.CallCommand(choice);
				command = true;
				break;
			}
		}
		if(command == false)
		{
			Util.Clear();
			System.out.println("Invalid Command. Please Try Again.");
			System.out.println("");
			DeclareWinner(winner, maxscore);
		}	
	}
	
	//Code for tie breaker
	public static void TieBreaker(int max)
	{
		//If tie breaker just started, say that we entered overtime
		if(round == 7)
		{
			Util.Clear();
			System.out.println("We are in overtime!");
			System.out.println("");
		}

		//Restriction variables.
		starting = false;
		menu = false;
		canroll = true;
		ending = false;
		
		for(int i = 0; i < playersamt; i++) //For all players
		{
			if(playertie[playerorder[i]] == true) //If they are in a tie situation
			{
				Round(i, true); //Give them a round with the overtime arg being true
			}
		}
		
		round++; //Once all rounds end in overtime, increase the round
		for(int i = 0; i < playersamt; i++) //For all players
		{
			playerrolled[playerorder[i]] = false; //Set their roll to false
		}
		
		EndRounds(); //At the end of the whole round, check for a winner and get rid of the lowest players if we have another tie. (For 3-4 players)
		System.out.println("We are going to extra rounds!"); 
	}
}
