package scrabble;

public class Main{

    public static void main(String[] args) {
        //Dictionary dictionary = new Dictionary("animals.txt");
        //dictionary.readAndStoreDictionaryInTrie();

        Board board = new Board(15);

        Dictionary dictionary = new Dictionary("sowpods.txt");
        dictionary.readAndStoreDictionaryInTrie();

        Bag bag = new Bag();

        ComputerPlayer p2 = new ComputerPlayer(board, dictionary, bag);

        p2.getValidCordinate();
        p2.makeCombinationandMoves();
    }



}
