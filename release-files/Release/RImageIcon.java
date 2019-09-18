import javax.swing.ImageIcon;

//Release ImageIcon

@SuppressWarnings("serial")
public class RImageIcon extends ImageIcon
{
	public RImageIcon(String pathname)
	{
		super(RConfig.File(pathname));
	}
}
