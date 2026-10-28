package gradebook;

/**
 * Класс, представляющий пару: (дифф зачет, оценка)
 */
public class DiffCreditRecord extends GradeRecord{

    /**
     * Оценка за предмет.
     */
    private final Grade grade;

    /**
     * Конструктор объекта.
     * @param subjectName - название предмета
     * @param grade - оценка
     */
    public DiffCreditRecord(String subjectName, Grade grade) {
        super(subjectName);
        this.grade = grade;
    }

    @Override
    public int getGrade() {
        return grade.toInt();
    }

    @Override
    public boolean isSatisfactory() {
        return grade.toInt() == 3;
    }

    @Override
    public boolean isExcellent() {
        return grade.toInt() == 5;
    }

    @Override
    public boolean isPassed() {
        return grade.toInt() > 2;
    }
}
