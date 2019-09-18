import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Hangman 
{
	static String topic; //Topic var
	static String word;  //Word var

	static String let = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z"; //The alphabet because I was lazy
	static String[] allletters = let.split(","); //All letters from the alphabet
	static String[] available = let.split(","); //Available letters
	static String[] used; //used letters (Starts as null)
	static int attempts = 6; //Lives
	static String hiddenword; //The word that is displayed

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
	
	//Run when the play options is chosen in the menu
	public static void Entry()
	{
		GenWord();
	}

	public static void GenWord()
	{
		attempts = 6; //Set attempts to 6
		used = null; //Reset used

		File root = new File("./Topics"); //Get the topics file
		File[] list = root.listFiles();   //List all files of that dir
		if(list.length == 0)
		{
			System.out.println("There are no files inside of the topics folder. Please run the utility to add some!"); //If their folders are empty on topics
		}

		Random rand = new Random(); //Assign random
		int num = 0; //Init temp variable to hold the random
		num = rand.nextInt(list.length); //Set the random to one of the files in the list

		for(int i = 0; i < list.length; i++) //For each file on the list
		{
			if(i == num) //If the index is the same as the rand
			{
				if(list[i].getName().contains(".txt")) //If the filename at that index is a txt file
				{
					topic = list[i].getName().replace(".txt", ""); //Set the topic name to the filename without .txt
				}
				else //If the file isnt a .txt file then something is wrong.
				{
					System.out.println("The chosen file for topics was not a txt file. Therefore invalid. Please make sure all folders within topics were made by the utility!");
				}
			}
		}

		//Use topic var assigned above to get the filename
		String filename = topic + ".txt"; 

		File topicdir = new File("./Topics/" + filename); //Get the topic file in the Topics directory

		int lines = 0; //Set temp int to 0
		String line = null; //Set line to 0

		String[] words = new String[1]; //Create words array with 1 slot
		try
		{
			//Read the file of the topic
			FileReader filereader = new FileReader(topicdir);
			BufferedReader bufferedreader = new BufferedReader(filereader);
			//Run it first to set the words into the array and get the amount of words there are.
			while((line = bufferedreader.readLine()) != null) //If the line has some content
			{
				words[lines] = line; //words at index of its line number = the word on the line
				lines += 1; //Increase lines index
				words = Arrays.copyOf(words, words.length+1); //Resize words array to add another slot
			}
			bufferedreader.close(); //When done close the reader.
			num = rand.nextInt(lines); 	//Gen the random number
			word = words[num]; //Set the word to words at the index of the random
		}
		catch(FileNotFoundException ex) //If the file isnt found
		{
			System.out.println("File Not Found Or Unable to Open."); 
		}
		catch(IOException ex) //If the file could not be read
		{
			System.out.println("Error Reading File."); 
		}
		catch(Exception ex) //Some other error.
		{
			System.out.println("An Error Occured");
		}

		hiddenword = word; //Hiddenword (display word) = word
		for(int i = 0; i < hiddenword.length(); i++) //for each character
		{
			if(Character.isLetter(hiddenword.charAt(i))) //If the character is a letter
			{
				hiddenword = hiddenword.replace(hiddenword.charAt(i), '-'); //Replace it with a dash
			}
		}
		Clear();
		DisplayUi(); //Move on to the game
	}

	public static void DisplayUi()
	{
		if(!word.equalsIgnoreCase(hiddenword)) //If the hiddenword isnt fully uncovered then continue
		{
			HangGuy(attempts); //Print the hangman guy hanging
			System.out.println(Ansi.YELLOW.GetCode() + "Topic: " + topic + Ansi.RESET.GetCode()); //Write the topic
			System.out.println("Word: " + hiddenword); //The hiddenword
			System.out.print("Available Letters: "); //Print out the available letters
			for(int i = 0; i < available.length; i++) //For all available letters
			{
				boolean repeat = false; //Set temp bool repeat to false
				if(used != null) //If there are used letters
				{
					for(int k = 0; k < used.length; k++) //For each used letter
					{
						if(used[k].equalsIgnoreCase(available[i])) //If each used letter is equal to the currently checked available letter
						{
							repeat = true; //Set repeat to true
						}
					}
				}
				if(!repeat) //If repeat is false
				{
					System.out.print(available[i].toUpperCase()); //Print out the available letter
				}
			}
			System.out.print("\nUsed Letters: "); //Used letters
			if(used != null) //If there are used letters
			{
				for(int i = 0; i < used.length; i++)
				{
					System.out.print(used[i].toUpperCase()); //Print them all out
				}
			}
			System.out.print("\n");
			System.out.println("_________________________");
			System.out.println("!<word> To guess the word"); //Tell the user what to do
			System.out.print("Enter A Letter:  "); 
			String chosen = Hangmain.sc.nextLine(); //Scanner to take the input

			if(!chosen.contains("!")) //If the word doesnt contain a ! which means it should just be a letter
			{
				if(chosen.length() > 1 || chosen.length() == 0) //If the length is greater than 1 character or there isnt any message
				{
					Clear();
					System.out.println(Ansi.RED.GetCode() + "Please Enter A Single Letter!" + Ansi.RESET.GetCode()); //Retry with error code
					System.out.println("");
					DisplayUi();
				}
				if(!Character.isLetter(chosen.charAt(0))) //If the character isnt a letter
				{
					Clear();
					System.out.println(Ansi.RED.GetCode() + "Please Enter A Letter!" + Ansi.RESET.GetCode()); //Retry with error code
					System.out.println("");
					DisplayUi();
				}

				if(used != null) //If used has values
				{
					if(!Arrays.asList(used).contains(chosen.toUpperCase())) //If the used arrays does not have a value of our just chosen letter
					{
						used = Arrays.copyOf(used, used.length+1); //Resize the used array
						used[used.length-1] = chosen.toUpperCase(); //Add the new letter to the last index that was just increased
						Arrays.sort(used); //Sort it in alpha order
					}
					else //If the used array has the letter we just chose
					{
						Clear();
						System.out.println(Ansi.YELLOW.GetCode() + chosen + " was already chosen!" + Ansi.RESET.GetCode()); //Retry with warning
						System.out.println("");
						DisplayUi();
					}
				}
				else //If used is null
				{
					used = new String[1]; //Create it with 1 index
					used[0] = chosen.toUpperCase(); //Add the value to that first index
				}
				Calculate(chosen.charAt(0)); //Call calculate method for the letter that was inputted
			}
			else //If the input has a ! (whole word)
			{
				boolean symbols = false; //Set temp symbols to false
				if(chosen.charAt(0) == '!') //If the first character is the !
				{
					if(chosen.length() < 2) //If its length is not the ! and at least one other letter
					{
						Clear();
						System.out.println(Ansi.RED.GetCode() + "A word has letters!" + Ansi.RESET.GetCode()); //Error code and retry
						System.out.println("");
						DisplayUi();
					}
					else //If its length is at least the ! and another letter
					{
						for(int i = 1; i < chosen.length(); i++) //For each character in the input
						{
							if(!Character.isLetter(chosen.charAt(i))) //If that character is a symbol
							{
								if(!word.contains(Character.toString(chosen.charAt(i)))) //If that symbol is not in the word (The only symbols in a word world be spaces, , . not |}{ )
								{
									symbols = true; //Set symbols to true
								}
							}
						}
						if(!symbols) //If there are no symbols
						{
							chosen = chosen.substring(1, chosen.length()); //Substring the input to remove the !
							if(chosen.equalsIgnoreCase(word)) //If the input equals the real word
							{
								GameOver(true); //The user wins
							}
							else //Otherwise
							{
								attempts = 0; //No more lives
								GameOver(false); //The user loses
							}
						}
						else //If there are symbols
						{
							Clear();
							System.out.println(Ansi.RED.GetCode() + "Your entry contained an invalid symbol!" + Ansi.RESET.GetCode()); //Error code and retry
							System.out.println("");
							DisplayUi();
						}
					}
				}
				else //If the character ! is in the string but not the first character, there should be an error
				{
					Clear();
					System.out.println(Ansi.RED.GetCode() + "Please Enter A Letter!" + Ansi.RED.GetCode() ); //Retry
					System.out.println("");
					DisplayUi();
				}
			}
		}
		else //If the word equals the hiddenword
		{
			GameOver(true); //User wins
		}
		DisplayUi(); //Restart the method
	}

	public static void Calculate(char chosen)
	{
		boolean correct = false; //Set correct temp bool
		if(!hiddenword.contains(Character.toString(chosen))) //if the hiddenword does not contain the inputted character
		{
			for(int i = 0; i < word.length(); i++) //For each letter in the word
			{
				if(Character.isLetter(word.charAt(i))) //If that letter is a character
				{
					if(Character.toLowerCase(word.charAt(i)) == Character.toLowerCase(chosen)) //If the character of the word equals our inputted letter
					{
						hiddenword = hiddenword.substring(0,i) + word.charAt(i) + hiddenword.substring(i+1, hiddenword.length()); //Substring hiddenword to replace the dash at the index of the letter with that letter
						correct = true; //Set correct to true
					}
				}
			}
		}
		if(!correct) //If the character inputted is not in the word
		{
			attempts--; //Remove a life
			if(attempts == 0) //If there are no lives left
			{
				GameOver(false); //Game over lose
			}
			Clear();
			System.out.println(Ansi.RED.GetCode() + Character.toUpperCase(chosen) + " Is not a part of the word!"+Ansi.RESET.GetCode()); //Tell the user its not a part of the word
			System.out.println("");
		}
		else
		{
			Clear(); //Else just move on
		}
	}

	public static void Clear() //Clear = Topics.Clear(), easier to run than calling the class.
	{
		Topics.Clear();
	}

	public static void GameOver(boolean win)
	{
		Clear();
		if(win) //If the user wins, display the hangman guy freed, tell them they won
		{
			HangGuy(-1);
			System.out.println(Ansi.GREEN.GetCode() + "You Win! The word was " + Ansi.RESET.GetCode() + Ansi.YELLOW.GetCode() + word + Ansi.RESET.GetCode() + Ansi.GREEN.GetCode() + " from the topic " + Ansi.RESET.GetCode() + Ansi.YELLOW.GetCode() + topic + Ansi.RESET.GetCode());
			//Win
		}
		else //Else tell them they lost and show the guy at the last stage
		{
			HangGuy(0);
			System.out.println(Ansi.RED.GetCode() + "You Lose! The word was " + Ansi.RESET.GetCode() + Ansi.YELLOW.GetCode() + word + Ansi.RESET.GetCode() + Ansi.RED.GetCode()+ " from the topic " + Ansi.RESET.GetCode() + Ansi.YELLOW.GetCode() + topic + Ansi.RESET.GetCode());
			//Lose
		}
		System.out.println("_________________________"); //Give a menu to continue or go to menu
		System.out.println("Would you like to: ");
		System.out.println(Ansi.GREEN.GetCode() + "Play - Play Again" + Ansi.RESET.GetCode());
		System.out.println(Ansi.YELLOW.GetCode() + "Menu - Return to menu" + Ansi.RESET.GetCode());
		String choice = "";
		try
		{
			choice = Hangmain.sc.nextLine(); //Take users input
		}
		catch(Exception ex)
		{
			Clear();
			System.out.println("There was an error with your input. Please try again.\n"); //If an error somehow occurs
			GameOver(win); //Restart the method with win's value
		}

		if(choice.equalsIgnoreCase("Play")) //If the user wants to play
		{
			Clear(); 
			Entry(); //Restart
		}
		else if(choice.equalsIgnoreCase("Menu")) //If the user wants to go to the menu
		{
			Clear();
			Hangmain.main(null); //Goto the entry point of menu
		}
		else //If they wrote something else
		{
			Clear();
			System.out.println("Unknown Option. Please Try Again.\n"); //Restart the loop.
			GameOver(win);
		}
	}

	//-------------------
	//Hangman Guy Display
	//-------------------
	public static void HangGuy(int stage)
	{
		if(stage == -1) //If the user won
		{
			System.out.println("+------------------+");
			System.out.println("|" + Ansi.CYAN.GetCode() + "Attempts Left: " + attempts + Ansi.RESET.GetCode() + " |");
			System.out.println("+------------------+");
		}
		else if(stage == 0) //If the user lost
		{
			//Do nothing
		}
		else //If the user is playing
		{
			System.out.println("+-------------+");
			System.out.println("|" + Ansi.CYAN.GetCode() + "Attempts: " + attempts + Ansi.RESET.GetCode() + " |");
			System.out.println("+-------------+");
		}

		if(stage == 0) //Lose
		{
			System.out.println( " _________     ");
			System.out.println( "|         |    ");
			System.out.println( "|         0    ");
			System.out.println( "|        /|\\  ");
			System.out.println( "|        / \\  ");
			System.out.println( "|              ");
			System.out.println( "|              ");
		}
		if(stage == 1)
		{
			System.out.println( " _________     ");
			System.out.println( "|         |    ");
			System.out.println( "|         0    ");
			System.out.println( "|        /|\\  ");
			System.out.println( "|        /     ");
			System.out.println( "|              ");
			System.out.println( "|              ");
		}
		if(stage == 2)
		{
			System.out.println( " _________     ");
			System.out.println( "|         |    ");
			System.out.println( "|         0    ");
			System.out.println( "|        /|\\  ");
			System.out.println( "|              ");
			System.out.println( "|              ");
			System.out.println( "|              ");
		}
		if(stage == 3)
		{
			System.out.println( " _________     ");
			System.out.println( "|         |    ");
			System.out.println( "|         0    ");
			System.out.println( "|        /|    ");
			System.out.println( "|              ");
			System.out.println( "|              ");
			System.out.println( "|              ");
		}
		if(stage == 4)
		{
			System.out.println( " _________     ");
			System.out.println( "|         |    ");
			System.out.println( "|         0    ");
			System.out.println( "|         |    ");
			System.out.println( "|              ");
			System.out.println( "|              ");
			System.out.println( "|              ");
		}
		if(stage == 5)
		{
			System.out.println( " _________     ");
			System.out.println( "|         |    ");
			System.out.println( "|         0    ");
			System.out.println( "|              ");
			System.out.println( "|              ");
			System.out.println( "|              ");
			System.out.println( "|              ");
		}
		if(stage == 6)
		{
			System.out.println( " _________     ");
			System.out.println( "|         |    ");
			System.out.println( "|              ");
			System.out.println( "|              ");
			System.out.println( "|              ");
			System.out.println( "|              ");
			System.out.println( "|              ");
		}
		if(stage == -1) //Win
		{
			System.out.println( " _________     ");
			System.out.println( "|         |    ");
			System.out.println( "|              ");
			System.out.println( "|              ");
			System.out.println( "|        \\O/  ");
			System.out.println( "|         |    ");
			System.out.println( "|        / \\  "); 
		}

		System.out.println("_________________________");
	}
}
