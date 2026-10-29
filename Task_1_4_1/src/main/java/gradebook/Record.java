package gradebook;

/**
 * Абстрактный класс представляющий пару: (предмет, оценка).
 */
public abstract class Record {

    private final String subjectName;

    /**
     * Конструктор абстрактного класса.
     * Вызывается конструктором наследников
     *
     * @param subjectName имя предмета
     */
    public Record(String subjectName) {
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
     * Возвращает true, если предмет сдан, false - иначе.
     * (для зачетов - сдан ли предмет, для остальных - оценка > 2)
     *
     * @return true, если предмет сдан, false - иначе
     */
    public abstract boolean isPassed();
}
