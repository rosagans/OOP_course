package mathExpression;
import java.util.Scanner;

public class Main {

    /**
     * Точка входа в программу. Запускает бесконечный цикл чтения выражений из консоли.
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Введите выражение:");
            try {
                Expression e = Expression.makeExpression(scanner.next());
                e.print();
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }
    }
}
