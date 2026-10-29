package gradebook;

/**
 * Класс представляющий пару: (экзамен, оценка).
 */
public class ExamRecord extends GradeRecord {
    /**
     * Конструктор объекта.
     * @param subjectName - название предмета
     * @param grade - оценка
     */
    public ExamRecord(String subjectName, Grade grade) {
        super(subjectName, grade);
    }
}
