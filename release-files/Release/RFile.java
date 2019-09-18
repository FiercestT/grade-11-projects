import java.io.File;

//Release File

@SuppressWarnings("serial")
public class RFile extends File
{
	public RFile(String pathname)
	{
		super(RConfig.File(pathname));
	}
}
