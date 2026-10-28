package gradebook;

import java.util.*;
/**
 * Класс объекта семестра.
 */
public class Semester {

    /**
     * List, состоящий из пар: (предмет, оценка),
     * где предмет это либо экзамен, либо дифф зачет, либо ВКР, либо зачет.
     * В случае обычного зачета оценка = сдал или не сдал.
     */
    private final List<GradeRecord> RecordList;

    /**
     * Конструктор объекта.
     * Создает пустую коллекцию записей
     */
    public Semester() {
        this.RecordList = new ArrayList<>();
    }

    /**
     * Добавление новой записи в RecordList;
     *
     * @param record новая запись
     */
    public void addRecord(GradeRecord record) {
        RecordList.add(record);
    }

    /**
     * Геттер RecordList.
     *
     *
     * @return неизменяемый список RecordList
     */
    public  List<GradeRecord> getRecordList() {
        return Collections.unmodifiableList(RecordList);
    }
}
