import java.io.*;

public class CommandConfig 
{
	//CONFIG FOR COMMANDS
	//Method that creates all the commands
	public static void InitializeCommands()
	{
		Commands.CreateCommand("Play", "Start the Game!", Bunco.menu, true); //Play at menu
		Commands.CreateCommand("Rules", "Learn Bunco!", Bunco.menu, true);   //Read rules file
		//Commands.CreateCommand("Help", "Shows this menu.", Bunco.canroll, true); //Dont need that anymore since it was moved to util
		//Commands.CreateCommand("Roll", "Roll the dice!", Bunco.canroll, true); //Dont need that anymore due to some issues with it.
		Commands.CreateCommand("Ansi", "Test Ansi Colors with Ansicon!", Bunco.menu, true); //Test ansicon in menu
		Commands.CreateCommand("Continue", "Start the game!", Bunco.starting, true); //Continue command to start game
		Commands.CreateCommand("Menu", "Return to the menu.", Bunco.starting, true); //2 Different menus for start and end of the game.
		Commands.CreateCommand("Menu", "Return to the menu.", Bunco.ending, true); 
		Commands.CreateCommand("Replay", "Replay with the same users!", Bunco.ending, true); //Replay the game with the same players
		Commands.CreateCommand("Close", "Close the Game!"); //Close the game at any time
	}
		
	//All of the Methods and Such
	
	public static void Replay()
	{
		//Goes back to the method where I randomly set the order to keep playernames, but reset everything else.
		Util.Clear();
		Bunco.Order();
	}
	
	public static void Continue()
	{
		//Refresh the commands, set canroll to true and start the rolling loop.
		Bunco.canroll = true;
		Commands.RefreshCommands();
		Bunco.RoundLoop();
	}
	
	public static void Menu()
	{
		//Go back to the menu
		Util.Clear();
		Bunco.Menu();
	}
	
	public static void Ansi()
	{
		//Tests ansicon colors with the enum.
		Util.Clear();
		for(int i = 0; i < Bunco.Ansi.values().length; i++)
		{
			System.out.println(Bunco.Ansi.values()[i].GetCode() + "Welcome to Bunco!" + Bunco.Ansi.RESET.GetCode());
		}
		System.out.println("");
		Bunco.Menu();
	}
	
	public static void Play()
	{
		//Set the menu variable to false and call GameCreation, which will start the main loop.
		Util.Clear();
		Bunco.menu = false;
		Bunco.GameCreation();
	}
	
	public static void Rules()
	{
		Util.Clear();
		
		String filename = "rules.txt"; //The filename, this should stay the same.
		
		//While in eclipse, the rules.txt file is not inside of the src folder so this will find it in the parent directory. It will also work with the packaged jar file.
		File currentdir = new File("."); 
		File parentdir = currentdir.getParentFile();
		File newfile = new File(parentdir, filename);
		
		String line = null; //Init line local
		try//Try running this code incase of errors. 
		{
			//Filereader and Bufferedreader
			FileReader filereader = new FileReader(newfile);
			BufferedReader bufferedreader = new BufferedReader(filereader);
			while((line = bufferedreader.readLine()) != null)
			{
				//If there is content on the line, print it
				System.out.println(line);
			}
			bufferedreader.close();
		}
		catch(FileNotFoundException ex)
		{
			System.out.println("Rules File Not Found Or Unable to Open."); //If the file isnt found give this message.
		}
		catch(IOException ex)
		{
			System.out.println("Error Reading Rules File."); //If it cant be read give this message.
		}
		
		System.out.println("");
		System.out.println("");
		Bunco.Menu();
	}
	
	public static void Close()
	{
		//Close the program.
		Util.Clear();
		System.out.println("Thanks for playing!");
		System.exit(1);
	}
}
