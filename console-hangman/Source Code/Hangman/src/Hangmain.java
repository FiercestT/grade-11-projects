import java.util.Scanner;

public class Hangmain 
{
	static Scanner sc = new Scanner(System.in); //Create a new scanner
	
	public static void main(String[] args)
	{
		Menu(); //Go to menu method
	}
	
	public static void Menu()
	{
		//Main menu display with info
		System.out.println("Welcome to Hangman. By Fiercest");
		System.out.println("");
		System.out.println("Would you like to: ");
		System.out.println(Hangman.Ansi.GREEN.GetCode() + "Play - Start the game!" + Hangman.Ansi.RESET.GetCode());
		System.out.println(Hangman.Ansi.YELLOW.GetCode() + "Utility - Generate new topics and wordlists from a website!" + Hangman.Ansi.RESET.GetCode());
		System.out.println(Hangman.Ansi.RED.GetCode() + "Quit - See you soon!" + Hangman.Ansi.RESET.GetCode());
		String input = "";
		System.out.println("");
		try
		{
			input = sc.nextLine(); //Take user choice
		}
		catch(Exception ex) //If there was somehow an error
		{
			Topics.Clear();
			System.out.println("There was an error during entry. Please Try Again!");
			System.out.println("");
			Menu();
		}

		
		if(input.equalsIgnoreCase("Play"))
		{
			Hangman.Entry(); //Play the game
		}
		else if(input.equalsIgnoreCase("Utility"))
		{
			Topics.Clear(); //Clear
			Topics.Gen();   //Start Utility
		}
		else if(input.equalsIgnoreCase("Quit"))
		{
			System.exit(1); //Close the game
		}
		else //They chose a non existent choice
		{
			Topics.Clear();
			System.out.println("Invalid Entry. Please Try Again!");
			System.out.println("");
			Menu();
		}
		
	}
	
	public static void exit() //When System.exit(int) is called close the scanner.
	{
		sc.close();
	}
}
