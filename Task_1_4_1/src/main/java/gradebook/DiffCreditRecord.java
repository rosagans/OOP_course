package gradebook;

/**
 * Класс, представляющий пару: (дифф зачет, оценка)
 */
public class DiffCreditRecord extends GradeRecord {
    /**
     * Конструктор объекта.
     * @param subjectName - название предмета
     * @param grade - оценка
     */
    public DiffCreditRecord(String subjectName, Grade grade) {
        super(subjectName, grade);
    }
}
