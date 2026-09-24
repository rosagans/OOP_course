package blackjack;

import java.util.Scanner;

public class Main {

    /** Старт программы.
     * Запускает консольный блэкджек
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        Game blackjack = new Game(new Scanner(System.in));
        blackjack.runGame();
    }
}
