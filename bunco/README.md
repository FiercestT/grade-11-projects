# Bunco
A console based dice game entitled Bunco that I developed in my Introduction to Computer Science class in Grade 11.
## How to Use
To run the program, use RunJar(x64).bat. This will load the program in a command prompt window with Ansicon.
Source code is in the Source Code folder. I would recommend downloading the Ansi escape sequences addon for Eclipse when running through Eclipse.
## Features
- A command System that invokes commands based on their String name and class. (See Commands.java for command backend and CommandConfig.java for the commands that are invoked). These commands also have boolean restrictions so they cant be called at certain times.
- Ansicon implementation to color the console. (https://github.com/adoxa/ansicon).
- Much more.
## How to Play
- Players will be put into a random order.
- Players will roll a dice in their order.
- Once all players have rolled, a new round will start.
- If a player scores a point, they can roll again until they do not score any more points.
- The user with the highest score wins.
- If there is a tie, there will be extra rounds where the dice has to roll a 6 rather than the round number.
- At the end of each round, the user with the highest score wins.
- If there is another tie, the tie break will continue until there is a clear winner.

##### Scoring
- 21 Score: All 3 dice are equal to the round number.
- 5 Score: All 3 dice are equal to each other but not the round.
- 1 Score: For each die equal to the round number.
