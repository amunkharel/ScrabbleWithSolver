package scrabble;

public class Main{

    public static void main(String[] args) {
        //Dictionary dictionary = new Dictionary("sowpods.txt");
        //dictionary.readAndStoreDictionaryInTrie();

        Board board = new Board(15);

        board.printBoard();

        Bag bag = new Bag();
    }
}
