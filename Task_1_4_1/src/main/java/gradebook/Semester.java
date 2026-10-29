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
    private final List<Record> recordList;

    /**
     * Конструктор объекта.
     * Создает пустую коллекцию записей
     */
    public Semester() {
        this.recordList = new ArrayList<>();
    }

    /**
     * Добавление новой записи в RecordList.
     *
     * @param record новая запись
     */
    public void addRecord(Record record) {
        recordList.add(record);
    }

    /**
     * Геттер RecordList.
     *
     *
     * @return неизменяемый список RecordList
     */
    public  List<Record> getRecordList() {
        return Collections.unmodifiableList(recordList);
    }
}
