import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.util.*;

public class Decrypter 
{
	private String encrypted = ""; //Raw input string
	private String decrypted = ""; //Output string
	private int shift = 0; //Key Shift
	
	//Message Property
	public String message()
	{
		return decrypted;
	}
	
	//Entry Constructor on Instantiation
	Decrypter()
	{
		this.CheckClipboard();
	}
	
	//Instantiate Scanner For Input
	public Scanner input = new Scanner(System.in);
	//Get User Input
	public void CheckClipboard()
	{
		try
		{
			//Get the value of the user's clipboard
			var clipboard = (String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
			if(clipboard != null) //If the clipboard isnt empty
			{
				//Tell the user that they have something in their clipboard
				System.out.println("Content has been found on your clipboard: ");
				System.out.println("\"" + clipboard + "\"");
				System.out.println("Would you like to decrypt it? (Yes/No)");
				
				String choice = input.nextLine(); //Prompt them if they want to use it
				if(choice.equalsIgnoreCase("Yes"))
				{
					encrypted = clipboard.replace("\"", ""); //If yes, Set it to the input string
					CipherMain.Clear();
					this.GetKey();
				}
				else if(choice.equalsIgnoreCase("No"))//If no, ask for their string from scratch
				{
					CipherMain.Clear();
					System.out.println("Enter your string that you want to decrypt: ");
					encrypted = input.nextLine();
					CipherMain.Clear();
					this.GetKey();
				}
				else //If they entered and invalid option, restart
				{
					CipherMain.Clear();
					System.out.println("Invalid Input, Please try again");
					System.out.println("");
					this.CheckClipboard();
				}
			}
			else //If the clipboard is null, ask them for their input
			{
				CipherMain.Clear();
				System.out.println("Enter your string that you want to decrypt: ");
				encrypted = input.nextLine();
				CipherMain.Clear();
				this.GetKey();
			}
		}
		catch(Exception ex)
		{
			//Catch an error
			CipherMain.Clear();
			System.out.println("There was an error.");
			System.out.println();
			this.CheckClipboard();
		}
	}
	
	public void GetKey()
	{
		//Ask for user key (same as encrypter object)
		System.out.println("What key was assigned? (1-26)");
		shift = Integer.parseInt(input.nextLine());
		if(shift >= 1 && shift <= 26)
		{
			CipherMain.Clear();
			Decrypt();
		}
		else
		{
			CipherMain.Clear();
			System.out.println("Please enter a number between 1 and 26!");
			System.out.println("");
			this.GetKey();
		}
	}
	
	//Decrypt It
	public void Decrypt()
	{
		try
		{
			for(int i = 0; i < encrypted.length(); i++) //For each character
			{		
				int asciichar = (int)(Character.toLowerCase(encrypted.charAt(i))); //Set its value as a lowercase
				if( asciichar > 96 && asciichar < 123) //If its value as a lowercase is in the range of a letter
				{
					//If the letter as a lowercase is in the ascii range for letters
					//This just runs the same as encrypter with a different mathematical formula that applies the key backwards instead of forwards
					if(Character.isUpperCase(encrypted.charAt(i)))
					{
						//If letter is uppercase
						if(asciichar - shift < 97)
						{
							int x = 97 - (asciichar - shift);
							int y = 123 - x;
							decrypted += Character.toUpperCase((char)(y));
						}
						else
						{
							var y = (asciichar - shift);
							decrypted += Character.toUpperCase((char)(y));
						}
					}
					else
					{
						//If letter is lowercase
						if(asciichar - shift < 97)
						{
							int x = 97 - (asciichar - shift);
							int y = 123 - x;
							decrypted += (char)(y);
						}
						else
						{
							var y = (asciichar - shift);
							decrypted += (char)(y);
						}
					}
				}
				else //Otherwise dont do anything to it, it is just a symbol
				{
					decrypted += encrypted.charAt(i);
				}
			}
		}
		catch(Exception ex)
		{
			CipherMain.Clear();
			System.out.println("There was an error");
			System.out.println("");
			this.CheckClipboard();
		}
	}
}
