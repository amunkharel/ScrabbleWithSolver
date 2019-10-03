package scrabble;

public class Main{

    public static void main(String[] args) {

        Board board = new Board(15);

        Dictionary dictionary = new Dictionary("sowpods.txt");
        dictionary.readAndStoreDictionaryInTrie();


        Bag bag = new Bag();
        Score score = new Score(bag, board);

        ComputerPlayer p2 = new ComputerPlayer(board, dictionary, bag, score);

        //score.getScore(4, 10, 'c', 'v', "modeleD");
        p2.startAI();

        p2.placeBestMove();
        board.printBoard();
        //board.printBoard();
    }

}
