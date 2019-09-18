# Console Hangman
A console based hangman I developed in my Introduction to Computer Science class in Grade 11.
## How to Use
To run the program, use RunJar(x64).bat. This will load the program in a command prompt window with Ansicon.
Source code is in the Source Code folder. I would recommend downloading the Ansi escape sequences addon for Eclipse when running through Eclipse.
## Features
- Reads custom wordlists to generate random hangman words.
- Add custom wordlists manually, or use the wordlist utility which scrapes words and topics from a wordlist website.
- Colored command prompt through the use of Ansicon (https://github.com/adoxa/ansicon)
## How to add a wordlist
- When in the main menu, type `Utility`. This will open a link in your browser.
- Click on a topic and copy its URL.
- Paste the URL in the console (right click), then press enter.
- The wordlist will generate and go into the /Topics folder. (Note, if the wordlist already exists, it will be overwritten).
- The words and topic will be added to the hangman word rotation.
