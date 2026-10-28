package gradebook;

/**
 * Абстрактный класс представляющий пару: (предмет, оценка).
 */
public abstract class GradeRecord {

    private final String subjectName;

    /**
     * Конструктор абстрактного класса.
     * Вызывается конструктором наследников
     *
     * @param subjectName имя предмета
     */
    public GradeRecord(String subjectName) {
        this.subjectName = subjectName;
    }

    /**
     * Геттер названия предмета.
     *
     * @return предмет
     */
    public String getSubjectName() {
        return subjectName;
    }

    /**
     * Геттер оценки за предмет.
     * Возвращает 0 для зачетов
     *
     * @return оценка за предмет
     */
    public abstract int getGrade();

    /**
     * Возвращает true, если оценка - тройка, false - иначе.
     * (Для обыкновенных зачетов всегда false)
     *
     * @return true, если оценка - тройка, false - иначе
     */
    public abstract boolean isSatisfactory();

    /**
     * Возвращает true, если оценка - пятерка, false - иначе.
     * (Для обыкновенных зачетов всегда false)
     *
     * @return true, если оценка - пятерка, false - иначе
     */
    public abstract boolean isExcellent();

    /**
     * Возвращает true, если предмет сдан, false - иначе.
     * (для зачетов - сдан ли предмет, для остальных - оценка > 2)
     *
     * @return true, если предмет сдан, false - иначе
     */
    public abstract boolean isPassed();
}
