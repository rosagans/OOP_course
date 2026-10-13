package mathexpression;

import java.util.Scanner;

/**
 * Главный класс приложения для интерактивного ввода и разбора математических выражений.
 */
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
