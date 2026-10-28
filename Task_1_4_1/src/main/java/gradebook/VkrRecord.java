package gradebook;

public class VkrRecord extends GradeRecord{

    /**
     * Оценка за ВКР.
     */
    private final Grade grade;

    /**
     * Конструктор объекта.
     * @param grade - оценка
     */
    public VkrRecord(String subjectName, Grade grade) {
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
