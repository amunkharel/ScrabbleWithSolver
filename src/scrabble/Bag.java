package scrabble;

import java.util.ArrayList;
import java.util.List;

public class Bag {
    private List<Tile> tiles;

    public Bag() {

        tiles  = new ArrayList<Tile>();
        int counter = 0;


        for(int i = 0; i < 2; i++) {
            tiles.add(new Tile('*', 0));
        }
        counter = counter + 2;

        for(int i = counter; i < counter + 12; i++) {
            tiles.add(new Tile('e', 1));
        }
        counter = counter + 12;

        for(int i = counter; i < counter + 9; i++) {
            tiles.add(new Tile('a', 1));
        }
        counter = counter + 9;

        for(int i = counter; i < counter + 9; i++) {
            tiles.add(new Tile('i', 1));

        }
        counter = counter + 9;

        for(int i = counter; i < counter + 8; i++) {
            tiles.add(new Tile('o', 1));

        }
        counter = counter + 8;

        for(int i = counter; i < counter + 6; i++) {
            tiles.add(new Tile('n', 1));

        }
        counter = counter + 6;

        for(int i = counter; i < counter + 6; i++) {
            tiles.add(new Tile('r', 1));

        }
        counter = counter + 6;


        for(int i = counter; i < counter + 6; i++) {
            tiles.add(new Tile('t', 1));

        }
        counter = counter + 6;

        for(int i = counter; i < counter + 4; i++) {
            tiles.add(new Tile('l', 1));

        }
        counter = counter + 4;

        for(int i = counter; i < counter + 4; i++) {
            tiles.add(new Tile('s', 1));

        }

        counter = counter + 4;

        for(int i = counter; i < counter + 4; i++) {
            tiles.add(new Tile('u', 1));

        }
        counter = counter + 4;

        for(int i = counter; i < counter + 4; i++) {
            tiles.add(new Tile('d', 2));

        }
        counter = counter + 4;

        for(int i = counter; i < counter + 3; i++) {
            tiles.add(new Tile('g', 2));

        }
        counter = counter + 3;

        for(int i = counter; i < counter + 2; i++) {
            tiles.add(new Tile('b', 3));

        }
        counter = counter + 2;

        for(int i = counter; i < counter + 2; i++) {
            tiles.add(new Tile('c', 3));

        }
        counter = counter + 2;

        for(int i = counter; i < counter + 2; i++) {
            tiles.add(new Tile('m', 3));

        }
        counter = counter + 2;

        for(int i = counter; i < counter + 2; i++) {
            tiles.add(new Tile('p', 3));

        }
        counter = counter + 2;

        for(int i = counter; i < counter + 2; i++) {
            tiles.add(new Tile('f', 4));

        }
        counter = counter + 2;

        for(int i = counter; i < counter + 2; i++) {
            tiles.add(new Tile('h', 4));

        }

        counter = counter + 2;

        for(int i = counter; i < counter + 2; i++) {
            tiles.add(new Tile('v', 4));
        }
        counter = counter + 2;


        for(int i = counter; i < counter + 2; i++) {
            tiles.add(new Tile('w', 4));

        }
        counter = counter + 2;

        for(int i = counter; i < counter + 2; i++) {
            tiles.add(new Tile('y', 4));
        }
        counter = counter + 2;

        for(int i = counter; i < counter + 1; i++) {
            tiles.add(new Tile('k', 5));

        }
        counter = counter + 1;

        for(int i = counter; i < counter + 1; i++) {
            tiles.add(new Tile('j', 8));
        }

        counter = counter + 1;

        for(int i = counter; i < counter + 1; i++) {
            tiles.add(new Tile('x', 8));
        }

        counter = counter + 1;

        for(int i = counter; i < counter + 1; i++) {
            tiles.add(new Tile('q', 10));
        }
        counter = counter + 1;

        for(int i = counter; i < counter + 1; i++) {
            tiles.add(new Tile('z', 10));
        }
    }

    public int getScore(char character) {
        int charac = character - 'a';

        if(charac > 25) {
            return  0;
        }

        for(int i = 0; i < tiles.size(); i++) {
            if(tiles.get(i).getLetter() == character) {
               return tiles.get(i).getScore();
            }
        }
        return 0;
    }

}
