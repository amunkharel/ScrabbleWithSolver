# Scrabble Game

## Important Information
Brooke gave me extra few days extention to finish up my documentation because of my twisted ankle

## Game Play

### Starting the file

There are two versions of the game. The first version is the console game which solves a given board and
another is the GUI version that plays the game from start to finish. <br>
To start the console world solver. <br>
1) Go to the game directory where scrabble1.0_akharel.jar 
is placed.
2) Then type "java -jar scrabble1.0_akharel.jar sowpods.txt" or you can use any other dictionary
instead of sowpods.txt. Any files should be placed in "resource" folder
3) Then in the terminal copy and paste the input board you'd like to solve
4) If you are solving one board, copy and paste the board and place enter
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
8) If there is a wildcard, you can get the desired letter by clicking on it. On you click
the empty wildcard, it will change to 'A'. Second click it will change to 'B' and so on. It stops at 'Z'. 
If you would like to revert to empty tile, please press the undo button. 
You need to change wildcard to desired letter before you start making your move. Otherwise, it 
won't work.  <br>
9) You play with each and score is shown in the scorecard. The game continues until it is over. <br>



