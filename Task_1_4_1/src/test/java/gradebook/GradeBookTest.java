package gradebook;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GradeBookTest {

    // Тестирование исключительных ситуаций и лимитов
    @Test
    public void testSemesterLimitsThrowsException() {
        Semester semester = new Semester(SemesterType.FIRST);

        // В 1-м семестре максимум 3 экзамена
        semester.addExamGrade("Математика", 5);
        semester.addExamGrade("Физика", 4);
        semester.addExamGrade("Программирование", 5);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            semester.addExamGrade("История", 4); // 4-й экзамен
        });
        assertTrue(exception.getMessage().contains("Максимум 3 экзаменов"));
    }

    @Test
    public void testSemesterLimitsThrowsException_NoExamsInEighthSemester() {
        GradeBook gb = new GradeBook(8);
        Semester sem8 = gb.initSemestersArrayForTest().get(7);

        // Пытаемся добавить экзамен туда, где лимит 0
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            sem8.addExamGrade("Прак", 5);
        });
        assertTrue(exception.getMessage().contains("Максимум 0 экзаменов"));
    }

    @Test
    public void testSemesterLimitsThrowsException_NoRegularCreditsInThirdSemester() {
        GradeBook gb = new GradeBook(3);
        Semester sem3 = gb.initSemestersArrayForTest().get(2); // Третий семестр: THIRD(3, 2, 6, 0)

        // Пытаемся добавить обычный зачет туда, где лимит 0
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            sem3.addRegularCredit("История", true);
        });
        assertTrue(exception.getMessage().contains("Максимум 0 зачетов"));
    }

    // Тестирование подсчета среднего балла
    @Test
    public void testCalculateAverageGrade() {
        GradeBook gb = new GradeBook(2);

        // 1 семестр
        Semester sem1 = gb.initSemestersArrayForTest().get(0);
        sem1.addExamGrade("Математика", 5);
        sem1.addDiffCreditGrade("Английский", 4);

        // 2 семестр
        Semester sem2 = gb.initSemestersArrayForTest().get(1);
        sem2.addExamGrade("Физика", 4);

        // (5 + 4 + 4) / 3 = 4.333...
        assertEquals(4.333, gb.calculateAverageGrade(), 0.01);
    }

    @Test
    public void testEmptyAverageGradeIsZero() {
        GradeBook gb = new GradeBook(1);
        assertEquals(0.0, gb.calculateAverageGrade());
    }

    // Тестирование перехода на бюджет
    @Test
    public void testCanTransferToBudget_Fail_DueToExam3() {
        GradeBook gb = new GradeBook(2);
        Semester sem2 = gb.initSemestersArrayForTest().get(1);
        sem2.addExamGrade("Математика", 3); // Тройка за экзамен
        sem2.addRegularCredit("Физра", true);

        assertFalse(gb.canTransferToBudget());
    }

    @Test
    public void testCanTransferToBudget_Fail_FirstSemester() {
        GradeBook gb = new GradeBook(1);
        assertFalse(gb.canTransferToBudget()); // Нужно минимум 2 семестра
    }

    @Test
    public void testCanTransferToBudget_Success() {
        // Для бюджета нужно проверить 2 последние сессии. Возьмем 1 и 2 семестры.
        GradeBook gb = new GradeBook(2);

        // 1-й семестр: FIRST(1, 3, 3, 3) - лимиты позволяют
        Semester sem1 = gb.initSemestersArrayForTest().get(0);
        sem1.addExamGrade("Линейная алгебра", 4);
        sem1.addRegularCredit("Физра", true);

        // 2-й семестр: SECOND(2, 3, 3, 2) - лимиты позволяют
        Semester sem2 = gb.initSemestersArrayForTest().get(1);
        sem2.addExamGrade("Математический анализ", 5);
        sem2.addDiffCreditGrade("Английский", 3); // В дифф. зачетах "3" допускается для бюджета
        sem2.addRegularCredit("История", true);

        assertTrue(gb.canTransferToBudget());
    }

    // Тестирование повышенной стипендии
    @Test
    public void testCanGetIncreasedScholarship_Success1() {
        GradeBook gb = new GradeBook(2); // Текущий семестр - 2

        // Оценки за 1-й семестр (последняя завершенная сессия)
        Semester sem1 = gb.initSemestersArrayForTest().get(0);
        sem1.addExamGrade("Математика", 5);
        sem1.addDiffCreditGrade("Английский", 5);
        sem1.addRegularCredit("Физра", true);

        assertTrue(gb.canGetIncreasedScholarship());
    }

    @Test
    public void testCanGetIncreasedScholarship_Fail_MissingCredit() {
        GradeBook gb = new GradeBook(2);
        Semester sem1 = gb.initSemestersArrayForTest().get(0);
        sem1.addExamGrade("Математика", 5);
        sem1.addRegularCredit("Физра", false); // Зачет не сдан

        assertFalse(gb.canGetIncreasedScholarship());
    }

    @Test
    public void testCanGetIncreasedScholarship_Success2() {
        GradeBook gb = new GradeBook(2);

        Semester sem1 = gb.initSemestersArrayForTest().get(0);
        sem1.addExamGrade("Математика", 5);
        sem1.addDiffCreditGrade("Английский", 5);
        sem1.addRegularCredit("Физра", true);
        sem1.addRegularCredit("БЖД", true);

        assertTrue(gb.canGetIncreasedScholarship());
    }

    // Тестирование красного диплома
    @Test
    public void testCanGetRedDiploma_Forecast_SuccessWithOverwrittenGrade1() {
        GradeBook gb = new GradeBook(2);

        Semester sem1 = gb.initSemestersArrayForTest().get(0);
        sem1.addExamGrade("Математика", 3); // Получил 3 в первом семестре
        sem1.addExamGrade("Физика", 5);
        sem1.addRegularCredit("Физра", true);

        Semester sem2 = gb.initSemestersArrayForTest().get(1);
        sem2.addExamGrade("Математика", 5); // Исправил (итоговая оценка)
        sem2.addExamGrade("Программирование", 5);
        sem2.addExamGrade("Алгебра", 4); // 75% пятерок (3 из 4 предметов)
        sem2.addRegularCredit("История", true);

        assertTrue(gb.canGetRedDiploma());
    }

    @Test
    public void testCanGetRedDiploma_Forecast_SuccessWithOverwrittenGrade2() {
        GradeBook gb = new GradeBook(8);

        // 1-й семестр: FIRST(1, 3, 3, 3)
        Semester sem1 = gb.initSemestersArrayForTest().get(0);
        sem1.addExamGrade("Математика", 3); // Получил 3
        sem1.addRegularCredit("Физра", true);

        // 2-й семестр: SECOND(2, 3, 3, 2)
        Semester sem2 = gb.initSemestersArrayForTest().get(1);
        sem2.addExamGrade("Математика", 5); // Исправил предмет "Математика" (перезапись в Map)
        sem2.addExamGrade("Программирование", 5);
        sem2.addExamGrade("Алгебра", 4); // Итого: 3 оценки (5, 5, 4). Доля пятерок 66.6% < 75%
        sem2.addRegularCredit("Право", true);

        // Добавим еще пятерок в 4-м семестре, чтобы добить 75%
        // FOURTH(4, 5, 5, 0)
        Semester sem4 = gb.initSemestersArrayForTest().get(3);
        sem4.addExamGrade("Базы данных", 5); // Теперь оценок 4: (5, 5, 4, 5). Пятерок 3/4 = 75%

        // Восьмой семестр: ставим оценку за ВКР на 5
        gb.setThesisGrade(5);

        assertTrue(gb.canGetRedDiploma());
    }
}
