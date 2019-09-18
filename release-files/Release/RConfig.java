//WHAT THIS DOES
//When packaging to a .exe or .jar externally, there is no ./src, this takes all file references of a specified path (./src)
//and replaces them if PRODUCTION is true.

public class RConfig 
{
	public static final boolean PRODUCTION = false;					// Are we in production or not (Set this before exporting - May add automated functionality making this deprecated in the near future)
	public static final String REPLACE_FROM_PATH = "./src"; 		// What is the string that should be replaced
	public static final String PRODUCTION_ROOT_PATH = "./Files";	// Where should the root of these files be outside of production(make sure this folder exists)

	public static String File(String input)
	{
		if(PRODUCTION)
			return input.replace(REPLACE_FROM_PATH, PRODUCTION_ROOT_PATH);
		else
			return input;
	}
}