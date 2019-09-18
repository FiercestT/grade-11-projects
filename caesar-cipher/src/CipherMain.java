import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.*;

public class CipherMain 
{
	public static void main(String[] args)
	{
		Entry(); //Go to entry when the program starts.
	}

	public static void Entry() //Entry void allows us to easily return to the menu in case of error
	{
		String choice = ""; //Initialize choice and declare it incase it is used
		while(true)
		{
			Scanner sc = new Scanner(System.in); // Init scanner (i dont know why it wants to not warn me)
			System.out.println("Welcome to Caesar Cipher"); //Menu Options
			System.out.println("Do you want to?");
			System.out.println("Encrypt");
			System.out.println("Decrypt");
			System.out.println("Brute Force");
			System.out.println("Close");
			System.out.println("");
			try //Try user input for their choice
			{
				choice = sc.nextLine();
			}
			catch(Exception ex)
			{
				//If there is an error, tell them they messed up.
				Clear();
				System.out.println("There was an error.");
				Entry();
			}
			Clear();
			if(choice.equalsIgnoreCase("Encrypt"))
			{
				//Instantiate encrypter, let it run, then get its result.
				Encrypter encrypt = new Encrypter();
				Clear();
				System.out.println("Encrypted String: " + encrypt.message());
				Copy(encrypt.message()); //Copy the message to clipboard
				System.out.println("Copied Encrypted String to Clipboard");
				System.out.println("");
				System.out.println("");
			}
			else if(choice.equalsIgnoreCase("Decrypt"))
			{
				//Instantiate decrypter, let it do the same as encrypter
				Decrypter decrypt = new Decrypter();
				Clear();
				System.out.println("Decrypted String: " + decrypt.message());
				Copy(decrypt.message()); //Copy decrypted message to clipboard
				System.out.println("Copied Decrypted String to Clipboard");
				System.out.println("");
				System.out.println("");
			}
			else if(choice.equalsIgnoreCase("Brute Force"))
			{
				//Instantiate a brute forcer object and let it work
				new BruteForcer();
			}
			else if(choice.equalsIgnoreCase("Close"))
			{
				//Exit
				System.exit(1);
			}
			else
			{
				//If they didnt enter a proper option, retry
				System.out.println("Invalid Entry, Try Again.");
				System.out.println("");
				System.out.println("");
				Entry();
			}
		}
	}

	//Copy Method, adds a string to the user's clipboard
	public static void Copy(String copyinput)
	{
		String string = copyinput; //The string I want to add
		StringSelection stringSelection = new StringSelection(string); //String Selection
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard(); //Get System Clipboard to a variable
		clipboard.setContents(stringSelection, null); //Set the clipboard to the string selection
	}

	//Clear Method
	public static void Clear()
	{
		for(int i = 0; i < 65; i++)
		{
			System.out.println("");
		}
	}
}
