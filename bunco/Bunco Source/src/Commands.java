import java.lang.reflect.*;

public class Commands 
{
	public static int commands = 0; //The amount of commands
	public static String[] name = new String[1000]; //The properties of each command is stored in an array since I didnt make an enum
	public static String[] description = new String[1000]; //They are set to 1k so I dont hit a limit.
	public static boolean[] restriction = new boolean[1000];
	public static boolean[] type = new boolean[1000];

	//Commands Backend With Methods and their Overalods.

	//The big callcommand method
	public static void CallCommand(String classname, String methodname)
	{
		//Since all of my methods follow a strict first letter uppercase rule, take any input and make it uppercase at first letter.
		var fixedmethod = methodname.toUpperCase().charAt(0)+methodname.substring(1,methodname.length());
		for(int i = 0; i < commands; i++) //Run through all of the commands
		{
			if(fixedmethod.equalsIgnoreCase(name[i])) //If the command exists 
			{
				if(restriction[i] == type[i]) //And there are no restrictions of it at the moment(restriction is the variable, type is the condition)
				{
					try //Attempt to do this in a try since it can cause every error known to Java.
					{
						Class<?> c = Class.forName(classname); //Find a class by the name of classname. Set it to c
						Method method = c.getDeclaredMethod(fixedmethod); //Find a Method by the name of fixedmethod. Set it to method.
						method.invoke(c); //Invoke method in class c
					}
					catch(Exception ex) //If there happens to be an error
					{
						//Shut down the program. It will most likely give an invocation exception since most of the methods derived from this method will trace back to this.
						System.out.println("Developer Error: Error within invoked method, exiting program.");
						System.out.println(ex);
						System.exit(1);
					}
				}
			}
		}
	}

	//Overload for CallCommand where commandconfig is the default. Just in case.
	public static void CallCommand(String methodname)
	{
		CallCommand("CommandConfig", methodname);
	}
	
	//Method that creates the commands in the array
	public static void CreateCommand(String iname, String idescription, boolean irestriction, boolean itype)
	{
		name[commands] = iname; //Set the name at index commands to input name
		description[commands] = idescription; //etc
		restriction[commands] = irestriction; //etc
		type[commands] = itype; //etc
		commands++; //Increase commands so that new commands created will be at a new index
	}
	
	//Overload for CreateCommand where the command can be run at any time
	public static void CreateCommand(String iname, String idescription)
	{
		CreateCommand(iname, idescription, true, true);
	}
	
	//Commands Being printed out in the Util.Help() method would show their older versions with their restrictions. The solution to fix this is...
	public static void RefreshCommands()
	{
		//For all commands
		for(int i = 0; i < commands; i++)
		{
			name[i] = null;
			description[i] = null;
			restriction[i] = true;
			type[i] = true;
			//Set them to null
		}
		//Reset commands index
		commands = 0;
		//Reinitialize all commands
		CommandConfig.InitializeCommands();
		//Eureka it works!
	}
}