# Java Release File Objects
A simple solution to File paths for standalone programs in Java.
## What is It?
When creating standalone programs, all code is packaged into the jar and there are no longer references of ./src. This can be problematic if you have folders (e.g. resources) within the ./src folder.

The simple solution to this problem is changing the path to files before they are even instantiated, and this utility makes it very simple.

## RConfig
RConfig: This is a static class that determines how to replace file paths.
- `public static final boolean PRODUCTION` - The boolean to determine if file paths should be replaced or not.
- `public static final String REPLACE_FROM_PATH` - The portion of the path that you would like to remove (e.g. ./src)
- `public static final String PRODUCTION_ROOT_PATH` - What you would like to replace the removed String with (e.g. ./Files)

Here is an example:
```Java
// In the Config:
// REPLACE_FROM_PATH = "./src";
// PRODUCTION_ROOT_PATH = "./Files";

// Result: 

// Production = false
new RFile("./src/images/image.jpg");
// File path = "./src/images/image.jpg"

// Production = true
new RFile("./src/images/image.jpg");
// File path = "./Files/images/image.jpg"
```


## How to use RFiles and RImageIcons, etc.
Instead of creating reference to a `File`, instead you can use an `RFile`. An RFile inherits all functionality as it is a child to a `File`, the only difference being that it will change its file path based off of the RConfig.
The same concept applies to RImageIcons.

## How to Add Your Own Release Objects
- Create a new class (Proper convention may be to add an `R` in front of the parent name).
- Set its parent to the Object that you want.
- Within the constructor, add `super(RConfig.File(String path));`
- For instance, the RFile looks like this:
```Java
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

```
