package blackjack;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Game blackjack = new Game(new Scanner(System.in));
        blackjack.runGame();
    }
}
