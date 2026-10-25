package gradebook;

import java.util.*;
/**
 * Класс объекта семестра.
 */
public class Semester {

    /**
     * Пара значений: название предмета и оценка за него.
     *
     * @param subject название предмета
     * @param grade оценка за него
     */
    public record PairSubjectGrade(String subject, Integer grade) {}

    /**
     * Пара значений: название предмета и оценка за него.
     *
     * @param subject название предмета
     * @param hasCredit true, если есть зачет по этому предмету, false - иначе
     */
    public record PairSubjectBool(String subject, Boolean hasCredit) {}


    /**
     *  Тип семестра.
     */
    private final SemesterType semType;

    /**
     *  List из пар: (экзамен, оценка).
     */
    private final List<PairSubjectGrade> examsGradeList;

    /**
     *  List из пар: (дифф зачет, оценка).
     */
    private final List<PairSubjectGrade> diffCreditGradeList;

    /**
     *  List из пар: (зачет, наличие).
     *  т.е true, если есть зачет, false - иначе
     */
    private final List<PairSubjectBool> regularCreditList;


    /**
     * Конструктор семестра.
     * По типу семестра создает пустые коллекции с заданной capacity:
     * examsGradeList - список экзаменов и оценок за них,
     * diffCreditGradeList - список дифф зачетов и оценок за них
     * regularCreditList - список зачетов и наличие у их.
     *
     *
     * @param semType тип семестра(порядковый номер)
     */
    public Semester(SemesterType semType) {
        this.semType = semType;
        this.examsGradeList = new ArrayList<>(semType.getNumOfExams());
        this.diffCreditGradeList = new ArrayList<>(semType.getNumOfDiffCredits());
        this.regularCreditList = new ArrayList<>(semType.getNumOfRegularCredits());
    }

    /**
     * Добавляет пару: (экзамен, оценка).
     *
     * @param subjectName предмет
     * @param grade оценка
     */
    public void addExamGrade(String subjectName, Integer grade) {
        if (examsGradeList.size() >= semType.getNumOfExams()) {
            throw new IllegalStateException("Максимум " + semType.getNumOfExams() + " экзаменов в "
                + semType.getId() + "семестре");
        }
        examsGradeList.add(new PairSubjectGrade(subjectName, grade));
    }

    /**
     * Добавляет пару: (дифф зачет, оценка).
     *
     * @param subjectName предмет
     * @param grade оценка
     */
    public void addDiffCreditGrade(String subjectName, Integer grade) {
        if (diffCreditGradeList.size() >= semType.getNumOfDiffCredits()) {
            throw new IllegalStateException("Максимум " + semType.getNumOfDiffCredits()
                + " дифф зачетов в " + semType.getId() + "семестре");
        }
        diffCreditGradeList.add(new PairSubjectGrade(subjectName, grade));
    }

    /**
     * Добавляет пару: (зачет, наличие).
     * т.е true, если есть зачет, false - иначе
     *
     * @param subjectName предмет
     * @param hasCredit true, если зачет получен, false - иначе
     */
    public void addRegularCredit(String subjectName, boolean hasCredit) {
        if (regularCreditList.size() >= semType.getNumOfRegularCredits()) {
            throw new IllegalStateException("Максимум " + semType.getNumOfRegularCredits()
                + " зачетов в " + semType.getId() + "семестре");
        }
        regularCreditList.add(new PairSubjectBool(subjectName, hasCredit));
    }

    /**
     * Геттер списка с экзаменами и оценками за них.
     *
     * @return список оценок за экзамены
     */
    public List<PairSubjectGrade> getExamsGradeList() {
        return examsGradeList;
    }

    /**
     * Геттер списка с дифф зачетами и оценками за них.
     *
     * @return список оценок за дифференцированные зачеты
     */
    public List<PairSubjectGrade> getDiffCreditGradeList() {
        return diffCreditGradeList;
    }

    /**
     * Геттер списка с зачетами и их наличием.
     *
     * @return список результатов обычных зачетов
     */
    public List<PairSubjectBool> getRegularCreditList() {
        return regularCreditList;
    }

}
