package gradebook;

/**
 * Класс представляющий пару: (ВКР, оценка).
 */
public class VkrRecord extends GradeRecord {
    /**
     * Конструктор объекта.
     * @param grade - оценка
     */
    public VkrRecord(String subjectName, Grade grade) {
        super(subjectName, grade);
    }
}
