package gradebook;

/**
 * Класс, представляющий пару: (название зачета, есть ли зачет).
 */
public class RegularCreditRecord extends Record {
    /**
     * true, если зачет сдан, false - иначе.
     */
    private final boolean hasPassed;

    /**
     * Конструктор объекта.
     * @param subjectName - название предмета
     * @param hasPassed - true, если зачет сдан, false - иначе
     */
    public RegularCreditRecord(String subjectName, boolean hasPassed) {
        super(subjectName);
        this.hasPassed = hasPassed;
    }

    @Override
    public boolean isPassed() {
        return hasPassed;
    }
}
