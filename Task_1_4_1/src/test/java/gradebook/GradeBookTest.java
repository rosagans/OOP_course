package gradebook;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GradeBookTest {

    // Вспомогательный метод для удобного создания пустых семестров
    private List<Semester> createSemesters(int count) {
        List<Semester> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(new Semester());
        }
        return list;
    }

    // --- 1. Тестирование подсчета среднего балла ---
    @Test
    public void testCalculateAverageGrade() {
        List<Semester> semesters = createSemesters(2);

        // 1 семестр
        semesters.get(0).addRecord(new ExamRecord("Математика", Grade.FIVE));
        semesters.get(0).addRecord(new DiffCreditRecord("Английский", Grade.FOUR));

        // 2 семестр
        semesters.get(1).addRecord(new ExamRecord("Физика", Grade.FOUR));

        GradeBook gb = new GradeBook(2, semesters);

        assertEquals(4.333, gb.calculateAverageGrade(), 0.01);
    }

    @Test
    public void testEmptyAverageGradeIsZero() {
        List<Semester> semesters = createSemesters(1);
        GradeBook gb = new GradeBook(1, semesters);
        assertEquals(0.0, gb.calculateAverageGrade());
    }

    // --- 2. Тестирование перехода на бюджет ---
    @Test
    public void testCanTransferToBudget_Fail_DueToExam3() {
        List<Semester> semesters = createSemesters(2);

        semesters.get(1).addRecord(new ExamRecord("Математика", Grade.THREE));
        semesters.get(1).addRecord(new RegularCreditRecord("Физра", true));

        GradeBook gb = new GradeBook(2, semesters);

        assertFalse(gb.canTransferToBudget());
    }

    @Test
    public void testCanTransferToBudget_Fail_FirstSemester() {
        List<Semester> semesters = createSemesters(1);
        GradeBook gb = new GradeBook(1, semesters);

        assertFalse(gb.canTransferToBudget());
    }

    @Test
    public void testCanTransferToBudget_Success() {
        List<Semester> semesters = createSemesters(2);

        // 1-й семестр
        semesters.get(0).addRecord(new ExamRecord("Линейная алгебра", Grade.FOUR));
        semesters.get(0).addRecord(new RegularCreditRecord("Физра", true));

        // 2-й семестр
        semesters.get(1).addRecord(new ExamRecord("Математический анализ", Grade.FIVE));
        semesters.get(1).addRecord(new DiffCreditRecord("Английский", Grade.THREE));
        semesters.get(1).addRecord(new RegularCreditRecord("История", true));

        GradeBook gb = new GradeBook(2, semesters);

        assertTrue(gb.canTransferToBudget());
    }

    // --- 3. Тестирование повышенной стипендии ---
    @Test
    public void testCanGetIncreasedScholarship_Success1() {
        List<Semester> semesters = createSemesters(2);

        semesters.get(0).addRecord(new ExamRecord("Математика", Grade.FIVE));
        semesters.get(0).addRecord(new DiffCreditRecord("Английский", Grade.FIVE));
        semesters.get(0).addRecord(new RegularCreditRecord("Физра", true));

        GradeBook gb = new GradeBook(2, semesters);

        assertTrue(gb.canGetIncreasedScholarship());
    }

    @Test
    public void testCanGetIncreasedScholarship_Fail_MissingCredit() {
        List<Semester> semesters = createSemesters(2);

        semesters.get(0).addRecord(new ExamRecord("Математика", Grade.FIVE));
        semesters.get(0).addRecord(new RegularCreditRecord("Физра", false));

        GradeBook gb = new GradeBook(2, semesters);

        assertFalse(gb.canGetIncreasedScholarship());
    }

    @Test
    public void testCanGetIncreasedScholarship_Success2() {
        List<Semester> semesters = createSemesters(2);

        semesters.get(0).addRecord(new ExamRecord("Математика", Grade.FIVE));
        semesters.get(0).addRecord(new DiffCreditRecord("Английский", Grade.FIVE));
        semesters.get(0).addRecord(new RegularCreditRecord("Физра", true));
        semesters.get(0).addRecord(new RegularCreditRecord("БЖД", true));

        GradeBook gb = new GradeBook(2, semesters);

        assertTrue(gb.canGetIncreasedScholarship());
    }

    // --- 4. Тестирование красного диплома ---
    @Test
    public void testCanGetRedDiploma_Forecast_SuccessWithOverwrittenGrade1() {
        List<Semester> semesters = createSemesters(2);

        // 1-й семестр
        semesters.get(0).addRecord(new ExamRecord("Математика", Grade.THREE));
        semesters.get(0).addRecord(new ExamRecord("Физика", Grade.FIVE));
        semesters.get(0).addRecord(new RegularCreditRecord("Физра", true));

        // 2-й семестр
        semesters.get(1).addRecord(new ExamRecord("Математика", Grade.FIVE));
        semesters.get(1).addRecord(new ExamRecord("Программирование", Grade.FIVE));
        semesters.get(1).addRecord(new ExamRecord("Алгебра", Grade.FOUR));
        semesters.get(1).addRecord(new RegularCreditRecord("История", true));

        GradeBook gb = new GradeBook(2, semesters);

        assertTrue(gb.canGetRedDiploma());
    }

    @Test
    public void testCanGetRedDiploma_Forecast_SuccessWithOverwrittenGrade2() {
        List<Semester> semesters = createSemesters(8);

        // 1-й семестр
        semesters.get(0).addRecord(new ExamRecord("Математика", Grade.THREE));
        semesters.get(0).addRecord(new RegularCreditRecord("Физра", true));

        // 2-й семестр
        semesters.get(1).addRecord(new ExamRecord("Математика", Grade.FIVE));
        semesters.get(1).addRecord(new ExamRecord("Программирование", Grade.FIVE));
        semesters.get(1).addRecord(new ExamRecord("Алгебра", Grade.FOUR));
        semesters.get(1).addRecord(new RegularCreditRecord("Право", true));

        // 4-й семестр
        semesters.get(3).addRecord(new ExamRecord("Базы данных", Grade.FIVE));

        // 8-й семестр: ставим оценку за ВКР на 5
        semesters.get(7).addRecord(new VkrRecord("ВКР", Grade.FIVE));

        GradeBook gb = new GradeBook(8, semesters);

        assertTrue(gb.canGetRedDiploma());
    }

    @Test
    public void testCanGetRedDiploma_Fail_HasGradeThree() {
        List<Semester> semesters = createSemesters(2);

        semesters.get(0).addRecord(new ExamRecord("Математика", Grade.THREE)); // Тройка осталась
        semesters.get(0).addRecord(new ExamRecord("Физика", Grade.FIVE));
        semesters.get(0).addRecord(new RegularCreditRecord("Физра", true));

        GradeBook gb = new GradeBook(2, semesters);

        assertFalse(gb.canGetRedDiploma());
    }

    @Test
    public void testCanGetRedDiploma_EighthSemester_Fail_VkrNotFive() {
        List<Semester> semesters = createSemesters(8);

        semesters.get(0).addRecord(new ExamRecord("Математика", Grade.FIVE));
        semesters.get(0).addRecord(new RegularCreditRecord("Физра", true));
        semesters.get(7).addRecord(new VkrRecord("ВКР", Grade.FOUR)); // Оценка 4 за ВКР

        GradeBook gb = new GradeBook(8, semesters);

        assertFalse(gb.canGetRedDiploma());
    }
}