# Scrabble Game

## Important Information
Brooke gave me extra few days extension to finish up my documentation because of my twisted ankle

## Game Play

### Starting the file

There are two versions of the game. The first version is the console game which solves a given board and
another is the GUI version that plays the game from start to finish. <br>
To start the console world solver. <br>
1) Go to the game directory where scrabble1.0_akharel.jar 
is placed. <br>
2) Then type "java -jar scrabble1.0_akharel.jar sowpods.txt" or you can use any other dictionary
instead of sowpods.txt. Any files should be placed in "resource" folder <br>
3) Then in the terminal copy and paste the input board you'd like to solve <br>
4) If you are solving one board, copy and paste the board and place enter <br>
5) If you are solving multiple board, copy and paste and wait for the board to be solved
accurately
<br>

One thing you will notice in the solved board is symbol such as '@', '!'. My board uses
character array instead of string to solve the board. Therefore, '@' is premium 3 word
multiplier and '!' is premium 2 word multiplier. '2' is letter multiplier by 2 and '3' is
letter multiplier by 3.  <br>
One known bug is my solver is that it does not exit cleanly, which will later be put in 
my known bug but this is due to the fact that I used file reader to solve input board at the
beginning but later had to change to standard input in a hurry. <br>

To start the GUI game: <br>
1) First, go to the game directory where scrabble2.0_akharel.jar <br>
2) Type "java -jar scrabble2.0_akharel.jar" <br>
3) Then the game starts <br>
4) Please remember to have "sowpods.txt" and "scrabble_board.txt" in the resource folder. Otherwise,
the game won't work. It uses sowpods.txt to get dictionary and scrabble_board.txt to make 
the board look like what it is. <br>

### How to Play - GUI version
1) After we start the game, we see the board, scorecard, play, swap and undo buttons. Below that
we will see the tray with scrabble tile <br>
2) The game starts after you make the first move or swap tiles from the tray <br>
3) To make a move, select tile and place it on the board. If you do not like your placement, 
you can select undo button and it will place all your tiles back on the tray <br>
4) If you make a placement and it is not placed correctly, it will be undone by the game itself <br>
5) If you do make a valid placement but do not make a valid word, you will lose your turn
and computer will make his move <br>
6) You can also swap your tiles in the expense of losing your turn. To swap tile/tiles, first press 
on the swap button and the swap button will have red coloring in the border. This shows swapping
has started. After swapping has starting press on tile/tiles you would like to swap. 
Press swap again and those tiles will be swapped. Swapping might seem weird as it appends the 
swapped element to the end of the tray but it works perfectly. <br>
7) After swapping, computer will make its move. <br>
8) If there is a wildcard, you can get the desired letter by clicking on it. On clicking
the empty wildcard, it will change to 'A'. Second click it will change to 'B' and so on. It stops at 'Z'. 
If you would like to revert to empty tile, please press the undo button. 
You need to change wildcard to desired letter before you start making your move. Otherwise, it 
won't work.  <br>
9) You play with each and score is shown in the scorecard. The game continues until it is over. <br>


## Description of Program Internals

### Description of Classes

1) Bag- Bag class contains the letter tiles. It gives tiles to the player and keep tracks
of what is left in the bag. It also gives out score of each letter to the player <br>
2) Board- Board is where the move is made. Board is where you store your moves. It also gives where
the valid cordinates are to store your move <br>
3) ComputerPlayer- ComputerPlayer is where computer looks at the board and generate the best
move. It also sets the tray after each move. <br>
4) Console - Console is used to solve the console game. Dictionary should be passed as a text
file for console solver to work. Standard input of board is sent to solve the boards <br>
5) Cordinate - Cordinate consists of row and column and it stores cordinates of board <br>
6) Dictionary- Dictionary class stores from the input text file and stores the word in Trie
data structure. Later, it checks if a word is valid or not. For wildcard/wildcards, it will generate
list of valid words. <br>
7) EventHandler- It encapsulates the click of a mouse and makes the clicks on button, trays and
board possible. It will use those clicks to change the backend of the code <br>
8) GUI - GUI is where we animate the canvas. It refreshes all the time to give us realtime
backend view of the game <br>
9) Main- Main Class is where game is initialized <br>
10) Score- Score is where score of computer is tracked.  <br>
11) HumanPlayer- HumanPlayer keep track of its tray. Checks if a placement is valid or not. 
It also keeps track of the human score. It should kept score in score class but I did not 
want to ruin word solver console after it was created by change the score class. <br>
12) Tile- Tile consists of letter and its score<br>
13) Trie- Trie Data Structure stores the words of the dictionary, which can accessed later 
in tremendous speed. <br>

### Algorithm and Data Structure

1) Trie was the major data structure in the game to check to see if a word is valid or not.
For wild card, it also generated list of valid words. <br>
2)  I read a research paper on world's fastest scrabble that
will be referenced below to get access to the algorithm. <br>

## Known Bugs and Feature Requests
Before writing this section, I would like to inform the reader that the game has not been
tested extensively. I tried my best and probably tested it more times than I can remember but
it might still not be enough due to the complexity of the game logic. 
1) Like I said before, the console word solver does not exit cleanly because I had never done standard 
input prior to this project and I had to change it from file reader to input reader in the 
last moment. Also, '@', '!' are characters used as word multipliers instead of string. <br>
2) My console solver solved all the 4 boards but only 3/4 boards
 gave the correct word output from the 'example_input.txt' file because I was not
able to code up entire algorithm from the research paper. I could not make letter permutations for
words between a letter to solve the board due to lack of time. If I am given an extra week, I 
can make this game that solves all the boards perfectly. However, it is accurate most of the time <br>
3) The game runs flawlessly in the GUI but the game end is created without following criteria
of the 'Game Rules'. The game ends simply when bag has less than 7 tiles and declares the winner.
This was also done due to lack of enough time. All the other requirement given by Brooke has
been followed. 

## References
1) To understand Trie Data Structure- https://www.geeksforgeeks.org/trie-insert-and-search/ <br>
2) To generate subset of words - https://www.geeksforgeeks.org/recursive-program-to-generate-power-set/  <br>
3) Worked with Shreeman Gautam together to solve small utilities and problems during the game
making process. His contribution is that he helped me make Standard Input and Jar file <br>
4) Google searches and stack-overflow was used extensively to debug the project. 
5) https://www.geeksforgeeks.org/different-ways-reading-text-file-java/ - for reading files


## Author - Amun Kharel

## Date - 2019/10/16