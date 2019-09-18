public class Util 
{
	//Just a class with some things I didnt want to throw into commandconfig. 
	
	//Clears the screen with 75 whitespacess
	public static void Clear()
	{
		for(int i = 0; i < 75; i++)
		{
			System.out.println("");
		}
	}
	
	//Sets the score of a user (just looks cleaner this way when adding score)
	public static void AddScore(int amount, int user)
	{
		Bunco.playerscore[user] += amount;
	}
	
	//Display all of the commands that are within their restrictions
	public static void Help()
	{
		for(int i = 0; i < Commands.commands; i++)
		{
			if(Commands.restriction[i] == Commands.type[i])
			{
				System.out.println(Commands.name[i] + ": " + Commands.description[i]);
			}
		}
	}
}