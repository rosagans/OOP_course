package gradebook;

import java.util.*;
import java.util.stream.Stream;
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
     * Список пройденных и текущих семестров.
     */
    private List<Semester> semestersArray;

    /**
     * Оценка за выпускную квалификационную работу (ВКР).
     * Может быть null, если еще не защищена.
     */
    private Integer thesisGrade;

    /**
     * Конструктор зачетки студента.
     * Создает и заполняет список с семестрами.
     *
     * @param curSemester номер текущего семестра
     */
    public GradeBook(int curSemester) {
        this.curSemester = curSemester;
        this.semestersArray = new ArrayList<Semester>();
        initSemestersArray();
    }

    /**
     * Инициализирует массив состоящий из объектов семестров.
     * до текущего семестра включительно
     *
     */
    public void initSemestersArray() {
        SemesterType[] types = SemesterType.values();
        for (int i = 0; i < curSemester; i++) {
            semestersArray.add(new Semester(types[i]));
        }
    }

    /**
     * Геттер массива семестров для тестов.
     *
     * @return массив семестров
     */
    public List<Semester> initSemestersArrayForTest() {
        return semestersArray;
    }

    /**
     * Устанавливает оценку за квалификационную работу (ВКР).
     *
     * @param grade оценка за ВКР
     */
    public void setThesisGrade(Integer grade) {
        this.thesisGrade = grade;
    }

    /**
     * Вычисляет текущий средний балл за все время обучения.
     * Учитывает оценки за экзамены и дифференцированные зачеты.
     *
     * @return средний балл, либо 0.0, если оценок нет
     */
    public double calculateAverageGrade() {
        return semestersArray.stream()
            .flatMap(sem -> Stream.concat(
                sem.getExamsGradeList().stream(),
                sem.getDiffCreditGradeList().stream()
            ))
            .mapToInt(Semester.PairSubjectGrade::grade)
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
            .flatMap(sem -> sem.getRegularCreditList().stream())
            .allMatch(Semester.PairSubjectBool::hasCredit);
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
            .flatMap(sem -> sem.getExamsGradeList().stream())
            .noneMatch(pair -> pair.grade() == 3);

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
        // Если это выпускной курс, то оценка за ВКР уже должна быть 5
        if (curSemester == 8 && (thesisGrade == null || thesisGrade != 5)) {
            return false;
        }

        // Базовое условие: долгов по обычным зачетам быть не должно
        if (!allCreditsPassed(semestersArray)) {
            return false;
        }

        /*
         * Собираем последние оценки. Если предмет идет несколько семестров,
         * в Map останется только самая последняя (итоговая) оценка.
         */
        Map<String, Integer> finalGrades = semestersArray.stream()
            .flatMap(sem -> Stream.concat(
                sem.getExamsGradeList().stream(),
                sem.getDiffCreditGradeList().stream()
            ))
            .collect(Collectors.toMap(
                Semester.PairSubjectGrade::subject,
                Semester.PairSubjectGrade::grade,
                (oldGrade, newGrade) -> newGrade
            ));

        if (finalGrades.isEmpty()) {
            return false;
        }

        boolean noSatisfactory = finalGrades.values().stream().noneMatch(g -> g == 3);
        long excellentCount = finalGrades.values().stream().filter(g -> g == 5).count();
        double excellentPercentage = (double) excellentCount / finalGrades.size();

        return noSatisfactory && (excellentPercentage >= 0.75);
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

        boolean allExcellent = Stream.concat(
                lastSemester.getExamsGradeList().stream(),
                lastSemester.getDiffCreditGradeList().stream()
            )
            .allMatch(pair -> pair.grade() == 5);

        return allExcellent && allCreditsPassed(List.of(lastSemester));
    }
}
