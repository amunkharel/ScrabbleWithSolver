package scrabble;

import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public int getScore(char letter) {
        if(letter == 'a' || letter == 'e' || letter == 'i' || letter == 'l' || letter == 'n' || letter == 'o' ||
                letter == 'r' || letter == 's' || letter == 't' || letter == 'u'){
            return 1;
        }
        if(letter == 'd' || letter == 'g'){
            return 2;
        }
        if(letter == 'b' || letter == 'c' || letter == 'm' || letter == 'p'){
            return 3;
        }
        if(letter == 'f' || letter == 'h' || letter == 'v' || letter == 'w' || letter == 'y'){
            return 4;
        }
        if(letter == 'k'){
            return 5;
        }
        if(letter == 'j' || letter == 'x'){
            return 8;
        }
        if(letter == 'q' || letter == 'z'){
            return 10;
        }
        return 0;
    }

    public void printBag() {
        for(int i = 0; i < tiles.size(); i++) {
            System.out.println(tiles.get(i).getLetter() + " " + tiles.get(i).getScore());
        }

        System.out.println(tiles.size());
    }

    public List<Tile> giveTilesToTray(int number) {
        List<Tile> tiles = new ArrayList<Tile>();

        Random rand = new Random();

        int randomNumber = 0;

        for(int i = 0; i < number; i++) {
            randomNumber = rand.nextInt(this.tiles.size() - 1);
            tiles.add(this.tiles.get(randomNumber));
            this.tiles.remove(randomNumber);
        }

        return tiles;
    }
}
