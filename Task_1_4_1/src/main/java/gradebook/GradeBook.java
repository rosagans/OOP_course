package gradebook;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс зачетки студента.
 */
public class GradeBook {

    /**
     * Текущий семестр учащегося (Число от 1 до 8 включительно).
     */
    private int curSemester;

    /**
     * Список пройденных и текущий семестров.
     */
    private List<Semester> semestersArray;

    /**
     * Конструктор зачетки студента.
     * Создает список семестров до curSemester включительно
     *
     * @param curSemester номер текущего семестра
     */
    public GradeBook(int curSemester) {
        if (curSemester < 1 || curSemester > 8) {
            throw new IllegalStateException("Попытка создать зачетку с текущим семестром: "
                + curSemester);
        }
        this.curSemester = curSemester;
        this.semestersArray = new ArrayList<Semester>();
        for (int i = 0; i < curSemester; i++) {
            semestersArray.add(new Semester());
        }
    }

    /**
     * Конструктор зачетки студента.
     *
     * @param curSemester номер текущего семестра
     * @param semestersArray список семестров
     */
    public GradeBook(int curSemester, List<Semester> semestersArray) {
        if (curSemester < 1 || curSemester > 8) {
            throw new IllegalStateException("Попытка создать зачетку с текущим семестром: "
                + curSemester);
        }
        this.curSemester = curSemester;
        this.semestersArray = semestersArray;
    }

    public void addRecord(GradeRecord record, int semester) {
        semestersArray.get(semester).addRecord(record);
    }

    /**
     * Вычисляет текущий средний балл за все время обучения.
     * Учитывает оценки за экзамены и дифференцированные зачеты.
     *
     * @return средний балл, либо 0.0, если оценок нет
     */
    public double calculateAverageGrade() {
        return semestersArray.stream()
            .flatMap(sem -> sem.getRecordList().stream())
            .filter(GradeRecord.class::isInstance)
            .map(GradeRecord.class::cast)
            .mapToInt(GradeRecord::getGrade)
            .average()
            .orElse(0.0);
    }

    /**
     * Проверяет, сданы ли все обычные зачеты в переданном списке семестров.
     *
     * @param semesters список семестров для проверки
     * @return true, если все зачеты имеют статус "сдан" (true), иначе false
     */
    private boolean allCreditsPassed(List<Semester> semesters) {

        return semesters.stream()
            .flatMap(sem -> sem.getRecordList().stream())
            .filter(RegularCreditRecord.class::isInstance)
            .allMatch(Record::isPassed);
    }

    /**
     * Проверяет возможность перевода с платной на бюджетную форму обучения.
     *
     * <p>
     * Условия:
     * 1. Отсутствие оценок "удовлетворительно" (3) за экзамены в двух последних сессиях.
     * 2. Все обычные зачеты за этот период должны быть сданы.
     *
     * @return true, если перевод возможен, иначе false
     */
    public boolean canTransferToBudget() {
        if (curSemester < 2) {
            return false;
        }

        int startSem = Math.max(0, curSemester - 2);
        List<Semester> lastTwoSemesters = semestersArray.subList(startSem, curSemester);

        boolean noSatisfactoryInExams = lastTwoSemesters.stream()
            .flatMap(sem -> sem.getRecordList().stream())
            .filter(ExamRecord.class::isInstance)
            .map(ExamRecord.class::cast)
            .noneMatch(ExamRecord::isSatisfactory);
        return noSatisfactoryInExams && allCreditsPassed(lastTwoSemesters);
    }

    /**
     * Проверяет возможность получения диплома с отличием («красного» диплома).
     *
     * <p>
     * Условия:
     * 1. 75% итоговых оценок (последняя оценка по каждому предмету) — "отлично" (5).
     * 2. Отсутствие итоговых оценок "удовлетворительно" (3).
     * 3. Оценка за квалификационную работу (ВКР) — "отлично" (5).
     * 4. Все обычные зачеты за все время обучения сданы.
     *
     * @return true, если диплом с отличием достижим или уже получен, иначе false
     */
    public boolean canGetRedDiploma() {
        if (!allCreditsPassed(semestersArray)) {
            return false;
        }

        Map<String, Record> finalGrades = semestersArray.stream()
            .flatMap(sem -> sem.getRecordList().stream())
            .filter(rec -> rec instanceof GradeRecord)
            .collect(Collectors.toMap(
                Record::getSubjectName,
                rec -> rec,
                (oldRec, newRec) -> newRec // Оставляем самую последнюю оценку
            ));

        if (curSemester == 8) {
            boolean vkrExcellent = finalGrades.values().stream()
                .filter(VkrRecord.class::isInstance)
                .map(VkrRecord.class::cast)
                .anyMatch(VkrRecord::isExcellent);
            if (!vkrExcellent) {
                return false;
            }
        }

        if (finalGrades.isEmpty()) {
            return false;
        }

        boolean noSatisfactory = finalGrades.values().stream()
            .filter(GradeRecord.class::isInstance)
            .allMatch(record -> ((GradeRecord) record).getGrade() > 3);

        long excellentCount = finalGrades.values().stream()
            .filter(GradeRecord.class::isInstance)
            .filter(record -> ((GradeRecord) record).isExcellent())
            .count();

        double excellentPercentage = (double) excellentCount / finalGrades.size();

        return allCreditsPassed(semestersArray) && noSatisfactory && (excellentPercentage >= 0.75);
    }

    /**
     * Проверяет возможность получения повышенной стипендии в текущем семестре.
     *
     * <p>
     * Условия:
     * 1. Все оценки за последнюю завершенную сессию (предыдущий семестр) — "отлично" (5).
     * 2. Все обычные зачеты в этой сессии успешно сданы.
     *
     * @return true, если студент претендует на повышенную стипендию, иначе false
     */
    public boolean canGetIncreasedScholarship() {
        if (curSemester <= 1) {
            return false;
        }
        Semester lastSemester = semestersArray.get(curSemester - 2);

        boolean allExcellent = lastSemester.getRecordList().stream()
            .filter(record -> record instanceof GradeRecord)
            .allMatch(record -> ((GradeRecord) record).isExcellent());
        return allExcellent && allCreditsPassed(List.of(lastSemester));
    }
}
