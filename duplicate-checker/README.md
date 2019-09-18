# Duplicate Checker
A java based utility I made in Grade 11. It will recursively find duplicate files based on their name (filename not path).
## How to Use
To run the program, use RunJar.bat. This will load the program in a command prompt window.
Source code is in DupeFinder.java file.
## How it Works
- The program will open up in a command prompt window.
- Please enter the directory that you wish to find duplicate files in.
- The program will run much slower in `visual mode` in the command prompt. The buffers in Eclipse are much faster and will allow for a full speed experience. (Visual Mode reports the files that the algorithm is working on in the console).
- The program will recursively run 3 passes on the files. 1: Files will be scanned and an array will be initialized based on the amount of files found (fastest). 2: All files in folders will be added to said array (fast). 3: A serial check will be run on all files within the array. Any duplicates will be reported at the end (slowest).
- When the program has completed, duplicate files will be printed to the console (filename and path).

Please note, I have not added any special error catches, any errors will stop the program.
