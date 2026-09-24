package blackjack;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, представляющий участника игры в блэкджек (игрока или дилера).
 * Управляет картами в руке, подсчетом очков и статистикой побед.
 */
public class Player {

    /** Список карт, находящихся в руке у игрока. */
    protected final List<Card> cards;

    /** Имя игрока.
     *  В общем случае "Игрок" или "Дилер".
     */
    protected final String name;

    /** Количество выигранных раундов. */
    protected int roundsWon;

    /** Конструирует нового игрока. */
    public Player(String name) {
        this.cards = new ArrayList<>();
        this.name = name;
        this.roundsWon = 0;
    }

    /**
     * Вычисляет суммарную стоимость всех карт в руке игрока.
     *
     * @return сумма очков карт
     */
    public int sumUpCards() {
        int sum = 0;
        for (Card card : this.cards) {
            sum += card.getValue();
        }
        return sum;
    }

    /**
     * Проверяет, превысила ли сумма очков игрока допустимый лимит в 21 очко.
     *
     * @return true, если у игрока перебор, иначе false
     */
    public boolean isBusted() {
        return this.sumUpCards() > 21;
    }

    /**
     * Очищает руку игрока от карт перед началом нового раунда.
     */
    public void clearUpHand() {
        this.cards.clear();
    }

    /**
     * Проверяет наличие хотя бы одного Туза в руке у игрока.
     *
     * @return true, если в руке есть Туз, иначе false
     */
    public boolean hasAces() {
        for (Card card : this.cards) {
            if (card.isAce()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Пересчитывает очковое значение Тузов с 11 на 1, если у игрока перебор.
     */
    public void recalculateGrades() {
        int curSum = this.sumUpCards();
        if (curSum <= 21 || !this.hasAces()) {
            return;
        }

        for (Card card : this.cards) {
            if (card.isAce() && card.getValue() == 11) {
                card.setAceValueOne();
                if (this.sumUpCards() <= 21) {
                    return;
                }
            }
        }
    }

    /**
     * Добавляет карту в руку игрока и при необходимости пересчитывает тузы.
     *
     * @param card добавляемая карта
     */
    public void addCard(Card card) {
        this.cards.add(card);
        this.recalculateGrades();
    }

    /**
     * Увеличивает счетчик побед игрока на один раунд.
     */
    public void addWin() {
        this.roundsWon += 1;
    }

    /**
     * Возвращает имя игрока.
     *
     * @return имя игрока
     */
    public String getName() {
        return this.name;
    }

    /**
     * Возвращает количество выигранных игроком раундов.
     *
     * @return число побед
     */
    public int getRoundsWon() {
        return this.roundsWon;
    }

    /**
     * Возвращает список карт, находящихся на руках у игрока.
     *
     * @return список объектов Card
     */
    public List<Card> getCards() {
        return this.cards;
    }

    /**
     * Проверяет собрал ли игрок blackjack.
     *
     * @return Возвращает true, если у игрока blackjack, false - иначе.
     */
    public boolean hitBlackjack() {
        return this.sumUpCards() == 21 && this.cards.size() == 2;
    }
}