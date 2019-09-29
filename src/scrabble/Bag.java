package scrabble;

public class Bag {
    private  Tile [] tiles;

    public Bag() {
        int counter = 0;
        tiles = new Tile[100];

        for(int i = 0; i < 2; i++) {
            tiles[i] = new Tile('*', 0);
        }
        counter = counter + 2;

        for(int i = counter; i < counter + 12; i++) {
            tiles[i] = new Tile('e', 1);
        }
        counter = counter + 12;

        for(int i = counter; i < counter + 9; i++) {
            tiles[i] = new Tile('a', 1);
        }
        counter = counter + 9;

        for(int i = counter; i < counter + 9; i++) {
            tiles[i] = new Tile('i', 1);

        }
        counter = counter + 9;

        for(int i = counter; i < counter + 8; i++) {
            tiles[i] = new Tile('o', 1);

        }
        counter = counter + 8;

        for(int i = counter; i < counter + 6; i++) {
            tiles[i] = new Tile('n', 1);

        }
        counter = counter + 6;

        for(int i = counter; i < counter + 6; i++) {
            tiles[i] = new Tile('r', 1);

        }
        counter = counter + 6;


        for(int i = counter; i < counter + 6; i++) {
            tiles[i] = new Tile('t', 1);

        }
        counter = counter + 6;

        for(int i = counter; i < counter + 4; i++) {
            tiles[i] = new Tile('l', 1);

        }
        counter = counter + 4;

        for(int i = counter; i < counter + 4; i++) {
            tiles[i] = new Tile('s', 1);

        }

        counter = counter + 4;

        for(int i = counter; i < counter + 4; i++) {
            tiles[i] = new Tile('u', 1);

        }
        counter = counter + 4;

        for(int i = counter; i < counter + 4; i++) {
            tiles[i] = new Tile('d', 2);

        }
        counter = counter + 4;

        for(int i = counter; i < counter + 3; i++) {
            tiles[i] = new Tile('g', 2);

        }
        counter = counter + 3;

        for(int i = counter; i < counter + 2; i++) {
            tiles[i] = new Tile('b', 3);

        }
        counter = counter + 2;

        for(int i = counter; i < counter + 2; i++) {
            tiles[i] = new Tile('c', 3);

        }
        counter = counter + 2;

        for(int i = counter; i < counter + 2; i++) {
            tiles[i] = new Tile('m', 3);

        }
        counter = counter + 2;

        for(int i = counter; i < counter + 2; i++) {
            tiles[i] = new Tile('p', 3);

        }
        counter = counter + 2;

        for(int i = counter; i < counter + 2; i++) {
            tiles[i] = new Tile('f', 4);

        }
        counter = counter + 2;

        for(int i = counter; i < counter + 2; i++) {
            tiles[i] = new Tile('h', 4);

        }

        counter = counter + 2;

        for(int i = counter; i < counter + 2; i++) {
            tiles[i] = new Tile('v', 4);
        }
        counter = counter + 2;


        for(int i = counter; i < counter + 2; i++) {
            tiles[i] = new Tile('w', 4);

        }
        counter = counter + 2;

        for(int i = counter; i < counter + 2; i++) {
            tiles[i] = new Tile('y', 4);
        }
        counter = counter + 2;

        for(int i = counter; i < counter + 1; i++) {
            tiles[i] = new Tile('k', 5);

        }
        counter = counter + 1;

        for(int i = counter; i < counter + 1; i++) {
            tiles[i] = new Tile('j', 8);
        }

        counter = counter + 1;

        for(int i = counter; i < counter + 1; i++) {
            tiles[i] = new Tile('x', 8);
        }

        counter = counter + 1;

        for(int i = counter; i < counter + 1; i++) {
            tiles[i] = new Tile('q', 10);
        }
        counter = counter + 1;

        for(int i = counter; i < counter + 1; i++) {
            tiles[i] = new Tile('z', 10);
        }
        counter = counter + 1;

        System.out.println(counter);
        for (int j = 0; j < 100; j++) {
            System.out.println(tiles[j].getLetter() + " " + tiles[j].getScore());
        }
    }

}
