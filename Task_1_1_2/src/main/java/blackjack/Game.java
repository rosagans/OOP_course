package blackjack;

import java.util.Scanner;

/**
 * Класс, управляющий основным игровым циклом блэкджека.
 * Отвечает за координацию действий игрока, дилера, колоды и вывод состояния стола.
 */
public class Game {

    /** Объект игрока. */
    private final Player player;

    /** Объект дилера. */
    private final Player dealer;

    /** Игровая колода карт. */
    private final Deck deck;

    /** Счетчик текущего раунда. */
    private int round;

    /** Сканер ввода. */
    private final Scanner scanner;

    /**
     * Конструирует новую игру, создавая игрока, дилера и колоду карт.
     *
     * @param scanner поток ввода
     */
    public Game(Scanner scanner) {
        this.player = new Player("Игрок");
        this.dealer = new Player("Дилер");
        this.scanner = scanner;
        this.deck = new Deck();
        this.round = 1;
    }

    /**
     * Конструирует новую игру, создавая игрока, дилера и колоду карт.
     *
     * @param deck кастомная колода карт.
     * @param scanner поток ввода.
     */
    public Game(Scanner scanner, Deck deck) {
        this.player = new Player("Игрок");
        this.dealer = new Player("Дилер");
        this.scanner = scanner;
        this.deck = deck;
        this.round = 1;
    }

    /**
     * Запускает бесконечный игровой цикл раундов блэкджека.
     */
    public void runGame() {
        Uiconsole.printWelcome();
        while (true) {
            playSingleRound();

            if (this.deck.isLowOnCards()) {
                this.deck.refillCards();
            }
        }
    }

    /**
     * Подготавливает стол к новому раунду: очищает руки,
     * раздает начальные карты.
     */
    private void startGame() {
        this.player.clearUpHand();
        this.dealer.clearUpHand();

        for (int i = 0; i < 2; i++) {
            this.dealer.addCard(this.deck.draw());
            this.player.addCard(this.deck.draw());
        }

        Uiconsole.startRound(this.round);
    }

    /**
     * Обрабатывает черед ходов игрока,
     * пока тот не остановится или не наберет 21+ очко.
     */
    private void playerTurn(Scanner scanner) {
        Uiconsole.printTable(this.player, this.dealer, false);
        while (this.player.sumUpCards() < 21) {
            if (Uiconsole.getPlayerChoice(scanner) == 0) {
                return;
            }

            this.player.addCard(this.deck.draw());
            Uiconsole.printTable(this.player, this.dealer, false);
        }
    }

    /**
     * Обрабатывает ход дилера:
     * открывает карту и добирает карты, пока сумма < 17.
     */
    private void dealerTurn() {
        Uiconsole.printDealerMove();
        Uiconsole.printLastCard(this.dealer);
        Uiconsole.printTable(this.player, this.dealer, true);

        while (this.dealer.sumUpCards() < 17) {
            this.dealer.addCard(this.deck.draw());
            Uiconsole.printLastCard(this.dealer);
            Uiconsole.printTable(this.player, this.dealer, true);
        }
    }

    /**
     * Сравнивает суммарные очки игрока и дилера после завершения всех ходов и начисляет победу.
     */
    private void determineWinner() {
        if (this.player.sumUpCards() > this.dealer.sumUpCards()) {
            this.player.addWin();
            Uiconsole.printWinner(this.player, this.player, this.dealer);
        } else if (this.player.sumUpCards() == this.dealer.sumUpCards()) {
            this.player.addWin();
            this.dealer.addWin();
            Uiconsole.printTie(this.player, this.dealer);
        } else {
            this.dealer.addWin();
            Uiconsole.printWinner(this.dealer, this.player, this.dealer);
        }
    }

    /** Симуляция одного раунда в блэкджеке. */
    protected void playSingleRound() {

        this.startGame();

        boolean playerHasBJ = this.player.hitBlackjack();
        boolean dealerHasBJ = this.dealer.hitBlackjack();

        if (playerHasBJ || dealerHasBJ) {
            Uiconsole.printTable(this.player, this.dealer, true);

            if (playerHasBJ && dealerHasBJ) {
                this.player.addWin();
                this.dealer.addWin();
                Uiconsole.printTie(this.player, this.dealer);
            } else if (playerHasBJ) {
                this.player.addWin();
                Uiconsole.printWinner(this.player, this.player, this.dealer);
            } else {
                this.dealer.addWin();
                Uiconsole.printWinner(this.dealer, this.player, this.dealer);
            }
            this.round += 1;
            return;
        }

        this.playerTurn(this.scanner);


        if (this.player.isBusted()) {
            this.dealer.addWin();
            Uiconsole.printBusted(this.player, this.player, this.dealer);
        } else {
            this.dealerTurn();
            if (this.dealer.isBusted()) {
                this.player.addWin();
                Uiconsole.printBusted(this.dealer, this.player, this.dealer);
            } else {
                this.determineWinner();
            }
        }
        this.round += 1;
    }

    /** Геттер для игрока.
     *
     * @return Объект игрока.
     */
    public Player getPlayer() {
        return this.player;
    }

    /** Геттер для дилера.
     *
     * @return Объект дилера.
     */
    public Player getDealer() {
        return this.dealer;
    }

    /**
     *  Геттер для колоды карт.
     *
     * @return Объект колода карт
     */
    public Deck getDeck() {
        return this.deck;
    }
}