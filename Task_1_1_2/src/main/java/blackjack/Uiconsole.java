package blackjack;

import java.util.Scanner;

/**
 * Утилитный класс для отображения консольного интерфейса и обработки ввода пользователя.
 */
public class Uiconsole {

    /**
     * Выводит приветственное сообщение перед началом игры.
     */
    public static void printWelcome() {
        System.out.println("=== ДОБРО ПОЖАЛОВАТЬ В БЛЭКДЖЕК ===");
    }

    /**
     * Выводит заголовок начала нового раунда.
     *
     * @param round номер текущего раунда
     */
    public static void startRound(int round) {
        System.out.printf("\n--- Раунд %d ---\n", round);
        Uiconsole.sleep(2);
    }

    /**
     * Отображает текущее состояние стола: карты и счет очков игрока и дилера.
     *
     * @param player     объект игрока
     * @param dealer     объект дилера
     * @param showHidden флаг, указывающий, нужно ли открывать закрытую карту дилера
     */
    public static void printTable(Player player, Player dealer, boolean showHidden) {
        System.out.printf("Карты %s: %s | Сумма: %d\n",
                player.getName(), player.getCards(), player.sumUpCards());

        if (showHidden) {
            System.out.printf("Карты %s: %s | Сумма: %d\n",
                    dealer.getName(), dealer.getCards(), dealer.sumUpCards());
        } else {
            System.out.printf("Карты %s: [%s, <закрытая карта>]\n",
                    dealer.getName(), dealer.getCards().getFirst());
        }
        Uiconsole.sleep(1);
    }

    /**
     * Запрашивает у пользователя выбор действия с повторным запросом при некорректном вводе.
     *
     * @return 1, если выбран ход "взять карту", 0, если "остановиться"
     */
    public static int getPlayerChoice(Scanner scanner) {
        while (true) {
            System.out.println("\nВаш ход:");
            System.out.println("1 — Взять карту");
            System.out.println("0 — Остановиться");
            System.out.print("Введите команду: ");

            String input = scanner.nextLine().strip();
            if ("1".equals(input)) {
                return 1;
            } else if ("0".equals(input)) {
                return 0;
            }
            System.out.println("Неверная команда! Введите 0 или 1.");
        }
    }

    /**
     * Выводит сообщение об открытии последней взятой карты участником.
     *
     * @param player участник, взявший карту
     */
    public static void printLastCard(Player player) {
        Card lastCard = player.getCards().get(player.getCards().size() - 1);
        System.out.printf("%s открывает карту %s\n", player.getName(), lastCard);
    }

    /**
     * Выводит заголовок о переходе хода к дилеру.
     */
    public static void printDealerMove() {
        System.out.println("\nХод дилера");
        System.out.println("----------");
    }

    /**
     * Вспомогательный приватный метод для вывода счета побед в раундах.
     *
     * @param player объект игрока
     * @param dealer объект дилера
     */
    private static void printScore(Player player, Player dealer) {
        System.out.printf("Счет: %s %d — %d %s\n",
                dealer.getName(), dealer.getRoundsWon(),
                player.getRoundsWon(), player.getName());
    }

    /**
     * Выводит сообщение о переборе очков и итоговый счет.
     *
     * @param bustedPlayer участник, превысивший 21 очко
     * @param player       объект игрока
     * @param dealer       объект дилера
     */
    public static void printBusted(Player bustedPlayer, Player player, Player dealer) {
        System.out.printf("%s перебрал! ", bustedPlayer.getName());
        printScore(player, dealer);
    }

    /**
     * Выводит сообщение о победе участника в раунде и итоговый счет.
     *
     * @param winner победитель раунда
     * @param player объект игрока
     * @param dealer объект дилера
     */
    public static void printWinner(Player winner, Player player, Player dealer) {
        System.out.printf("%s выиграл раунд! ", winner.getName());
        printScore(player, dealer);
    }

    /**
     * Выводит сообщение о ничьей и итоговый счет.
     *
     * @param player объект игрока
     * @param dealer объект дилера
     */
    public static void printTie(Player player, Player dealer) {
        System.out.print("Ничья! Очко обоим! ");
        printScore(player, dealer);
    }

    /** Утилита для паузы. Останавливает процесс на seconds времени
     *
     * @param seconds количество секунд на паузу
     */
    private static void sleep(int seconds) {
        try {
            java.util.concurrent.TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}