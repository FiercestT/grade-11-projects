import java.util.*;

public class Encrypter 
{
	//Private variables
	private String unencrypted = ""; //The initial message
	private String encrypted = ""; //The encrypted result
	private int shift = 0; //Key shift
	
	//Message Property
	public String message()
	{
		return encrypted;
	}
	
	//Entry Constructor on Instantiation
	Encrypter()
	{
		//Go to Askinput
		this.AskInput();
	}
	
	//Instantiate Scanner For Input
	public Scanner input = new Scanner(System.in);
	//Get User Input
	public void AskInput()
	{
		try
		{
			System.out.println("Enter your string that you want to encrypt:");
			unencrypted = input.nextLine(); //Get the user's string
			CipherMain.Clear();
			this.GetKey(); //If all goes well, get the shift key
		}
		catch(Exception ex)
		{
			//Restart the method if there is an issue
			CipherMain.Clear();
			System.out.println("There was an error.");
			System.out.println("");
			this.AskInput();
		}
	}
	
	public void GetKey()
	{
		//Get shift key
		System.out.println("What key shift do you want (1-26)");
		shift = Integer.parseInt(input.nextLine());
		if(shift >= 1 && shift <= 26) //If the shift is between 1 and 26
		{
			CipherMain.Clear();
			Encrypt(); //Encrypt
		}
		else
		{
			//Otherwise tell them they messed up
			CipherMain.Clear();
			System.out.println("Please enter a number between 1 and 26!");
			System.out.println("");
			this.GetKey();
		}
	}
	
	//Encrypt It
	public void Encrypt()
	{
		try
		{
			for(int i = 0; i < unencrypted.length(); i++) //For each letter
			{		
				int asciichar = (int)(Character.toLowerCase(unencrypted.charAt(i))); //Get the letter's ascii code (as a lowercase)
				if( asciichar > 96 && asciichar < 123) //If the variable above (lowercase letter's ascii code) is between the ascii values of a letter.
				{
					if(Character.isUpperCase(unencrypted.charAt(i))) //If the character from the raw string is uppercase
					{
						//If letter is uppercase
						if(asciichar + shift > 122) //If the ascii code + shift is greater than z
						{
							int x = (asciichar + shift) - 123; //Run some calculations
							int y = x + 97;                    //To calculate the overflow
							encrypted += Character.toUpperCase((char)(y)); //Add the letter with its shift to the encrypted string
						}
						else //Otherwise
						{
							var y = (asciichar + shift);
							encrypted += Character.toUpperCase((char)(y)); //Just add the character to the encrypted string
						}
					}
					else //If the letter is lowercase
					{
						//Runs the same as above without converting it to uppercase
						if(asciichar + shift > 122) 
						{
							int x = (asciichar + shift) - 123;
							int y = x + 97;
							encrypted += (char)(y);
						}
						else
						{
							var y = (asciichar + shift);
							encrypted += (char)(y);
						}
					}
				}
				else //If the character isnt a letter, dont do anything and just add the symbol.
				{
					encrypted += unencrypted.charAt(i);
				}
			}
		}
		//If something happens during the encrypting, restart it.
		catch(Exception ex)
		{
			CipherMain.Clear();
			System.out.println("There was an error");
			System.out.println("");
			this.AskInput();
		}
	}
}