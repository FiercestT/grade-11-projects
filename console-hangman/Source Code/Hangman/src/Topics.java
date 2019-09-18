import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;


public class Topics 
{
	static String link = ""; //Set link to false
	public static void URLInput()
	{
		try
		{
			Scanner sc = new Scanner(System.in); //Open scanner
			link = sc.nextLine(); //Set link to input
			if(!link.matches("https://www.enchantedlearning.com/wordlist/*.*.shtml") || !link.contains("enchantedlearning.com")) //If the link is not a subfile of /wordlist and is an shtml and doesnt come from the given site,
			{
				Clear();
				System.out.println("This link is not from a proper category."); //Restart
				System.out.println("Please Reinput a proper link: ");
				URLInput();
			}
			
			//Check if page that was inputted exists or gives a 404 redirect
			URL url = new URL (link); //Create a connection with the link that was entered
			HttpURLConnection huc =  (HttpURLConnection)url.openConnection(); 
			huc.setRequestMethod ("HEAD");
			huc.setInstanceFollowRedirects(true);
			HttpURLConnection.setFollowRedirects(true);
			huc.connect();
			huc.getResponseMessage(); 
			String code = huc.getURL().toString(); //Get the url after connecting to the page, if the link the user entered doesnt exist, the url returned would have been redirected to nosuchfile.html
			//sc.close();
			huc.disconnect(); //Disconnect
			if(code.contains("nosuchfile.html")) //If there was a redirect
			{
				Clear();
				System.out.println("This is category does not exist");
				System.out.println("Please Reinput a proper link: ");  //Restart
				URLInput();
			}
		}
		catch(Exception ex) //If there was an error
		{
			Clear();
			System.out.println("An error occured. Please try again. Make sure you have an internet connection.");
			System.out.println("");
			ex.printStackTrace();
			URLInput();
		}
	}
	
	public static void Gen()
	{
		try 
		{
			//Try and ping google with icmp packets to check if there is an internet connection. Google allows icmp packets through so it will work 99.9% of the time if the user has internet
		    InetAddress inet = InetAddress.getByName("google.com");
			if(!inet.isReachable(1000)) //If the internet is not reachable let the user know
			{
				System.out.println("No connection could be established with google. The program will be run, but if there is actually no connection, there may be errors.");
			}
		}
		catch(Exception ex) //If there was an error let the user know
		{
			System.out.println("There was an error establishing a connection with the internet.");
			System.out.println("The program will be run, but if there is no connection, there may be errors.");
			System.out.println("");
		}

		try //Open a browser to the website's wordlist
		{
			Desktop desktop = Desktop.getDesktop();
			desktop.browse(new URL("https://www.enchantedlearning.com/wordlist/").toURI());
		}
		catch(Exception ex) //If that doesnt work give the link
		{
			System.out.println("An error occured when attempting to launch the browser.");
			System.out.println("Please access this link: https://www.enchantedlearning.com/wordlist/");
			System.out.println("");
		}

		Scanner sc = new Scanner(System.in);
		//Give instructions
		System.out.println("Welcome To The Hangman Topics Utility");
		System.out.println("Coded By: Paul Economou");
		System.out.println("A browser has been opened, please select one of the categories and supply the link of a category!");
		System.out.println("Please Enter the Link: ");

		URLInput(); //Run the URL input

		Clear();
		System.out.println("Processing..."); //Let the user know that it is processing
		String newline = ""; //Set newline to an empty string
		BufferedReader br = null; //Make a bufferedreader
		try  
		{
			URL url = new URL(link); //Create a URL with the inputted link
			br = new BufferedReader(new InputStreamReader(url.openStream())); //Catch all the incoming html code of the site and then isolate the words from the wordlist

			String line;

			StringBuilder sb = new StringBuilder();

			while ((line = br.readLine()) != null) //Read the html code and while the line has data on it, perform these actions
			{
				line = sb.append(line).toString();
				line = line.replaceAll("</div>", "\n</div>"); //Replace all /divs with a newline in front
				newline += line; //Add the modified line to newline
			}

		} 
		catch(Exception ex) //If there was an error
		{
			Clear();
			System.out.println("An error occured when attempting to read that url. Please try again");
			System.out.println("");
			Gen();
		}

		String rawlist = "";

		Scanner listgen = new Scanner(newline); //Create a scanner to read each line of newline
		while(listgen.hasNextLine()) //If there is another line
		{
			if(listgen.nextLine().contains("wordlist-item")) //If that line has the value wordlist-item (indicates a word from the list - duh)
			{
				//Add that value to the rawlist with a \ at the end and remove all the dirty html code from it
				rawlist += (listgen.nextLine().replaceAll("</div>", "").replaceAll("<div", "").replaceAll("class", "").replaceAll("=wordlist-item", "").replaceAll(">","").replaceAll("<a", "").replaceAll("</a", "").replaceAll("href=*.*", "")) + "\\";
			}
		}
		listgen.close(); //Close that scanner
		String[] rawarray = rawlist.split("\\\\"); //Create an array that splits the rawlist. For some reason the words found would repeat multiple times in the array so this structure removes those dupes.
		String[] wordlist = new String[1]; //Create a wordlist with index of one
		int words = 0;
		for(int i = 0 ; i < rawarray.length-1; i++) //For all the words in the rawarray
		{
			for(int k = 0; k < rawarray[i].length(); k++) // I forget why but this works or else nothing will match and nothing will be added to the file (I think it was making duplicates based off of the amount of letters)
			{
				if(Character.isLetter(rawarray[i].charAt(k)))
				{
					boolean repeat = false;
					for(int v = 0; v < wordlist.length-1; v++) //For all words currently in the wordlist
					{
						if(words > 0) //If there are words
						{
							if(wordlist[v].equals(rawarray[i].trim())) //If the word is equal to the word in the rawarray
							{
								repeat = true; //Repeat is true
							}
						}
					}
					if(!repeat) //If there is no repeat
					{
						wordlist = Arrays.copyOf(wordlist, words+1); //Resize array
						wordlist[words] = rawarray[i].trim(); //Add the word to the array
						words++;
						break; //Break the loop and move on
					}
				}
			}
		}
		
		wordlist = Arrays.copyOf(wordlist, words-1); //Remove the overflow (an extra word of the last index will be there)
		
		String[] urlfilename = link.split("/"); //Split the url to get the .shtml filename
		String filename = urlfilename[urlfilename.length-1].replace(".shtml", ""); //Get the filename and remove .shtml
		String uppername = Character.toUpperCase(filename.charAt(0)) + filename.substring(1, filename.length()); //Set the first letter to uppercase
		
		//Create file and write to it
		try
		{
			File currentdir = new File(".","/Topics"); //Get the current directory
			
			File newfile = new File(currentdir, "/" + uppername + ".txt"); //Create a new File in the current dir with the uppercase file name (from above) as a .txt
			
			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newfile), "utf-8")); //Create the file defined above
			writer.close(); //Close the writer
			
			FileWriter fw = new FileWriter(newfile); //Make a file writer
			for(int i = 0; i < wordlist.length; i++)
			{
				//Write the words from the wordlist into the file with a newline each time, dont add a newline to the last word.
				if(i == wordlist.length-1)
				{
					fw.write(wordlist[i]);
				}
				else
				{
					fw.write(wordlist[i] + "\n");
				}
			}
			fw.close();
			
			Clear();
			System.out.print("Successfully wrote list to dir: " + newfile); //Let the user know it all went wells
			System.out.println("\n");
			Hangmain.Menu();
		}
		catch(Exception ex)
		{
			Clear();
			System.out.println("There was an error while creating the list file."); //Let the user know there was an error
			System.out.println("");
			Gen();
		}
	}
	
	public static void Clear()
	{
		for(int i = 0 ; i < 70; i++) //Clear the screen by adding whitespaces
		{
			System.out.println("");
		}
	}
}
