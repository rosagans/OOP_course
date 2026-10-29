package gradebook;

public abstract class GradeRecord extends Record {

    /**
     * Оценка за предмет.
     */
    private final Grade grade;

    /**
     * Конструктор абстрактного класса.
     * Вызывается конструктором наследников
     *
     * @param subjectName имя предмета
     */
    public GradeRecord(String subjectName, Grade grade) {
        super(subjectName);
        this.grade = grade;
    }

    /**
     * Геттер оценки за предмет.
     *
     * @return оценка за предмет
     */
    public int getGrade() {
        return grade.toInt();
    }

    /**
     * Возвращает true, если оценка - тройка, false - иначе.
     *
     * @return true, если оценка - тройка, false - иначе
     */
    public boolean isSatisfactory() {
        return grade.toInt() == 3;
    }

    /**
     * Возвращает true, если оценка - пятерка, false - иначе.
     *
     * @return true, если оценка - пятерка, false - иначе
     */
    public boolean isExcellent() {
        return grade.toInt() == 5;
    }

    /**
     * Возвращает true, если предмет сдан, false - иначе.
     * (для зачетов - сдан ли предмет, для остальных - оценка > 2)
     *
     * @return true, если предмет сдан, false - иначе
     */
    public boolean isPassed() {
        return grade.toInt() > 2;
    }
}
