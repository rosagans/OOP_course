package task_1_1_2;

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

    /**
     * Конструирует новую игру, создавая игрока, дилера и колоду карт.
     */
    public Game() {
        this.player = new Player("Игрок");
        this.dealer = new Player("Дилер");
        this.deck = new Deck();
        this.round = 1;
    }

    /**
     * Запускает бесконечный игровой цикл раундов блэкджека.
     */
    public void runGame() {
        UIconsole.printWelcome();
        while (true) {
            this.startGame();
            this.playerTurn();

            if (this.player.isBusted()) {
                this.dealer.addWin();
                UIconsole.printBusted(this.player, this.player, this.dealer);
            } else {
                this.dealerTurn();
                if (this.dealer.isBusted()) {
                    this.player.addWin();
                    UIconsole.printBusted(this.dealer, this.player, this.dealer);
                } else {
                    this.determineWinner();
                }
            }
            this.round += 1;
        }
    }

    /**
     * Подготавливает стол к новому раунду: очищает руки,
     * при необходимости обновляет колоду и раздает начальные карты.
     */
    private void startGame() {
        this.player.clearUpHand();
        this.dealer.clearUpHand();

        if (this.deck.isLowOnCards()) {
            this.deck.refillCards();
        }

        for (int i = 0; i < 2; i++) {
            this.dealer.addCard(this.deck.draw());
            this.player.addCard(this.deck.draw());
        }

        UIconsole.startRound(this.round);
    }

    /**
     * Обрабатывает черед ходов игрока, пока тот не остановится или не наберет 21+ очко.
     */
    private void playerTurn() {
        UIconsole.printTable(this.player, this.dealer, false);
        while (this.player.sumUpCards() < 21) {
            if (UIconsole.getPlayerChoice() == 0) {
                return;
            }

            this.player.addCard(this.deck.draw());
            UIconsole.printTable(this.player, this.dealer, false);
        }
    }

    /**
     * Обрабатывает ход дилера: открывает карту и добирает карты, пока сумма < 17.
     */
    private void dealerTurn() {
        UIconsole.printDealerMove();
        UIconsole.printLastCard(this.dealer);
        UIconsole.printTable(this.player, this.dealer, true);

        while (this.dealer.sumUpCards() < 17) {
            this.dealer.addCard(this.deck.draw());
            UIconsole.printLastCard(this.dealer);
            UIconsole.printTable(this.player, this.dealer, true);
        }
    }

    /**
     * Сравнивает суммарные очки игрока и дилера после завершения всех ходов и начисляет победу.
     */
    private void determineWinner() {
        if (this.player.sumUpCards() > this.dealer.sumUpCards()) {
            this.player.addWin();
            UIconsole.printWinner(this.player, this.player, this.dealer);
        } else if (this.player.sumUpCards() == this.dealer.sumUpCards()) {
            this.player.addWin();
            this.dealer.addWin();
            UIconsole.printTie(this.player, this.dealer);
        } else {
            this.dealer.addWin();
            UIconsole.printWinner(this.dealer, this.player, this.dealer);
        }
    }
}