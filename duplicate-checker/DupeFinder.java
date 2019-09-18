import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class DupeFinder 
{
	static String[] filename;
	static File[] filepath;
	static int files = 0;
	static String[] duplicates;
	static int dupes = 0;
	static int scannedfiles = 0;
	static boolean visual;
	
	public static void main(String[] args)
	{
		System.out.println("Enter search path ('/' for root)");
		Scanner sc = new Scanner(System.in);
		String path = sc.nextLine();
		
		System.out.println("Run Visually (Will Run Slower) (true or false)");
		visual = sc.nextBoolean();
		Clear();
		System.out.println("Scanning Amount of Files... (This may take a second)");
		Scan(path);
		Clear();
		System.out.println("Finished Scanning " + files + " Files!");
		System.out.println("Initializing Collection... (This may also take a second)");
		Collection(path);
		Clear();
		System.out.println("Collection Complete. Collected " + files + " Files.");
		System.out.println("Running Check... (This will take some time)\n");
		Check();
		System.out.println("");
		System.out.println("Program complete");
	}
	
	public static void Scan(String path)
	{
		File root = new File(path);
		File[] list = root.listFiles();
		for(int i = 0; i < list.length; i++)
		{
			if(list[i].isDirectory())
			{
				Scan(list[i].getAbsolutePath());
			}
			else
			{
				String[] rawfile = list[i].toString().split("\\\\");
				if(!rawfile[rawfile.length-1].contains(".meta"))
				{
					files+= 1;
				}
			}
		}
		filename = new String[files];
		filepath = new File[files];
		duplicates = new String[1]; //fixedthat.exe
	}
	
	public static void Collection(String path)
	{
		File root = new File(path);
		File[] list = root.listFiles();
		for(int i = 0; i < list.length; i++)
		{
			if(list[i].isDirectory())
			{
				Collection(list[i].getAbsolutePath());
			}
			else
			{
				if(visual)
				{
					System.out.println("File: " + list[i]);
				}
				String[] rawfile = list[i].toString().split("\\\\");
				if(!rawfile[rawfile.length-1].contains(".meta"))
				{
					filename[scannedfiles] = rawfile[rawfile.length-1];
					filepath[scannedfiles] = list[i];
					scannedfiles++;
				}

			}
		}
	}
	
	public static void Check()
	{
		for(int i = 0; i < filename.length; i++)
		{
			for(int k = 0; k < filename.length; k++)
			{
				if(filename[i] != null && filename[k] != null)
				{
					if(filename[i].equals(filename[k]) && filepath[i] != filepath[k])
					{
						duplicates = Arrays.copyOf(duplicates, dupes+1);
						duplicates[dupes] = (filename[i] + " | Dir: " + filepath[i] + " = " + filepath[k]);
						dupes++;
						if(visual)
						{
							System.out.print("Match: ");
						}
					}
					if(visual)
					{
						System.out.println(filename[i] + " | " + filename[k]);
					}
				}
			}
		}
		Finish();
	}

	public static void Finish()
	{
		Clear();
		String[] finaldupe = new String[duplicates.length];
		int newdupes = 0;
		Arrays.sort(duplicates);
		for(int i = 0; i < duplicates.length; i+=2)
		{
			finaldupe[newdupes] = duplicates[i];
			newdupes++;
		}
		System.out.println(newdupes + " Duplicates Found Out of " + files + " Files");
		
		for(int i = 0; i < newdupes; i++)
		{
			if(finaldupe[i] != null)
			{
				System.out.println("Duplicate#" + (i+1) + " | " + finaldupe[i]);
			}
		}
		if(dupes > 100)
		{
			System.out.println(newdupes + " Duplicates Found Out of " + files + " Files");
		}
	}
	
	public static void Clear()
	{
		for(int i = 0; i < 60; i++)
		{
			System.out.println(" ");
		}
	}
}
