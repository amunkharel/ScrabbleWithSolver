package scrabble;

public class Main{

    public static void main(String[] args) {

        Board board = new Board(15);

        Dictionary dictionary = new Dictionary("sowpods.txt");
        dictionary.readAndStoreDictionaryInTrie();


        Bag bag = new Bag();
        Score score = new Score(bag, board);

        ComputerPlayer p2 = new ComputerPlayer(board, dictionary, bag, score);

        p2.startAI();

        System.out.println(score.getCompBestCurrentWord());
        System.out.println(score.getCompBestMoveDirection());
        System.out.println(score.getComputerBestMoveRow());
        System.out.println(score.getComputerBestMoveColumn());
        System.out.println(score.getCompCurrentBestScore());
        //board.printBoard();
    }

}
