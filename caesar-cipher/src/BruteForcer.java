import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.util.*;

public class BruteForcer 
{
	private String encrypted = "";
	private String decrypted = "";
	private String initial = "";
	private int shift = 0;
	
	//Message Property
	public String message()
	{
		return decrypted;
	}
	
	//Shift key property
	public int key()
	{
		return shift;
	}
	
	//Entry Constructor on Instantiation
	BruteForcer()
	{
		this.CheckClipboard();
	}
	
	//Instantiate Scanner For Input
	public Scanner input = new Scanner(System.in);
	//Get User Input
	public void CheckClipboard()
	{
		//Check clipboard just like in Decrypter
		try
		{
			var clipboard = (String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
			if(clipboard != null)
			{
				System.out.println("Content has been found on your clipboard: ");
				System.out.println("\"" + clipboard + "\"");
				System.out.println("Would you like to decrypt it? (Yes/No)");
				
				String choice = input.nextLine();
				if(choice.equalsIgnoreCase("Yes"))
				{
					encrypted = clipboard.replace("\"", "");
					//Get the value that you are using to get the key. If left empty, it cycles through all of the possible values but gives not real result
					CipherMain.Clear();
					System.out.println("What was the initial unencrypted String? (Leave empty to display all results)");
					initial = input.nextLine();
					CipherMain.Clear();
					Brute();
				}
				else if(choice.equalsIgnoreCase("No"))
				{
					CipherMain.Clear();
					System.out.println("Enter your string that you want to decrypt: ");
					encrypted = input.nextLine();
					CipherMain.Clear();
					System.out.println("What was the initial unencrypted String? (Leave empty to display all results)");
					initial = input.nextLine();
					CipherMain.Clear();
					Brute();
				}
				else
				{
					CipherMain.Clear();
					System.out.println("Invalid Input, Please try again.");
					System.out.println("");
					this.CheckClipboard();
				}
			}
			else
			{
				CipherMain.Clear();
				System.out.println("Enter your string that you want to decrypt: ");
				encrypted = input.nextLine();
				CipherMain.Clear();
				System.out.println("What was the initial unencrypted String? (Leave empty to display all results)");
				initial = input.nextLine();
				CipherMain.Clear();
				Brute();
			}
		}
		catch(Exception ex)
		{
			CipherMain.Clear();
			System.out.println("There was an error.");
			System.out.println("");
			this.CheckClipboard();
		}
	}
	
	//Brute Force It
	public void Brute()
	{
		try
		{
			//Tell the user the keys are being queried
			System.out.println("Querying Keys:");
			//For all possible values of shift
			for(int v = 1; v < 27; v++)
			{
				decrypted = ""; //Set the output to empty
				for(int i = 0; i < encrypted.length(); i++) //Run through the letters just like in decrypter
				{		
					int asciichar = (int)(Character.toLowerCase(encrypted.charAt(i)));
					if( asciichar > 96 && asciichar < 123)
					{
						//If the letter as a lowercase is in the ascii range for letters
						if(Character.isUpperCase(encrypted.charAt(i)))
						{
							//If letter is uppercase
							if(asciichar - v < 97)
							{
								int x = 97 - (asciichar - v);
								int y = 123 - x;
								decrypted += Character.toUpperCase((char)(y));
							}
							else
							{
								var y = (asciichar - v);
								decrypted += Character.toUpperCase((char)(y));
							}
						}
						else
						{
							//If letter is lowercase
							if(asciichar - v < 97)
							{
								int x = 97 - (asciichar - v);
								int y = 123 - x;
								decrypted += (char)(y);
							}
							else
							{
								var y = (asciichar - v);
								decrypted += (char)(y);
							}
						}
					}
					else //Otherwise dont do anything to it, it is just a symbol
					{
						decrypted += encrypted.charAt(i);
					}
				}
				System.out.println(String.format("%4s%s",v + ": ", decrypted)); //The key and its decrypted valuew
				if(decrypted.equals(initial)) //If there is a match
				{
					//Stop the loops and give the match's key
					System.out.println("We have a match: \"" + decrypted + "\" Has a key of " + v + ".");
					System.out.println("Copied decrypted key to clipboard.");
					CipherMain.Copy(this.message());
					this.shift = v;
					break;
				}
			}
			//If there was no exact match, tell the user
			if(!decrypted.equals(initial))
			{
				System.out.println("There was no exact match!");
				System.out.println("Copied encrypted key to clipboard.");
				CipherMain.Copy(encrypted);
			}
			System.out.println("");
			System.out.println("");
		}
		catch(Exception ex)
		{
			CipherMain.Clear();
			System.out.println("There was an error.");
			System.out.println("");
			this.CheckClipboard();
		}
	}
}
