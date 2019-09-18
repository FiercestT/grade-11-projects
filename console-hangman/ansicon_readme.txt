Use RunJar (x64).bat for 64 bit windows. This will use the 64 bit version of ansicon.
Use RunJar (x86).bat for 32bit windows. This will use the 32bit version of ansicon.

If ansicon does not work and you do not see color in the console. We will build it manually
Download:
https://github.com/adoxa/ansicon/archive/v1.85.zip

Move it to the root folder. Open your command prompt. Run these commands (Assuming the prompt opens in your user folder).

Use x64 or x86 where applicable

cd <Wherethefileis>/Bunco/ansi156/x64
ansicon.exe -i

Now move the x64 folder to the Ansicon folder I created in the Bunco project.
Tada... it now works! (Hopefully), if not go online and find the solution or just talk to me :)

If you want to uninstall it use:
ansicon.exe -u