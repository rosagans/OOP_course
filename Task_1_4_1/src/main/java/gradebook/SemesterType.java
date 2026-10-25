package gradebook;

public enum SemesterType {
    FIRST(1, 3, 3, 3),
    SECOND(2, 3, 3, 2),
    THIRD(3, 2, 6, 0),
    FOURTH(4, 5, 5, 0),
    FIFTH(5, 3, 4, 0),
    SIXTH(6, 2, 6, 0),
    SEVENTH(7, 1, 4, 1),
    EIGHTH(8, 0, 4, 1);

    /**
     * Номер семестра.
     */
    private final int id;

    /**
     * Количество экзаменов в конкретном семестре.
     */
    private final int numOfExams;

    /**
     * Количество экзаменов в конкретном семестре.
     */
    private final int numOfDiffCredits;

    /**
     * Количество экзаменов в конкретном семестре.
     */
    private final int numOfRegularCredits;

    /**
     * Создает тип семестра.
     *
     * @param numOfExams количество экзаменов
     * @param numOfDiffCredits количество дифф зачетов
     * @param numOfRegularCredits количество обычных зачетов
     */
    private SemesterType(int id, int numOfExams, int numOfDiffCredits, int numOfRegularCredits) {
        this.id = id;
        this.numOfExams = numOfExams;
        this.numOfDiffCredits = numOfDiffCredits;
        this.numOfRegularCredits = numOfRegularCredits;
    }

    /**
     * Геттер количества экзаменов в данном семестре.
     *
     * @return количество экзаменов в данном семестре
     */
    public int getNumOfExams() {
        return numOfExams;
    }

    /**
     * Геттер количества дифф зачетов в данном семестре.
     *
     * @return количество дифф зачетов в данном семестре
     */
    public int getNumOfDiffCredits() {
        return numOfDiffCredits;
    }

    /**
     * Геттер количества обычных зачетов в данном семестре.
     *
     * @return количество обычных зачетов в данном семестре
     */
    public int getNumOfRegularCredits() {
        return numOfRegularCredits;
    }

    /**
     * Геттер номера семестра
     *
     * @return номер семестра
     */
    public int getId() {
        return id;
    }
}
